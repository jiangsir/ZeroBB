package jiangsir.zerobb.Scopes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import jiangsir.zerobb.Tables.User;

public class ApplicationScope {
	public static ServletContext servletContext = null;

	private static String built = null;
	private static HashMap<String, HttpSession> onlineSessions = new HashMap<String, HttpSession>();
	private static HashMap<String, User> onlineUsers = new HashMap<String, User>();
	private static HashMap<String, HttpServlet> urlpatterns = new HashMap<String, HttpServlet>();
	private static File appRoot = null;

	public static void setAllAttributes(ServletContext servletContext) {
		ApplicationScope.servletContext = servletContext;

		ApplicationScope.setAppRoot(new File(servletContext.getRealPath("/")));
		ApplicationScope.setBuilt();
		ApplicationScope.setOnlineSessions(onlineSessions);
		ApplicationScope.setOnlineUsers(onlineUsers);
		ApplicationScope.setUrlpatterns(urlpatterns);
		// ApplicationScope.setAppConfig(ConfigHandler.getAppConfig());
		// ApplicationScope.setCanBookup();
	}

	public static HashMap<String, HttpSession> getOnlineSessions() {
		return onlineSessions;
	}

	public static void setOnlineSessions(
			HashMap<String, HttpSession> onlineSessions) {
		ApplicationScope.onlineSessions = onlineSessions;
		servletContext.setAttribute("onlineSessions", onlineSessions);
	}

	public static HashMap<String, User> getOnlineUsers() {
		return onlineUsers;
	}

	public static void setOnlineUsers(HashMap<String, User> onlineUsers) {
		ApplicationScope.onlineUsers = onlineUsers;
		servletContext.setAttribute("onlineUsers", onlineUsers);
	}

	public static HashMap<String, HttpServlet> getUrlpatterns() {
		return urlpatterns;
	}

	public static void setUrlpatterns(HashMap<String, HttpServlet> urlpatterns) {
		ApplicationScope.urlpatterns = urlpatterns;
		servletContext.setAttribute("urlpatterns", urlpatterns);
	}

	public static File getAppRoot() {
		return appRoot;
	}

	public static void setAppRoot(File appRoot) {
		ApplicationScope.appRoot = appRoot;
		servletContext.setAttribute("appRoot", appRoot);
	}

	public static String getBuilt() {
		if (ApplicationScope.built == null) {
			setBuilt();
		}
		return ApplicationScope.built;
	}

	public static void setBuilt() {
		ApplicationScope.built = new SimpleDateFormat("yyMMdd")
				.format(new Date(ApplicationScope.getAppRoot().lastModified()));
		servletContext.setAttribute("built", ApplicationScope.built);
	}

}
