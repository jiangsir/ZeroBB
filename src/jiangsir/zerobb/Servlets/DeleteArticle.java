package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.zerobb.Annotations.RoleSetting;
import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.Article_TagDAO;
import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Interfaces.IAccessible;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/DeleteArticle" })
@RoleSetting(allowHigherThen = User.ROLE.USER)
public class DeleteArticle extends HttpServlet implements IAccessible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2637525087132870308L;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * jiangsir.zerobb.Interfaces.IAccessible#isAccessible(javax.servlet.http
	 * .HttpServletRequest)
	 */
	public void isAccessible(HttpServletRequest request) throws AccessException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		Article article = new ArticleDAO().getArticleById(request
				.getParameter("articleid"));
		if (!article.isUpdatable(currentUser)) {
			throw new AccessException("您(" + currentUser.getAccount()
					+ ") 不能刪除本題目。");
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		ArticleDAO articleDao = new ArticleDAO();
		Article_TagDAO article_TagDao = new Article_TagDAO();
		int articleid = Integer.parseInt(request.getParameter("articleid"));
		Article article = articleDao.getArticleById(articleid);
		article.setVisible(Article.visible_FALSE);
		try {
			articleDao.update(article);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		article_TagDao.delete(articleid);

		response.sendRedirect("."
				+ new SessionScope(session).getHistories().get(0));
	}

}
