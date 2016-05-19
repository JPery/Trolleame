package controller;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

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
import model.News;
import model.User;

import javax.servlet.RequestDispatcher;

import java.sql.Connection;

/**
 * Servlet implementation class ListOrderServlet
 */
@WebServlet("/index")
public class PrimaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrimaryServlet() {
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
		NewsDAO newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);
		CommentDAO commentDao = new JDBCCommentDAOImpl();
		commentDao.setConnection(conn);
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		String category = request.getParameter("category");
		String order = request.getParameter("order");
		List<News> newsList;
		if (order != null) {
			newsList = newsDAO.getAllByOrder(category,order);
		}
		else if (category != null) {
			newsList = newsDAO.getAllByCategory(category);
		} else {
			newsList = newsDAO.getAll();
		}
		Map<Long, User> usersMap = new HashMap<Long, User>();
		for (News news : newsList) {
			usersMap.put(news.getId(), userDAO.get(news.getOwner()));
		}
		request.setAttribute("usersMap", usersMap);

		Map<Long, Integer> commentMap = new HashMap<Long, Integer>();
		for (News news : newsList) {
			commentMap.put(news.getId(), commentDao.getNumberCommentsbyNews(news.getId()));
		}
		request.setAttribute("commentMap", commentMap);
		Map<Long, String> timestampMap = new HashMap<Long, String>();
		for (News news : newsList) {
			timestampMap.put(news.getId(), DateTimeHelper.timestamp2String(news.getTimeStamp(),news.getDateStamp()));
		}
		request.setAttribute("timestampMap", timestampMap);
		request.setAttribute("category", category);
		request.setAttribute("newsList", newsList);

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/index.jsp");
		view.forward(request, response);
	}
}
