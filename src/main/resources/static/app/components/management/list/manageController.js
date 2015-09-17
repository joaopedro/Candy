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
	  
	$scope.deleteEntity = function(name, url, index){
		bootbox.confirm('Delete entity ' + name + '?', function(result) {
			if (result) {
				var request = {
					method: 'DELETE',
					url: url
				}

				$http(request)
				.success(function(response){
					console.log(response);
					$scope.managedEntities.splice(index, 1);
				})
				.error(function(response){
					console.log(response);
				});
			}
		});
	};
}]);