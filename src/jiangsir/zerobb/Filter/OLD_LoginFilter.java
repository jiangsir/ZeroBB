package jiangsir.zerobb.Filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.Beans.AlertBean;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Servlets.LoginServlet;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

public class OLD_LoginFilter implements Filter {

	private String FilterName;

	public void init(FilterConfig config) throws ServletException {
		FilterName = config.getFilterName();
		config.getServletContext().log(FilterName + "進入初始化...");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String fromUrl = request.getHeader("REFERER");// 取得上一级页面的RUL
		System.out.println("從 " + fromUrl + "進入 LoginFilter");

		HttpSession session = request.getSession(false);
		// qx 測試在 filter 內部再來過濾 如 regex 的判斷
		// String requestURI = request.getRequestURI();
		String servletPath = request.getServletPath();
		// String urlpattern = requestURI
		// .substring(requestURI.lastIndexOf('/') + 1);
		System.out.println("servletPath=" + servletPath + ", serveltname="
				+ ", serveltclass=");
		// String loginURI = new WebXmlParser().getUrlpattern(Login.class);
		// String loginURI = ;

		if (session != null) {
			String passed = (String) session.getAttribute("passed");
			String privilege = (String) session
					.getAttribute("session_privilege");
			String parseprivilege = Utils
					.parsePrivilege(privilege, servletPath);
			System.out.println("passed=" + passed + ", session_privilege="
					+ privilege + ", parseprivilege=" + parseprivilege
					+ ", servletPath=" + servletPath);
			if (privilege != null && !"allowed".equals(parseprivilege)) {
				// String[] param = { requestURI };
				// request.setAttribute("ResourceMessage_param", param);
				// request.setAttribute("ResourceMessage", parseprivilege);
				// Message message = new Message();
				// message.setType(Message.MessageType_ERROR);
				// message.setPlainTitle("您沒有權限處理!!");
				// request.setAttribute("message", message);
				// request.getRequestDispatcher("Message.jsp").forward(request,
				// response);
				// new AlertDispatcher(request, response).forward(new AlertBean(
				// "您沒有權限處理!!"));
				// return;
				throw new DataException("您沒有權限處理!!");
			} else if ("allowed".equals(parseprivilege)) {
				Class<?> servletclass;
				boolean isAccessible = false;
				try {
					// servletclass = Class.forName(new WebXmlParser()
					// .getServletClass(request.getServletPath()));
					servletclass = ENV.getServlets().get(servletPath);
					Method m = servletclass.getMethod("isAccessible",
							new Class[] { HttpServletRequest.class });
					isAccessible = (Boolean) m.invoke(
							servletclass.newInstance(),
							new Object[] { request });
					System.out.println("isAccessible=" + isAccessible);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					// Message message = new Message(request, e);
					// message.setPlainTitle(e.getLocalizedMessage());
					// request.setAttribute("message", message);
					// request.getRequestDispatcher("Message.jsp").forward(
					// request, response);
					// new AlertDispatcher(request, response)
					// .forward(new AlertBean(e));
					// return;
					throw new DataException(e);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					// Message message = new Message(request, e);
					// AccessCause cause = (AccessCause) e.getCause();
					// message.setPlainTitle(cause.getLocalizedMessage());
					// request.setAttribute("message", message);
					// request.getRequestDispatcher("Message.jsp").forward(
					// request, response);
					// new AlertDispatcher(request, response)
					// .forward(new AlertBean(e));
					// return;
					throw new DataException(e);
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
				if (isAccessible) {
					chain.doFilter(request, response);
				}

				return;
			} else if ("passing".equals(passed)) {
				if (servletPath.equals(LoginServlet.class.getAnnotation(
						WebServlet.class).urlPatterns()[0])) {
					chain.doFilter(request, response);
					return;
				}
			} else {
				System.out.println(ENV.logHeader() + "LoginFilter 進入 Unknown");
				// HashMap Userinfo = (HashMap)
				// session.getAttribute("Userinfo");
				String session_account = (String) session
						.getAttribute("session_account");
				User user = (User) session.getAttribute("UserObject");
				if (user == null) {
					user = new UserDAO().getUserByAccount(session_account);
				}
				System.out.println(ENV.logHeader() + "UserObject=" + user);
				System.out.println(ENV.logHeader() + "privilege=" + privilege);
				System.out.println(ENV.logHeader() + "passeprivilege="
						+ parseprivilege);
				System.out.println(ENV.logHeader() + "passed=" + passed);
			}
			session.removeAttribute("passed");
		}
		// StringBuffer requestURL = new StringBuffer(5000);
		// requestURL.append(request.getRequestURL());
		// qx 把經過 filter 的頁面再導回原來的頁面, 就不用處理參數傳遞的問題了
		// requestURL.append(session.getAttribute("CurrentPage"));
		// String query = request.getQueryString();
		// if (query != null) {
		// requestURL.append("?" + query); // qx 加了 ? 會否造成影響?
		// }
		// request.setAttribute("originalURI", requestURL.toString());
		// request.setAttribute("parammap", request.getParameterMap());
		// qx 在 filter 中把 前一頁的 parammap 放入 session 裡, 以便通過 Login之後取用
		// qx 暫時廢止
		// HashMap parammap = (HashMap) request.getParameterMap();
		// if (parammap.size() != 0) {
		// session.setAttribute("parammap", parammap.clone());
		// }
		if (request.getQueryString() != null) {
			servletPath += "?" + request.getQueryString();
		}
		session.setAttribute("OriginalURI", servletPath);
		request.getRequestDispatcher(
				LoginServlet.class.getAnnotation(WebServlet.class)
						.urlPatterns()[0]).forward(request, response);
	}

	public void destroy() {

	}

}
