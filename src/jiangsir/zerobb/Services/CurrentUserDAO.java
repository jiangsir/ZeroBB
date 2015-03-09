/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.zerobb.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import jiangsir.zerobb.Tables.CurrentUser;

/**
 * @author jiangsir
 * 
 */
public class CurrentUserDAO extends GeneralDAO<CurrentUser> {

	public CurrentUser getCurrentUserById(long userid, HttpSession session) {
		String sql = "SELECT * FROM users WHERE id=?";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			pstmt.setLong(1, userid);
			for (CurrentUser currentUser : this.executeQuery(pstmt,
					CurrentUser.class)) {
				currentUser.setSession(session);
				return currentUser;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public int insert(CurrentUser t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(CurrentUser t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(int i) {
		// TODO Auto-generated method stub
		return false;
	}

}
