app.controller("listCtrl",['newsFactory','usersFactory','commentsFactory', 'utils','$route','$location', function(newsFactory,usersFactory,commentsFactory, utils,$route,$location){
    var vm = this;
    vm.noticias=[];
    vm.noticiaTroll;
    vm.noticiaClic;
    vm.funciones = {
			obtenerNoticias : function() {
		        newsFactory.leerNoticias(utils)
					.then(function(respuesta){
	    			console.log("Trayendo todas las noticias: ", respuesta);
	    			vm.noticias = respuesta;
	    			var arrayLength = vm.noticias.length;
	    			for (var i = 0; i < arrayLength; i++) {
	    				vm.funciones.setUserNoticia(vm.noticias[i]);
	    				vm.funciones.setCommentNumber(vm.noticias[i]);
	    				timeAgo.setTimeAgoNoticia(vm.noticias[i]);
	    			}
	    			}, function(respuesta){
	    			console.log("Error obteniendo noticias", vm.noticias);
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
    	trollearNoticia : function(){
    		newsFactory.trollearNoticia(vm.noticiaTroll);
    	},
    	clicNoticia : function(){
    		newsFactory.clicNoticia(vm.noticiaClic);
    	},
    	changeLocation : function(id){
    		$location.path('/comments/'+id);
    	}
	}
	vm.funciones.obtenerNoticias();
}]);