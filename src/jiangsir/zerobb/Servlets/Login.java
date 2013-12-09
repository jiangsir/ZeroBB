package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Tables.Parameter;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.*;

@WebServlet(urlPatterns = { "/Login" }, name = "Login.do")
public class Login extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2574978673882597645L;
	public static String urlpattern = Login.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

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
		String passwd = Parameter.parseString(request.getParameter("passwd"));

		// 20100110 為何會有這一段？會導致密碼明顯錯誤？
		// passwd = passwd.toUpperCase();

		// 2012-08-08 註解掉。這裡應該不是必須的判斷。
		// if (passwd == null || !Parameter.islegalParameter(passwd)) {
		// Message message = new Message();
		// message.setPlainTitle("登入不正確");
		// request.setAttribute("message", message);
		// request.getRequestDispatcher("Message.jsp").forward(request,
		// response);
		// return;
		// }

		session.removeAttribute("OriginalURI");

		String CurrentIP = request.getRemoteAddr();
		LoginChecker checker = new LoginChecker();
		// String theURI = targetURI.substring(targetURI.lastIndexOf('/') + 1);
		String CurrentPage = (String) session.getAttribute("CurrentPage");
		if (CurrentPage == null) {
			CurrentPage = "./";
		}
		CurrentPage = CurrentPage.substring(CurrentPage.lastIndexOf('/') + 1);
		// qx 如果 CurrentPage 是 Login 代表user 直接按 登入
		if (CurrentPage != null && "Login".equals(CurrentPage)) {
			// theURI = CurrentPage;
			// qx target 指向 前一頁, 也就是 Login 完 跳回原來那一頁
			Utils.PreviousPage(session);
		}
		// qx 舊時有 authhost 的作法
		// String message = checker.isLegalUser(account, UserPasswd, authhost,
		// theURI, CurrentIP);
		if (!checker.isLegalUser(account, passwd, CurrentIP)) {
			session.setAttribute("LoginMessage", "驗證有誤！");
			// qx 不能用 Dispatcher 的方式, 如果登入錯誤,會照成無線循環,無法結束
			// 因為 method 一直維持 POST, 就會一直進來 doPost 方法
			// 只能用 redirect 的方式, 因此 LoginMessage 就只能由 session傳遞
			response.sendRedirect("." + Login.urlpattern);
			return;
		}
		User user = new UserDAO().getUser(account);
		session.setAttribute("Logintime", ENV.getNow());
		session.setAttribute("sessionid", session.getId());
		session.setAttribute("UserObject", user);
		session.setAttribute("session_account", user.getAccount());
		session.setAttribute("session_usergroup", user.getUsergroup());
		session.setAttribute("session_privilege", user.getPrivilege());
		session.setAttribute("Locale", request.getLocale().toString());
		session.setAttribute("passed", "true");
		response.sendRedirect(Utils.CurrentPage(request));
	}
}
