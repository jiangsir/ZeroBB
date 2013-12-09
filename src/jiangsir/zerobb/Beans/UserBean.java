/**
 * idv.jiangsir.Beans - CourseBean.java
 * 2009/12/20 下午5:07:14
 * nknush-001
 */
package jiangsir.zerobb.Beans;

import java.util.ArrayList;

import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Tables.User;

/**
 * @author nknush-001
 * 
 */
public class UserBean {
	private User user;
	private String account;

	public UserBean() {
	}

	public UserBean(String account) {
		this.account = account;
	}

	public void setAccount(String account) {
		this.account = account;
		this.user = new UserDAO().getUser(account);
	}

	public String getAccount() {
		return account;
	}

	// ===================================================================================

	public User getUser() {
		if (this.user == null) {
			return new UserDAO().getUser(this.account);
		}
		return this.user;
	}

	public ArrayList<User> getUsers() {
		return new UserDAO().getUsers();
	}

	public boolean isHeadlineVisible() {
		return this.user.getHeadline();
	}

}
