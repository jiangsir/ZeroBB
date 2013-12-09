package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.UserDAO;
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
		String[] infos = (String[]) request.getParameterValues("info");
		String division = request.getParameter("division");
		request.setAttribute("articles",
				new ArticleDAO().getOutdateArticles(infos, division, page));
		request.setAttribute("page", page);
		request.setAttribute("querystring",
				Utils.querystingFilter(request.getQueryString()));
		request.setAttribute("divisions", new UserDAO().getDivisions());
		request.getRequestDispatcher("Index.jsp").forward(request, response);
	}

}
