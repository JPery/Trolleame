app.controller("registerCtrl", [
		'usersFactory',
		'$window',
		'$templateCache',
		'auth',
		function(usersFactory, $window, $templateCache, auth) {
			if (!auth.isAuth()) {
				var vm = this;
				vm.usuario={};
				vm.funciones = {
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
						if(vm.usuario.password==vm.usuario.verpass)
							usersFactory.newUser(vm.usuario)
						.then(function(respuesta) {
							auth.login(respuesta.id);
							$window.location = ("#");
							$window.location.reload();
							console.log("Insertando usuario . Respuesta recibida:",respuesta);
						},
						function(respuesta) {
							// acciones a realizar
							// cuando se recibe una
							// respuesta de error
							console.log("Error: inserción de noticia no realizado");
						})
					}
				}
			} else
				$window.location = ("#");
		} ]);