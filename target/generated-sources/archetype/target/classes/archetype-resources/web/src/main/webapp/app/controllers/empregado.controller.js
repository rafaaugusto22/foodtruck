(function() {
	'use strict';

	angular.module('app.controllers').controller('EmpregadoController',
			EmpregadoController);

	EmpregadoController.$inject = [ 'EmpregadoService', 'NotificationFactory' ];

	function EmpregadoController(EmpregadoService, NotificationFactory) {
		var vm = this;

		vm.empregado = {};
		vm.empregados = [];

		// Controles da Smart Table
		vm.lista = [];
		vm.itemsByPage = 15;

		vm.listAll = _listAll;
		vm.findById = _findById;
		vm.edit = _edit;
		vm.remove = _remove;

		function _edit(empregado) {
			$state.go('empregado.edit', {
				id : empregado.id
			});
		}

		function _remove() {

		}

		function _listAll() {
			EmpregadoService.listAll().then(function(response) {
				vm.empregados = response.data;
			}, function(response) {
				NotificationFactory.error(response);
			});
		}

		function _findById() {
			EmpregadoService.findById(vm.empregado.id).then(function(response) {
				vm.empregado = response.data;
				vm.empregados = [];
				vm.empregados.push(vm.empregado);
			}, function(response) {
				NotificationFactory.error(response);
			});
		}

	}

})();