package resources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import model.Comment;
import model.User;
import resources.exceptions.CustomBadRequestException;
import resources.exceptions.CustomNotFoundException;

@Path("/comments")
public class CommentsResource {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
  
	  @GET
	  @Path("/news/{id: [0-9]+}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Comment> getCommentsJSON(@PathParam("id") long id) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		List<Comment> comments = commentDao.getAllByNews(id);	
	    return comments;
	  }
	  
	  @GET
	  @Path("/{id: [0-9]+}")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public Comment getCommentJSON(@PathParam("id") long id) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		
		Comment comment = commentDao.get(id);
		if (comment == null) {
		   throw new CustomNotFoundException("Comment ("+ id + ") is not found");
		   
		  }
		
	    return comment; 
	  }
	  
	  //POST que recibe datos del nuevo comentario
	  @POST	  	  
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response post(Comment comentario, @Context HttpServletRequest request) {	
		  
		 
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			  if (user!=null)
					System.out.println("usuario: "+user.getName());
			  
		  Response res;
		  Connection conn = (Connection) sc.getAttribute("dbConn");
		  CommentDAO commentDao = new JDBCCommentDAOImpl();
		  commentDao.setConnection(conn);

		  Comment comment = new Comment();
		  comment.setText(comentario.getText());
		  comment.setOwner(user.getId());
		  comment.setNews(comentario.getNews());

		  Map<String, String> messages = new HashMap<String, String>();
		  if (!comment.validate(messages))
				  throw new CustomBadRequestException("Errors in parameters");
		  //save new in DB
		  long id = commentDao.add(comment);

		  res = Response //return 201 and Location: /comment/commentid
				  .created(
						  uriInfo
						  .getAbsolutePathBuilder()
						  .path(Long.toString(id))
						  .build())
				  .contentLocation(
					      uriInfo
						  .getAbsolutePathBuilder()
						  .path(Long.toString(id))
						  .build())
				  .build();

		  return res; 
	  }
	  
	  //PUT que actualiza a partir del objeto recibido
	  @PUT
	  @Path("/{id: [0-9]+}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response put(Comment commentUpdate, @PathParam("id") long id) throws Exception{
			Response response = null;
			
			Connection conn = (Connection)sc.getAttribute("dbConn");
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(conn);
						
			
			//Comprobamos que existe la orden
			Comment comment = commentDao.get(commentUpdate.getId());
			if(comment != null){
				if (comment.getId()!=id)
					{
					throw new CustomBadRequestException("Error in id");
					}
				else 
				{
					comment.setText(commentUpdate.getText());
					Map<String, String> messages = new HashMap<String, String>();
					if (commentUpdate.validate(messages)){
						commentDao.save(commentUpdate);
						
					}
					else {
							
						throw new CustomBadRequestException("Errors in parameters");
						
					}
					  
				}
			}
				
			else{
				
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}			
			return response;
			
		}
	  
	  
	  
	  @DELETE
	  @Path("/{id: [0-9]+}")	  
	  public Response deleteComment(@PathParam("id") long id) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		
		commentDao.delete(id);
		
	    return Response.noContent().build(); //204 no content 
	  }
} 
