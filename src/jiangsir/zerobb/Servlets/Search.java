package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

@WebServlet(urlPatterns = { "/Search" }, name = "Search.do")
public class Search extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3805355353616928055L;

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
		String keyword = request.getParameter("keyword");
		request.setAttribute("page", page);
		request.setAttribute("querystring",
				Utils.querystingFilter(request.getQueryString()));
		request.setAttribute("divisions", new UserDAO().getDivisions());
		request.setAttribute(
				"articles",
				new ArticleDAO().searchArticles(keyword, page,
						ENV.getPAGESIZE()));
		request.getRequestDispatcher("Index.jsp").forward(request, response);
	}
}
