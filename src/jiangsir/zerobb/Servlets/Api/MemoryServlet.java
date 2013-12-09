package jiangsir.zerobb.Servlets.Api;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.Tools.ENV;

import org.codehaus.jackson.map.ObjectMapper;

@WebServlet(urlPatterns = { "/Memory" }, name = "Memory.do")
public class MemoryServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6844158620543006799L;
	public static String urlpattern = MemoryServlet.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> memorymap = new HashMap<String, String>();
		Runtime runtime = Runtime.getRuntime();
		int danwei = 1024 * 1024;
		memorymap.put("danwei", "MB");
		memorymap.put("freeMemory",
				String.valueOf(runtime.freeMemory() / danwei));
		memorymap.put("totalMemory",
				String.valueOf(runtime.totalMemory() / danwei));
		response.getWriter().print(mapper.writeValueAsString(memorymap));
	}

}
