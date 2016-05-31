app.controller(
	"editCtrl",['newsFactory','usersFactory','commentsFactory', '$routeParams', '$location', '$scope', 'auth', '$window',
	function(newsFactory, usersFactory, commentsFactory,
		$routeParams, $location, $scope, auth, $window) {
		if (auth.isAuth()) {
			var error;
			var vm = this;
			vm.noticia= {};
			vm.funciones = {
				estoy : function(ruta) {
					return $location.path() == ruta;
				},
				obtenerNoticia : function(id) {
					newsFactory.leerNoticia(id)
					.then(function(respuesta) {
						console.log("Trayendo la noticia con id: ",id," Respuesta: ",respuesta);
						vm.noticia = respuesta;
						if(!auth.isMe(vm.noticia.owner)){
							$location.path('403');
						}
					},
					function(respuesta) {
						console.log("Error: datos de noticia no obtenidos");
					})
				},
				cambiarNoticia : function() {
					if(vm.noticia.url!=undefined && vm.noticia.url!=""){
						if(vm.noticia.title!=undefined && vm.noticia.title!=""){
							if(vm.noticia.text!=undefined && vm.noticia.text!=""){
								if(vm.noticia.category!=undefined && vm.noticia.category!=""){
									newsFactory.actualizarNoticia(vm.noticia)
									.then(function(respuesta) {
										console.log("Actualizando noticia con id:",vm.noticia.id," Respuesta recibida:",respuesta);
										$location.path('/');
									},
									function(respuesta) {
										// acciones a realizar
										// cuando se recibe una
										// respuesta de error
										vm.error="Alguno de los campos no cumple los requisitos";
										console.log("Error: cambio de noticia no realizado");
									})
								}else{vm.error="Selecciona una categoría";}
							}else{vm.error="Escribe algo de texto";}
						}else{vm.error="Ponle un título a la noticia";}
					}else{vm.error="La url está vacía o no es válida";}
				},
				agregarNoticia : function() {
					if(vm.noticia.url!=undefined && vm.noticia.url!=""){
						if(vm.noticia.title!=undefined && vm.noticia.title!=""){
							if(vm.noticia.text!=undefined && vm.noticia.text!=""){
								if(vm.noticia.category!=undefined && vm.noticia.category!=""){
									newsFactory.nuevaNoticia(vm.noticia)
									.then(function(respuesta) {
										console.log("Insertando noticia . Respuesta recibida:",respuesta);
										$location.path('/');
									},
									function(respuesta) {
										// acciones a realizar
										// cuando se recibe una
										// respuesta de error
										vm.error="Alguno de los campos no cumple los requisitos";
										console.log("Error: inserción de noticia no realizado");
									})
								}else{vm.error="Selecciona una categoría";}
							}else{vm.error="Escribe algo de texto";}
						}else{vm.error="Ponle un título a la noticia";}
					}else{vm.error="La url está vacía o no es válida";}
				},
				editarNoticia : function() {
					if (vm.funciones.estoy('/nuevaNoticia')) {
						console.log($location.path());
						vm.funciones.agregarNoticia();
					} else if (vm.funciones.estoy('/editarNoticia/'+ vm.noticia.id)) {
						console.log($location.path());
						vm.funciones.cambiarNoticia();
					} else {
						console.log($location.path());
					}
				}
			}
			console.log("$routeParams.ID=", $routeParams.ID);
			if (!($routeParams.ID == undefined)){
				vm.funciones.obtenerNoticia($routeParams.ID);
			}
			} else {
				$window.location = ("#/login");
			}
}]);