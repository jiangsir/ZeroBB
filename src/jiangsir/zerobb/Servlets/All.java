package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

@WebServlet(urlPatterns = { "/All" }, name = "All.do")
public class All extends HttpServlet {
	public static String urlpattern = All.class.getAnnotation(WebServlet.class)
			.urlPatterns()[0];

	/**
	 * 
	 */
	private static final long serialVersionUID = -5743244963652446413L;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		String by = (String) request.getParameter("by");
		request.setAttribute("articles",
				new ArticleDAO().getAllArticles(by, page, ENV.getPAGESIZE()));
		request.setAttribute("page", page);
		request.setAttribute("querystring",
				Utils.querystingFilter(request.getQueryString()));
		request.getRequestDispatcher("Index.jsp").forward(request, response);
	}
}
