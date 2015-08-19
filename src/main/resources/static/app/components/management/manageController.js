'use strict';

angular.module('candyApp.manage', [])
.controller('ManagerController', ['$scope', '$http', function($scope, $http) {
      var request = {
        method: 'GET',
        url: 'manage/entity',
      };
      $http(request).success(function (response) {
        $scope.managedEntities = response._embedded.entity;
      });

}]);