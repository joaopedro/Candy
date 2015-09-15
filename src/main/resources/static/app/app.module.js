'use strict';

var xAuthTokenHeaderName = 'x-auth-token';

// Declare app level module which depends on views, and components
var app = angular.module('candyApp', [
    'ngRoute',
    'ngCookies',
    'datatables',
    'candyApp.login',
    'candyApp.login.services',
    'candyApp.welcome',
    'candyApp.entity.list',
    'candyApp.entity.edit',
    'candyApp.manage.list',
	'candyApp.manage.edit',
    'candyApp.version'
])
    .config(['$routeProvider', '$httpProvider', '$provide', function ($routeProvider, $httpProvider, $provide) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $provide.factory('responseInterceptor', function ($rootScope, $q, $location) {
            return {
                // optional method
                'responseError': function (rejection) {
                    var status = rejection.status;
                    var config = rejection.config;
                    var method = config.method;
                    var url = config.url;
                    if (status == 401) {
                        $location.path("/login");
                    } else {
                        $rootScope.error = method + " on " + url + " failed with status " + status;
                    }

                    return $q.reject(rejection);
                }
            };
        });

        $httpProvider.interceptors.push('responseInterceptor');

    }])
    .run(function ($rootScope, $http, $location, $cookieStore, $window) {
        /* Reset error when a new view is loaded */
        $rootScope.$on('$viewContentLoaded', function () {
            delete $rootScope.error;
        });

        $rootScope.hasRole = function (role) {

            if ($rootScope.user === undefined) {
                return false;
            }

            if ($rootScope.user.roles[role] === undefined) {
                return false;
            }

            return $rootScope.user.roles[role];
        };


        $rootScope.logout = function () {
            delete $rootScope.user;
            delete $http.defaults.headers.common[xAuthTokenHeaderName];
            $cookieStore.remove('user');
            $location.path("/login");
            $window.location.reload();
        };

        /* Try getting valid user from cookie or go to login page */
        var originalPath = $location.path();
        $location.path("/login");
        var user = $cookieStore.get('user');
        if (user !== undefined) {
            $rootScope.user = user;
            $http.defaults.headers.common[xAuthTokenHeaderName] = user.token;

            $location.path(originalPath);
        }
    });
