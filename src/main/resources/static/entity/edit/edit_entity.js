/*'use strict';

angular.module('candyApp.entity', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/entity', {
            templateUrl: 'entity/entity.html',
            controller: 'entityCtrl'
        });
    }])

    .controller('entityCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
        $scope.entityName = $routeParams.name;
        var request = {
            method: 'GET',
            url: 'api/'+$scope.entityName,
        };
        $http(request).success(function (response) {
            $scope.elements = response;
            $scope.fields = {};
            //Get Dynamic Fields from json
            angular.forEach($scope.elements, function (item) {
                for (var key in item) {
                    if (item.hasOwnProperty(key) && !$scope.fields[key]) {
                        console.log("Field "+key+" added!")
                        $scope.fields[key] = true;
                    }
                }
            })

        });

    }]);*/