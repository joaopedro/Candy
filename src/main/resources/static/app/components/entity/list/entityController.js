'use strict';

angular.module('candyApp.entity.list', ['ngRoute'])
    .controller('ListEntidadeController', ['$scope', '$http', '$routeParams', function ($scope, $http, $routeParams) {
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
    }]);