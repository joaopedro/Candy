'use strict';

var xAuthTokenHeaderName = 'x-auth-token';

// Declare app level module which depends on views, and components
angular.module('candyApp', [
    'ngRoute',
    'ngCookies',
    'datatables' ,
    'candyApp.services',
    'candyApp.login',
    'candyApp.welcome',
    'candyApp.entity',
    'candyApp.manage',
    'candyApp.version'
])
    .config(['$routeProvider','$httpProvider','$provide', function ($routeProvider, $httpProvider, $provide) {
        $routeProvider.otherwise({redirectTo: '/welcome'});

        $provide.factory('responseInterceptor', function($rootScope, $q, $location) {
            return {
                // optional method
                'responseError': function(rejection) {
                    var status = rejection.status;
                    var config = rejection.config;
                    var method = config.method;
                    var url = config.url;

                    if (status == 403) {
                        $location.path( "/login" );
                    } else {
                        $rootScope.error = method + " on " + url + " failed with status " + status;
                    }

                    return $q.reject(rejection);                }
            };
        });

        $httpProvider.interceptors.push('responseInterceptor');

    }])
    .directive('footer', function () {
        return {
            restrict: 'A', //This menas that it will be used as an attribute and NOT as an element. I don't like creating custom HTML elements
            replace: true,
            templateUrl: "/footer.html",
        }
    })
    .directive('header', function () {
        return {
            restrict: 'A', //This menas that it will be used as an attribute and NOT as an element. I don't like creating custom HTML elements
            replace: true,
            scope: {user: '='}, // This is one of the cool things :). Will be explained in post.
            templateUrl: "/header.html",
            controller: ['$rootScope', '$scope', '$filter', '$http', function ($rootScope, $scope, $filter, $http) {
                var request = {
                    method: 'GET',
                    url: 'manage/entity',
                };
                $http(request).success(function (response) {
                    $scope.entities = response._embedded.entity;
                });
                $scope.user = $rootScope.user;
                $scope.logout = $rootScope.logout;
            }]
        }
    })
    .run(function($rootScope, $http, $location, $cookieStore, LoginService) {
        /* Reset error when a new view is loaded */
        $rootScope.$on('$viewContentLoaded', function() {
            delete $rootScope.error;
        });

        $rootScope.hasRole = function(role) {

            if ($rootScope.user === undefined) {
                return false;
            }

            if ($rootScope.user.roles[role] === undefined) {
                return false;
            }

            return $rootScope.user.roles[role];
        };



        $rootScope.logout = function() {
            debugger;
            delete $rootScope.user;
            delete $http.defaults.headers.common[xAuthTokenHeaderName];
            $cookieStore.remove('user');
            $location.path("/login");
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
