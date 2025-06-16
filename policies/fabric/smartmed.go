package main

import (
	"encoding/json"
	"fmt"
	"strings"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

// SmartContract provides functions for managing ABAC events and policies
type SmartContract struct {
	contractapi.Contract
}

// --- Event Payload Structs ---

// ABACEventPayload structure for KeycloakABACEvents
type ABACEventPayload struct {
	Event string `json:"event"`
}

// PolicyDomainPayload structure for PAP domain events
type PolicyDomainPayload struct {
	Domain string `json:"domain"`
}

// PolicyDecisionPayload structure for PEP decision events
type PolicyDecisionPayload struct {
	Email string `json:"email"`
}

// --- Keycloak ABAC Event Saving Function ---

// SaveKeycloakABACEvent emits an event corresponding to KeycloakABACEvents.ABACEvent
func (s *SmartContract) SaveKeycloakABACEvent(ctx contractapi.TransactionContextInterface, eventDetails string) error {
	payload := ABACEventPayload{
		Event: eventDetails,
	}

	payloadBytes, err := json.Marshal(payload)
	if err != nil {
		return fmt.Errorf("failed to marshal ABAC event payload: %w", err)
	}

	eventName := "keycloakABACEvent" // Define a unique name for the event
	err = ctx.GetStub().SetEvent(eventName, payloadBytes)
	if err != nil {
		return fmt.Errorf("failed to set ABAC event: %w", err)
	}
	fmt.Printf("Event Emitted: %s - Payload: %s\n", eventName, string(payloadBytes))
	return nil
}

// --- Policy Evaluation Functions (Combining PAP and PEP) ---

// validateDomain checks if the domain part of an email matches the target domain.
// This internal function replicates the logic from PolicyAdministrationPoint.validateDomain & compare.
// It also emits the relevant events.
func (s *SmartContract) validateDomain(ctx contractapi.TransactionContextInterface, email string) (bool, error) {
	targetDomain := "@midiacom.uff.br" // Hardcoded target domain

	parts := strings.SplitN(email, "@", 2)
	var domain string
	if len(parts) == 2 && parts[1] != "" {
		domain = "@" + parts[1]
	} else {
		domain = "" // No valid domain found
	}

	// Emit evaluatedDomain event regardless of validity
	evalPayload := PolicyDomainPayload{Domain: domain}
	evalBytes, err := json.Marshal(evalPayload)
	// Log marshalling errors but try to proceed
	if err != nil {
		fmt.Printf("Warning: Failed to marshal evaluatedDomain event payload: %v\n", err)
	} else {
		err = ctx.GetStub().SetEvent("evaluatedDomain", evalBytes)
		// Log SetEvent errors but try to proceed
		if err != nil {
			fmt.Printf("Warning: Failed to set evaluatedDomain event: %v\n", err)
		} else {
			fmt.Printf("Event Emitted: evaluatedDomain - Payload: %s\n", string(evalBytes))
		}
	}


	// Direct string comparison (equivalent to keccak256 comparison for equality in Solidity)
	isValid := (domain == targetDomain)

	// Emit PAP's permitted/denied events based on domain match *within* validation
	// This differs slightly from PEP emitting its own events, but matches PAP's events
	if isValid {
		// PAP Permitted Event
		papPermittedPayload := PolicyDomainPayload{Domain: domain}
		papPermittedBytes, _ := json.Marshal(papPermittedPayload) // Ignore marshal error for event
		ctx.GetStub().SetEvent("papDomainPermitted", papPermittedBytes) // Best effort emit
		fmt.Printf("Event Emitted: papDomainPermitted - Payload: %s\n", string(papPermittedBytes))
	} else {
        // PAP Denied Event
		papDeniedPayload := PolicyDomainPayload{Domain: domain} // Include domain even if denied/empty
		papDeniedBytes, _ := json.Marshal(papDeniedPayload) // Ignore marshal error for event
		ctx.GetStub().SetEvent("papDomainDenied", papDeniedBytes) // Best effort emit
        fmt.Printf("Event Emitted: papDomainDenied - Payload: %s\n", string(papDeniedBytes))
	}


	return isValid, nil
}

// EvaluateRequest checks if an email belongs to the allowed domain.
// This function replicates the logic from PolicyEvaluationPoint.evaluateRequest.
func (s *SmartContract) EvaluateRequest(ctx contractapi.TransactionContextInterface, email string) (bool, error) {
	fmt.Printf("Evaluating request for email: %s\n", email)

	// Call the internal validation logic
	isValid, err := s.validateDomain(ctx, email)
	if err != nil {
		// This shouldn't happen with current validateDomain logic, but handle defensively
		return false, fmt.Errorf("error during domain validation: %w", err)
	}

	// Emit PEP's permitted or denied event based on the overall validation result
	decisionPayload := PolicyDecisionPayload{Email: email}
	decisionBytes, err := json.Marshal(decisionPayload)
	// Log marshalling errors but try to proceed
	if err != nil {
		fmt.Printf("Warning: Failed to marshal policy decision event payload: %v\n", err)
	} else {
		var eventName string
		if isValid {
			eventName = "pepAccessPermitted" // Use distinct name for PEP event
		} else {
			eventName = "pepAccessDenied" // Use distinct name for PEP event
		}
		err = ctx.GetStub().SetEvent(eventName, decisionBytes)
		// Log SetEvent errors but try to proceed
		if err != nil {
			fmt.Printf("Warning: Failed to set %s event: %v\n", eventName, err)
		} else {
			fmt.Printf("Event Emitted: %s - Payload: %s\n", eventName, string(decisionBytes))
		}
	}


	if isValid {
		fmt.Printf("Access Permitted for: %s\n", email)
		return true, nil
	} else {
		fmt.Printf("Access Denied for: %s\n", email)
		return false, nil
	}
}

// --- Main Function ---

func main() {
	chaincode, err := contractapi.NewChaincode(&SmartContract{})
	if err != nil {
		fmt.Printf("Error creating PAP/ABAC chaincode: %s", err.Error())
		return
	}

	if err := chaincode.Start(); err != nil {
		fmt.Printf("Error starting PAP/ABAC chaincode: %s", err.Error())
	}
}