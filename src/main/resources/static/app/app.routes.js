app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/welcome', {
                templateUrl: 'welcome/welcomeView.html',
                controller: 'WelcomeController',
                controllerAs: 'welcomeCtrl'})
            .when('/login', {
                templateUrl: 'login/loginView.html',
                controller: 'LoginController',
                controllerAs: 'loginCtrl'})
            .when('/entity', {
                templateUrl: 'entity/list/entityView.html',
                controller: 'ListEntidadeController',
                controllerAs: 'listEntidadeCtrl'})
            .when('/entity/edit', {
                templateUrl: 'entity/edit/entityView.html',
                controller: 'EditEntidadeController',
                controllerAs: 'editEntidadeCtrl'})
            .when('/manage', {
                templateUrl: 'management/manageView.html',
                controller: 'ManagerController',
                controllerAs: 'manageCtrl'})
            .otherwise({redirectTo: '/welcome'});
    }]);
