package jiangsir.zerobb.Filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import jiangsir.zerobb.Services.LogDAO;
import jiangsir.zerobb.Tables.Log;

public class ExceptionFilter_NOUSE implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
		if (throwable == null) {
			// System.out.println("throwable == null null");
			// String status_code = (String) req
			// .getAttribute("javax.servlet.error.status_code");
			// System.out.println("status_code=" + status_code);
			// String error_message = (String) req
			// .getAttribute("javax.servlet.error.message");
			// System.out.println("error_message=" + error_message);
			// String exception_type = (String) req
			// .getAttribute("javax.servlet.error.exception_type");
			// System.out.println("exception_type=" + exception_type);
			chain.doFilter(request, response);
			return;
		}
		String uri = request.getAttribute("javax.servlet.error.request_uri")
				.toString();

		StackTraceElement[] trace;
		// System.out.println("throwable.toString()=" + throwable.toString());
		trace = throwable.getStackTrace();
		// System.out.println("throwable.getMessage()=" +
		// throwable.getMessage());
		// System.out.println("trace.toString()=" + trace.toString());
		StringBuffer tracestring = new StringBuffer(5000);
		for (int i = 0; i < trace.length; i++) {
			tracestring.append(trace[i] + "\n");
			// System.out.println("trace[" + i + "]=" + trace[i]);
		}
		HttpSession session = request.getSession();
		String session_account = null;
		try {
			session_account = (String) session.getAttribute("session_account");
		} catch (Exception e) {
			throw new javax.servlet.ServletException(
					"session 無效，請稍候再試。 系統管理員敬上");
		}
		// User session_user = (User) session.getAttribute("UserObject");
		// HashMap Userinfo = (HashMap) session.getAttribute("Userinfo");
		// String account = "No Userinfo";
		// if (session_user == null) {
		// // account = (String) Userinfo.get("account");
		// session_user = new User(session_account);
		// }
		String s = tracestring.toString();
		// 如果錯誤來自 errorlog 本身，代表資料庫連線有問題，不能記錄下來，否則會無窮迴圈
		if (!s.contains("SQL ERROR: INSERT INTO errorlog")) {
			new LogDAO().insert(new Log(uri + "?" + request.getQueryString(),
					session_account, request.getRemoteAddr(), throwable
							.toString(), (Exception) throwable));
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
