/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.zerobb.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Tables.AppConfig;

/**
 * @author jiangsir
 * 
 */
public class AppConfigDAO extends SuperDAO<AppConfig> {

	@Override
	protected synchronized int insert(AppConfig appConfig) throws SQLException {
		String sql = "INSERT INTO appconfigs(title, header, pagesize, defaultlogin, authdomains, "
				+ "client_id, client_secret, redirect_uri, signinip, "
				+ "announcement, timestamp) VALUES (?,?,?,?,?, ?,?,?,?,?, now());";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, appConfig.getTitle());
		pstmt.setString(2, appConfig.getHeader());
		pstmt.setInt(3, appConfig.getPagesize());
		pstmt.setString(4, appConfig.getDefaultlogin());
		pstmt.setString(5, appConfig.getAuthdomains().toString());
		pstmt.setString(6, appConfig.getClient_id());
		pstmt.setString(7, appConfig.getClient_secret());
		pstmt.setString(8, appConfig.getRedirect_uri());
		pstmt.setString(9, appConfig.getSigninip().toString());
		pstmt.setString(10, appConfig.getAnnouncement());
		return this.executeInsert(pstmt);
	}

	protected synchronized int update(AppConfig appConfig) throws SQLException {
		String sql = "UPDATE appconfigs SET title=?, header=?, pagesize=?, defaultlogin=?, "
				+ "authdomains=?, client_id=?, client_secret=?, redirect_uri=?, "
				+ "signinip=?, announcement=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, appConfig.getTitle());
		pstmt.setString(2, appConfig.getHeader());
		pstmt.setInt(3, appConfig.getPagesize());
		pstmt.setString(4, appConfig.getDefaultlogin());
		pstmt.setString(5, appConfig.getAuthdomains().toString());
		pstmt.setString(6, appConfig.getClient_id());
		pstmt.setString(7, appConfig.getClient_secret());
		pstmt.setString(8, appConfig.getRedirect_uri());
		pstmt.setString(9, appConfig.getSigninip().toString());
		pstmt.setString(10, appConfig.getAnnouncement());
		pstmt.setLong(11, appConfig.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	@Override
	protected boolean delete(long id) throws SQLException {
		String sql = "DELETE FROM appconfigs WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

	protected ArrayList<AppConfig> getAppConfigByFields(TreeMap<String, Object> fields, String orderby, int page) {
		String sql = "SELECT * FROM appconfigs " + this.makeFields(fields, orderby, page);
		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
			int i = 1;
			for (String field : fields.keySet()) {
				pstmt.setObject(i++, fields.get(field));
			}
			return executeQuery(pstmt, AppConfig.class);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	/**
	 * 清空 appconfig
	 * 
	 * @return
	 */
	protected boolean truncate() {
		return this.execute("TRUNCATE TABLE `appconfigs`");
	}

}
