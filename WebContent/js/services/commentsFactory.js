app.factory("commentsFactory", [ '$http', function($http) {
	var url = 'http://localhost:8080/Trolleame/rest/comments/';
	var interfaz = {
		getComentario : function($id){
			urlU = url + $id;
			return $http.get(urlU).then(function(response) {
				return response.data;
			});
		},
		getComentarios : function($id) {
			urlU = url + "news/" + $id;
			return $http.get(urlU).then(function(response) {
				return response.data;
			});
		},
		actualizarComentario : function(comment){
            var urlid = url+comment.id;
              return $http.put(urlid, comment)
              .then(function(response){
  				 return response.status;
				 });                   
            },
		nuevoComentario :  function(comment){
                return $http.post(url,comment)
                .then(function(response){
     				 return response.status;
 				 });
                }, 
          eliminarComentario : function(id){
				var urlid = url+id;
                return $http.delete(urlid)
                .then(function(response){
     				 return response.status;
 				 });
              }

	}
	return interfaz;
} ]);