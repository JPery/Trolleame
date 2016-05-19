app.service('auth', [ '$location', '$route', 'usersFactory',
		function($location, $route, usersFactory) {
			var logged = false;
			var userId = -1;
			this.login = function(id) {
				logged = true;
				userId = id;
				console.log(logged);
			}
			this.logout = function() {
				logged = false;
				userId = -1;
				console.log(logged);
			}
			this.isAuth = function() {
				return logged;
			}
			this.isMe = function(id){
				return userId==id;
			}
			this.setInitial = function() {
				usersFactory.leerUser().then(function(respuesta) {
					userId = respuesta.id;
					logged = respuesta.id != -1
				}, function(respuesta) {
					console.log("Error:", respuesta.status);
				});
			}
			this.setInitial();
		} ]);