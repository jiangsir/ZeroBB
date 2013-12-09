/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.zerobb.DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import jiangsir.zerobb.Tables.User;

/**
 * @author jiangsir
 * 
 */
public class UserDAO extends GeneralDAO<User> {

	/**
	 * 取得所有 user
	 * 
	 * @return
	 */
	public ArrayList<User> getUsers() {
		String sql = "SELECT * FROM users WHERE visible=true";
		return this.executeQuery(sql, User.class);
	}

	public User getUser(int userid) {
		String sql = "SELECT * FROM users WHERE id=" + userid;
		for (User user : this.executeQuery(sql, User.class)) {
			return user;
		}
		return new User();
	}

	public User getUser(String account) {
		if (account == null) {
			return null;
		}
		String sql = "SELECT * FROM users WHERE account=?";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			pstmt.setString(1, account);
			for (User user : this.executeQuery(sql, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new User();
	}

	public boolean isUser(String account, String passwd) {
		String sql = "SELECT * FROM users WHERE account=? AND passwd=?";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			pstmt.setString(1, account);
			pstmt.setString(2, passwd);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<User> getUserByDivision(String division) {
		String sql = "SELECT * FROM users WHERE division='" + division + "'";
		return this.executeQuery(sql, User.class);
	}

	public LinkedHashMap<String, String> getDivisions() {
		LinkedHashMap<String, String> divisions = new LinkedHashMap<String, String>();
		ArrayList<User> users = this.getUsers();
		for (User user : users) {
			divisions.put(user.getDivision(), user.getDivisionName());
		}
		return divisions;
	}

	@Override
	public int insert(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(int i) {
		// TODO Auto-generated method stub
		return false;
	}
}
