package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.TagDAO;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

@WebServlet(urlPatterns = { "/Index" }, name = "Index.do")
public class Index extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2143360587297486944L;
	public static String urlpattern = Index.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

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
		} catch (NumberFormatException e) {
		}

		// String[] param_infos = (String[]) request.getParameterValues("info");
		String param_division = request.getParameter("division");
		String tagname = request.getParameter("tagname");
		if (param_division != null) {
			User.DIVISION division;
			try {
				division = User.DIVISION.valueOf(request
						.getParameter("division"));
			} catch (Exception e) {
				e.printStackTrace();
				division = User.DIVISION.jiaowu;
			}
			// Article.INFO[] infos = new Article.INFO[param_infos.length];
			// for (int i = 0; i < param_infos.length; i++) {
			// infos[i] = Article.INFO.valueOf(param_infos[i]);
			// }
			request.setAttribute(
					"articles",
					new ArticleDAO().getArticles(null, division, page,
							ENV.getPAGESIZE()));
		} else if (tagname != null) {
			request.setAttribute("articles", new ArticleDAO().getArticles(
					new String[] { tagname }, page, ENV.getPAGESIZE()));
		} else {
			request.setAttribute(
					"articles",
					new ArticleDAO().getArticles(null, null, page,
							ENV.getPAGESIZE()));
		}
		request.setAttribute("tags", new TagDAO().getTags());
		request.setAttribute("page", page);
		request.setAttribute("querystring",
				Utils.querystingFilter(request.getQueryString()));
		request.setAttribute("divisions", new UserDAO().getDivisions());
		request.getRequestDispatcher("Index.jsp").forward(request, response);
	}
}
