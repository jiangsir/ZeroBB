package jiangsir.zerobb.GoogleChecker;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiangsir.zerobb.Scopes.ApplicationScope;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Tables.AppConfig;
import jiangsir.zerobb.Tables.CurrentUser;

@WebServlet(urlPatterns = {"/GoogleLogin"})
public class GoogleLoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionScope sessionScope = new SessionScope(request);
		CurrentUser currentUser = sessionScope.getCurrentUser();
		if (currentUser == null || !currentUser.getIsGoogleUser()) {
			// https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=156955164629.apps.googleusercontent.com&redirect_uri=http://apps.nknush.kh.edu.tw/ZeroJiaowu/OAuth2Callback&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email
			AppConfig appConfig = ApplicationScope.getAppConfig();

			String oauth_uri = "https://accounts.google.com/o/oauth2/auth?response_type=code";
			String scope = "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email";
			response.sendRedirect(oauth_uri + "&client_id=" + appConfig.getClient_id() + "&redirect_uri="
					+ appConfig.getRedirect_uri() + "&scope=" + scope);
			return;
		} else {
			response.sendRedirect(sessionScope.getCurrentPage());
			return;
		}
	}

}
