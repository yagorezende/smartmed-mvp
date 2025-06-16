package br.uff.lid.labgen;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authorization.AuthorizationProvider;
import org.keycloak.authorization.AuthorizationProviderFactory;
import org.keycloak.authorization.policy.evaluation.PolicyEvaluator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;

import java.util.Map;

public class CustomAuthorizationProviderFactory implements AuthorizationProviderFactory {
    private static final Logger log = Logger.getLogger(CustomAuthorizationProviderFactory.class);
    // Create a new instance of the DefaultPolicyEvaluator class
    private final PolicyEvaluator policyEvaluator = new CustomPolicyEvaluator();
//    private DefaultPolicyEvaluator defaultPolicyEvaluator = new DefaultPolicyEvaluator();

    public static final String contractAddress = System.getenv("SM_EVALUATION_POINT_ADDRESS");

    private static final Map<String, String> contracts = Map.of(
            contractAddress, "PolicyEvaluationPoint"
    );

    @Override
    public AuthorizationProvider create(KeycloakSession keycloakSession, RealmModel realmModel) {
        log.infof("## CustomAuthorizationProviderFactory.create(1) ->", keycloakSession, realmModel);
        return new AuthorizationProvider(keycloakSession, realmModel, this.policyEvaluator);
    }

    @Override
    public AuthorizationProvider create(KeycloakSession keycloakSession) {
        log.infof("## CustomAuthorizationProviderFactory.create(2) ->", keycloakSession);
        return this.create(keycloakSession, keycloakSession.getContext().getRealm());
    }

    @Override
    public void init(Config.Scope scope) {
        log.infof("## CustomAuthorizationProviderFactory.init() -> %s", scope);
        log.info("### [CAPF] Loading contracts...");
//        ContractLoader contractLoader = ContractLoader.getInstance();
//        contracts.forEach((address, contract) -> {
//            log.infof("#### [CAPF] Loading contract %s running...", contract);
//            try {
//                contractLoader.loadContract(address, contract);
//            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//            log.infof("#### [CAPF] Loaded contract %s done!", contract);
//        });
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        log.infof("## CustomAuthorizationProviderFactory.postInit() ->", keycloakSessionFactory);
    }

    @Override
    public void close() {
        log.infof("## CustomAuthorizationProviderFactory.close()");
    }

    @Override
    public String getId() {
        return "authorization";
    }
}
