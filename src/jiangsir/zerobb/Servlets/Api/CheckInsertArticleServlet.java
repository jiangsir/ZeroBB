package jiangsir.zerobb.Servlets.Api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jiangsir.zerobb.Exceptions.AlertException;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/CheckInsertArticle" }, name = "CheckInsertArticle.do")
public class CheckInsertArticleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336123130335663471L;
	public static String urlpattern = CheckInsertArticleServlet.class
			.getAnnotation(WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Article article = new Article();
		try {
			article.setTitle(request.getParameter("title"));
			article.setPostdate(request.getParameter("postdate"));
			article.setOutdate(request.getParameter("outdate"));
		} catch (AlertException e) {
			e.printStackTrace();
			response.getWriter().print(e.getLocalizedMessage());
			return;
		}
		response.getWriter().print("");
		return;
	}
}
