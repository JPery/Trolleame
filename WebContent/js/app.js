var app = angular.module('TrolleameApp', [ "ngRoute" ]).config(
		function($routeProvider) {
			$routeProvider.when("/", {
				controller : "listCtrl",
				controllerAs : "vm",
				templateUrl : "pages/list.html",
				resolve : {
					// provoca 100 milisegundos (0,1 segundos) de delay que
					// deberían
					// ser suficientes para que el servidor haga cualquier
					// actualización que se le haya pedido antes de leer las
					// órdenes.
					// extraído del script.js utilizado como ejemplo en
					// https://docs.angularjs.org/api/ngRoute/service/$route
					delay : function($q, $timeout) {
						var delay = $q.defer();
						$timeout(delay.resolve, 100);
						return delay.promise;
					}
				}
			}).when("/nuevaNoticia", {
				controller : "editCtrl",
				controllerAs : "vm",
				templateUrl : "pages/newnotice.html"
			}).when("/editarNoticia/:ID", {
				controller : "editCtrl",
				controllerAs : "vm",
				templateUrl : "pages/newnotice.html"
			}).when("/deleteNews/:ID", {
				controller : "deleteCtrl",
				controllerAs : "vm",
				templateUrl : "pages/confirm.html"
			}).when("/deleteComments/:ID", {
				controller : "deleteCtrl",
				controllerAs : "vm",
				templateUrl : "pages/confirm.html"
			}).when("/deleteUser/:ID", {
				controller : "deleteCtrl",
				controllerAs : "vm",
				templateUrl : "pages/confirm.html"
			}).when("/profile/:ID", {
				controller : "profileCtrl",
				controllerAs : "vm",
				templateUrl : "pages/profile.html"
			}).when("/login/", {
				controller : "loginCtrl",
				controllerAs : "vm",
				templateUrl : "pages/login.html",
				isLogin : true
			}).when("/comments/:ID", {
				controller : "commentsCtrl",
				controllerAs : "vm",
				templateUrl : "pages/comentarios.html"
			}).when("/registro", {
				controller : "registerCtrl",
				controllerAs : "vm",
				templateUrl : "pages/register.html"
			}).when("/403", {
				templateUrl : "pages/403.html"
			}).otherwise({templateUrl:'pages/404.html'});;
		});