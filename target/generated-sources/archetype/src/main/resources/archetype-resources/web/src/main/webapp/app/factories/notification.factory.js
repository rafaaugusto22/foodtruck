(function() {
	'use strict';

	angular.module('app.factories').factory('NotificationFactory',
			NotificationFactory);

	function NotificationFactory() {
		return {
			success : function(text) {
				toastr.success(text, "Sucesso", {
					closeButton : true,
					positionClass : 'toast-top-right'
				});
			},
			error : function(text) {
				toastr.error(text, "Erro", {
					closeButton : true,
					positionClass : 'toast-top-right'
				});
			}
		};

	}
	
	/*
	 * angular.module('MonitorApp').factory('NotificationFactory', function () {
	 * return { success: function (text) { toastr.success(text,"Sucesso", {
	 * closeButton: true, positionClass: 'toast-bottom-left' }); }, error:
	 * function (text) { toastr.error(text, "Erro", { closeButton: true,
	 * positionClass: 'toast-bottom-left' }); } }; });
	 */	

})();