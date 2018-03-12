(function() {
	'use strict';

	angular.module('app.services', [])
			.service('SessionService', SessionService);

	SessionService.$inject = [ '$window', 'jwtHelper' ];

	function SessionService($window, jwtHelper) {

		var _getAccessToken = function() {
			var token = $window.sessionStorage.getItem('access_token');
			if (!token) {
				return undefined;
			}
			return token;
		};
		
		var _getIdToken = function() {
			var token = $window.sessionStorage.getItem('id_token');
			if (!token) {
				return undefined;
			}
			return token;
		};
		
	
		var _setToken = function(data) {
			$window.sessionStorage.setItem('access_token', data.access_token);
			$window.sessionStorage.setItem('id_token', data.id_token);			
		};

		var _removeToken = function() {
			$window.sessionStorage.removeItem('access_token');
			$window.sessionStorage.removeItem('id_token');
		};

		var _isLogged = function(logged) {
			var token = _getIdToken();
			if (!token) {
				return false;
			}
			return true;
		};

		/*var _getUser = function() {
			var token = getToken();
			if (token !== undefined) {
				var tokenPayload = jwtHelper.decodeToken(token);
				return tokenPayload.usuario;
			}
			return undefined;
		};*/

		return {
			//getUser : _getUser,
			getAccessToken : _getAccessToken,
			getIdToken: _getIdToken,
			setToken : _setToken,
			removeToken : _removeToken,
			isLogged : _isLogged
		};

	}

})();