(function() {
	'use strict';
	angular.module('app.services').service('LoginService', LoginService);
	

	LoginService.$inject = [ '$http', '$base64', 'AppConfig', 'SessionService',	'$location'];

	function LoginService($http, $base64, AppConfig, SessionService,$location) {

		// PUBLIC Methods
		return {
			login : _login,
			logout : _logout
		};

		// PRIVATE Methods		
		function _login() {
			return $http.get(AppConfig.api + '/public/token/'+$location.search().code);			
		}
		
		function _logout(idToken) {
			return $http.get(AppConfig.api + '/public/logout/'+idToken);			
		}

	}

})();