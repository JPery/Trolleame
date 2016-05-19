package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import helper.ImageHelper;
import model.News;
import model.User;

/**
 * Servlet implementation class DetailOrderServlet
 */
@WebServlet(urlPatterns = { "/private/editar" })
@MultipartConfig
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);

		String id = request.getParameter("id");
		logger.info("get parameter id (" + id + ")");
		long oid = 0;
		if (id != null)
			oid = Long.parseLong(id);

		logger.info("get parameter id (" + id + ") and casting " + oid);
		User user = (User) request.getSession().getAttribute("user");
		News news = newsDAO.get(oid);
		if (news != null) {
			if(news.getOwner() == user.getId()){
			request.setAttribute("noticia", news);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/newnotice.jsp");
			view.forward(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		News news = new News();
		news.setOwner(user.getId());
		news.setId(Long.parseLong(request.getParameter("id")));
		news.setTitle(request.getParameter("title"));
		System.out.println(news.getTitle());
		news.setText(request.getParameter("text"));
		news.setUrl(request.getParameter("url"));
		news.setCategory(request.getParameter("category"));
		Part filePart = request.getPart("file_upload");
		Map<String, String> messages = new HashMap<String, String>();
		boolean upload = false;
		if (filePart != null && filePart.getSize() != 0) {
			if (filePart.getSize()<ImageHelper.DEFAULT_SIZE) {
				upload = true;
				news.setImg(request.getContextPath() + "/img/news/news" + news.getId() + ".jpg");
			} else {
				messages.put("error", "La imagen excede el tamaño máximo (50KB)");
			}
		} else if (request.getParameter("file") != null) {
			news.setImg(request.getParameter("file"));
		}
		logger.info("Noticia: " + news.getTitle());
		if (messages.get("error") == null && news.validate(messages)) {
			if (upload) {
				ImageHelper.uploadImage(filePart, request.getServletContext().getRealPath("/") + "img/news/",
						"news" + news.getId() + ".jpg");
			}
			newsDAO.save(news);
			response.sendRedirect(request.getContextPath() + "/index");
		} else {
			request.setAttribute("messages", messages);
			request.setAttribute("noticia", news);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/newnotice.jsp");
			view.forward(request, response);
		}
	}

}