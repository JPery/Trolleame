package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import model.User;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(urlPatterns = { "/user/register" })

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/register.jsp");
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

		User usuario = new User();

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		String nUser = request.getParameter("user");
		User existentUser = userDAO.get(nUser);
		if (existentUser == null) {
			usuario.setName(nUser);
			usuario.setEmail(request.getParameter("email"));
			usuario.setPassword(request.getParameter("pass"));
			logger.info("Usuario: " + usuario.getName());

			Map<String, String> messages = new HashMap<String, String>();
			if (usuario.validate(messages, request.getParameter("verpass"))) {
				userDAO.add(usuario);
				usuario = userDAO.get(usuario.getName());
				HttpSession session = request.getSession();
				session.setAttribute("user", usuario);
				response.sendRedirect("index");
			} else {
				request.setAttribute("messages", messages);
				usuario.setPassword("");
				request.setAttribute("usuario", usuario);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/register.jsp");
				view.forward(request, response);
			}
		} else {
			Map<String, String> messages = new HashMap<String, String>();
			messages.put("error", "El usuario ya existe");
			request.setAttribute("messages", messages);
			usuario.setPassword("");
			request.setAttribute("usuario", usuario);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/register.jsp");
			view.forward(request, response);
		}

	}

}
