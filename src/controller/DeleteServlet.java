package controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCNewsDAOImpl;
import dao.NewsDAO;
import model.News;
import model.User;

import java.sql.Connection;

/**
 * Servlet implementation class ListOrderServlet
 */

@WebServlet("/private/eliminar")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteServlet() {
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
		String id = request.getParameter("id");
		long newId = Long.parseLong(id);
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		News news = newsDao.get(newId); 
		User user = (User) request.getSession().getAttribute("user");
		if (news != null) {
			if (user!=null && news.getOwner() == user.getId()) {
				request.setAttribute("message", "¿Seguro que quieres eliminar la noticia?");
				request.setAttribute("id", id);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/confirm.jsp");
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
		logger.info("Atendiendo POST");

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		String id = request.getParameter("id");
		long newId = Long.parseLong(id);
		NewsDAO newsDao = new JDBCNewsDAOImpl();
		newsDao.setConnection(conn);
		News news = newsDao.get(newId); 
		User user = (User) request.getSession().getAttribute("user");
		if (news != null) {
			if (user!=null && news.getOwner() == user.getId()) {
					newsDao.delete(news.getId());
					response.sendRedirect(request.getContextPath()+"/index");
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
