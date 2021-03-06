package jiangsir.zerobb.Filter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import jiangsir.zerobb.Exceptions.Alert;
import jiangsir.zerobb.Exceptions.JQueryException;
import jiangsir.zerobb.Scopes.SessionScope;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter(filterName = "ExceptionHandlerFilter", urlPatterns = {"/*"}, asyncSupported = true)
public class ExceptionHandlerFilter implements Filter {
	Logger logger = Logger.getAnonymousLogger();

	/**
	 * Default constructor.
	 */
	public ExceptionHandlerFilter() {
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
		try {
			chain.doFilter(req, resp);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getLocalizedMessage() + " | " + e.getMessage());
			e.printStackTrace();
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) resp;
			HttpSession session = request.getSession(false);

			Throwable rootCause = e;
			Alert alert = new Alert(e);
			alert.getDebugs().add("由 " + this.getClass().getSimpleName() + " 所捕捉");
			ArrayList<String> list = alert.getList();
			while (rootCause.getCause() != null) {
				list.add(rootCause.getClass().getSimpleName() + ": " + rootCause.getLocalizedMessage());
				rootCause = rootCause.getCause();
			}

			if (e instanceof JQueryException) {
				alert = ((JQueryException) e).getAlert();
				ObjectMapper mapper = new ObjectMapper();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write(mapper.writeValueAsString(alert));
				response.flushBuffer();
				return;
			} else {
				try {
					alert.getUris().put("回前頁", new URI("./" + new SessionScope(session).getPreviousPage()));
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
				request.setAttribute("alert", alert);
				request.getRequestDispatcher("/Alert.jsp").forward(request, response);
				return;
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
