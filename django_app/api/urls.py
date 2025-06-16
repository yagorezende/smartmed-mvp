from django.urls import path

from api.views import *

urlpatterns = [
    path('v1/logs/', GetLogs.as_view()),
]
