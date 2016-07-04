package jiangsir.zerobb.Services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.Log;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

public class LogDAO extends SuperDAO<Log> {

	public LogDAO() {

	}

	public int getCount() {
		String SQL = "SELECT * FROM logs";
		return this.executeCount(SQL);
	}

	public ArrayList<Log> getErrors(int count) throws SQLException {
		String sql = "SELECT * FROM logs ORDER BY id DESC LIMIT 0," + count;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		return this.executeQuery(pstmt, Log.class);
	}

	public ArrayList<Log> getErrorsByIP(String ip) throws SQLException {
		String sql = "SELECT * FROM logs WHERE ipaddr='" + ip + "' ORDER BY id DESC";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		return this.executeQuery(pstmt, Log.class);
	}

	public int insert_OLD(String uri, String account, String ipaddr, String exceptiontype, String exception) {
		if (exception.contains("INSERT INTO exceptions")) {
			return 0;
		}
		// if (exceptiontype.contains("OutOfMemoryError")) {
		// Thread mailer = new Thread(new Mailer(
		// "jiangsir@tea.nknush.kh.edu.tw", exceptiontype, exception));
		// mailer.start();
		// }
		uri = Utils.intoSQL(uri);
		if (uri != null && uri.length() > 100) {
			uri = uri.substring(uri.length() - 100);
		}
		if (exceptiontype != null && exceptiontype.length() > 200) {
			exceptiontype = exceptiontype.substring(0, 200);
			exceptiontype += "訊息太長(>200)，到此省略...";
		}
		if (exception != null && exception.length() > 100000) {
			exception = exception.substring(0, 10000);
			exception += "訊息太長(>100000)，到此省略...";
		}
		exceptiontype = Utils.intoSQL(exceptiontype);
		exception = Utils.intoSQL(exception);
		String sql = "INSERT INTO exceptions (uri, account, ipaddr, "
				+ "exceptiontype, exception, exceptiontime) VALUES('" + uri + "', '" + account + "', '" + ipaddr
				+ "', '" + exceptiontype + "', '" + exception + "', '" + ENV.getNow() + "')";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			return this.executeInsert(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int insert(Log log) {
		if (log.getException().contains("INSERT INTO logs")) {
			return 0;
		}

		String sql = "INSERT INTO logs (uri, account, ipaddr, exceptiontype, "
				+ "exception, exceptiontime) VALUES (?,?,?,?,?, ?)";
		int articleid = 0;
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, log.getUri());
			pstmt.setString(2, log.getAccount());
			pstmt.setString(3, log.getIpaddr());
			pstmt.setString(4, log.getExceptiontype());
			pstmt.setString(5, log.getException());
			pstmt.setTimestamp(6, new Timestamp(log.getExceptiontime().getTime()));
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			articleid = rs.getInt(1);
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articleid;
	}

	@Override
	public int update(Log t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean delete(long i) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
}
