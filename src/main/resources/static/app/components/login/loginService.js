var services = angular.module('candyApp.login.services', ['ngResource']);

services.factory('LoginService', function($resource) {

    return $resource(':action', {},
        {
            authenticate: {
                method: 'POST',
                params: {'action' : 'authenticate'},
                headers : {'Content-Type': 'application/x-www-form-urlencoded'}
            }
        }
    );
});

