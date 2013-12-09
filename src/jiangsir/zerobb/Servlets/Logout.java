package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/Logout" }, name = "Logout.do")
public class Logout extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8094253689280801894L;
	public static String urlpattern = Logout.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String session_account = (String) session
				.getAttribute("session_account");
		User session_user = (User) session.getAttribute("UserObject");
		if (session_user == null) {
			session_user = new UserDAO().getUser(session_account);
		}

		session_user.Logout(session);
		response.sendRedirect("./");
	}
}
