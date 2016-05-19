app.controller("commentsCtrl", ['newsFactory','usersFactory','commentsFactory','$routeParams','$route', 'auth', function(newsFactory,usersFactory,commentsFactory,$routeParams,$route,auth){
    var vm = this;
    vm.noticia=null;
    vm.comentarios=[];
    vm.comentario;
    vm.comentarioEditado={};
    vm.funciones = {
		obtenerNoticia : function(ID) {
	        newsFactory.leerNoticia(ID)
				.then(function(respuesta){
					console.log("Trayendo noticia con id: ", respuesta.id, respuesta);
					vm.noticia = respuesta;
					console.log("Trayendo noticia con id: ", respuesta.id, respuesta);
					vm.funciones.setUserNoticia(vm.noticia);
					vm.funciones.setCommentNumber(vm.noticia);
					timeAgo.setTimeAgoNoticia(vm.noticia);
					vm.funciones.obtenerComentariosNoticia(vm.noticia);
    			}, function(respuesta){
    				console.log("Error obteniendo noticias", vm.noticia);
    			})
		},
    	setUserNoticia: function(noticia){
			usersFactory.getUser(noticia.owner)
			.then(function(respuesta){
				console.log("Trayendo user con id: ", respuesta.id," Respuesta: ", respuesta);
				noticia.username=respuesta.name;
				noticia.imgUser=respuesta.img;
    			}, function(respuesta){
    			// acciones a realizar cuando se recibe una respuesta de error
    			console.log("Error:", respuesta.status);
    			})
    	},
    	setCommentNumber: function(noticia){
			commentsFactory.getComentarios(noticia.id)
			.then(function(respuesta){
				console.log("Trayendo comentarios de la noticia con id: ", respuesta.id," Respuesta: ", respuesta);
				noticia.numComentarios=respuesta.length;
    			}, function(respuesta){
    			// acciones a realizar cuando se recibe una respuesta de error
    			console.log("Error:", respuesta.status);
    			})
    	},
    	obtenerComentariosNoticia: function(noticia){
			commentsFactory.getComentarios(noticia.id)
			.then(function(respuesta){
				console.log("Trayendo comentarios de la noticia con id: ", respuesta.id," Respuesta: ", respuesta);
				vm.comentarios=respuesta;
				var arrayLength = vm.comentarios.length;
    			for (var i = 0; i < arrayLength; i++) {
    				vm.funciones.setUserComentario(vm.comentarios[i]);
    				timeAgo.setTimeAgoNoticia(vm.comentarios[i]);
    			}
    			}, function(respuesta){
    			// acciones a realizar cuando se recibe una respuesta de error
    			console.log("Error:", respuesta.status);
    			})
    	},
    	setUserComentario: function(comentario){
			usersFactory.getUser(comentario.owner)
			.then(function(respuesta){
				console.log("Trayendo user con id: ", respuesta.id," Respuesta: ", respuesta);
				comentario.username=respuesta.name;
				comentario.imgUser=respuesta.img;
    			}, function(respuesta){
    			// acciones a realizar cuando se recibe una respuesta de error
    			console.log("Error:", respuesta.status);
    			})
    	},
		cambiarComentario : function() {
			commentsFactory.getComentario(vm.comentarioEditado.id)
			.then(function(respuesta) {
				console.log("Trayendo la noticia con id: ",vm.comentarioEditado.id," Respuesta: ",respuesta);
				vm.comentarioEditado = respuesta;
				if(!auth.isMe(vm.comentarioEditado.owner)){
					$window.location = ("pages/403.html");
				}
			},
			function(respuesta) {
				console.log("Error: datos de noticia no obtenidos");
			})
			commentsFactory.actualizarComentario(vm.comentarioEditado)
			.then(function(respuesta) {
				console.log("Actualizando noticia con id:",vm.comentarioEditado.id," Respuesta recibida:",respuesta);
				$route.reload();
			},
				function(respuesta) {
				// acciones a realizar
				// cuando se recibe una
				// respuesta de error
				console.log("Error: cambio de comentario no realizado");
			})
		},
		agregarComentario : function() {
			if(vm.comentario!=undefined){
				vm.comentario.news=vm.noticia.id;
				commentsFactory.nuevoComentario(vm.comentario)
				.then(function(respuesta) {
					console.log("Insertando comentario . Respuesta recibida:",respuesta);
					$route.reload();
				},
				function(respuesta) {
					// acciones a realizar
					// cuando se recibe una
					// respuesta de error
					console.log("Error: inserción de noticia no realizado");
				})
			}
		},
		setValues : function(text,id){
			if(visual.enabled){
				vm.comentarioEditado.text=text; 
				vm.comentarioEditado.id=id;
				visual.funciones.disable();
			}
		},
    	trollearNoticia : function(){
    		newsFactory.trollearNoticia(vm.noticia.id);
    	},
    	clicNoticia : function(){
    		newsFactory.clicNoticia(vm.noticia.id);
    	}
    }
    if (!($routeParams.ID==undefined)) {
    	visual.funciones.enable();
    	vm.funciones.obtenerNoticia($routeParams.ID);
    }
}]);