'use strict';

angular.module('candyApp.manage.list', [])
.controller('ListManagerController', ['$scope', '$http', 'configs', function($scope, $http, configs) {
      var request = {
        method: 'GET',
        url: configs.api.basePath+'manage/entity',
      };
      $http(request).success(function (response) {
        $scope.managedEntities = response._embedded.entity;
      });

}]);