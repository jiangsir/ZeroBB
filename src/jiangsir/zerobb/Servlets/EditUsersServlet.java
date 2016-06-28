package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiangsir.zerobb.Annotations.RoleSetting;
import jiangsir.zerobb.Scopes.ApplicationScope;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Services.AppConfigService;
import jiangsir.zerobb.Services.UserDAO;
import jiangsir.zerobb.Tables.AppConfig;

/**
 * Servlet implementation class EditUsersServlet
 */
@WebServlet(urlPatterns = {"/EditUsers"})
@RoleSetting
public class EditUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUsersServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("users", new UserDAO().getUsers());
		request.getRequestDispatcher("EditUsers.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + new SessionScope(request).getPreviousPage());
		return;
	}

}
