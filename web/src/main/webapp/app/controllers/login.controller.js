(function() {
	'use strict';

	angular.module('app.controllers').controller('LoginController',
			LoginController);

	LoginController.$inject = [ '$state', 'LoginService', 'SessionService',
			'NotificationFactory' ,'$location'];

	function LoginController($state, LoginService, SessionService,
			NotificationFactory,$location) {
		var vm = this;

		vm.usuario = {};
		vm.usuario.logado = false;
		
		_init();

		function _init() {
			if (SessionService.isLogged()) {
				$location.url('/home');
			}else{
				//_login();
				$location.url('/home');
			}
		}
		
		function _login() {
			LoginService.login().then(function(response) {
				SessionService.setToken(response.data);
				vm.usuario.logado = true;
				NotificationFactory.success("Usuário conectado com sucesso!");
				$state.go('home');
			}, function() {
				NotificationFactory.error("Erro ao conectar.");
			});
		}
	}

})();