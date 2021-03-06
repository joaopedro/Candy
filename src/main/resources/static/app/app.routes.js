app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/welcome', {
                templateUrl: '/app/components/welcome/welcomeView.html',
                controller: 'WelcomeController',
                controllerAs: 'welcomeCtrl'})
            .when('/login', {
                templateUrl: '/app/components/login/loginView.html',
                controller: 'LoginController',
                controllerAs: 'loginCtrl'})
            .when('/entity', {
                templateUrl: '/app/components/entity/list/entityView.html',
                controller: 'ListEntidadeController',
                controllerAs: 'listEntidadeCtrl'})
            .when('/entity/edit', {
                templateUrl: '/app/components/entity/edit/entityView.html',
                controller: 'EditEntidadeController',
                controllerAs: 'editEntidadeCtrl'})
            .when('/manage', {
                templateUrl: '/app/components/management/manageView.html',
                controller: 'ManagerController',
                controllerAs: 'manageCtrl'})
            .otherwise({redirectTo: '/welcome'});
    }]);
