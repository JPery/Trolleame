app.controller("profileCtrl",['usersFactory','$routeParams', function(usersFactory,$routeParams){
    var vm = this;
    vm.profile={};
    vm.funciones = {
		obtenerUsuario : function(id) {
	        usersFactory.getUser(id)
				.then(function(respuesta){
    			vm.profile = respuesta
    			console.log("Trayendo user con id: ", vm.profile.id," Respuesta: ", respuesta);
    			}, function(respuesta){
    			// acciones a realizar cuando se recibe una respuesta de error
    			console.log("Error:", respuesta.status);
    			})
		}
	}	
    if (!($routeParams.ID==undefined)) vm.funciones.obtenerUsuario($routeParams.ID);
}]);