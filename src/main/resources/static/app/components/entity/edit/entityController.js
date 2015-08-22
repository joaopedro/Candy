'use strict';

angular.module('candyApp.entity.edit', ['ngRoute'])
    .controller('EditEntidadeController', ['$scope', '$http', '$routeParams', 'configs', function ($scope, $http, $routeParams, configs) {
        $scope.entityName = $routeParams.name;

        //get fields description
        var request = {
            method: 'GET',
            url: configs.api.basePath+'manage/entity/',
        };
        $http(request).success(function (response) {
            angular.forEach(response._embedded.entity, function (entity) {
                if (entity.friendlyName == $scope.entityName) {
                    $scope.fieldsDefs = entity.fields;
                }
            });
        });

        //edit
        if (typeof $routeParams.id != "undefined") {
            $scope.entityId = $routeParams.id;

            var request = {
                method: 'GET',
                url: configs.api.basePath+'api/' + $scope.entityName + '/' + $scope.entityId,
            };
            $http(request).success(function (response) {
                $scope.element = response;
            });
        }
        //create
        else {
            $scope.element = {};

            var request = {
                method: 'GET',
                url: configs.api.basePath+'manage/entity',
            };
            $http(request).success(function (response) {
                angular.forEach(response._embedded.entity, function (item) {
                    if (item.friendlyName === $scope.entityName) {
                        angular.forEach(item.fields, function (field) {
                            $scope.element[field.friendlyName] = null;
                        })
                    }
                });
            });
        }

        $scope.submit = function (form) {
            var url = 'api/' + $scope.entityName;
            if (typeof $routeParams.id != "undefined") {
                url += '/' + $scope.entityId;
            }

            var request = {
                method: 'POST',
                url: configs.api.basePath+url,
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.element
            }

            $http(request)
                .success(function (response) {
                    console.log(response);

                    window.location.href = "#/entity?name=" + $scope.entityName;
                })
                .error(function (response) {
                    console.log(response);
                });
        };
    }]);