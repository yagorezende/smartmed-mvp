from rest_framework import status
from rest_framework.generics import ListAPIView
from rest_framework.response import Response

keycloak_roles = {
    'GET': ['uma_authorization'],
}

class GetLogs(ListAPIView):
    keycloak_roles = keycloak_roles
    protect = True

    def get(self, request, *args, **kwargs):
        return Response([{"data": {"value": "OK"}}], status=status.HTTP_200_OK)
