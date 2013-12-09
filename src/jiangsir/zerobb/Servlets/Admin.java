package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.TagDAO;
import jiangsir.zerobb.DAOs.UpfileDAO;
import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Interfaces.IAccessible;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/Admin" })
public class Admin extends HttpServlet implements IAccessible {
    public static String urlpattern = Admin.class.getAnnotation(
	    WebServlet.class).urlPatterns()[0];
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
    public boolean isAccessible(HttpServletRequest request)
	    throws AccessException {
	return true;
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
	request.getRequestDispatcher("Admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
    }

}
