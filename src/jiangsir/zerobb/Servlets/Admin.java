package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.Annotations.RoleSetting;
import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Interfaces.IAccessFilter;
import jiangsir.zerobb.Services.TagDAO;
import jiangsir.zerobb.Services.UserDAO;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tables.User;

@WebServlet(urlPatterns = { "/Admin" })
@RoleSetting(allowHigherThen = User.ROLE.ADMIN)
public class Admin extends HttpServlet implements IAccessFilter {
	// public static String urlpattern = Admin.class.getAnnotation(
	// WebServlet.class).urlPatterns()[0];
	private static final long serialVersionUID = -3241402006500382488L;

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
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		String by = (String) request.getParameter("by");
		// request.setAttribute("articles",
		// new ArticleDAO().getAllArticles(by, page, ENV.getPAGESIZE()));
		// request.setAttribute("upfiles",
		// new UpfileDAO().getNullBlobUpfiles(1, 0));
		Runtime runtime = Runtime.getRuntime();
		request.setAttribute("freeMemory", runtime.freeMemory());
		request.setAttribute("maxMemory", runtime.maxMemory());
		request.setAttribute("totalMemory", runtime.totalMemory());
		request.setAttribute("tags", new TagDAO().getTags());
		request.setAttribute("users", new UserDAO().getUsers());
		request.getRequestDispatcher("Admin.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
