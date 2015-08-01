'use strict';

var services = angular.module('candyApp.services', ['ngResource']);

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

angular.module('candyApp.login', ['ngRoute', 'candyApp.services'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'loginCtrl'
  });
}])
.controller('loginCtrl', ['$scope', '$rootScope', '$location','$http', '$cookieStore', 'LoginService',
        function ($scope, $rootScope, $location, $http, $cookieStore, LoginService) {
            $scope.login = function() {
                LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function(user) {
                    $rootScope.user = user;
                    $http.defaults.headers.common[ xAuthTokenHeaderName ] = user.token;
                    $cookieStore.put('user', user);
                    $location.path("/");
                });
            };
    }]);

