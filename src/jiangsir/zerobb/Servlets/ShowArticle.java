package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.Beans.AlertBean;
import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.Article_TagDAO;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tools.AlertDispatcher;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/ShowArticle" }, name = "ShowArticle.do")
public class ShowArticle extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4378081894758609314L;
	public static String urlpattern = ShowArticle.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int articleid = Integer.parseInt(request.getParameter("id"));
		// int articleid = Integer.parseInt(request.getParameter("articleid"));
		HttpSession session = request.getSession(false);
		String session_account = (String) session
				.getAttribute("session_account");
		Article article;
		try {
			article = new ArticleDAO().getArticle(
					new UserDAO().getUser(session_account), articleid);
		} catch (DataException e) {
			e.printStackTrace();
			new AlertDispatcher(request, response).forward(new AlertBean(e));
			return;
		}
		request.setAttribute("article", article);
		article.setHitnum(article.getHitnum() + 1);
		// new ArticleDAO().updateHitnum(articleid);
		// new ArticleDAO().update_PSTMT(article);
		try {
			new ArticleDAO().update(article);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("article_tags",
				new Article_TagDAO().getArticle_TagNames(articleid));
		request.setAttribute("tags", new Article_TagDAO().getTags(articleid));
		request.getRequestDispatcher("ShowArticle.jsp").forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
}
