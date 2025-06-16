package br.uff.lid.labgen;

import br.uff.lid.labgen.blockchain.ABACLogger;
import org.json.JSONObject;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

import java.util.Arrays;
import java.util.List;

public class CustomEventListenerProvider implements EventListenerProvider {

//    private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);

    private final KeycloakSession session;
    private final RealmProvider model;

    private static final List<EventType> eventsSet = Arrays.asList(
            EventType.LOGIN,
            EventType.LOGIN_ERROR,
//            EventType.INTROSPECT_TOKEN,
//            EventType.INTROSPECT_TOKEN_ERROR,
            EventType.PERMISSION_TOKEN,
            EventType.PERMISSION_TOKEN_ERROR
//            EventType.LOGOUT,
//            EventType.LOGOUT_ERROR,
//            EventType.USER_INFO_REQUEST,
//            EventType.USER_INFO_REQUEST_ERROR,
//            EventType.CODE_TO_TOKEN,
//            EventType.CODE_TO_TOKEN_ERROR
        );

    public CustomEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {
//        System.out.printlnf("## NEW %s EVENT ->", event.getType());
        if (eventsSet.contains(event.getType())) {
            System.out.printf("## NEW %s EVENT TO LOG", event.getType());
            System.out.println("-----------------------------------------------------------");

            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel newRegisteredUser = this.session.users().getUserById(realm, event.getUserId());

            JSONObject jsonObject = new JSONObject();
            System.out.println("## LOG Content \n" + jsonObject.toString(2));
            jsonObject.put("type", event.getType().toString());
            jsonObject.put("email", newRegisteredUser.getEmail());
            jsonObject.put("username", newRegisteredUser.getUsername());
            jsonObject.put("client", event.getClientId());
            jsonObject.put("timestamp", event.getTime());
            jsonObject.put("ipAddress", event.getIpAddress());

            if(event.getType().equals(EventType.PERMISSION_TOKEN_ERROR) || event.getType().equals(EventType.PERMISSION_TOKEN)){
                // check if event has details
                if(event.getDetails() != null){
                    jsonObject.put("permission", event.getDetails().get("permission"));
                }else return; // ignore event
            }

//            jsonObject.put("details", event.getDetails());

            String loginPlainText = jsonObject.toString();

            ABACLogger logger = new ABACLogger();
            System.out.printf("[SmartMed] Event: %s", loginPlainText);
            System.out.println("-----------------------------------------------------------");
            try {
                System.out.printf("[SmartMed] Publishing event (OFF): %s", loginPlainText);
                logger.asyncPublish(loginPlainText);
            } catch (Exception e) {
                System.out.printf(String.format("[SmartMed] Error: %s", e.getMessage()));
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}