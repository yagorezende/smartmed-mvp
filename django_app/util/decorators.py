from requests import session


def static_vars(**kwargs):
    def decorate(func):
        for k in kwargs:
            setattr(func, k, kwargs[k])
        return func
    return decorate

def bearer_token_decorator(func):
    def wrapper(request, *args, **kwargs):
        response = func(request, *args, **kwargs)
        response.set_cookie('AUTH_TOKEN', request.session.get('oidc_access_token'))
        return response
    return wrapper