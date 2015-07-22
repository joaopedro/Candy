'use strict';

// Declare app level module which depends on views, and components
angular.module('candyApp', [
    'ngRoute',
    'datatables' ,
    'candyApp.welcome',
    'candyApp.entity',
    'candyApp.manage',
    'candyApp.version'
])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/welcome'});
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
            controller: ['$scope', '$filter', '$http', function ($scope, $filter, $http) {
                var request = {
                    method: 'GET',
                    url: 'manage/entity',
                };
                $http(request).success(function (response) {
                    $scope.entities = response._embedded.entity;
                });

            }]
        }
    });