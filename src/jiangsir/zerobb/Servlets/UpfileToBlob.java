package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.FileToBlob;

/**
 * Servlet implementation class UpfileToBlob
 */
@WebServlet(urlPatterns = { "/UpfileToBlob" }, name = "UpfileToBlob.do")
public class UpfileToBlob extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String urlpattern = UpfileToBlob.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		int lastupfileid = Integer.parseInt(request
				.getParameter("lastupfileid"));
		FileToBlob filetoBlob = new FileToBlob();
		String result = "";
		if (lastupfileid <= 5983) {
			result = filetoBlob.UserhomeToBlob(lastupfileid);
			result += filetoBlob.UpfilesToBlob(10323);
		} else if (lastupfileid <= 10323) {
			result = filetoBlob.UpfilesToBlob(lastupfileid);
		}
		// result = filetoBlob.UpfilesToBlob(lastupfileid);

		response.getWriter().write(result);
	}

}
