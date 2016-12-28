package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiangsir.zerobb.Annotations.RoleSetting;
import jiangsir.zerobb.Exceptions.Alert;
import jiangsir.zerobb.Exceptions.AlertException;
import jiangsir.zerobb.Exceptions.JQueryException;
import jiangsir.zerobb.Scopes.ApplicationScope;
import jiangsir.zerobb.Services.AppConfigService;
import jiangsir.zerobb.Tables.AppConfig;

/**
 * Servlet implementation class EditAppConfigServlet
 */
@MultipartConfig
@WebServlet(urlPatterns = {"/EditAppConfig"})
@RoleSetting
public class EditAppConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getAnonymousLogger();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditAppConfigServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("EditAppConfig.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AppConfig appConfig = ApplicationScope.getAppConfig();
		for (String name : request.getParameterMap().keySet()) {
			logger.info("name=" + name + ", request.getParameter(name)=" + request.getParameter(name));
			try {
				Method method = appConfig.getClass()
						.getMethod("set" + name.toUpperCase().substring(0, 1) + name.substring(1), String.class);
				method.invoke(appConfig, new Object[]{(String) request.getParameter(name)});
			} catch (SecurityException e) {
				throw new JQueryException(new Alert(e));
			} catch (NoSuchMethodException e) {
			} catch (IllegalArgumentException e) {
				throw new JQueryException(new Alert(e));
			} catch (IllegalAccessException e) {
				throw new JQueryException(new Alert(e));
			} catch (InvocationTargetException e) {
				throw new JQueryException(new Alert(e.getTargetException()));
			}
		}
		new AppConfigService().insert(appConfig);
		ApplicationScope.setAppConfig(appConfig);

		// response.sendRedirect(request.getContextPath() + new
		// SessionScope(request).getPreviousPage());
		return;
	}

}
