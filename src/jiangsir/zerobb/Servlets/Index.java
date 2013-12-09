package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.TagDAO;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

@WebServlet(urlPatterns = { "/Index" }, name = "Index.do")
public class Index extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2143360587297486944L;
	public static String urlpattern = Index.class.getAnnotation(
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
		} catch (NumberFormatException e) {
		}
		// if (page <= 0) {
		// Message message = new Message();
		// message.setType(Message.MessageType_ERROR);
		// message.setPlainTitle("page 參數必須為正整數");
		// request.setAttribute("message", message);
		// request.getRequestDispatcher("Message.jsp").forward(request,
		// response);
		// return;
		// }

		String[] infos = (String[]) request.getParameterValues("info");
		String division = request.getParameter("division");
		String tagname = request.getParameter("tagname");
		if (infos != null || division != null) {
			if (division == null) {
				division = "";
			} else {
				// 這裡只要將 tomcat6 裡面的 seerver.xml connector設定裡面加上一行
				// URIEncoding="UTF-8"
				// 即可。就不需要在程式內處理中文編碼問題。
				// 而且如果目標 tomcat6 已經設定了 URLEncoding 這樣寫反而會錯。
				// division = new String(division.getBytes("ISO-8859-1"),
				// "UTF-8");
			}
			if (infos == null) {
				infos = new String[] {};
			} else {
				for (int i = 0; i < infos.length; i++) {
					// infos[i] = new String(infos[i].getBytes("ISO-8859-1"),
					// "UTF-8");
				}
			}
			request.setAttribute(
					"articles",
					new ArticleDAO().getArticles(infos, division, page,
							ENV.getPAGESIZE()));
		} else if (tagname != null) {
			request.setAttribute("articles", new ArticleDAO().getArticles(
					new String[] { tagname }, page, ENV.getPAGESIZE()));
		} else {
			request.setAttribute(
					"articles",
					new ArticleDAO().getArticles(null, null, page,
							ENV.getPAGESIZE()));
		}
		request.setAttribute("tags", new TagDAO().getTags());
		request.setAttribute("page", page);
		request.setAttribute("querystring",
				Utils.querystingFilter(request.getQueryString()));
		request.setAttribute("divisions", new UserDAO().getDivisions());
		request.getRequestDispatcher("Index.jsp").forward(request, response);
	}
}
