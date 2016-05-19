app.controller("mainAppCtrl", ['usersFactory','$route','$templateCache', 'utils','$rootScope','auth',function(usersFactory,$route,$templateCache, utils,$rootScope,auth){
    var vm = this;
    vm.user=null;
    vm.utils = utils;
    vm.query;
    vm.funciones = {
		obtenerUsuario : function() {
	        usersFactory.leerUser()
				.then(function(respuesta){
    			vm.user = respuesta
    			console.log("Trayendo user con id: ", vm.user.id," Respuesta: ", respuesta);
    			}, function(respuesta){
    			// acciones a realizar cuando se recibe una respuesta de error
    			console.log("Error:", respuesta.status);
    			})
		},
		logout : function(){
			usersFactory.unLoginUser()
				.then(function(respuesta){
					auth.logout();
					vm.funciones.obtenerUsuario();
					$route.reload();
					console.log("Unloging user");
    			}, function(respuesta){
    			// acciones a realizar cuando se recibe una respuesta de error
    			console.log("Error:", respuesta.status);
    			})
		}
	}	
	vm.funciones.obtenerUsuario();
}]);