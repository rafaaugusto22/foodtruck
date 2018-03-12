(function() {
	'use strict';

	angular.module('app').config(Router);

	Router.$inject = [ '$stateProvider', '$urlRouterProvider', '$locationProvider' ];

	function Router($stateProvider, $urlRouterProvider, $locationProvider) {

		$urlRouterProvider.otherwise('/');

		// Retira o sinal # da URL (necessária para compatibilidade com
		// navegador que não são HTML5).
		$locationProvider.html5Mode({
			enabled : true,
			requireBase : true
		});		
		
		$stateProvider
		
		.state('login', {
			url : '/',
			controller : 'LoginController',
			controllerAs : 'log'
		})
		
		.state('home', {
			url : '/home',
			abstract: false,
            views: {
	            'header-container': {
	                templateUrl: 'app/views/templates/header.template.html',
	                controller : 'HeaderController',
	                controllerAs : 'hea'
	            },
	            'page-container': {
	                templateUrl: 'app/views/home.html',
	                controller : 'LoginController',
	                controllerAs : 'log'
	            },
	            'footer-container': {
	                templateUrl: 'app/views/templates/footer.template.html'
	            }
	        }  
		})
		
		.state('home.empregado', {
			url : '/empregado',
            views: {
	            'page-container@': {
	                templateUrl: 'app/views/empregado.html',
	                controller : 'EmpregadoController',
	                controllerAs : 'epg'
	            }
	        }  
		})
		
	}

})();