package jiangsir.zerobb.Servlets.Api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Interfaces.IAccessFilter;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Services.ArticleDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/Touch.api" })
public class TouchServlet extends HttpServlet implements IAccessFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4578239873969391214L;

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
	public void AccessFilter(HttpServletRequest request) throws AccessException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		Article article = new ArticleDAO().getArticleById(request
				.getParameter("articleid"));
		if (!article.isUpdatable(currentUser)) {
			throw new AccessException("您(" + currentUser.getAccount()
					+ ") 不能 touch 本題目。");
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int articleid = Integer.parseInt(request.getParameter("articleid"));
		ArticleDAO articleDao = new ArticleDAO();
		Article article = articleDao.getArticleById(articleid);
		article.setSortable(Calendar.getInstance().getTimeInMillis());
		try {
			articleDao.update(article);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getWriter().print(Calendar.getInstance().getTimeInMillis());
		return;
	}

}
