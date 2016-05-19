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
import javax.servlet.http.Part;

import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import helper.ImageHelper;
import model.User;

/**
 * Servlet implementation class DetailOrderServlet
 */
@WebServlet(urlPatterns = { "/private/editProfile" })
@MultipartConfig
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/editProfile.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		User user = (User) request.getSession().getAttribute("user");
		Part filePart = request.getPart("file_upload");
		boolean upload = false;
		Map<String, String> messages = new HashMap<String, String>();
		if (filePart != null && filePart.getSize() != 0) {
			if ((filePart.getSize()<ImageHelper.DEFAULT_SIZE)) {
				upload = true;
				user.setImg(request.getContextPath() + "/img/users/user"+user.getId()+".jpg");
			} else {
				messages.put("error", "La imagen excede el tamaño máximo (50KB)");
			}
		} else if (request.getParameter("file") != null) {
			user.setImg(request.getParameter("file"));
		}
		logger.info("Usuario: " + user.getName());
		if (messages.get("error") == null && user.validate(messages)) {
			if (upload) {
				ImageHelper.uploadImage(filePart, request.getServletContext().getRealPath("/") + "img/users/",
						"user" + user.getId() + ".jpg");
			}
			userDao.save(user);
			response.sendRedirect(request.getContextPath() + "/private/profile");
		} else {
			request.setAttribute("messages", messages);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/editProfile.jsp");
			view.forward(request, response);
		}
	}

}