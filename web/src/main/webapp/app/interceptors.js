(function() {
	'use strict';

	angular.module('app').config(Interceptor);

	Interceptor.$inject = [ '$httpProvider', '$compileProvider', '$locationProvider', '$provide' ];

	function Interceptor($httpProvider, $compileProvider, $locationProvider, $provide) {
		
	
		$compileProvider.debugInfoEnabled(false);

		$provide.factory('Interceptor', 
				['$q', '$injector', '$location', 'SessionService','$window', 
				 function($q, $injector, $location, SessionService,$window) {
		  return {
		    
		    'request': function(config) {
		    	var idToken = SessionService.getIdToken();
		    	if(idToken !== undefined){
		    		config.headers['id_token'] = idToken;
		    	}
		    	var accessToken = SessionService.getAccessToken();
		    	if(accessToken !== undefined){
		    		config.headers['Authorization'] = 'Bearer ' + accessToken;
		    	}
				return config;
		    },

		   'requestError': function(rejection) {
		      if (canRecover(rejection)) {
		        return responseOrNewPromise;
		      }
		      return $q.reject(rejection);
		    },

		    'response': function(response) {
		    	debugger;
		    	return response || $q.when(response);
		    },

		   'responseError': function(rejection) {
			   if (rejection.status === 401) {
					SessionService.removeToken();
					$window.location.href ='';
			   }else if (rejection.status === 403) {
				   SessionService.removeToken();
				   $window.location.href ='';
			   }
			   return $q.reject(rejection);
		    }
		    
		  };
		  
		}]);

		$httpProvider.interceptors.push('Interceptor');
		
	}
	
})();