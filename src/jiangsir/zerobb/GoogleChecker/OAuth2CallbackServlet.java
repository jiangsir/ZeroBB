package jiangsir.zerobb.GoogleChecker;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Scopes.ApplicationScope;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Tables.AppConfig;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tables.User;

/**
 * Servlet implementation class OAuth2CallbackServlet
 */
@WebServlet(urlPatterns = {"/oauth2callback"})
public class OAuth2CallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper mapper = new ObjectMapper(); // can reuse, share

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String code = request.getParameter("code");
		Verifier verifier = new Verifier(code);
		AppConfig appConfig = ApplicationScope.getAppConfig();
		String apiKey = appConfig.getClient_id(); // 你的ClientID
		String apiSecret = appConfig.getClient_secret();// 你的Client secret
		String redirect_uri = appConfig.getRedirect_uri();// 你的轉址網址
		OAuthService service = new ServiceBuilder().provider(Google2Api.class).apiKey(apiKey).apiSecret(apiSecret)
				.callback(redirect_uri)
				.scope("https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email")
				.build();

		Token accessToken = service.getAccessToken(null, verifier);

		// Now let's go and ask for a protected resource!
		OAuthRequest req = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
		service.signRequest(accessToken, req);
		Response resp = req.send();
		// Got it! Lets see what we found...
		String _sBody = resp.getBody();

		// response.getWriter().println("callback code=" + code);
		// response.getWriter().println(
		// "callback returnPage=" + session.getAttribute("returnPage"));
		// response.getWriter().println(resp.getCode() + "<br>");
		// response.getWriter().println("Body=" + _sBody);
		System.out.println(_sBody);
		GoogleUser googleUser = mapper.readValue(_sBody, GoogleUser.class);
		String domain = googleUser.getEmail().split("@")[1];
		if (!ApplicationScope.getAppConfig().getAuthdomains().contains(domain)) {
			throw new DataException(
					"您所登入的 domain(" + googleUser.getEmail() + ") 並非有效的學生信箱，請點擊『<a href=\"./Logout\">登出</a>』並重新登入。");
		}
		CurrentUser currentUser = new CurrentUser();
		currentUser.setAccount(googleUser.getEmail().split("@")[0]);
		currentUser.setEmail(googleUser.getEmail());
		currentUser.setPicture(googleUser.getPicture());
		currentUser.setName(googleUser.getName());
		currentUser.setRole(User.ROLE.USER);
		currentUser.setIsGoogleUser(true);
		// currentUser.setSession(session);
		new SessionScope(session).setCurrentUser(currentUser);

		System.out.println("response.sendRedirect=" + new SessionScope(session).getCurrentPage());
		response.sendRedirect("." + new SessionScope(session).getCurrentPage());
		// response.sendRedirect("./");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
