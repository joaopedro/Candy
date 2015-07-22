'use strict';

angular.module('candyApp.manage', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/manage', {
    templateUrl: 'manage/manage.html',
    controller: 'manageCtrl'
  });
}])

.controller('manageCtrl', ['$scope', '$http', function($scope, $http) {
      var request = {
        method: 'GET',
        url: 'manage/entity',
      };
      $http(request).success(function (response) {
        $scope.managedEntities = response._embedded.entity;
      });

}]);