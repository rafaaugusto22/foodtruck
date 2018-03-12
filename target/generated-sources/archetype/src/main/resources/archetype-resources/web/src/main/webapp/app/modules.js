(function() {
	'use strict';

	var config = {
		api : "rest"
	};

	angular.module('app', [ 'ngResource', 'ui.router', 'smart-table',
			'angular-jwt', 'base64', 'app.controllers', 'app.services',
			'app.factories' ]);

	angular.module('app').constant("AppConfig", config);

	angular.module('app.controllers', []);
	angular.module('app.services', []);
	angular.module('app.factories', []);

})();