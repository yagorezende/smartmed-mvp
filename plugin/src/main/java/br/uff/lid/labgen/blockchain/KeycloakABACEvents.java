package br.uff.lid.labgen.blockchain;

import br.uff.lid.labgen.handlers.SmartMedContract;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class KeycloakABACEvents extends SmartMedContract {
    public static final String BINARY = "608060405234801561000f575f80fd5b505f80546001600160a01b031916331790556101f58061002e5f395ff3fe608060405234801561000f575f80fd5b5060043610610034575f3560e01c80634a81c695146100385780638da5cb5b1461004d575b5f80fd5b61004b6100463660046100c9565b61007b565b005b5f5461005f906001600160a01b031681565b6040516001600160a01b03909116815260200160405180910390f35b7fd0c80e1e152a691434f871b506da980c557636c7a6b8cc7428222c8c50c341c8816040516100aa9190610174565b60405180910390a150565b634e487b7160e01b5f52604160045260245ffd5b5f602082840312156100d9575f80fd5b813567ffffffffffffffff808211156100f0575f80fd5b818401915084601f830112610103575f80fd5b813581811115610115576101156100b5565b604051601f8201601f19908116603f0116810190838211818310171561013d5761013d6100b5565b81604052828152876020848701011115610155575f80fd5b826020860160208301375f928101602001929092525095945050505050565b5f6020808352835180828501525f5b8181101561019f57858101830151858201604001528201610183565b505f604082860101526040601f19601f830116850101925050509291505056fea2646970667358221220dcbdd24419c6f00fd9b7a3431da70d7bb44fc811feaa7f5e9165f69b4e9780a164736f6c63430008140033";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SAVEKEYCLOAKABACEVENT = "saveKeycloakABACEvent";

    public static final Event ABACEVENT_EVENT = new Event("ABACEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected KeycloakABACEvents(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected KeycloakABACEvents(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected KeycloakABACEvents(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected KeycloakABACEvents(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<ABACEventEventResponse> getABACEventEvents(TransactionReceipt transactionReceipt) {
//        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ABACEVENT_EVENT, transactionReceipt);
//        ArrayList<ABACEventEventResponse> responses = new ArrayList<ABACEventEventResponse>(valueList.size());
//        for (EventValuesWithLog eventValues : valueList) {
//            ABACEventEventResponse typedResponse = new ABACEventEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse._event = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public Flowable<ABACEventEventResponse> aBACEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ABACEventEventResponse>() {
            @Override
            public ABACEventEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ABACEVENT_EVENT, log);
                ABACEventEventResponse typedResponse = new ABACEventEventResponse();
                typedResponse.log = log;
                typedResponse._event = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ABACEventEventResponse> aBACEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ABACEVENT_EVENT));
        return aBACEventEventFlowable(filter);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> saveKeycloakABACEvent(String _event) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAVEKEYCLOAKABACEVENT, 
                Arrays.<Type>asList(new Utf8String(_event)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static KeycloakABACEvents load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new KeycloakABACEvents(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static KeycloakABACEvents load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new KeycloakABACEvents(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static KeycloakABACEvents load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new KeycloakABACEvents(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static KeycloakABACEvents load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new KeycloakABACEvents(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<KeycloakABACEvents> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(KeycloakABACEvents.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<KeycloakABACEvents> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(KeycloakABACEvents.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<KeycloakABACEvents> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(KeycloakABACEvents.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<KeycloakABACEvents> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(KeycloakABACEvents.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ABACEventEventResponse extends BaseEventResponse {
        public String _event;
    }
}
