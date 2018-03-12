(function() {
	'use strict';

	angular.module('app.services')
			.service('EmpregadoService', EmpregadoService);

	EmpregadoService.$inject = [ '$http', 'AppConfig' ];

	function EmpregadoService($http, AppConfig) {

		// PUBLIC Methods
		return {
			listAll : _listAll,
			findById : _findById
		};

		// PRIVATE Methods
		function _listAll() {
			return $http.get(AppConfig.api + '/empregado');
		}

		function _findById(id) {
			return $http.get(AppConfig.api + '/empregado/' + id);
		}

	}

})();