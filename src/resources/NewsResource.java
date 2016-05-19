package resources;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.tomcat.util.http.fileupload.FileItem;

import com.sun.jersey.multipart.FormDataParam;

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import helper.ImageHelper;
import model.News;
import model.User;
import resources.exceptions.CustomBadRequestException;
import resources.exceptions.CustomNotFoundException;

@Path("/news")
public class NewsResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		List<News> news = newsDao.getAll();

		return news;
	}

	@GET
	@Path("/{category: (actualidad|ocio|deportes)}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsJSON(@PathParam("category") String category) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		List<News> news = newsDao.getAllByCategory(category);

		return news;
	}

	@GET
	@Path("/{category: (actualidad|ocio|deportes)*}&{view: [a-zA-Z]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNewsJSON(@PathParam("category") String category, @PathParam("view") String view) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		
		List<News> news = newsDao.getAllByOrder(category, view);

		return news;
	}
	
	@GET
	@Path("/q?{query: [a-zA-Z0-9-ñÑ ]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getBySearch(@PathParam("query") String query) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		
		List<News> news = newsDao.getAllBySearch(query);

		return news;
	}

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public News getNewJSON(@PathParam("id") long id) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		News news = newsDao.get(id);
		if (news == null) {
			throw new CustomNotFoundException("New (" + id + ") is not found");

		}

		return news;
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file_upload") FileItem filePart, @Context HttpServletRequest request) {
		News noticia = new News();
		noticia.setTitle(request.getParameter("title"));
		noticia.setText(request.getParameter("text"));
		noticia.setUrl(request.getParameter("url"));
		try {
			noticia.setOwner((((User) request.getSession().getAttribute("user")).getId()));
		} catch (NullPointerException e) {
		}
		noticia.setCategory(request.getParameter("category"));
		Map<String, String> messages = new HashMap<String, String>();
		boolean upload = false;
		if (filePart != null && (filePart.getSize() != 0)) {
			if ((((Part) filePart).getSize() < ImageHelper.DEFAULT_SIZE)) {
				upload = true;
				noticia.setImg(request.getContextPath() + "/img/news/newsAux.jpg");
			} else {
				messages.put("error", "La imagen excede el tamaño máximo (50KB)");
			}
		} else if (request.getParameter("file") != null) {
			noticia.setImg(request.getParameter("file"));
		}
		String output = "File uploaded to : " + request.getContextPath() + "/img/news/newsAux.jpg";
		return Response.status(200).entity(output).build();
	}

	// POST que recibe datos de la nueva orden por formulario
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(News noticia, @Context HttpServletRequest request) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Response res;
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		News news = new News();
		news.setTitle(noticia.getTitle());
		news.setUrl(noticia.getUrl());
		news.setText(noticia.getText());
		news.setCategory(noticia.getCategory());
		news.setImg("/Trolleame/img/logo64x64.png");
		news.setOwner(user.getId());

		Map<String, String> messages = new HashMap<String, String>();
		if (!news.validate(messages))
			throw new CustomBadRequestException("Errors in parameters");
		// save new in DB
		long id = newsDao.add(news);

		res = Response // return 201 and Location: /news/newid
				.created(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build())
				.contentLocation(uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build()).build();

		return res;
	}

	// PUT que actualiza a partir del objeto recibido
	@PUT
	@Path("/{id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(News newsUpdate, @PathParam("id") long id) throws Exception {
		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		// Comprobamos que existe la orden
		News news = newsDao.get(newsUpdate.getId());
		if (news != null) {
			if (news.getId() != id) {
				throw new CustomBadRequestException("Error in id");
			} else {
				Map<String, String> messages = new HashMap<String, String>();
				if (newsUpdate.validate(messages)) {
					newsDao.save(newsUpdate);

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

	@PUT
	@Path("/trolleos/{id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response trolleos(@PathParam("id") long id) throws Exception {
		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		// Comprobamos que existe la orden
		News news = newsDao.get(id);
		if (news != null) {
			if (news.getId() != id) {
				throw new CustomBadRequestException("Error in id");
			} else {
				news.setLikes(news.getLikes()+1);
				newsDao.save(news);
			}
		}
		else {

			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return response;
	}
	
	@PUT
	@Path("/clics/{id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response clics(@PathParam("id") long id) throws Exception {
		Response response = null;

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		// Comprobamos que existe la orden
		News news = newsDao.get(id);
		if (news != null) {
			if (news.getId() != id) {
				throw new CustomBadRequestException("Error in id");
			} else {
				news.setHits(news.getHits()+1);
				newsDao.save(news);
			}
		}
		else {

			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return response;
	}
	
	@DELETE
	@Path("/{id: [0-9]+}")
	public Response deleteNews(@PathParam("id") long id) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);

		newsDao.delete(id);

		return Response.noContent().build(); // 204 no content
	}

}
