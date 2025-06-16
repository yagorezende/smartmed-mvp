package br.uff.lid.labgen.policy;

import br.uff.lid.labgen.handlers.fabric.ClientApp;
import org.keycloak.authorization.model.Resource;
import org.keycloak.authorization.permission.ResourcePermission;
import org.keycloak.authorization.policy.evaluation.EvaluationContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockchainValidator {
    private static final Map<String, String> policies = new HashMap<String, String>() {{
        put("logs", "simple_policy");
    }};

    private static Boolean runSimplePolicy(String email) throws Exception {
        return ClientApp.verifyEmail(email);

//        SimplePolicy policy = new SimplePolicy();
//        try {
//            return policy.rawValidate(email);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
    public static boolean validate(String resource, String email) throws Exception {
        System.out.println("Validating resource: " + resource + " with email: " + email);
        if(resource.equals("scheduled-visits") || resource.equals("scheduled-appointments")){
            return true;
        }

        return runSimplePolicy(email);
    }

    public static boolean validate(ResourcePermission resourcePermission, EvaluationContext context) throws Exception {
        Resource resource = resourcePermission.getResource();
        if(resource != null){

            for (Map.Entry<String, Collection<String>> entry : context.getIdentity().getAttributes().toMap().entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }

            String identity = context.getIdentity().getAttributes().getValue("email").asString(0);
            return validate(resource.getName(), identity);
        }
        return false;
    }
}
