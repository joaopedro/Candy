'use strict';

angular.module('candyApp.entity', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/entity', {
            templateUrl: 'entity/list/entity.html',
            controller: 'entityCtrl'
        }).when('/entity/edit', {
            templateUrl: 'entity/edit/edit_entity.html'
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

        $scope.deleteEntity = function(id, index){
            bootbox.confirm('Delete '+$scope.entityName+' '+id+'?', function(result) {
                if (result) {
                    var request = {
                        method: 'DELETE',
                        url: 'api/'+$scope.entityName+'/'+id
                    }

                    $http(request)
                    .success(function(response){
                        console.log(response);
                        $scope.elements.splice(index, 1);
                    })
                    .error(function(response){
                        console.log(response);
                    });
                }
            });
        }
    }]).controller('entityEditCtrl', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
        $scope.entityName = $routeParams.name;

        //get fields description
        var request = {
            method: 'GET',
            url: 'manage/entity/',
        };
        $http(request).success(function (response) {
            angular.forEach(response._embedded.entity, function (entity) {
                if(entity.friendlyName == $scope.entityName){
                    $scope.fieldsDefs = entity.fields;
                }
            });
        });

        //edit
        if(typeof $routeParams.id != "undefined"){
            $scope.entityId = $routeParams.id;

            var request = {
                method: 'GET',
                url: 'api/'+$scope.entityName+'/'+$scope.entityId,
            };
            $http(request).success(function (response) {
                $scope.element = response;
            });
        }
        //create
        else{
            $scope.element = {};

            var request = {
                method: 'GET',
                url: 'manage/entity',
            };
            $http(request).success(function (response) {
                angular.forEach(response._embedded.entity, function (item) {
                    if(item.friendlyName === $scope.entityName){
                        angular.forEach(item.fields, function (field) {
                            $scope.element[field.friendlyName] = null;
                        })
                    }
                });
            });
        }

        $scope.submit = function(form) {
            var url = 'api/'+$scope.entityName;
            if(typeof $routeParams.id != "undefined"){
                url += '/'+$scope.entityId;
            }

            var request = {
                method: 'POST',
                url: url,
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.element
            }

            $http(request)
            .success(function(response){
                console.log(response);

                window.location.href = "#/entity?name="+$scope.entityName;
            })
            .error(function(response){
                console.log(response);
            });
        };
    }]);