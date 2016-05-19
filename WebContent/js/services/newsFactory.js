app.factory("newsFactory",['$http', function($http){
   var url = 'http://localhost:8080/Trolleame/rest/news/';
    var interfaz = {	
    		 leerNoticias: function(utils){
    			 if ((utils.getCategory() == "" || utils.getCategory()===undefined) && (utils.getView() == "" || utils.getView()===undefined)){
    				 return $http.get(url).then(function(response){
    					 console.log("Trayendo noticias: "+response.data);
    				 return response.data;
    				 });
    			 }
    			 else if (utils.getCategory() != "" && (utils.getView() == "" || utils.getView()===undefined)){
    				 return $http.get(url+utils.getCategory()).then(function(response){
    					 console.log("Trayendo noticias: "+response.data);
    				 return response.data;
    				 });
    			 }
    			 else if ((utils.getCategory() == "" || utils.getCategory()===undefined) && utils.getView() != ""){
    				 return $http.get(url+"&"+utils.getView()).then(function(response){
    					 console.log("Trayendo noticias: "+response.data);
    				 return response.data;
    				 });
    			 }
    			 else{
    				 return $http.get(url+utils.getCategory()+"&"+utils.getView()).then(function(response){
    					 console.log("Trayendo noticias: "+response.data);
    				 return response.data;
    				 });
    			 };
    		 },
    		 leerNoticiasBusqueda: function(busqueda){
    				 return $http.get(url+busqueda).then(function(response){
    					 console.log("Trayendo noticias: "+response.data);
    				 return response.data;
    				 });
    		 },
    	     leerNoticia : function(id){
                  var urlid = url + id;
                      return $http.get(urlid)
                      .then(function(response){
         				 return response.data;
         				 });
                 },
    		actualizarNoticia : function(noticia){
                var urlid = url+noticia.id;
                  return $http.put(urlid, noticia)
                  .then(function(response){
      				 return response.status;
  				 });                   
                },
    		nuevaNoticia:  function(noticia){
                    return $http.post(url,noticia)
                    .then(function(response){
         				 return response.status;
     				 });
                    }, 
              eliminarNoticia : function(id){
					var urlid = url+id;
                    return $http.delete(urlid)
                    .then(function(response){
         				 return response.status;
     				 });
                  },
          		trollearNoticia : function(id){
                    var urlid = url+"trolleos/"+id;
                      return $http.put(urlid)
                      .then(function(response){
          				 return response.status;
      				 });                   
                    },
              		clicNoticia : function(id){
                        var urlid = url+"clics/"+id;
                          return $http.put(urlid)
                          .then(function(response){
              				 return response.status;
          				 });                   
                        },
                  
    }
    return interfaz;
}]);