/**
 * idv.jiangsir.api - GetCSV.java
 * 2011/6/17 下午3:39:34
 * nknush-001
 */
package jiangsir.zerobb.Servlets.Api;

import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(urlPatterns = {"/getPM25.json"}, name = "getPM25.json")
public class getPM25Servlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String urlpattern = getPM25Servlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];
	public static ArrayList<Article> articles = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
