package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.Article_TagDAO;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/ShowArticle" }, name = "ShowArticle.do")
public class ShowArticle extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4378081894758609314L;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int articleid = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		Article article = new ArticleDAO().getArticle(currentUser, articleid);
		request.setAttribute("article", article);
		article.setHitnum(article.getHitnum() + 1);
		try {
			new ArticleDAO().update(article);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
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
