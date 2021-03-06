package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.zerobb.Services.ArticleService;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = {"/Include"})
public class Include extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3887757319081296719L;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	public enum PATTERN {
		HEADLINE, // 頭條
		IMPORTANT, // 重要
		ACCOUNT, // 根據帳號
		DIVISION, // 根據處室
		NEWS, // 新聞
		TAGS;// 根據 Tag
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String p = (String) request.getParameter("p");
		PATTERN pattern;
		try {
			pattern = PATTERN.valueOf(request.getParameter("p"));
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println(
					"參數有誤！/Include?p={" + Arrays.toString(PATTERN.values()) + "}&" + "account={jiaowu, xuewu}");
			// String s = "";
			// for (PATTERN pp : PATTERN.values()) {
			// if (s.equals("")) {
			// s += pp;
			// } else {
			// s += ", " + pp;
			// }
			// }
			// // throw new DataException("參數有誤！ pattern=" + s);
			// response.getWriter().println("參數有誤！ pattern=" + s);
			return;
		}
		User.DIVISION division;
		try {
			division = User.DIVISION.valueOf(request.getParameter("division"));
		} catch (Exception e) {
			// e.printStackTrace();
			division = User.DIVISION.none;
		}

		switch (pattern) {
			case ACCOUNT :
				request.setAttribute("articles", new ArticleService().getArticlesByDivision(division, 1, 10));
				request.getRequestDispatcher("include/ACCOUNT.jsp").forward(request, response);
				return;
			case DIVISION :
				division = User.DIVISION.valueOf(request.getParameter("division"));
				request.setAttribute("articles", new ArticleService().getArticlesByInfoDivision(
						new Article.INFO[]{Article.INFO.HEADLINE, Article.INFO.IMPORTANT}, division, 1, 10));
				request.getRequestDispatcher("include/DIVISION.jsp").forward(request, response);
				return;
			case HEADLINE :
				request.setAttribute("articles", new ArticleService().getHeadLines());
				request.getRequestDispatcher("include/HEADLINE.jsp").forward(request, response);
				return;
			case IMPORTANT :
				request.setAttribute("articles", new ArticleService()
						.getArticlesByInfoDivision(new Article.INFO[]{Article.INFO.IMPORTANT}, division, 1, 5));
				request.getRequestDispatcher("include/IMPORTANT.jsp").forward(request, response);
				return;
			case NEWS :
				request.setAttribute("articles", new ArticleService().getArticlesByDivision(division, 1, 10));
				request.getRequestDispatcher("include/NEWS.jsp").forward(request, response);
				return;
			case TAGS :
				String[] tagnames = request.getParameterValues("tagname");
				request.setAttribute("articles", new ArticleService().getArticlesByTabnames(
						new Article.INFO[]{Article.INFO.HEADLINE, Article.INFO.IMPORTANT}, tagnames, 0, 6));
				request.getRequestDispatcher("include/TAGS.jsp").forward(request, response);
				return;
			default :
				break;

		}

	}
}
