package jiangsir.zerobb.Listener;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;

@WebListener
public class MyHttpSessionListener implements
	javax.servlet.http.HttpSessionListener, ServletRequestListener {

    private HttpServletRequest request;

    public void requestDestroyed(ServletRequestEvent event) {

    }

    public void requestInitialized(ServletRequestEvent event) {
	request = (HttpServletRequest) event.getServletRequest();
    }

    public void sessionCreated(HttpSessionEvent event) {
	HttpSession session = event.getSession();
	// TODO 20090210 在此分析 IP 連線數
	String ipfrom = request.getRemoteAddr();
	session.setAttribute("remoteAddr", ipfrom);
	session.setAttribute("Locale", request.getLocale().toString());

	synchronized (ENV.OnlineSessions) {
	    if (session != null && session.getId() != null) {
		ENV.OnlineSessions.put(session.getId(), session);
	    }
	}
	// qx request.getHeader 一定得在 Listener 裡
	HashMap<String, String> loggingmap = new HashMap<String, String>();
	Enumeration<String> enu = request.getHeaderNames();
	while (enu.hasMoreElements()) {
	    String HeaderName = enu.nextElement().toString();
	    // qx 觀察 getHeader
	    // System.out.println(ENV.logHeader() + HeaderName + " = "
	    // + request.getHeader(HeaderName));
	    loggingmap.put(HeaderName, request.getHeader(HeaderName));
	}

    }

    public void sessionDestroyed(HttpSessionEvent event) {
	String ipfrom = request.getRemoteAddr();
	// if (ENV.IP_CONNECTION.containsKey(ipfrom)
	// && ENV.IP_CONNECTION.get(ipfrom) > 1) {
	// ENV.IP_CONNECTION.put(ipfrom, ENV.IP_CONNECTION.get(ipfrom) - 1);
	// } else {
	// ENV.IP_CONNECTION.remove(ipfrom);
	// }
	HttpSession session = event.getSession();
	String sessionid = session.getId();
	User userObject = (User) session.getAttribute("UserObject");
	if (userObject == null) {
	    String session_account = (String) session
		    .getAttribute("session_account");
	    userObject = new UserDAO().getUser(session_account);
	}
	userObject.Logout(session);
	synchronized (ENV.OnlineSessions) {
	    ENV.OnlineSessions.remove(sessionid);
	}
    }

}
