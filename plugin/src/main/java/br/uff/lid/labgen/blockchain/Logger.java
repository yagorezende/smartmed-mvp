package br.uff.lid.labgen.blockchain;

import br.uff.lid.labgen.handlers.ContractLoader;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;

public class Logger {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Logger.class);
    private static final String keystore = System.getenv("SM_KEY_STORE_PATH");
    private static final String keyFile = System.getenv("SM_KEY_FILE_NAME");
    private static final String chainAddress = System.getenv("SM_CHAIN_ADDRESS");
    private static final String credentialsPassword = System.getenv("SM_CREDENTIALS_PASSWORD");
    public static final String contractAddress = System.getenv("SM_CONTRACT_ADDRESS");
    private final Web3j web3j = Web3j.build(new HttpService(chainAddress));
    public static final String TAG = "Logger";

    private final ContractLoader contractLoader;

    public Logger() {
        contractLoader = ContractLoader.getInstance();
        log.info(TAG + "[Logger] - keystore: " + keystore + " keyFile: " + keyFile +
                " chainAddress: " + chainAddress + " contractAddress: " + contractAddress);
    }

    public void publish(String message) throws Exception {
        if (contractLoader.getCredentials() != null) {
            TransactionReceipt transactionReceipt = ((KeycloakLogs) contractLoader.getContract(contractAddress)).push(message).send();
            log.info(TAG + "[publish] - Transaction complete:  " + transactionReceipt.getTransactionHash());
        }
    }

    public void asyncPublish(String message) throws Exception {
        // call the publish method in a new thread
        // TODO: change this to use a thread pool
        new Thread(() -> {
            try {
                publish(message);
            } catch (Exception e) {
                log.error(TAG + "[asyncPublish]", e);
            }
        }).start();
    }
}
