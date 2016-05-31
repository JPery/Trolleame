app.controller("registerCtrl", [
		'usersFactory',
		'$window',
		'$templateCache',
		'auth',
		'$routeParams',
		function(usersFactory, $window, $templateCache, auth, $routeParams) {
			if (!auth.isAuth() || ($window.location.hash=="#/editUser/"+$routeParams.ID && auth.isAuth())) {
				var vm = this;
				vm.usuario={};
				vm.funciones = {
					estoy : function(ruta) {
						return $window.location.hash=="#/"+ruta;
					},
					obtenerUsuario : function(id) {
						usersFactory.getUser(id)
						.then(function(respuesta) {
							console.log("Trayendo la noticia con id: ",id," Respuesta: ",respuesta);
							vm.usuario = respuesta;
							if(!auth.isMe(vm.usuario.id)){
								$window.location = ('#/403');
							}
						},
						function(respuesta) {
							console.log("Error: datos de noticia no obtenidos");
						})
					},
					loginUser : function(user) {
						usersFactory.loginUser(user).then(
								function(respuesta) {
									auth.login(respuesta.id);
									$window.location = ("#");
									$window.location.reload();
									console.log("Trayendo user con id: ",
											respuesta.id, " Respuesta: ",
											respuesta);
								}, function(respuesta) {
									// acciones a realizar cuando se recibe una
									// respuesta de error
									console.log("Error:", respuesta.status);
								})
					},	
					agregarUsuario : function() {
						if(vm.usuario.name!=undefined && vm.usuario.name!=""){
							if(vm.usuario.email!=undefined && vm.usuario.email!=""){
								if(vm.usuario.password!=undefined && vm.usuario.password!=""){
									if(vm.usuario.password==vm.usuario.verpass){
									vm.usuario.img="/Trolleame/img/logo64x64.png"
									usersFactory.newUser(vm.usuario)
									.then(function(respuesta) {
										auth.login(respuesta.id);
										$window.location = ("#");
										$window.location.reload();
										console.log("Insertando usuario . Respuesta recibida:",respuesta);
									},function(respuesta) {
										// acciones a realizar
										// cuando se recibe una
										// respuesta de error
										vm.error="Alguno de los campos no cumple los requisitos";
										console.log("Error: cambio de noticia no realizado");
									})
								}else{vm.error="Las contraseñas no coinciden";}
								}else{vm.error="Introduce una contraseña";}	
							}else{vm.error="Introduce un email";}
						}else{vm.error="El nombre de usuario no puede estar vacío";}
					},
					cambiarUsuario : function() {
						if(vm.usuario.name!=undefined && vm.usuario.name!=""){
							if(vm.usuario.email!=undefined && vm.usuario.email!=""){
								if(vm.usuario.password!=undefined && vm.usuario.password!=""){
									if(vm.usuario.password==vm.usuario.verpass){
										vm.usuario.password=Sha256.hash(vm.usuario.password);
										usersFactory.editUser(vm.usuario)
										.then(function(respuesta) {
											$window.location = ("#/profile/"+vm.usuario.id);
											console.log("Insertando usuario . Respuesta recibida:",respuesta);
										},
										function(respuesta) {
										// acciones a realizar
											// cuando se recibe una
										// respuesta de error
											console.log("Error: inserción de noticia no realizado");
										})
									}else{vm.error="Las contraseñas no coinciden";}
								}else{vm.error="Introduce una contraseña";}	
							}else{vm.error="Introduce un email";}
						}else{vm.error="El nombre de usuario no puede estar vacío";}
					},
					editarUsuario : function() {
						if (vm.funciones.estoy("registro")) {
							console.log($window.location);
							vm.funciones.agregarUsuario();
						} else if (vm.funciones.estoy("editUser/"+ vm.usuario.id)) {
							console.log($window.location);
							vm.funciones.cambiarUsuario();
						} else {
							console.log($location.path());
						}
					}
				}
			if (!($routeParams.ID == undefined)){
				vm.funciones.obtenerUsuario($routeParams.ID);
			}
			} else
				$window.location = ("#");
		} ]);