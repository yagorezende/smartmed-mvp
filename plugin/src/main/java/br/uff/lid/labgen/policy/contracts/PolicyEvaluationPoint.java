package br.uff.lid.labgen.policy.contracts;

import br.uff.lid.labgen.handlers.SmartMedContract;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

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
public class PolicyEvaluationPoint extends SmartMedContract {
    public static final String BINARY = "608060405234801561000f575f80fd5b505f80546001600160a01b0319163317905561035a8061002e5f395ff3fe608060405234801561000f575f80fd5b506004361061003f575f3560e01c80638da5cb5b146100435780639e4bdcdc14610072578063df000b14146100a4575b5f80fd5b5f54610055906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b6100a26100803660046101ce565b600280546001600160a01b0319166001600160a01b0392909216919091179055565b005b6100b76100b236600461020f565b6100c7565b6040519015158152602001610069565b600254600180546001600160a01b0319166001600160a01b03909216918217905560405163fb78fef160e01b81525f919063fb78fef19061010c9085906004016102ba565b6020604051808303815f875af1158015610128573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061014c9190610305565b15610190577f5957d84c2c4a2a3f3f6fc3a42f4f7f25f355f7a5757f08473f949cfe778e82b68260405161018091906102ba565b60405180910390a1506001919050565b7f314b627c53a07309a571871314d42700390a7e4c3f874e86cf2385ab62cb8021826040516101bf91906102ba565b60405180910390a1505f919050565b5f602082840312156101de575f80fd5b81356001600160a01b03811681146101f4575f80fd5b9392505050565b634e487b7160e01b5f52604160045260245ffd5b5f6020828403121561021f575f80fd5b813567ffffffffffffffff80821115610236575f80fd5b818401915084601f830112610249575f80fd5b81358181111561025b5761025b6101fb565b604051601f8201601f19908116603f01168101908382118183101715610283576102836101fb565b8160405282815287602084870101111561029b575f80fd5b826020860160208301375f928101602001929092525095945050505050565b5f6020808352835180828501525f5b818110156102e5578581018301518582016040015282016102c9565b505f604082860101526040601f19601f8301168501019250505092915050565b5f60208284031215610315575f80fd5b815180151581146101f4575f80fdfea26469706673582212203a93091a22d7de2fe3cf37e4401423c12325b2d80dd48aa93cb21fc4d47d91db64736f6c63430008140033";

    public static final String FUNC_EVALUATEREQUEST = "evaluateRequest";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETPAPCONTRACTADDR = "setPAPContractAddr";

    public static final Event DENIED_EVENT = new Event("denied", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event PERMITTED_EVENT = new Event("permitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected PolicyEvaluationPoint(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PolicyEvaluationPoint(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PolicyEvaluationPoint(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PolicyEvaluationPoint(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<DeniedEventResponse> getDeniedEvents(TransactionReceipt transactionReceipt) {
//        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DENIED_EVENT, transactionReceipt);
//        ArrayList<DeniedEventResponse> responses = new ArrayList<DeniedEventResponse>(valueList.size());
//        for (EventValuesWithLog eventValues : valueList) {
//            DeniedEventResponse typedResponse = new DeniedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse._email = (String) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._email = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DeniedEventResponse> deniedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DENIED_EVENT));
        return deniedEventFlowable(filter);
    }

//    public static List<PermittedEventResponse> getPermittedEvents(TransactionReceipt transactionReceipt) {
//        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PERMITTED_EVENT, transactionReceipt);
//        ArrayList<PermittedEventResponse> responses = new ArrayList<PermittedEventResponse>(valueList.size());
//        for (EventValuesWithLog eventValues : valueList) {
//            PermittedEventResponse typedResponse = new PermittedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse._email = (String) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._email = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PermittedEventResponse> permittedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PERMITTED_EVENT));
        return permittedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> evaluateRequest(String email) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EVALUATEREQUEST, 
                Arrays.<Type>asList(new Utf8String(email)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setPAPContractAddr(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPAPCONTRACTADDR, 
                Arrays.<Type>asList(new Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PolicyEvaluationPoint load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PolicyEvaluationPoint(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PolicyEvaluationPoint load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PolicyEvaluationPoint(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PolicyEvaluationPoint load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PolicyEvaluationPoint(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PolicyEvaluationPoint load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PolicyEvaluationPoint(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PolicyEvaluationPoint> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PolicyEvaluationPoint.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PolicyEvaluationPoint> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PolicyEvaluationPoint.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<PolicyEvaluationPoint> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PolicyEvaluationPoint.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PolicyEvaluationPoint> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PolicyEvaluationPoint.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DeniedEventResponse extends BaseEventResponse {
        public String _email;
    }

    public static class PermittedEventResponse extends BaseEventResponse {
        public String _email;
    }
}
