angular.module('candyApp.login.services', ['ngResource'])
    .factory('LoginService', function ($resource, configs) {

        return $resource(':action', {},
            {
                authenticate: {
                    method: 'POST',
                    url: configs.api.basePath+'/authenticate',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }
            }
        );
    });

