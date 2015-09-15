'use strict';

angular.module('candyApp.manage.edit', [])
.controller('EditManagerController', ['$scope', '$http', '$routeParams', 'configs', function ($scope, $http, $routeParams, configs) {	  
	//edit
	if (typeof $routeParams.entityHref != "undefined") {
        var request = {
            method: 'GET',
            url: $routeParams.entityHref,
        };
        $http(request).success(function (response) {
			$scope.entity = response;
        });		
	}
	//create
	else{
		$scope.entity = {
			friendlyName: null,
			physicalName: null,
			deleted: false,
			fields: []
		};
	}
	
	$scope.submit = function (form) {
		/*var url = 'api/' + $scope.entityName;
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
			});*/
	};
}]);