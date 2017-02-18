/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.zerobb.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import jiangsir.zerobb.Exceptions.AlertException;
import jiangsir.zerobb.Factories.UserFactory;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tables.User.DIVISION;

/**
 * @author jiangsir
 * 
 */
public class UserDAO extends SuperDAO<User> {

	/**
	 * 取得所有 user
	 * 
	 * @return
	 */
	public ArrayList<User> getUsers() {
		String sql = "SELECT * FROM users WHERE visible=true";
		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
			return this.executeQuery(pstmt, User.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<User>();
	}

	public User getUserById(int userid) {
		String sql = "SELECT * FROM users WHERE id=?";
		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userid);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserFactory.getNullUser();
	}

	public User getUserByAccount(String account) {
		if (account == null) {
			return null;
		}
		String sql = "SELECT * FROM users WHERE account=?";
		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setString(1, account);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserFactory.getNullUser();
	}

	/**
	 * 用 account, passwd 取得 User, 找不到的話，則回傳 NullUser
	 * 
	 * @param account
	 * @param passwd
	 * @return
	 */
	public User getUserByAccountPasswd(String account, String passwd) {
		String sql = "SELECT * FROM users WHERE account=? AND passwd=?";
		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setString(1, account);
			pstmt.setString(2, passwd);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserFactory.getNullUser();
	}

	// public boolean isUser(String account, String passwd) {
	// return this.getUserByAccountPasswd(account, passwd).isNullUser();
	// }

	public ArrayList<User> getUserByDivision(DIVISION division) {
		String sql = "SELECT * FROM users WHERE division=?";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setString(1, division.name());
			return this.executeQuery(pstmt, User.class);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AlertException(e.getLocalizedMessage());
		}
	}

	public LinkedHashMap<String, String> getDivisions() {
		LinkedHashMap<String, String> divisions = new LinkedHashMap<String, String>();
		for (User.DIVISION division : User.DIVISION.values()) {
			divisions.put(division.name(), division.getValue());
		}
		// ArrayList<User> users = this.getUsers();
		// for (User user : users) {
		// divisions.put(user.getDivision().name(), user.getDivision()
		// .getValue());
		// }
		return divisions;
	}

	@Override
	public int insert(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User user) throws SQLException {
		String SQL = "UPDATE users SET passwd=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = getConnection().prepareStatement(SQL);
		pstmt.setString(1, user.getPasswd());
		pstmt.setInt(2, user.getId());
		result = this.executeUpdate(pstmt);
		return result;
	}

	@Override
	protected boolean delete(long i) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
