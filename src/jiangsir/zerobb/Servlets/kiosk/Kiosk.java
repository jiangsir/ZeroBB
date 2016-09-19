package jiangsir.zerobb.Servlets.kiosk;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.zerobb.Services.ArticleService;
import jiangsir.zerobb.Services.TagDAO;
import jiangsir.zerobb.Services.UserDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

@WebServlet(urlPatterns = {"/Kiosk"}, name = "Kiosk.do")
public class Kiosk extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2143360587297486944L;
	public static String urlpattern = Kiosk.class.getAnnotation(WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Article> articles = new ArticleService().getArticlesByInfo(new Article.INFO[]{Article.INFO.HEADLINE},
				1, 10);

		request.getRequestDispatcher("Kiosk.jsp").forward(request, response);
	}
}
