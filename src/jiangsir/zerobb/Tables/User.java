package jiangsir.zerobb.Tables;

import javax.servlet.http.HttpSession;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Tools.ENV;

/**
 *  - User.java
 * 2008/4/29 下午 05:41:54
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class User {

	private Integer id = 0;
	private String account = "";
	private String name = "";
	private String division = "";
	public static String GROUP_USER = "GroupUser";
	public static String GROUP_ADMIN = "GroupAdmin";
	private String usergroup = "GroupUser";
	private String passwd = "";
	private String email = "";
	private String homepage = "";
	private String description = "";
	private Boolean headline = false;
	private Boolean visible = true;

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) throws DataException {
		if (account == null || account.equals("")) {
			throw new DataException("帳號資訊不存在，可能為空。");
		}
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}

	public String getDivisionName() {
		if (ENV.divisionmap.containsKey(division)) {
			return ENV.divisionmap.get(division);
		} else {
			return division;
		}
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getHeadline() {
		return headline;
	}

	public void setHeadline(Boolean headline) {
		this.headline = headline;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}

	/**
	 * 登出相關動作, 包含 session 逾時也執行 doLogout <br>
	 */
	public void Logout(HttpSession session) {

		session.invalidate();
	}

	/**
	 *
	 *
	 */
	public static void relogin(User user, HttpSession session) {
		// 在 context restart 後，上線人數會歸零，這時可以透過其他 servlet 讓這些人慢慢重新加入
		session.setAttribute("session_account", user.getAccount());
		session.setAttribute("session_usergroup", user.getUsergroup());
		session.setAttribute("passed", "true");
		session.setAttribute("UserObject", user);
		// ENV.OnlineUsers.put(session.getId(), session);
	}

	/**
	 * 這個 privilege 結合了 Group 裡的權限加上 extraprvilege
	 * 
	 * @return
	 */
	public String getPrivilege() {
		return ENV.context.getInitParameter(usergroup);
	}

}
