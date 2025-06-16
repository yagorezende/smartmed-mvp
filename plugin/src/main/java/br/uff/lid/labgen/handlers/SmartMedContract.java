package br.uff.lid.labgen.handlers;

import br.uff.lid.labgen.blockchain.KeycloakLogs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.ens.EnsResolver;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SmartMedContract extends Contract {

    private Map<String, String> functionSignatures;

    protected SmartMedContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasProvider);
    }

    protected SmartMedContract(EnsResolver ensResolver, String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(ensResolver, contractBinary, contractAddress, web3j, transactionManager, gasProvider);
    }

    protected SmartMedContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, gasProvider);
    }

    protected SmartMedContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartMedContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmartMedContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartMedContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static String loadFunctionSignatureFromABI(String abiPath, String functionName) throws Exception {
        // read JSON file
        ObjectMapper mapper = new ObjectMapper();

        // get file from resource as InputStream
        ClassLoader classLoader = SmartMedContract.class.getClassLoader();
        System.out.println("classLoader: " + classLoader);
        InputStream inputStream = classLoader.getResourceAsStream(abiPath);



        List<Map<String, Object>> abi = mapper.readValue(inputStream, List.class);

        String functionSignature = null;
        for (Map<String, Object> entry : abi) {
            if (entry.get("name").equals(functionName)) {
                StringBuilder result = new StringBuilder();
                result.append(functionName);
                result.append("(");
                ArrayList<LinkedHashMap<String, String>> parameters = (ArrayList<LinkedHashMap<String, String>>) entry.get("inputs");
                String params = parameters.stream().map(p -> {
                    return (String) p.get("type");
                }).collect(Collectors.joining(","));
                result.append(params);
                result.append(")");

                byte[] input = result.toString().getBytes();
                byte[] hash = Hash.sha3(input);
                return Numeric.toHexString(hash).substring(0, 10);
            }
        }
        throw  new Exception("Function signature not found");
    }

    public static Contract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return null;
    }

    @Deprecated
    public static Contract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return null;
    }

    public static Contract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return null;
    }

    public static Contract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return null;
    }
}
