// SPDX-License-Identifier: MIT
//// ================================================================ ////
pragma solidity ^0.8.18;

contract Ownable {

  address public owner;

  modifier onlyOwner {
    require(msg.sender == owner, "Denied: You are not the owner.");
    _;
  }

  constructor () {
    owner = msg.sender;
  }
}

contract KeycloakLoginEvents is Ownable {
    
    event Event(
        string _type,         
        uint8 _timestamp, 
        string _client, 
        string _user,
        string _ip_address,
        string _details
    );

    function saveKeycloakEvent(string memory _type, uint8 _timestamp, string memory _client, string memory _user, string memory _ip_address, string memory _details) public{
        emit Event(
            _type, 
            _timestamp,
            _client,
            _user,
            _ip_address, 
            _details
        );
    }
}

contract KeycloakAdminEvents is Ownable {
    
    event Event(
        string _type,         
        uint8 _timestamp, 
        string _operation_type,
        string _resource_type,
        string _realm, 
        string _client, 
        string _user,
        string _ip_address        
    );

    function saveKeycloakEvent(string memory _type, uint8 _timestamp, string memory _operation_type, string memory _resource_type, string memory _realm, string memory _client, string memory _user, string memory _ip_address) public{
        emit Event(
            _type, 
            _timestamp,
            _operation_type,
            _resource_type,
            _realm,
            _client,
            _user,
            _ip_address           
        );
    }
}

contract KeycloakABACEvents is Ownable {
    event ABACEvent (
        string _event
    );

    function saveKeycloakABACEvent(string memory _event) public{
        emit ABACEvent(
            _event       
        );
    }
}

contract PolicyAdministrationPoint is Ownable {
    event evaluatedDomain(
        string _domain
    );

    event permitted(
        string _domain
    );

    event denied(
        string _domain
    );

    function trim(string calldata str, uint start, uint end) internal pure returns(string memory) {
        return str[start:end];
    }

    function validateDomain(string calldata str) public returns (bool){
        bytes memory b = bytes(str);        

        for(uint i; i<b.length; i++){
            bytes1 char = b[i];

            if (char == "@"){
                string memory domain = trim(str, i, b.length);
                emit evaluatedDomain(
                    domain
                );                
                if(compare(domain, "@midiacom.uff.br")){
                    return true;
                }
                return false;
            }            
        }

        return false;
    }
    
    function compare(string memory domain, string memory comparison) public pure returns (bool) {        
        if (keccak256(abi.encodePacked(domain)) == keccak256(abi.encodePacked(comparison))){            
            return true;
        }        
        return false;
    }
}


contract PolicyEvaluationPoint is Ownable {
    event permitted(
        string _email
    );

    event denied(
        string _email
    );

    PolicyAdministrationPoint pap_contract;    
    address pap_contract_addr; 
    function setPAPContractAddr(address _address) public {        
        pap_contract_addr = _address;
        return;
    }

    function evaluateRequest(string memory email) public returns (bool){
        pap_contract = PolicyAdministrationPoint(pap_contract_addr);        
        if (pap_contract.validateDomain(email)){
            emit permitted(email);
            return true;
        }
        emit denied(email);
        return false;
    }
}
