import java.net.URI;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;



import model.News;




public class RESTClientTest {
	

	  @Context
	  ServletContext sc;
	  
	  private static URI getBaseURI() {
		  	//Sin SSL:
		    return UriBuilder.fromUri("http://localhost:8080/Trolleame").build();
		  
		  	//Con SSL
			//return UriBuilder.fromUri("https://localhost:8443/sl07_restful").build();
		}

	
	public static void main(String[] args) throws Exception {
 
		//Configurac�n inicial del cliente
		ClientConfig config = new ClientConfig();
	    
		//Sin SSL:
		Client client = ClientBuilder.newClient(config);
		//Fin de sin SSL
		
		//Con SSL :
		//OJO Solo para pruebas en desarrollo
		//JerseyWithSSL jerseySSL= new JerseyWithSSL();
		//Client client = jerseySSL.initClient(config);
		//Fin de con SSL
		
	    WebTarget target = client.target(getBaseURI());

	    //Inicio de las pruebas
	    
	    //Probamos GET
	    
	    //Comprobamos que /rest/news no acepta petici�n pidiendo respuesta en MediaType.TEXT_PLAIN 
	    String responseString = 
	    		target.
	    		  path("rest").
	              path("news").
	              request().
	              accept(MediaType.TEXT_PLAIN).
	              get(Response.class)
	              .toString();
	    
	    System.out.println(responseString);
	    
	  //Comprobamos que /rest/news no acepta petici�n pidiendo respuesta en MediaType.APPLICATION_XML 
	    responseString = 
	    		target.
	    		  path("rest").
	              path("news").
	              request().
	              accept(MediaType.APPLICATION_XML).
	              get(Response.class)
	              .toString();
	    
	    System.out.println(responseString);
	    
	  //Comprobamos que /rest/news s� acepta petici�n pidiendo respuesta en MediaType.APPLICATION_JSON 
	    
	    responseString = 
	    		target.
	    		  path("rest").
	              path("news").
	              request().
	              accept(MediaType.APPLICATION_JSON).
	              get(Response.class)
	              .toString();
	    
	    System.out.println(responseString);
	    
	  
		//Comprobamos la salida de /rest/news pidiendo respuesta en MediaType.APPLICATION_JSON 
		
		System.out.println(
				target
				.path("rest")
				.path("news")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class));
		
		
  //Comprobamos que /rest/news/0 acepta petici�n pidiendo respuesta en MediaType.APPLICATION_JSON  
	    
	    responseString = 
	    		target.
	    		  path("rest").
	              path("news").
	              path("0").
	              request().
	              accept(MediaType.APPLICATION_JSON).
	              get(Response.class)
	              .toString();
	    	    
	    System.out.println(responseString);
		
				
		//Comprobamos la salida de  /rest/news/0 pidiendo respuesta en MediaType.APPLICATION_JSON
		System.out.println(
				target
				.path("rest")
				.path("news")
				.path("0")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class));
		
		
		
		//Comprobamos si /rest/news/1 acepta petici�n pidiendo respuesta en MediaType.APPLICATION_JSON  
		responseString = 
	    		target.
	    		  path("rest").
	              path("news").
	              path("1").
	              request().
	              accept(MediaType.APPLICATION_JSON).
	              get(Response.class)
	              .toString();
	    
	    System.out.println(responseString);
	    
	    
	    
		
	  //Si /rest/news/1 acepta petici�n pidiendo respuesta en MediaType.APPLICATION_JSON solicitamos esa orden en JSON 
	    Response checkResponse = target.path("rest").path("news/1").request().accept(MediaType.APPLICATION_JSON).get();
	    
	    if (checkResponse.getStatus()==200){
	  
	    	
		System.out.println(
				target
				.path("rest")
				.path("news")
				.path("1")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class));
		}
		
	    //Probamos PUT
		
	    
	    //Nos creamos un objeto orden similar al news 0 por defecto pero con algunos cambios, algunos de ellos err�neos
	    
		 Response response;
		 
		  News news = new News();
		  news.setId((long)1);//id no se corresponde con el id correcto que es 0
		  news.setTitle("Hola Mijos");//Nombre debe fallar al incluir un n�mero
		  news.setCategory("ocio");
		  news.setText("JEJEJE");
	
		
		//probamos con path a orden incorrecta, debe devolver error 404  == NOT FOUND
	
		response = target.path("rest").path("news").path(""+news.getId()+"").request(MediaType.APPLICATION_JSON).put(Entity.entity(news,MediaType.APPLICATION_JSON),Response.class);
		System.out.println(response.getStatus());
		
	    //ponemos el id correcto en el objeto orden, pero indicamos mal en el path, debe devolver 400 == BAD REQUEST 
	    news.setId((long)0);
	    response = target.path("rest").path("news").path("1").request(MediaType.APPLICATION_JSON).put(Entity.entity(news,MediaType.APPLICATION_JSON),Response.class);
		System.out.println(response.getStatus());
	    
 	   //probamos con id y path correctamente indicado pero con nombre incorrecto John2, debe devolver 400 == BAD REQUEST 
		 response = target.path("rest").path("news").path(""+news.getId()+"").request(MediaType.APPLICATION_JSON).put(Entity.entity(news,MediaType.APPLICATION_JSON),Response.class);
		 System.out.println(response.getStatus());
		    
		 
	    //Probamos con todo correcto, debe devolver 204 == The server successfully processed the request and is not returning any content.
		news.setTitle("Hola mijos");
	    response = target.path("rest").path("news").path("0").request(MediaType.APPLICATION_JSON).put(Entity.entity(news,MediaType.APPLICATION_JSON),Response.class);
	    System.out.println(response.getStatus());
	    
	     //Comprobamos la salida de  /rest/news/0 con petici�n pidiendo MediaType.APPLICATION_JSON
	  		System.out.println(
	  				target
	  				.path("rest")
	  				.path("news")
	  				.path("0")
	  				.request()
	  				.accept(MediaType.APPLICATION_JSON)
	  				.get(String.class));
	  		
	  	//Probamos con todo correcto de nuevo, dejando el nombre como estaba originalmente, debe devolver 204 == The server successfully processed the request and is not returning any content.
		    news.setTitle("Hola mijos");
		    response = target.path("rest").path("news").path("0").request(MediaType.APPLICATION_JSON).put(Entity.entity(news,MediaType.APPLICATION_JSON),Response.class);
		    System.out.println(response.getStatus());
		    
		 //Comprobamos la salida de  /rest/news/0 con petici�n pidiendo MediaType.APPLICATION_JSON
		  		System.out.println(
		  				target
		  				.path("rest")
		  				.path("news")
		  				.path("0")
		  				.request()
		  				.accept(MediaType.APPLICATION_JSON)
		  				.get(String.class));

				
		  		
		  		
	  	//Probamos POST
	  			 	
		  		//Nos creamos un objeto formulario que simule el formulario de �rdenes
		  		
			  Form form =new Form();
			    form.param("name", "Pepe Test");
			    form.param("email", "pepe@test.es");
			    form.param("tel","927887766");
			    form.param("delivery", "19:30");
			    form.param("size", "large");
			    form.param("comments","a comer!!!");
			    
			    String[] values ={"bacon","cheese","onion",};
			    
			    form.asMap().put("topping", Arrays.asList(values));
			    
			   // Probamos el POST
			    response = 
			    		target
			    		.path("rest")
			    		.path("news")
			    		.request()
			    		.post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
			    
			 // Debe devolver 201 == created resource
			    System.out.println("Form response " +response.toString());
			    //Consultamos la URI del nuevo recurso creado
			    System.out.println("Form response Location" +response.getLocation());
			    
			    //Pedimos todas las �rdenes para comprobar que se ha creado la nueva orden
			    
			    System.out.println(
						target
						.path("rest")
						.path("news")
						.request()
						.accept(MediaType.APPLICATION_JSON)
						.get(String.class));

			    
			    
			    //Probamos DELETE
			    Response checkDelete = target.path("rest").path("news/1").request().accept(MediaType.APPLICATION_JSON).get();

			      	   			
				//Si /rest/news/1 acepta petici�n pidiendo respuesta en MediaType.APPLICATION_JSON y devuelve algo lo borramos 
					if (checkDelete.getStatus()==200){
				  
						target
						.path("rest")
						.path("news/1")
						.request()
						.delete();
					}
						
					System.out.println(
							target
							.path("rest")
							.path("news")
							.request()
							.accept(MediaType.APPLICATION_JSON)
							.get(String.class));
					
			    

	}

	
} 
