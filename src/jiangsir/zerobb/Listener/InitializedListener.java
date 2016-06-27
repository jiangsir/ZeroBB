package jiangsir.zerobb.Listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import jiangsir.zerobb.Scopes.ApplicationScope;
import jiangsir.zerobb.Tools.*;

@WebListener
public class InitializedListener implements ServletContextListener {

	/**
	 *
	 */
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext context = event.getServletContext();
			ApplicationScope.setAllAttributes(context);

			Map<String, ? extends ServletRegistration> registrations = context.getServletRegistrations();
			for (String key : registrations.keySet()) {
				String servletClassName = registrations.get(key).getClassName();
				WebServlet webServlet;
				try {
					if (Class.forName(servletClassName).newInstance() instanceof HttpServlet) {
						HttpServlet httpServlet = (HttpServlet) Class.forName(servletClassName).newInstance();
						webServlet = httpServlet.getClass().getAnnotation(WebServlet.class);
						if (webServlet != null) {
							for (String urlpattern : webServlet.urlPatterns()) {
								ApplicationScope.getUrlpatterns().put(urlpattern, httpServlet);
							}
						}
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			for (String urlpattern : ApplicationScope.getUrlpatterns().keySet()) {
				System.out.println(
						"urlpattern=" + urlpattern + ", servlet=" + ApplicationScope.getUrlpatterns().get(urlpattern));
			}
			context.setAttribute("urlpatterns", ApplicationScope.getUrlpatterns());

			ENV.context = context;
			ENV.LastContextRestart = new Date();
			ENV.setAPP_REAL_PATH(context.getRealPath("/"));
			ENV.setAPP_NAME(context.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		while (!ENV.ThreadPool.isEmpty()) {
			Thread thread = ENV.ThreadPool.get(ENV.ThreadPool.firstKey());
			thread.interrupt();
			// System.out.println("關閉 thread: " + thread);
			ENV.ThreadPool.remove(ENV.ThreadPool.firstKey());
		}
		// TODO_DONE! qx 結束連結

		Connection conn = (Connection) context.getAttribute("conn");
		context.removeAttribute("conn");
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
			System.out.println(ENV.logHeader() + "資料庫連結關閉");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
