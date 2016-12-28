package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import jiangsir.zerobb.Exceptions.AlertException;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/ShowSessions" })
public class ShowSessionsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4136309018469803793L;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// Message message = new Message();
		String text = "";
		if (session == null) {
			text = "目前 session = null";
		} else {
			Enumeration<?> enumeration = session.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String name = enumeration.nextElement().toString();
				text += name + " = " + session.getAttribute(name) + "<br>";
			}
		}
		if ("".equals(text)) {
			text += "Session 內沒有任何資料";
		}
		// message.setPlainTitle("列出所有的 sessions");
		// message.setPlainMessage(text);
		// request.setAttribute("message", message);
		// request.getRequestDispatcher("/Message.jsp").forward(request,
		// response);
		// AlertBean alert = new AlertBean();
		// alert.setTitle("列出所有的 sessions");
		// alert.setPlainText(text);
		// new AlertDispatcher(request, response).forward(alert);
		throw new AlertException(text);
	}
}
