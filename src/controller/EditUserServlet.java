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
import helper.EncryptHelper;
import model.User;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(urlPatterns = { "/private/editUser" })

public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUserServlet() {
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

		

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
		HttpSession session = request.getSession();
		User usuario = (User) session.getAttribute("user");
			usuario.setName(request.getParameter("user"));
			usuario.setEmail(request.getParameter("email"));
			usuario.setPassword(request.getParameter("pass"));
			logger.info("Usuario: " + usuario.getName());

			Map<String, String> messages = new HashMap<String, String>();
			if (usuario.validate(messages, request.getParameter("verpass"))) {
				usuario.setPassword(EncryptHelper.sha256(usuario.getPassword()));
				userDAO.save(usuario);
				session.setAttribute("user", usuario);
				response.sendRedirect("profile");
			} else {
				request.setAttribute("messages", messages);
				usuario.setPassword("");
				request.setAttribute("usuario", usuario);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/register.jsp");
				view.forward(request, response);
			}

	}

}
