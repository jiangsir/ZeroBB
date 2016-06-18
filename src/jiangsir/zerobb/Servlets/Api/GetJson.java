/**
 * idv.jiangsir.api - GetCSV.java
 * 2011/6/17 下午3:39:34
 * nknush-001
 */
package jiangsir.zerobb.Servlets.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jiangsir.zerobb.Services.ArticleService;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tools.ENV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author nknush-001
 * 
 */
@WebServlet(urlPatterns = {"/GetJson"}, name = "GetJson.do")
public class GetJson extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String urlpattern = GetJson.class.getAnnotation(WebServlet.class).urlPatterns()[0];
	public static ArrayList<Article> articles = null;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	public enum ACTION {
		headlines, // 取得頭條
		tags; // 取得分類資料。
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("a");
		switch (ACTION.valueOf(action)) {
			case headlines :
				// http://127.0.0.1:8080/GetJson?a=headlines
				JSONArray json_articles = new JSONArray();
				int min = Calendar.getInstance().get(Calendar.MINUTE);
				if (articles == null || min % 5 == 0) {
					articles = new ArticleService().getArticlesByInfo(new Article.INFO[]{Article.INFO.頭條}, 1, 10);
				}
				for (Article article : articles) {
					JSONObject json_article = new JSONObject();
					try {
						json_article.put("articleid", article.getId());
						json_article.put("info", article.getInfo());
						json_article.put("title", article.getTitle());
						json_article.put("account", article.getUser().getName());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					// System.out.println("json=" + json_article.toString());
					json_articles.put(json_article);
					// response.getWriter().print(json_article.toString());
				}
				response.getWriter().print(json_articles.toString());
				return;
			case tags :
				// http://127.0.0.1:8080/GetJson?a=tags&tagname=???&tagname=???.....
				String[] tags = request.getParameterValues("tagname");
				Iterator<Article> articles = new ArticleService().getArticlesByTabnames(null, tags, 0, 10).iterator();
				json_articles = new JSONArray();

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
				return;
			default :
				response.getWriter().println("parameter 有誤！");
				response.getWriter().println("使用範例：/ZeroBB/api/get.json?a={\"headlines\", \"tags\"}");
				return;

		}
	}
}
