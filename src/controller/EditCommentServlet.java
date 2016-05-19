package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDAO;
import dao.JDBCCommentDAOImpl;
import dao.JDBCNewsDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.NewsDAO;
import dao.UserDAO;
import helper.DateTimeHelper;
import model.Comment;
import model.News;
import model.User;

/**
 * Servlet implementation class DetailOrderServlet
 */
@WebServlet(urlPatterns = { "/private/editarComentario" })
public class EditCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditCommentServlet() {
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
		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		String id = request.getParameter("id");
		logger.info("get parameter id (" + id + ")");
		long oid = 0;
		if (id != null)
			oid = Long.parseLong(id);
		logger.info("get parameter id (" + id + ") and casting " + oid);
		User user = (User) request.getSession().getAttribute("user");
		Comment comment = commentDAO.get(oid);
		if (comment != null) {
			News noticia = newsDao.get(comment.getNews());
			request.setAttribute("noticia", noticia);
			UserDAO userDAO = new JDBCUserDAOImpl();
			userDAO.setConnection(conn);
			User usuario = userDAO.get(noticia.getOwner());
			request.setAttribute("propietario", usuario);
			List<Comment> commentList = commentDAO.getAllByNews(noticia.getId());
			Map<Long, User> commentMap = new HashMap<Long, User>();
			for (Comment commentario : commentList) {
				commentMap.put(commentario.getId(), userDAO.get(commentario.getOwner()));
			}
			Map<Long, String> timeStampMap = new HashMap<Long, String>();
			for (Comment commentario : commentList) {
				timeStampMap.put(commentario.getId(),
						DateTimeHelper.timestamp2String(commentario.getTimeStamp(), commentario.getDateStamp()));
			}
			request.setAttribute("timeStampMap", timeStampMap);
			request.setAttribute("timeStamp",
					DateTimeHelper.timestamp2String(noticia.getTimeStamp(), noticia.getDateStamp()));
			request.setAttribute("commentList", commentList);
			request.setAttribute("commentMap", commentMap);
			request.setAttribute("numComments", commentList.size());

			if (comment.getOwner() == user.getId()) {
				request.setAttribute("comentarioEditar", comment);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/comentarios.jsp");
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
		CommentDAO commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);
		String id = request.getParameter("id");
		Comment comment = commentDAO.get(Long.parseLong(id));
		String previousText=comment.getText();
		comment.setText(request.getParameter("text"+comment.getId()));

		Map<String, String> messages = new HashMap<String, String>();
		if (comment.validate(messages)) {
			commentDAO.save(comment);
			comment = commentDAO.get(comment.getId());
			response.sendRedirect(request.getContextPath() + "/comentarios?id=" + comment.getNews());
		} else {
			NewsDAO newsDao = new JDBCNewsDAOImpl();
			newsDao.setConnection(conn);
			News noticia = newsDao.get(comment.getNews());
			if (noticia != null) {
				noticia.setHits(noticia.getHits() + 1);
				newsDao.save(noticia);
				request.setAttribute("noticia", noticia);

				UserDAO userDAO = new JDBCUserDAOImpl();
				userDAO.setConnection(conn);
				User usuario = userDAO.get(noticia.getOwner());
				request.setAttribute("propietario", usuario);

				List<Comment> commentList = commentDAO.getAllByNews(noticia.getId());
				Map<Long, User> commentMap = new HashMap<Long, User>();
				for (Comment commenta : commentList) {
					commentMap.put(commenta.getId(), userDAO.get(commenta.getOwner()));
				}
				Map<Long, String> timeStampMap = new HashMap<Long, String>();
				for (Comment commenta : commentList) {
					timeStampMap.put(commenta.getId(),
							DateTimeHelper.timestamp2String(commenta.getTimeStamp(), commenta.getDateStamp()));
				}
				request.setAttribute("timeStampMap", timeStampMap);
				request.setAttribute("timeStamp",
						DateTimeHelper.timestamp2String(noticia.getTimeStamp(), noticia.getDateStamp()));
				request.setAttribute("commentList", commentList);
				request.setAttribute("commentMap", commentMap);
				request.setAttribute("numComments", commentList.size());
				comment.setText(previousText);
				request.setAttribute("comentarioEditar", comment);
				request.setAttribute("messages", messages);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/comentarios.jsp");
				view.forward(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}

}