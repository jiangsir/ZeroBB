package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import jiangsir.zerobb.DAOs.DBUpdateDAO;
import jiangsir.zerobb.JSON.Schema;
import jiangsir.zerobb.Tools.ENV;

/**
 * Servlet implementation class DBUpdate
 */
@WebServlet(urlPatterns = { "/DBUpdate" }, name = "DBUpdate.do")
public class DBUpdate extends HttpServlet {
	public static String urlpattern = DBUpdate.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	private static final long serialVersionUID = 1L;
	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBUpdate() {
		super();
		// TODO Auto-generated constructor stub
	}

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
		DBUpdateDAO updateDao = new DBUpdateDAO();
		Schema schema = updateDao.getSchema();
		// updateDao.updateDefault_tags();

		response.getWriter().write(mapper.writeValueAsString(schema));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
