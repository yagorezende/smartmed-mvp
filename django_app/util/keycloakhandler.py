import logging

from django.conf import settings
from keycloak import KeycloakAdmin
from keycloak import KeycloakOpenIDConnection


class KeycloakHandler:
    def __init__(self, environ):
        self.environ = environ
        self.connection = None
        self.keycloak_admin = None

    def connect(self, user, password):
        if self.environ is None:
            raise Exception("Environment variables not found")
        self.connection = KeycloakOpenIDConnection(
            server_url=settings.KEYCLOAK_CONFIG.get("KEYCLOAK_SERVER_URL") + "/realms",
            username=user,
            password=password,
            realm_name=settings.KEYCLOAK_CONFIG.get("KEYCLOAK_REALM"),
            client_id="admin-cli",
            user_realm_name="Master",
        )
        self.keycloak_admin = KeycloakAdmin(connection=self.connection)
        return self

    def get_realm_roles(self):
        roles = self.keycloak_admin.get_realm_roles()
        to_dict = {}
        for role in roles:
            if role['description'] == '${role_b2blue-roles}':
                to_dict[role['name']] = role
        return to_dict

    def get_user_role(self, user_id):
        return self.keycloak_admin.get_realm_roles_of_user(user_id)

    def set_user_role(self, user_id, role):
        self.keycloak_admin.assign_realm_roles(user_id, role)

    def get_keycloak_user(self, username: str):
        try:
            return self.keycloak_admin.get_user(self.keycloak_admin.get_user_id(username))
        except Exception as e:
            return None

    def get_keycloak_user_id(self, username: str):
        return self.keycloak_admin.get_user_id(username)

    def user_exists(self, username: str):
        return self.keycloak_admin.get_user_id(username) is not None

    def get_user_bearer_token_by_id(self, user_id):
        return self.keycloak_admin.get_user_bearer_token(user_id)

    def create_keycloak_user(self, data):
        try:
            return self.keycloak_admin.create_user({
                "email": data['email'],
                "username": data['username'],
                "enabled": True,
                "emailVerified": False,
                "firstName": data['name'].split(' ')[0],
                "lastName": ' '.join(x for x in data['name'].split(' ')[1::]),
                "attributes": {
                    "locale": ["en"]
                }
            })
        except Exception as e:
            logging.error(f"Error creating user: {e}")
            return self.keycloak_admin.get_user_id(data['email'])
