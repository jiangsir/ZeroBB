/**
 * idv.jiangsir.api - GetCSV.java
 * 2011/6/17 下午3:39:34
 * nknush-001
 */
package jiangsir.zerobb.Servlets.Api;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tools.ENV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author nknush-001
 * 
 */
@WebServlet(urlPatterns = { "/GetJson" }, name = "GetJson.do")
public class GetJson extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String urlpattern = GetJson.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	private String GET_HEADLINES = "headlines";
	private String GET_TAGS = "tags";

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
			JSONArray json_articles = new JSONArray();
			for (Article article : articleDao.getArticles(
					new Article.INFO[] { Article.INFO.頭條 }, null, 1, 10)) {
				JSONObject json_article = new JSONObject();
				try {
					json_article.put("articleid", article.getId());
					json_article.put("info", article.getInfo());
					json_article.put("title", article.getTitle());
					json_article.put("account", article.getAccount());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// System.out.println("json=" + json_article.toString());
				json_articles.put(json_article);
				// response.getWriter().print(json_article.toString());
			}
			response.getWriter().print(json_articles.toString());
		} else if (this.GET_TAGS.equals(action)) {
			String[] tags = request.getParameterValues("tagname");
			Iterator<Article> articles = articleDao.getArticles(tags, 0, 10)
					.iterator();
			JSONArray json_articles = new JSONArray();

			while (articles.hasNext()) {
				Article article = articles.next();
				JSONObject json_article = new JSONObject();
				try {
					json_article.put("articleid", article.getId());
					json_article.put("info", article.getInfo());
					json_article.put("title", article.getTitle());
					json_article.put("account", article.getAccount());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// System.out.println("json=" + json_article.toString());
				json_articles.put(json_article);
				// response.getWriter().print(json_article.toString());
			}
			response.getWriter().print(json_articles.toString());
		} else {
			response.getWriter().println("parameter 有誤！");
			response.getWriter().println(
					"使用範例：/ZeroBB/api/get.json?a={\"headlines\", \"tags\"}");
		}
	}
}
