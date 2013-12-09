package jiangsir.zerobb.Servlets.Ajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jiangsir.zerobb.DAOs.UpfileDAO;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/UpdateOldfilepath" }, name = "UpdateOldfilepath.ajax")
public class UpdateOldfilepathServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5771715224845178881L;
	public static String urlpattern = UpdateOldfilepathServlet.class
			.getAnnotation(WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		new UpfileDAO().updateOldfilepath();
		return;
	}
}
