package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.Services.ArticleDAO;
import jiangsir.zerobb.Services.UserDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

@WebServlet(urlPatterns = { "/History" }, name = "History.do")
public class History extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4817625736720518852L;
	public static String urlpattern = History.class.getAnnotation(
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
		} catch (Exception e) {
			// TODO: handle exception
		}
		// String[] infos = (String[]) request.getParameterValues("info");
		// String division = request.getParameter("division");
		String[] param_infos = (String[]) request.getParameterValues("info");
		String param_division = request.getParameter("division");
		User.DIVISION division = User.DIVISION.none;
		if (param_division != null) {
			try {
				division = User.DIVISION.valueOf(param_division);
			} catch (Exception e) {
				e.printStackTrace();
				division = User.DIVISION.jiaowu;
			}
		}
		Article.INFO[] infos = {};
		if (param_infos != null) {
			infos = new Article.INFO[param_infos.length];
			for (int i = 0; i < param_infos.length; i++) {
				infos[i] = Article.INFO.valueOf(param_infos[i]);
			}
		}

		request.setAttribute("articles",
				new ArticleDAO().getOutdateArticles(infos, division, page));
		request.setAttribute("page", page);
		request.setAttribute("querystring",
				Utils.querystingFilter(request.getQueryString()));
		request.setAttribute("divisions", new UserDAO().getDivisions());
		request.getRequestDispatcher("Index.jsp").forward(request, response);
	}
}
