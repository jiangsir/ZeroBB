package jiangsir.zerobb.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jiangsir.zerobb.Annotations.RoleSetting;
import jiangsir.zerobb.Exceptions.JQueryException;
import jiangsir.zerobb.Services.UserDAO;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tables.User.ROLE;

/**
 * Servlet implementation class EditUsersServlet
 */
@WebServlet(urlPatterns = {"/EditUser"})
@RoleSetting(allowHigherThen = ROLE.USER)
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUserServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action = request.getParameter("action");
			System.out.print("action=" + action);
			if ("changePasswd".equals(action)) {
				String userid = request.getParameter("userid");
				String oldpasswd = request.getParameter("oldpasswd");
				String newpasswd = request.getParameter("newpasswd");
				String newpasswd2 = request.getParameter("newpasswd2");
				UserDAO userDao = new UserDAO();
				User user = userDao.getUserById(Integer.parseInt(userid));
				if (userDao.getUserByAccountPasswd(user.getAccount(), oldpasswd).isNullUser()) {
					throw new JQueryException("原密碼可能有誤！");
				}
				if (!newpasswd.equals(newpasswd2)) {
					throw new JQueryException("新密碼兩次不相同！");
				}
				user.setPasswd(newpasswd);
				userDao.update(user);
				throw new JQueryException("密碼更改成功！");
			}
		} catch (Exception e) {
			throw new JQueryException(e.getLocalizedMessage());
		}
		// response.sendRedirect(request.getContextPath() + new
		// SessionScope(request).getPreviousPage());
		// return;
	}

}
