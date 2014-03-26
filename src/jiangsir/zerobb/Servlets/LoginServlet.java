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

import jiangsir.zerobb.DAOs.CurrentUserDAO;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Tables.Parameter;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.*;

@WebServlet(urlPatterns = { "/Login" }, name = "Login", initParams = { @WebInitParam(name = "VIEW", value = "/Login.jsp") })
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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");
		User user = new UserDAO().getUserByAccountPasswd(account, passwd);
		if (!user.isNullUser()) {
			SessionScope sessionScope = new SessionScope(session);
			sessionScope.setCurrentUser(new CurrentUserDAO()
					.getCurrentUserById(user.getId(), session));
			response.sendRedirect(request.getContextPath()
					+ sessionScope.getHistories().get(0));
			return;
		} else {
			request.setAttribute("users", new UserDAO().getUsers());
			request.getRequestDispatcher(VIEW).forward(request, response);
			return;
		}

		// String account = request.getParameter("account");
		// String passwd =
		// Parameter.parseString(request.getParameter("passwd"));
		//
		// session.removeAttribute("OriginalURI");
		//
		// String CurrentIP = request.getRemoteAddr();
		// LoginChecker checker = new LoginChecker();
		// // String theURI = targetURI.substring(targetURI.lastIndexOf('/') +
		// 1);
		// String CurrentPage = (String) session.getAttribute("CurrentPage");
		// if (CurrentPage == null) {
		// CurrentPage = "./";
		// }
		// CurrentPage = CurrentPage.substring(CurrentPage.lastIndexOf('/') +
		// 1);
		// // qx 如果 CurrentPage 是 Login 代表user 直接按 登入
		// if (CurrentPage != null && "Login".equals(CurrentPage)) {
		// // theURI = CurrentPage;
		// // qx target 指向 前一頁, 也就是 Login 完 跳回原來那一頁
		// Utils.PreviousPage(session);
		// }
		// // qx 舊時有 authhost 的作法
		// // String message = checker.isLegalUser(account, UserPasswd,
		// authhost,
		// // theURI, CurrentIP);
		// if (!checker.isLegalUser(account, passwd, CurrentIP)) {
		// session.setAttribute("LoginMessage", "驗證有誤！");
		// // qx 不能用 Dispatcher 的方式, 如果登入錯誤,會照成無線循環,無法結束
		// // 因為 method 一直維持 POST, 就會一直進來 doPost 方法
		// // 只能用 redirect 的方式, 因此 LoginMessage 就只能由 session傳遞
		// response.sendRedirect("." + LoginServlet.urlpattern);
		// return;
		// }
		// User user = new UserDAO().getUser(account);
		//
		// session.setAttribute("Logintime", ENV.getNow());
		// session.setAttribute("sessionid", session.getId());
		// session.setAttribute("UserObject", user);
		// // session.setAttribute("session_account", user.getAccount());
		// // session.setAttribute("session_usergroup", user.getUsergroup());
		// // session.setAttribute("session_privilege", user.getPrivilege());
		// session.setAttribute("Locale", request.getLocale().toString());
		// // session.setAttribute("passed", "true");
		// response.sendRedirect(Utils.CurrentPage(request));
	}
}
