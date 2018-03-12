(function() {
	'use strict';

	angular.module('app.controllers').controller('HeaderController',
			HeaderController);

	HeaderController.$inject = [ 'SessionService', 'LoginService',
			'NotificationFactory', '$state', '$window','$location'];

	function HeaderController(SessionService, LoginService,
			NotificationFactory, $state,$window,$location) {
		var vm = this;

		vm.logout = _logout;

		function _logout() {
			LoginService.logout(SessionService.getIdToken()).then(
					function success() {
						NotificationFactory
								.success("Usuário desconectado com sucesso!");
						$window.location.href = '';
					}, function error(response) {
						NotificationFactory.error(response);
					});
			vm.logged = false;
			SessionService.removeToken();
			$state.go("login");
			
		}

	}

})();