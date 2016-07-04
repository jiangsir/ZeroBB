package jiangsir.zerobb.Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jiangsir.zerobb.Scopes.ApplicationScope;
import jiangsir.zerobb.Scopes.SessionScope;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter(filterName = "HistoryFilter", urlPatterns = {"/*"}, asyncSupported = true)
public class HistoryFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public HistoryFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession(false);
		String servletPath = request.getServletPath();

		HttpServlet httpServlet = ApplicationScope.getUrlpatterns().get(servletPath);
		if (httpServlet != null && !servletPath.matches("\\.api")) {
			new SessionScope(session).addHistory(servletPath, request.getQueryString());
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
