app.directive('header', function () {
    return {
        restrict: 'A', //This menas that it will be used as an attribute and NOT as an element. I don't like creating custom HTML elements
        replace: true,
        scope: {user: '='}, // This is one of the cool things :). Will be explained in post.
        templateUrl: "/shared/header/headerView.html",
        controller: ['$rootScope', '$scope', '$filter', '$http', function ($rootScope, $scope, $filter, $http) {
            if($rootScope.user){
                var request = {
                    method: 'GET',
                    url: 'manage/entity',
                };
                $http(request).success(function (response) {
                    $scope.entities = response._embedded.entity;
                });
                $scope.user = $rootScope.user;
                $scope.logout = $rootScope.logout;
            }
        }]
    }
})