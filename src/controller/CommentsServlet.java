package controller;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import javax.servlet.RequestDispatcher;

import java.sql.Connection;

/**
 * Servlet implementation class ListOrderServlet
 */

@WebServlet("/comentarios")
public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentsServlet() {
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
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);

		String id = request.getParameter("id");
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		News noticia = newsDao.get(Long.valueOf(id));
		if (noticia != null) {
			request.setAttribute("noticia", noticia);

			UserDAO userDAO = new JDBCUserDAOImpl();
			userDAO.setConnection(conn);
			User usuario = userDAO.get(noticia.getOwner());
			request.setAttribute("propietario", usuario);

			List<Comment> commentList = commentDao.getAllByNews(noticia.getId());
			Map<Long, User> commentMap = new HashMap<Long, User>();
			for (Comment comment : commentList) {
				commentMap.put(comment.getId(), userDAO.get(comment.getOwner()));
			}
			Map<Long, String> timeStampMap = new HashMap<Long, String>();
			for (Comment comment : commentList) {
				timeStampMap.put(comment.getId(),
						DateTimeHelper.timestamp2String(comment.getTimeStamp(), comment.getDateStamp()));
			}
			request.setAttribute("timeStampMap", timeStampMap);
			request.setAttribute("timeStamp",
					DateTimeHelper.timestamp2String(noticia.getTimeStamp(), noticia.getDateStamp()));
			request.setAttribute("commentList", commentList);
			request.setAttribute("commentMap", commentMap);
			request.setAttribute("numComments", commentList.size());

			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/comentarios.jsp");
			view.forward(request, response);
		}else{
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
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			logger.info("Atendiendo POST");
			Connection conn = (Connection) getServletContext().getAttribute("dbConn");
			CommentDAO commentDao = new JDBCCommentDAOImpl();
			commentDao.setConnection(conn);
			String id = request.getParameter("id");
			Comment comment = new Comment();
			comment.setText(request.getParameter("text"));
			comment.setNews(Long.parseLong(id));
			comment.setOwner(user.getId());
			logger.info("Comentario: " + comment.getText());
			Map<String, String> messages = new HashMap<String, String>();
			if (comment.validate(messages)) {
				commentDao.add(comment);
				HttpSession session = request.getSession();
				session.setAttribute("comment", comment);
				response.sendRedirect("comentarios?id=" + Long.parseLong(id));
			} else {
				NewsDAO newsDao = new JDBCNewsDAOImpl();
				newsDao.setConnection(conn);
				News noticia = newsDao.get(Long.valueOf(id));
				if (noticia != null) {
					noticia.setHits(noticia.getHits() + 1);
					newsDao.save(noticia);
					request.setAttribute("noticia", noticia);

					UserDAO userDAO = new JDBCUserDAOImpl();
					userDAO.setConnection(conn);
					User usuario = userDAO.get(noticia.getOwner());
					request.setAttribute("propietario", usuario);

					List<Comment> commentList = commentDao.getAllByNews(noticia.getId());
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
				request.setAttribute("messages", messages);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/comentarios.jsp");
				view.forward(request, response);
				}
				else{
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
		} else {
			response.sendRedirect("login");
		}

	}

}
