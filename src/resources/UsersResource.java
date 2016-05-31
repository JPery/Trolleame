package resources;

import java.sql.Connection;
import java.util.HashMap;
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

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;
import resources.exceptions.CustomBadRequestException;
import resources.exceptions.CustomNotFoundException;

@Path("/users")
public class UsersResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;

	@GET
	@Path("/session")
	@Produces(MediaType.APPLICATION_JSON)
	public User getSessionUserJSON() {
		User user;
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		if (user != null)
			return user;
		else
			return new User(-1);
	}

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserJSON(@PathParam("id") long id) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		User user = userDao.get(id);
		if (user == null) {
			throw new CustomNotFoundException("User (" + id + ") is not found");
		}
		user.setPassword("");
		if(((User) request.getSession().getAttribute("user"))==null ||  ((User) request.getSession().getAttribute("user")).getId()!= user.getId())
			user.setEmail("");
		return user;
	}

	
	@POST
	@Path("/session")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(User userUpdate) throws Exception {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		// Comprobamos que existe el usuario
		User user = userDao.get(userUpdate.getName());
		if(user==null){
			user = userDao.getByEmail(userUpdate.getName());
		}
		if (user != null && user.getPassword().equals(userUpdate.getPassword())) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}
		else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return Response.accepted().build();
	}
	
	@DELETE
	@Path("/session")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout() throws Exception {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		HttpSession session = request.getSession();
		session.invalidate();
		return Response.accepted().build();
	}
	
	// POST que recibe datos de la nueva orden por formulario
	@POST
	@Consumes("application/json")
	public Response post(User usuario) {
		HttpSession session = request.getSession();
		Response res;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		User user = new User();
		user.setEmail(usuario.getEmail());
		user.setName(usuario.getName());
		user.setPassword(usuario.getPassword());
		user.setImg("/Trolleame/img/logo64x64.png");
		Map<String, String> messages = new HashMap<String, String>();
		if (!user.validate(messages, user.getPassword()))
			throw new CustomBadRequestException("Errors in parameters");
		// save new in DB
		long id = userDao.add(user);
		user = userDao.get(id);
		session.setAttribute("user", user);
		res = Response // return 201 and Location: /user/userid
				.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();

		return res;
	}

	// PUT que actualiza a partir del objeto recibido
	@PUT
	@Path("/{id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(User userUpdate, @PathParam("id") long id) throws Exception {
		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		// Comprobamos que existe el usuario
		User user = userDao.get(userUpdate.getId());
		if (user != null) {
			if (user.getId() != id) {
				throw new CustomBadRequestException("Error in id");
			} else {
				Map<String, String> messages = new HashMap<String, String>();
				if (userUpdate.validate(messages)) {
					userDao.save(userUpdate);

				} else {

					throw new CustomBadRequestException("Errors in parameters");

				}

			}
		}

		else {

			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return response;

	}

	@DELETE
	@Path("/{id: [0-9]+}")
	public Response delete(@PathParam("id") long id) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		userDao.delete(id);

		return Response.noContent().build(); // 204 no content
	}
}
