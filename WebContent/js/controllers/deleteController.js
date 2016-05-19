app.controller("deleteCtrl", [ 'newsFactory', 'usersFactory',
                       		'commentsFactory', '$routeParams', '$location', 'auth', '$window',function(newsFactory, usersFactory,
		commentsFactory, $routeParams, $location, auth, $window) {
	var vm=this;
	vm.mensaje;
	vm.funciones = {
		estoy : function(ruta) {
			return $location.path() == ruta;
		},
		eliminarNoticia : function(id){
			newsFactory.eliminarNoticia(id);
		},
		eliminarComentario : function(id){
			commentsFactory.eliminarComentario(id);
		},
		eliminarUsuario : function(id){
			usersFactory.deleteUser(id);
		},
		obtenerNoticia : function(ID) {
	        newsFactory.leerNoticia(ID)
				.then(function(respuesta){
					if(!auth.isMe(respuesta.owner))
						$location.path('403');
					console.log("Trayendo noticia con id: ", respuesta.id, respuesta);
    			}, function(respuesta){
    				console.log("Error obteniendo noticias", vm.noticia);
    			})
		},
		obtenerComentario : function(ID) {
	        commentsFactory.getComentario(ID)
				.then(function(respuesta){
					if(!auth.isMe(respuesta.owner))
						$location.path('403');
					console.log("Trayendo comentario con id: ", respuesta.id, respuesta);
    			}, function(respuesta){
    				console.log("Error obteniendo noticias", vm.noticia);
    			})
		},
		obtenerUsuario : function(ID) {
	        usersFactory.getUser(ID)
				.then(function(respuesta){
					if(!auth.isMe(respuesta.id))
						$location.path('403');
					console.log("Trayendo comentario con id: ", respuesta.id, respuesta);
    			}, function(respuesta){
    				console.log("Error obteniendo noticias", vm.noticia);
    			})
		},
		eliminar : function() {
			if (vm.funciones.estoy('/deleteNews/'+$routeParams.ID)) {
				console.log($location.path());
				vm.funciones.eliminarNoticia($routeParams.ID);
				$location.path('/');
			} else if (vm.funciones.estoy('/deleteComments/'+$routeParams.ID)) {
				console.log($location.path());
				vm.funciones.eliminarComentario($routeParams.ID);
				$window.history.back();
			} else if (vm.funciones.estoy('/deleteUser/'+$routeParams.ID)) {
				console.log($location.path());
				vm.funciones.eliminarUsuario($routeParams.ID);
				usersFactory.unLoginUser()
				.then(function(respuesta){
					auth.logout();
					vm.funciones.obtenerUsuario();
					auth.logout();
					$window.location = ("#");
					$window.location.reload();
					console.log("Unloging user");
    			}, function(respuesta){
    			// acciones a realizar cuando se recibe una respuesta de error
    			console.log("Error:", respuesta.status);
    			})
			}
		},
		back : function() {
			  $window.history.back();
		}
	}
	if (auth.isAuth()) {
		console.log("$routeParams.ID=", $routeParams.ID);
		if (vm.funciones.estoy('/deleteNews/'+$routeParams.ID)) {
			vm.funciones.obtenerNoticia($routeParams.ID);
			vm.mensaje="la noticia";
		} else if (vm.funciones.estoy('/deleteComments/'+$routeParams.ID)) {
			vm.funciones.obtenerComentario($routeParams.ID);
			vm.mensaje="el comentario";
		} else if (vm.funciones.estoy('/deleteUser/'+$routeParams.ID)) {
			vm.funciones.obtenerUsuario($routeParams.ID);
			vm.mensaje="su usuario y todas sus noticias y comentarios";
		}
	} else {
		$window.location = ("#/login");
	}
} ]);