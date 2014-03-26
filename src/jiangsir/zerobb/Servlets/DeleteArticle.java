package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.Article_TagDAO;
import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Interfaces.IAccessible;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/DeleteUpdate" }, name = "DeleteUpdate.do")
public class DeleteArticle extends HttpServlet implements IAccessible {
	public static String urlpattern = DeleteArticle.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];
	Article article;

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
	public boolean isAccessible(HttpServletRequest request)
			throws AccessException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		Article article = new ArticleDAO().getArticleById(request
				.getParameter("articleid"));
		try {
			return article.isUpdatable(currentUser);
		} catch (DataException e) {
			e.printStackTrace();

			// throw new AccessException(currentUser.getAccount(), "您("
			// + currentUser.getAccount() + ") 不能編輯本題目。");
			throw new AccessException("您(" + currentUser.getAccount()
					+ ") 不能刪除本題目。", e);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ArticleDAO articleDao = new ArticleDAO();
		Article_TagDAO article_TagDao = new Article_TagDAO();
		int articleid = Integer.parseInt(request.getParameter("articleid"));
		Article article = articleDao.getArticleById(articleid);
		article.setVisible(false);
		try {
			articleDao.update(article);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		article_TagDao.delete(articleid);

		response.sendRedirect("./");
	}

}
