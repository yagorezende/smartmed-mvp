package br.uff.lid.labgen;

import br.uff.lid.labgen.handlers.ContractLoader;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {
//    private static final Logger log = Logger.getLogger(CustomEventListenerProviderFactory.class);

    public static final String logsContractAddress = System.getenv("SM_ABAC_EVENTS_ADDRESS");

    private static final Map<String, String> contracts = Map.of(
            logsContractAddress, "KeycloakABACEvents"
    );

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new CustomEventListenerProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
        System.out.printf("## CustomEventListenerProviderFactory.init() -> %s", scope);
        System.out.println("## [CEPF] Loading contracts...");
        ContractLoader contractLoader = ContractLoader.getInstance();
        contracts.forEach((address, contract) -> {
            System.out.printf("### [CEPF] Loading contract %s running...", contract);
            try {
                contractLoader.loadContract(address, contract);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("### [CEPF] Loaded contract %s done!", contract);
        });
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "smartmed-event-listener";
    }
}