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
import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Interfaces.IAccessible;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/Touch" }, name = "Touch.do")
public class TouchServlet extends HttpServlet implements IAccessible {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4578239873969391214L;
	public static String urlpattern = TouchServlet.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];
	Article article;

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
		String session_account = (String) session
				.getAttribute("session_account");
		int articleid = Integer.parseInt(request.getParameter("articleid"));
		article = new ArticleDAO().getArticle(articleid);
		System.out.println("session_account=" + session_account);
		if (session_account != null
				&& !session_account.equals("")
				&& !article.getAccount().equals("")
				&& ("admin".equals(session_account) || session_account
						.equals(article.getAccount()))) {
			return true;
		}
		throw new AccessException(session_account, "您沒有存取權限！");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// if (!this.isAccessable(request, response)) {
		// response.getWriter().print(
		// Utils.parseDatetime(article.getPostdate().getTime()));
		// return;
		// }
		int articleid = Integer.parseInt(request.getParameter("articleid"));
		ArticleDAO articleDao = new ArticleDAO();
		Article article = articleDao.getArticle(articleid);
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
