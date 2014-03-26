/**
 * idv.jiangsir.api - GetCSV.java
 * 2011/6/17 下午3:39:34
 * nknush-001
 */
package jiangsir.zerobb.Servlets.Api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;

/**
 * @author nknush-001
 * 
 */
@WebServlet(urlPatterns = { "/GetCSV" }, name = "GetCSV.do")
public class GetCSV extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String urlpattern = GetCSV.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	private String GET_HEADLINES = "headlines";

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ArticleDAO articleDao = new ArticleDAO();
		String action = request.getParameter("a");

		if (this.GET_HEADLINES.equals(action)) {
			StringBuffer csv = new StringBuffer(5000);
			for (Article article : articleDao.getArticles(
					new String[] { Article.info_HEADLINE }, null, 1, 10)) {
				User user = new UserDAO().getUserByAccount(article.getAccount());
				csv.append(article.getTitle() + ",ShowArticle?id="
						+ article.getId() + "," + user.getName() + "\n");
				response.getWriter().print(csv.toString());
			}
		} else {
			response.getWriter().println("parameter 有誤！");
			response.getWriter().println(
					"使用範例：/ZeroBB/api/get.csv?a={\"headlines\"}");
		}
	}
}
