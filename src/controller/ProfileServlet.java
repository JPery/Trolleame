package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;

import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(urlPatterns = { "/profile" })

public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Visiting profile");
		String id = request.getParameter("id");
		if(id==null){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}else{
			Connection conn = (Connection) getServletContext().getAttribute("dbConn");
			UserDAO userDAO = new JDBCUserDAOImpl();
			userDAO.setConnection(conn);
			User usuario = userDAO.get(Long.parseLong(id));
			request.setAttribute("usuario", usuario);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/profile.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}

