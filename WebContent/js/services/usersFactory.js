app.factory("usersFactory", ['$http',function($http){
   var url = 'https://localhost:8443/Trolleame/rest/users/';
    var interfaz = {
    	     leerUser : function(){
    	    	 var urlU=url+"session";
                      return $http.get(urlU)
                      .then(function(response){
         				 return response.data;
     				 });
                 },	
    
    		getUser : function($id){
    			url = url;
    			urlU = url+$id;
    				return $http.get(urlU)
                    .then(function(response){
        				 return response.data;
    				 });
    		},
    		newUser : function(user){
    			url = url;
    				return $http.post(url,user)
                    .then(function(response){
                    	 return response.status;
    				 });
    		},
    		editUser : function(user){
    			url = url;
    			urlU=url+user.id;
    				return $http.put(urlU,user)
                    .then(function(response){
                    	 return response.status;
    				 });
    		},
    		deleteUser : function($id){
    			urlU = url+$id;
    				return $http.delete(urlU)
                    .then(function(response){
                    	 return response.status;
    				 });
    		},
    		loginUser : function(user){
    			var urlU=url+"session";
    			console.log(user);
    			var user2 = (JSON.parse(JSON.stringify(user)));
    			user2.password=Sha256.hash(user.password);
    			return $http.post(urlU,user2)
                .then(function(response){
     				 return response.status;
 				 });
    		},
    		unLoginUser : function(){
    			var urlU=url+"session";
    			return $http.delete(urlU)
                .then(function(response){
     				 return response.status;
 				 });
    		}
		
    }
    return interfaz;
}]);
