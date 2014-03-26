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
		// this.daemon = new Daemon();
		// Thread daemonthread = new Thread(daemon);
		// daemonthread.start();
		// qx 暫不進行應用程式初始化<br>
		// qx 可考慮在此將 properties.xml 全部讀入 Context Initialized
		ServletContext context = event.getServletContext();
		ApplicationScope.setAllAttributes(context);

		Map<String, ? extends ServletRegistration> registrations = context
				.getServletRegistrations();
		for (String key : registrations.keySet()) {
			String servletClassName = registrations.get(key).getClassName();
			WebServlet webServlet;
			try {
				if (Class.forName(servletClassName).newInstance() instanceof HttpServlet) {
					HttpServlet httpServlet = (HttpServlet) Class.forName(
							servletClassName).newInstance();
					webServlet = httpServlet.getClass().getAnnotation(
							WebServlet.class);
					if (webServlet != null) {
						for (String urlpattern : webServlet.urlPatterns()) {
							ApplicationScope.getUrlpatterns().put(urlpattern,
									httpServlet);
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
			System.out.println("urlpattern=" + urlpattern + ", servlet="
					+ ApplicationScope.getUrlpatterns().get(urlpattern));
		}
		context.setAttribute("urlpatterns", ApplicationScope.getUrlpatterns());

		ENV.context = context;
		ENV.LastContextRestart = new Date();
		ENV.setAPP_REAL_PATH(context.getRealPath("/"));
		ENV.setAPP_NAME(context.getContextPath());
		// ENV.setMyPropertiesPath(ENV.APP_REAL_PATH +
		// "META-INF/properties.xml");
		// ENV.resource = ResourceBundle.getBundle("resource");
		// context.setAttribute("OnlineUsers",
		// new Hashtable<String, HttpSession>());
		// new GeneralDAO().getConnection();

		// ENV.divisionmap.put("jiaowu", "教務處");
		// ENV.divisionmap.put("xuewu", "學務處");
		// ENV.divisionmap.put("zongwu", "總務處");
		// ENV.divisionmap.put("fudao", "輔導室");
		// ENV.divisionmap.put("renshi", "人事室");
		// ENV.divisionmap.put("kuaiji", "會計室");
		// ENV.divisionmap.put("lib", "圖資中心");
		// ENV.divisionmap.put("zhucezu", "註冊組");
		// ENV.divisionmap.put("shebei", "設備組");
		// ENV.divisionmap.put("jiankang", "健康中心");
		// ENV.divisionmap.put("documentation", "公文公告區");
		// ENV.divisionmap.put("zixun", "資訊組");
		// ENV.divisionmap.put("jiaoxue", "教學組");
		// ENV.divisionmap.put("weisheng", "學務處衛保組");
		// ENV.divisionmap.put("teachers", "教師研習");
		// ENV.divisionmap.put("xuewuhonor", "學務處榮譽榜");
		// ENV.divisionmap.put("principal", "校長室");
		// ENV.divisionmap.put("schoolnews", "校園新聞");
		// ENV.divisionmap.put("honoredlist", "榮譽榜");
		// ENV.divisionmap.put("alumni", "校友會");
		// ENV.divisionmap.put("shebei", "設備組");

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
