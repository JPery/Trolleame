app.controller("loginCtrl", [
		'usersFactory',
		'$window',
		'$templateCache',
		'auth',
		function(usersFactory, $window, $templateCache, auth) {
			if (!auth.isAuth()) {
				var vm = this;
				vm.funciones = {
					loginUser : function(user) {
						if(user!=undefined){
						if(user.name!=undefined && user.name!=""){
							if(user.password!=undefined && user.password!=""){
						usersFactory.loginUser(user).then(
								function(respuesta) {
									auth.login(respuesta.id);
									$window.location = ("#");
									$window.location.reload();
									console.log("Trayendo user con id: ",
											respuesta.id, " Respuesta: ",
											respuesta);
								}, function(respuesta) {
									vm.error="Usuario o contraseña incorrectos";
									// acciones a realizar cuando se recibe una
									// respuesta de error
									console.log("Error:", respuesta.status);
								})
							} else{
								vm.error="El campo contraseña no puede estar vacío";
							}
						} else{
							vm.error="El campo usuario no puede estar vacío";
						}
						} else{
							vm.error="Debes rellenar todos los campos";
						}
					}
				};
			} else
				$window.location = ("#");
		} ]);