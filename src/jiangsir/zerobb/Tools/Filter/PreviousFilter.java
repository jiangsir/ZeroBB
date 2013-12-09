package jiangsir.zerobb.Tools.Filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter
public class PreviousFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String requestURI = request.getRequestURI();
		requestURI = requestURI.substring(requestURI.lastIndexOf('/') + 1);

		chain.doFilter(request, response);

		// 此處紀錄上一頁, 主要是要知道從哪裡連過來的, 一定要在 doFilter之後
		// 可用 regular expression 判斷沒有 . 的檔名, 即可認定是 servlet
		// qx 所有具有副檔名的 page 都不處理, 只處理 servlet
		String CurrentPage = "";
		if ("GET".equals(request.getMethod()) && !requestURI.matches(".*\\..*")
				&& !requestURI.matches(".*SystemClosed.jsp$")
				&& !requestURI.matches(".*ShowSessions$")
				&& !requestURI.matches(".*Download$")
				&& !requestURI.matches(".*Logout.*")
				&& !requestURI.matches(".*Login.*")) {
			// qx 為何 Login 要記錄? 暫時 不把 Login 列入 PreviousFilter 觀察看看
			CurrentPage = (String) session.getAttribute("CurrentPage");
			if (request.getQueryString() != null) {
				requestURI += "?" + request.getQueryString();
			}

			if (!requestURI.equals(CurrentPage)) {
				session.setAttribute("PreviousPage", session
						.getAttribute("CurrentPage"));
				session.setAttribute("CurrentPage", requestURI);
			}
		}
	}

	public void destroy() {
	}

}
