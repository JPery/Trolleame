package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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
import helper.DateTimeHelper;
import helper.ImageHelper;
import model.News;
import model.User;

import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(urlPatterns = { "/private/nuevanoticia" })
@MultipartConfig
public class NewNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewNoticeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Atendiendo GET");
		HttpSession session = request.getSession();

		logger.info("Session id: " + session.getId());
		logger.info("Session new? " + session.isNew());
		logger.info("Session creation time: " + DateTimeHelper.time2Date(session.getCreationTime()));
		logger.info("Session last accessed time: " + DateTimeHelper.time2Date(session.getLastAccessedTime()));
		logger.info("Session max inactive time: " + DateTimeHelper.time2Date(session.getMaxInactiveInterval()));
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/newnotice.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		logger.info("Atendiendo POST");

		News noticia = new News();
		noticia.setTitle(request.getParameter("title"));
		noticia.setText(request.getParameter("text"));
		noticia.setUrl(request.getParameter("url"));
		noticia.setOwner((((User) request.getSession().getAttribute("user")).getId()));
		noticia.setCategory(request.getParameter("category"));
		Part filePart = request.getPart("file_upload");
		Map<String, String> messages = new HashMap<String, String>();
		boolean upload = false;
		if (filePart != null && filePart.getSize() != 0) {
			if ((filePart.getSize()<ImageHelper.DEFAULT_SIZE)) {
				upload = true;
				noticia.setImg(request.getContextPath() + "/img/news/newsAux.jpg");
			} else {
				messages.put("error", "La imagen excede el tamaño máximo (50KB)");
			}
		} else if (request.getParameter("file") != null) {
			noticia.setImg(request.getParameter("file"));
		}
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		logger.info("Noticia: " + noticia.getTitle());
		if (messages.get("error") == null && noticia.validate(messages)) {
			long newId = newsDao.add(noticia);
			if (upload) {
				noticia = newsDao.get(newId);
				ImageHelper.uploadImage(filePart, request.getServletContext().getRealPath("/") + "img/news/",
						"news" + newId + ".jpg");
				noticia.setImg(request.getContextPath() + "/img/news/news" + newId + ".jpg");
				newsDao.save(noticia);
			}
			response.sendRedirect(request.getContextPath() + "/index");
		} else {
			request.setAttribute("messages", messages);
			noticia.setImg("");
			request.setAttribute("noticia", noticia);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/newnotice.jsp");
			view.forward(request, response);
		}
	}

}
