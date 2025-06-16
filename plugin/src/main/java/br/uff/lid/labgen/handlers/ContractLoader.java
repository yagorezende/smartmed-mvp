package br.uff.lid.labgen.handlers;

import br.uff.lid.labgen.blockchain.KeycloakABACEvents;
import br.uff.lid.labgen.policy.contracts.PolicyEvaluationPoint;
import br.uff.lid.labgen.util.SmartMedGasProvider;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ContractLoader {
    // The field must be declared volatile so that double check lock would work
    // correctly.
    private static volatile ContractLoader instance;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ContractLoader.class);
    private static final String chainAddress = System.getenv("SM_CHAIN_ADDRESS");
    private static final String credentialsPK = System.getenv("SM_CREDENTIALS_PK");
    private final Web3j web3j = Web3j.build(new HttpService(chainAddress));
    private static final String TAG = "ContractLoader";

    private Credentials loadCredentials() {
        try {
            log.info("Connected to Ethereum client version: " + this.web3j.web3ClientVersion().send().getWeb3ClientVersion());
            return Credentials.create(credentialsPK);
        } catch (IOException e) {
            log.error(TAG + "[Credentials]", e);
            return null;
        }
    }

    private final Credentials credentials;
//    private final DefaultGasProvider contractGasProvider = new SmartMedGasProvider(BigInteger.valueOf(0), BigInteger.valueOf(9007199254740991L));
    private final DefaultGasProvider contractGasProvider = new SmartMedGasProvider();
    private final Map<String, SmartMedContract> contracts;

    private final Map<String, Class<?>> contractClasses = Map.of(
            "KeycloakABACEvents", KeycloakABACEvents.class,
            "PolicyEvaluationPoint", PolicyEvaluationPoint.class
    );

    private ContractLoader() {
        this.credentials = loadCredentials();
        this.contracts = new HashMap<>();
    }

    public static ContractLoader getInstance() {
        // The approach taken here is called double-checked locking (DCL). It
        // exists to prevent race condition between multiple threads that may
        // attempt to get singleton instance at the same time, creating separate
        // instances as a result.
        //
        // It may seem that having the `result` variable here is completely
        // pointless. There is, however, a very important caveat when
        // implementing double-checked locking in Java, which is solved by
        // introducing this local variable.
        //
        // You can read more info DCL issues in Java here:
        // https://refactoring.guru/java-dcl-issue
        ContractLoader result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ContractLoader.class) {
            if (instance == null) {
                instance = new ContractLoader();
            }
            return instance;
        }
    }

    public Web3j getWeb3j() {
        return this.web3j;
    }

    public void loadContract(String contractAddress, String contractName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (credentials != null) {
            log.info("Loading smart contract");
            Method load = contractClasses.get(contractName)
                    .getDeclaredMethod("load",
                            String.class, Web3j.class, TransactionManager.class,
                            ContractGasProvider.class
                    );

            TransactionManager transactionManager = new org.web3j.tx.RawTransactionManager(web3j, credentials, 40, 500);

            SmartMedContract contract = (SmartMedContract) load.invoke(
                    null,
                    contractAddress,
                    this.web3j, transactionManager,
                    contractGasProvider
            );
            this.contracts.put(contractAddress, contract);
        } else {
            log.error(TAG + "[loadContract] - Cannot load contract " + contractName + " without credentials");
        }
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public SmartMedContract getContract(String contractAddress) {
        return this.contracts.get(contractAddress);
    }
}
