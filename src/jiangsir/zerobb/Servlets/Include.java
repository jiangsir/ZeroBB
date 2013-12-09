package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/Include" }, name = "Include.do")
public class Include extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3887757319081296719L;
	public static String urlpattern = Include.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pattern = (String) request.getParameter("p");
		// String[] accounts = (String[]) request.getParameterValues("account");
		String division = request.getParameter("division");
		if (division == null) {
			division = "";
		} else {
			// division = new String(division.getBytes("ISO-8859-1"), "UTF-8");
		}
		if ("HEADLINE".equals(pattern)) {
			request.setAttribute("articles", new ArticleDAO().getArticles(
					new String[] { "頭條" }, division, 1, 10));
			request.getRequestDispatcher("include/HEADLINE.jsp").forward(
					request, response);
			return;
		} else if ("IMPORTANT".equals(pattern)) {
			request.setAttribute("articles", new ArticleDAO().getArticles(
					new String[] { "重要" }, division, 1, 5));
			request.getRequestDispatcher("include/IMPORTANT.jsp").forward(
					request, response);
			return;
		} else if ("ACCOUNT".equals(pattern)) {
			request.setAttribute("articles",
					new ArticleDAO().getArticles(null, division, 1, 10));
			request.getRequestDispatcher("include/ACCOUNT.jsp").forward(
					request, response);
			return;
		} else if ("NEWS".equals(pattern)) {
			request.setAttribute("articles",
					new ArticleDAO().getArticles(null, division, 1, 10));
			request.getRequestDispatcher("include/NEWS.jsp").forward(request,
					response);
			return;
		} else if ("TAGS".equals(pattern)) {
			String[] tagnames = request.getParameterValues("tagname");
			request.setAttribute("articles",
					new ArticleDAO().getArticles(tagnames, 0, 6));
			request.getRequestDispatcher("include/TAGS.jsp").forward(request,
					response);
			return;
		} else {
			response.getWriter()
					.println(
							"參數有誤！/ZeroBB/Include?p={\"HEADLINE\",\"IMPORTANT\",\"ACCOUNT\",\"NEWS\", \"TAGS\"}&"
									+ "account={jiaowu, xuewu}");
		}
	}
}
