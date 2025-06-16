package br.uff.lid.labgen;

import br.uff.lid.labgen.policy.BlockchainValidator;
import org.jboss.logging.Logger;
import org.keycloak.authorization.AuthorizationProvider;
import org.keycloak.authorization.Decision;
import org.keycloak.authorization.model.Policy;
import org.keycloak.authorization.model.Resource;
import org.keycloak.authorization.model.ResourceServer;
import org.keycloak.authorization.permission.ResourcePermission;
import org.keycloak.authorization.policy.evaluation.DefaultEvaluation;
import org.keycloak.authorization.policy.evaluation.EvaluationContext;
import org.keycloak.authorization.policy.evaluation.PolicyEvaluator;
import org.keycloak.authorization.policy.provider.PolicyProvider;
import org.keycloak.authorization.store.PolicyStore;
import org.keycloak.authorization.store.ResourceStore;
import org.keycloak.authorization.store.StoreFactory;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventType;
import org.keycloak.representations.idm.authorization.PolicyEnforcementMode;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;


public class CustomPolicyEvaluator implements PolicyEvaluator {
//    private static final Logger log = Logger.getLogger(CustomPolicyEvaluator.class);
    public CustomPolicyEvaluator() {}

    @Override
    public void evaluate(ResourcePermission resourcePermission, AuthorizationProvider authorizationProvider, EvaluationContext evaluationContext, Decision decision, Map<Policy, Map<Object, Decision.Effect>> map) {
        StoreFactory storeFactory = authorizationProvider.getStoreFactory();
        PolicyStore policyStore = storeFactory.getPolicyStore();
        ResourceStore resourceStore = storeFactory.getResourceStore();
        ResourceServer resourceServer = resourcePermission.getResourceServer();
        PolicyEnforcementMode enforcementMode = resourceServer.getPolicyEnforcementMode();

        if (PolicyEnforcementMode.DISABLED.equals(enforcementMode)) {
            System.out.println("## CustomPolicyEvaluator.evaluate() -> enforcementMode == DISABLED");
            grantAndComplete(resourcePermission, authorizationProvider, evaluationContext, decision);
        } else if (resourcePermission.isGranted()) {
            System.out.println("## CustomPolicyEvaluator.evaluate() -> isGranted()");
            grantAndComplete(resourcePermission, authorizationProvider, evaluationContext, decision);
        } else {
            AtomicBoolean verified = new AtomicBoolean();
            Consumer<Policy> policyConsumer = this.createPolicyEvaluator(resourcePermission, authorizationProvider, evaluationContext, decision, verified, map);
            Resource resource = resourcePermission.getResource();
            if (resource != null) {
//                System.out.println("## CustomPolicyEvaluator.evaluate().resource.getId() -> " + resource.getId());
//                System.out.println("## CustomPolicyEvaluator.evaluate().resource.getName() -> " + resource.getName());
//                log.info("## CustomPolicyEvaluator.evaluate().resource.getId() -> " + resource.getScopes().toString());
//                log.info("## CustomPolicyEvaluator.evaluate().resource.policyConsumer() -> " + policyConsumer);
//                log.info("## CustomPolicyEvaluator.evaluate().evaluationContext.getIdentity() -> " + evaluationContext.getIdentity().getAttributes().getValue("email").asString(0));
                System.out.println("## CustomPolicyEvaluator.evaluate() resource" + resource.getName() + "#" + resource.getScopes().get(0).getName());
                try {
                    if(BlockchainValidator.validate(resourcePermission, evaluationContext)){
                        System.out.println("## CustomPolicyEvaluator.evaluate() -> BlockchainValidator.validate() == true");
                        grantAndComplete(resourcePermission, authorizationProvider, evaluationContext, decision);
                    }
                } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else System.out.println("## CustomPolicyEvaluator.evaluate() -> resource == null");
        }
    }

    private void grantAndComplete(ResourcePermission permission, AuthorizationProvider authorizationProvider, EvaluationContext executionContext, Decision decision) {
        DefaultEvaluation evaluation = new DefaultEvaluation(permission, executionContext, decision, authorizationProvider);
        evaluation.grant();
        decision.onComplete(permission);
    }

    private Consumer<Policy> createPolicyEvaluator(ResourcePermission permission, AuthorizationProvider authorizationProvider, EvaluationContext executionContext, Decision decision, AtomicBoolean verified, Map<Policy, Map<Object, Decision.Effect>> decisionCache) {
        return (parentPolicy) -> {
            PolicyProvider policyProvider = authorizationProvider.getProvider(parentPolicy.getType());
            if (policyProvider == null) {
                throw new RuntimeException("Unknown parentPolicy provider for type [" + parentPolicy.getType() + "].");
            } else {
                policyProvider.evaluate(new DefaultEvaluation(permission, executionContext, parentPolicy, decision, authorizationProvider, decisionCache));
                verified.compareAndSet(false, true);
            }
        };
    }
}
