package br.uff.lid.labgen.policy;

import br.uff.lid.labgen.handlers.ContractLoader;
import br.uff.lid.labgen.policy.contracts.PolicyEvaluationPoint;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;

import static br.uff.lid.labgen.handlers.SmartMedContract.loadFunctionSignatureFromABI;

public class SimplePolicy {
    private static final String keystore = System.getenv("SM_KEY_STORE_PATH");
    private static final String keyFile = System.getenv("SM_KEY_FILE_NAME");
    private static final String chainAddress = System.getenv("SM_CHAIN_ADDRESS");
    private static final String contractAddress = System.getenv("SM_EVALUATION_POINT_ADDRESS");
    private final Web3j web3j = Web3j.build(new HttpService(chainAddress));
    public static final String TAG = "SimplePolicy";
    private final ContractLoader contractLoader;
    public static final String PERMITTED = "0x5957d84c2c4a2a3f3f6fc3a42f4f7f25f355f7a5757f08473f949cfe778e82b6";
    final String SM_EVALUATION_POINT_ABI = "contracts/PolicyEvaluationPoint.abi";
    final String SM_EVALUATION_POINT_ADDRESS = System.getenv("SM_EVALUATION_POINT_ADDRESS");
    final String SM_ABAC_EVENTS_ADDRESS = System.getenv("SM_ABAC_EVENTS_ADDRESS");
    private String functionSignature;

    public SimplePolicy() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        contractLoader = ContractLoader.getInstance();
        try {
            functionSignature = loadFunctionSignatureFromABI(SM_EVALUATION_POINT_ABI, "evaluateRequest");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        contractLoader.loadContract(SM_EVALUATION_POINT_ADDRESS, "PolicyEvaluationPoint");
//        contractLoader.loadContract(SM_ABAC_EVENTS_ADDRESS, "KeycloakABACEvents");
        System.out.println(TAG + "[SimplePolicy] - chainAddress: " + chainAddress + " contractAddress: " + contractAddress);
    }

    public boolean rawValidate(String email) throws Exception {
        ContractLoader contractLoader = ContractLoader.getInstance();

        var credentials = contractLoader.getCredentials();
        var w3 = contractLoader.getWeb3j();

        RawTransactionManager transactionManager = new RawTransactionManager(
                w3,
                credentials,
                w3.ethChainId().getId(),
                new NoOpProcessor(w3)
        );

        // Prepare the raw transaction
        BigInteger nonce = w3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getTransactionCount();
        BigInteger gasPrice = w3.ethGasPrice().send().getGasPrice();
        BigInteger gasLimit = BigInteger.valueOf(3141592L); // Check the gas limit in the genesis block
        BigInteger value = BigInteger.ZERO;

        byte[] dataBytes = email.getBytes();
        // pad data to 32 bytes
        byte[] dataPadding = new byte[32];
        System.arraycopy(dataBytes, 0, dataPadding, 0, dataBytes.length);


        byte[] dataFull = new byte[100];
        System.arraycopy(dataPadding, 0, dataFull, 100-dataPadding.length, dataPadding.length);
        // change the value in the position before the padding to add the data length
        dataFull[99-dataPadding.length] = (byte) dataBytes.length;

        String dataHex = Numeric.toHexString(dataFull);

        // convert function signature to bytes
        byte[] functionSignatureBytes = Numeric.hexStringToByteArray(functionSignature);
        // add the function signature to the beginning of the data
        System.arraycopy(functionSignatureBytes, 0, dataFull, 0, functionSignatureBytes.length);
        dataFull[35] = (byte) 32;

        String mounted_input = Numeric.toHexString(dataFull);


        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                SM_EVALUATION_POINT_ADDRESS,
                value,
                mounted_input
        );

        // Sign the transaction
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        // Send the transaction
        String transactionHash = w3.ethSendRawTransaction(hexValue).send().getTransactionHash();

        System.out.printf("Transaction hash: %s\n", transactionHash);

        // get the transaction receipt
        EthGetTransactionReceipt wreceipt = w3.ethGetTransactionReceipt(transactionHash).send();

        // await transaction receipt
        while (wreceipt.getTransactionReceipt().isEmpty()) {
            Thread.sleep(1);
            wreceipt = w3.ethGetTransactionReceipt(transactionHash).send();
        }

        TransactionReceipt receipt = wreceipt.getTransactionReceipt().get();
        System.out.println("[publish] - Transaction complete¹:  " + receipt.getTransactionHash());
        System.out.println("[publish] - Transaction complete²:  " + receipt.getLogs().toString());
        AtomicBoolean permitted = new AtomicBoolean(false);
        receipt.getLogs().forEach(transactionLog -> {
            transactionLog.getTopics().forEach(topic -> {
                System.out.println("[publish] - Topic:  " + topic);
                if (topic.equals(SimplePolicy.PERMITTED)) {
                    permitted.set(true);
                }
            });
        });
        return permitted.get();
    }

    public boolean validate(String email) throws Exception {
        if (contractLoader.getCredentials() != null) {
            System.out.println("Loading permision contract");
            PolicyEvaluationPoint contract = (PolicyEvaluationPoint) contractLoader.getContract(contractAddress);
            TransactionReceipt transactionReceipt = contract.evaluateRequest(email).send();
            System.out.println(TAG + "[publish] - Transaction complete¹:  " + transactionReceipt.getTransactionHash());
            System.out.println(TAG + "[publish] - Transaction complete²:  " + transactionReceipt.getLogs().toString());
            AtomicBoolean permitted = new AtomicBoolean(false);
            transactionReceipt.getLogs().forEach(transactionLog -> {
                transactionLog.getTopics().forEach(topic -> {
                    System.out.println(TAG + "[publish] - Topic:  " + topic);
                    if (topic.equals(PERMITTED)) {
                        permitted.set(true);
                    }
                });
            });
            return permitted.get();
        }
        return false;
    }

}
