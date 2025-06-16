package br.uff.lid.labgen.policy.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
public class PolicyAdministrationPoint extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b505f80546001600160a01b031916331790556104ec8061002e5f395ff3fe608060405234801561000f575f80fd5b506004361061003f575f3560e01c80633a96fdd7146100435780638da5cb5b1461006b578063fb78fef114610095575b5f80fd5b61005661005136600461031c565b6100a8565b60405190151581526020015b60405180910390f35b5f5461007d906001600160a01b031681565b6040516001600160a01b039091168152602001610062565b6100566100a336600461037c565b61010d565b5f816040516020016100ba919061040a565b60405160208183030381529060405280519060200120836040516020016100e1919061040a565b604051602081830303815290604052805190602001200361010457506001610107565b505f5b92915050565b5f8083838080601f0160208091040260200160405190810160405280939291908181526020018383808284375f9201829052509394505050505b815181101561022a575f82828151811061016357610163610425565b01602001516001600160f81b0319169050600160fe1b819003610217575f61018e8787858751610234565b90507fa759c265bccdecaf866b3737ba19bcc5ee332a985905acd365483815147634f9816040516101bf9190610439565b60405180910390a16101f9816040518060400160405280601081526020016f2036b4b234b0b1b7b6973ab33317313960811b8152506100a8565b1561020b576001945050505050610107565b5f945050505050610107565b50806102228161046b565b915050610147565b505f949350505050565b60606102428284868861048f565b8080601f0160208091040260200160405190810160405280939291908181526020018383808284375f920191909152509298975050505050505050565b634e487b7160e01b5f52604160045260245ffd5b5f82601f8301126102a2575f80fd5b813567ffffffffffffffff808211156102bd576102bd61027f565b604051601f8301601f19908116603f011681019082821181831017156102e5576102e561027f565b816040528381528660208588010111156102fd575f80fd5b836020870160208301375f602085830101528094505050505092915050565b5f806040838503121561032d575f80fd5b823567ffffffffffffffff80821115610344575f80fd5b61035086838701610293565b93506020850135915080821115610365575f80fd5b5061037285828601610293565b9150509250929050565b5f806020838503121561038d575f80fd5b823567ffffffffffffffff808211156103a4575f80fd5b818501915085601f8301126103b7575f80fd5b8135818111156103c5575f80fd5b8660208285010111156103d6575f80fd5b60209290920196919550909350505050565b5f5b838110156104025781810151838201526020016103ea565b50505f910152565b5f825161041b8184602087016103e8565b9190910192915050565b634e487b7160e01b5f52603260045260245ffd5b602081525f82518060208401526104578160408501602087016103e8565b601f01601f19169190910160400192915050565b5f6001820161048857634e487b7160e01b5f52601160045260245ffd5b5060010190565b5f808585111561049d575f80fd5b838611156104a9575f80fd5b505082019391909203915056fea26469706673582212200d17042551052d585863237763d36e81edf6ff7987804102cd5d7092ea67045664736f6c63430008140033";

    public static final String FUNC_COMPARE = "compare";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_VALIDATEDOMAIN = "validateDomain";

    public static final Event DENIED_EVENT = new Event("denied", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event EVALUATEDDOMAIN_EVENT = new Event("evaluatedDomain", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event PERMITTED_EVENT = new Event("permitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected PolicyAdministrationPoint(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PolicyAdministrationPoint(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PolicyAdministrationPoint(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PolicyAdministrationPoint(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<DeniedEventResponse> getDeniedEvents(TransactionReceipt transactionReceipt) {
//        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DENIED_EVENT, transactionReceipt);
//        ArrayList<DeniedEventResponse> responses = new ArrayList<DeniedEventResponse>(valueList.size());
//        for (EventValuesWithLog eventValues : valueList) {
//            DeniedEventResponse typedResponse = new DeniedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse._domain = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public Flowable<DeniedEventResponse> deniedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DeniedEventResponse>() {
            @Override
            public DeniedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DENIED_EVENT, log);
                DeniedEventResponse typedResponse = new DeniedEventResponse();
                typedResponse.log = log;
                typedResponse._domain = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DeniedEventResponse> deniedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DENIED_EVENT));
        return deniedEventFlowable(filter);
    }

//    public static List<EvaluatedDomainEventResponse> getEvaluatedDomainEvents(TransactionReceipt transactionReceipt) {
//        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EVALUATEDDOMAIN_EVENT, transactionReceipt);
//        ArrayList<EvaluatedDomainEventResponse> responses = new ArrayList<EvaluatedDomainEventResponse>(valueList.size());
//        for (EventValuesWithLog eventValues : valueList) {
//            EvaluatedDomainEventResponse typedResponse = new EvaluatedDomainEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse._domain = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public Flowable<EvaluatedDomainEventResponse> evaluatedDomainEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, EvaluatedDomainEventResponse>() {
            @Override
            public EvaluatedDomainEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(EVALUATEDDOMAIN_EVENT, log);
                EvaluatedDomainEventResponse typedResponse = new EvaluatedDomainEventResponse();
                typedResponse.log = log;
                typedResponse._domain = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<EvaluatedDomainEventResponse> evaluatedDomainEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVALUATEDDOMAIN_EVENT));
        return evaluatedDomainEventFlowable(filter);
    }

//    public static List<PermittedEventResponse> getPermittedEvents(TransactionReceipt transactionReceipt) {
//        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PERMITTED_EVENT, transactionReceipt);
//        ArrayList<PermittedEventResponse> responses = new ArrayList<PermittedEventResponse>(valueList.size());
//        for (EventValuesWithLog eventValues : valueList) {
//            PermittedEventResponse typedResponse = new PermittedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse._domain = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public Flowable<PermittedEventResponse> permittedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PermittedEventResponse>() {
            @Override
            public PermittedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(PERMITTED_EVENT, log);
                PermittedEventResponse typedResponse = new PermittedEventResponse();
                typedResponse.log = log;
                typedResponse._domain = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PermittedEventResponse> permittedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PERMITTED_EVENT));
        return permittedEventFlowable(filter);
    }

    public RemoteFunctionCall<Boolean> compare(String domain, String comparison) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COMPARE, 
                Arrays.<Type>asList(new Utf8String(domain),
                new Utf8String(comparison)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> validateDomain(String str) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_VALIDATEDOMAIN, 
                Arrays.<Type>asList(new Utf8String(str)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PolicyAdministrationPoint load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PolicyAdministrationPoint(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PolicyAdministrationPoint load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PolicyAdministrationPoint(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PolicyAdministrationPoint load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PolicyAdministrationPoint(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PolicyAdministrationPoint load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PolicyAdministrationPoint(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PolicyAdministrationPoint> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PolicyAdministrationPoint.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PolicyAdministrationPoint> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PolicyAdministrationPoint.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<PolicyAdministrationPoint> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PolicyAdministrationPoint.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PolicyAdministrationPoint> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PolicyAdministrationPoint.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DeniedEventResponse extends BaseEventResponse {
        public String _domain;
    }

    public static class EvaluatedDomainEventResponse extends BaseEventResponse {
        public String _domain;
    }

    public static class PermittedEventResponse extends BaseEventResponse {
        public String _domain;
    }
}
