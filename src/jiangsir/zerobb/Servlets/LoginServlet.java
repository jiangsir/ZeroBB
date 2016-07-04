package jiangsir.zerobb.Servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Services.CurrentUserDAO;
import jiangsir.zerobb.Services.UserDAO;
import jiangsir.zerobb.Tables.Parameter;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.*;

@WebServlet(urlPatterns = {"/Login"}, name = "Login", initParams = {@WebInitParam(name = "VIEW", value = "/Login.jsp")})
public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2574978673882597645L;
	// public static String urlpattern = LoginServlet.class.getAnnotation(
	// WebServlet.class).urlPatterns()[0];
	public String VIEW = "";

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.VIEW = config.getInitParameter("VIEW");
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");
		User user = new UserDAO().getUserByAccountPasswd(account, passwd);
		if (!user.isNullUser()) {
			SessionScope sessionScope = new SessionScope(session);
			sessionScope.setCurrentUser(new CurrentUserDAO().getCurrentUserById(user.getId(), session));
			response.sendRedirect("./" + sessionScope.getPreviousPage());
			return;
		} else {
			request.setAttribute("users", new UserDAO().getUsers());
			request.getRequestDispatcher(VIEW).forward(request, response);
			return;
		}

	}
}
