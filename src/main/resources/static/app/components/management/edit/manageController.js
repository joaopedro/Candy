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
			fields: [{
				friendlyName: null,
				physicalName: null,
				type: "STRING",
				fieldId: false,
				fieldIdentity: false
			}]
		};
	}
	
	$scope.addField = function () {
		console.log("addField");
		var field = {
			friendlyName: null,
			physicalName: null,
			type: "STRING",
			fieldId: false,
			fieldIdentity: false
		}
		
		$scope.entity.fields.push(field);
	};
	
	$scope.removeField = function (index) {		
		bootbox.confirm('Delete field?', function(result) {
			if (result) {
				$scope.entity.fields.splice(index, 1);
				
				$scope.$apply();
			}
		});
	};
	
	$scope.submit = function (form) {
		var url;
		if (typeof $routeParams.entityHref != "undefined") {
			url = $routeParams.entityHref;			
		}
		else{
			url = configs.api.basePath+'manage/entity';			
		}

		var request = {
			method: 'POST',
			url: url,
			headers: {
				'Content-Type': 'application/json'
			},
			data: $scope.entity
		}

		$http(request)
			.success(function (response) {
				console.log(response);

				window.location.href = "#/manage";
			})
			.error(function (response) {
				console.log(response);
			});
	};
}]);