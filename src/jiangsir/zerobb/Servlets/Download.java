package jiangsir.zerobb.Servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.Beans.AlertBean;
import jiangsir.zerobb.DAOs.LogDAO;
import jiangsir.zerobb.DAOs.UpfileDAO;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Tables.Log;
import jiangsir.zerobb.Tables.Upfile;
import jiangsir.zerobb.Tools.AlertDispatcher;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/Download" }, name = "Download.do")
public class Download extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5665900418349206294L;
	public static String urlpattern = Download.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	private HttpServletRequest request;
	private HttpServletResponse response;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	/**
	 * 檔案下載的權限完全開放，任何人都可以下載
	 * 
	 * @return
	 */
	public boolean isAccessable() {
		return true;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// qx 記錄下誰 Download，增加一個 table downloads
		this.request = request;
		this.response = response;
		int upfileid = Integer.parseInt(request.getParameter("upfileid"));
		HttpSession session = (HttpSession) request.getSession(false);
		String session_account = (String) session
				.getAttribute("session_account");
		try {
			new UpfileDAO().getArticle(new UserDAO().getUser(session_account),
					upfileid);
		} catch (DataException e) {
			e.printStackTrace();
			new AlertDispatcher(request, response).forward(new AlertBean(e));
			return;
		}
		// if (upfileid <= Upfile.OLD_UPFILDID) {
		// this.doOldDownload(upfileid);
		// } else if (upfileid <= Upfile.FILE_UPFILDID) {
		// this.doLocalDownload(upfileid);
		// } else {
		// this.doBlobDownload(upfileid);
		// }
		this.doBlobDownload(upfileid);
		return;
	}

	public void doBlobDownload(int upfileid) {
		ServletOutputStream out = null;

		Upfile upfile = new UpfileDAO().getUpfile(upfileid);
		try {
			out = response.getOutputStream();
			// response.setContentType("application/octet-stream");
			response.setContentType(upfile.getFiletype());
			String filename = new String(upfile.getFilename().getBytes("Big5"),
					"ISO8859_1");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ filename + "\"");
			// stream = new FileInputStream(file);
			InputStream is = new UpfileDAO().getUpfileInputStream(upfileid);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) { // writeatserverside
				out.write(buffer, 0, bytesRead);
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		new UpfileDAO().updateHitnum(upfileid);
	}

	/**
	 * 相容舊系統的附件下載
	 * 
	 * @param upfileid
	 * @throws ServletException
	 * @throws IOException
	 */
	private void doOldDownload(int upfileid) throws ServletException,
			IOException {
		if (!this.isAccessable()) {
			// Message message = new Message();
			// message.setType(Message.MessageType_ERROR);
			// message.setPlainTitle("您不能下載這個附件！");
			// request.getRequestDispatcher("Message.jsp").forward(request,
			// response);
			// return;
			new AlertDispatcher(request, response).forward(new AlertBean(
					"您不能下載這個附件！"));
			return;
		}
		Upfile upfile = new UpfileDAO().getUpfile(upfileid);
		String filepath = upfile.getFilepath();
		URL fileurl = new URL("http://127.0.0.1" + ENV.context.getContextPath()
				+ filepath + "/" + upfile.getFiletmpname());
		InputStream is = null;
		try {
			is = fileurl.openStream();
		} catch (FileNotFoundException e) {
			// request.setAttribute("Message", "URL 有錯，找不到檔案！");
			HttpSession session = request.getSession(false);
			LogDAO exceptionDao = new LogDAO();
			String exception = "upfileid=" + upfileid + "\n";
			exception += "url=" + fileurl.getPath() + "\n";
			exceptionDao.insert(new Log(request.getRequestURI(),
					(String) session.getAttribute("session_accunt"), request
							.getRemoteAddr(), "URL 有錯，找不到檔案！" + exception, e));

			// request.setAttribute("message", new Message(
			// Message.MessageType_ALERT, "URL 有錯，找不到檔案！"));
			// request.getRequestDispatcher("Message.jsp").forward(request,
			// response);
			// e.printStackTrace();
			// return;
			new AlertDispatcher(request, response).forward(new AlertBean(e));
			return;

		}
		String filename = new String(upfile.getFilename().getBytes("Big5"),
				"ISO8859_1");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + "\"");
		int read = 0;
		byte[] bytes = new byte[1024];
		OutputStream os = response.getOutputStream();
		while ((read = is.read(bytes)) != -1) {
			os.write(bytes, 0, read);
		}
		os.flush();
		os.close();
		new UpfileDAO().updateHitnum(upfileid);
	}

	/**
	 * 下載系統內部檔案，該檔案可以不放在 webapps 內，可以增加檔案的安全性
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doLocalDownload(int upfileid) throws ServletException,
			IOException {
		Upfile upfile = new UpfileDAO().getUpfile(upfileid);
		BufferedInputStream in = null;
		ServletOutputStream out = null;
		FileInputStream stream = null;
		// String REAL_PATH = "/home/tomcat6/ZeroBB_OLD_upfiles";
		String REAL_PATH = ENV.APP_REAL_PATH;
		File file = new File(REAL_PATH + upfile.getINNER_PATH(),
				upfile.getINNER_FILENAME());
		if (!file.exists()) {
			HttpSession session = request.getSession(false);
			LogDAO exceptionDao = new LogDAO();
			String exception = "upfileid=" + upfileid + "\n";
			exception += "filepath=" + file.getPath() + "\n";
			exceptionDao.insert(new Log(request.getRequestURI(),
					(String) session.getAttribute("session_accunt"), request
							.getRemoteAddr(), "檔案不存在！" + exception, null));
			// Message message = new Message();
			// message.setType(Message.MessageType_ERROR);
			// message.setPlainTitle("檔案不存在！");
			// request.setAttribute("message", message);
			// request.getRequestDispatcher("Message.jsp").forward(request,
			// response);
			// return;
			AlertBean alert = new AlertBean();
			alert.setType(AlertBean.Type_ERROR);
			alert.setTitle("檔案不存在！(" + file.getPath() + ")");
			new AlertDispatcher(request, response).forward(alert);
			return;
		}
		try {
			out = response.getOutputStream();
			// response.setContentType("application/octet-stream");
			response.setContentType(upfile.getFiletype());
			String filename = new String(upfile.getFilename().getBytes("Big5"),
					"ISO8859_1");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ filename + "\"");
			System.out.println("filepath=" + file.getPath());
			stream = new FileInputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) { // writeatserverside
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
			out.close();
			new UpfileDAO().updateHitnum(upfileid);
		} catch (IOException e) {

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
}
