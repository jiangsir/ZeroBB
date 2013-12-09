package jiangsir.zerobb.DAOs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

abstract public class GeneralDAO<T> {
	private static Connection conn = null;
	private static DataSource source = null;

	abstract public int insert(T t) throws SQLException, IOException;

	abstract public int update(T t) throws SQLException, IOException;

	abstract public boolean delete(int i);

	public Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				if (source == null) {
					InitialContext icontext = new InitialContext();
					source = (DataSource) icontext
							.lookup("java:comp/env/mysql");
				}
				System.out.println(this.getClass().getName()
						+ ": conn 不存在或關閉了！重新取得連線");
				conn = source.getConnection();
			} else {
				return conn;
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private String getSetMethodName(String columnName) {
		String firstchar = columnName.substring(0, 1);
		firstchar = firstchar.toUpperCase();
		return "set" + firstchar + columnName.substring(1);
	}

	public ArrayList<T> executeQuery(PreparedStatement pstmt, Class<T> theclass) {
		ResultSet rs = null;
		ArrayList<T> list = new ArrayList<T>(); // 準備用 ArrayList 來回傳查詢結果。
		try {
			rs = pstmt.executeQuery(); // 執行查詢
			ResultSetMetaData rsmd = rs.getMetaData(); // 取得資料表的 metadata
			int columnCount = rsmd.getColumnCount(); // 有幾個 column
			while (rs.next()) {
				T t = (T) theclass.newInstance(); // 實體化一個 T
				for (int i = 1; i <= columnCount; i++) {
					String settername = this.getSetMethodName(rsmd
							.getColumnName(i));
					Object value;
					if (rsmd.getColumnType(i) == Types.BOOLEAN
							|| rsmd.getColumnType(i) == Types.TINYINT) {
						value = rs.getBoolean(rsmd.getColumnName(i));
					} else if (rsmd.getColumnType(i) == Types.LONGVARBINARY) {
						continue;
					} else if (rsmd.getColumnType(i) == Types.BLOB) {
						continue;
						// } else if (rsmd.getColumnType(i) == Types.DATE) {
						// value = rs.getDate(rsmd.getColumnName(i));
					} else {
						value = rs.getObject(rsmd.getColumnName(i));
					}

					try {
						Method m = t.getClass().getMethod(settername,
								new Class[] { value.getClass() });
						m.invoke(t, new Object[] { value });
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
						continue;
					} catch (NoSuchMethodException e1) {
						e1.printStackTrace();
						continue;
					}
				}
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				// conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList<T> executeQuery(String sql, Class<T> theclass) {
		try {
			return this.executeQuery(
					this.getConnection().prepareStatement(sql), theclass);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
	}

	/**
	 * 執行 SQL 指令
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public boolean execute(String sql) {
		boolean result = false;
		Connection conn = this.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			result = stmt.execute(sql);
			stmt.close();
			// conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}

	/**
	 * 只用來獲取總筆數
	 * 
	 * @param sql
	 * @return
	 */
	public int executeCount(String sql) {
		int result = 0;
		if (sql.matches("^SELECT.+FROM.*")) {
			sql = sql.replaceFirst("^SELECT.+FROM",
					"SELECT COUNT(*) AS COUNT FROM");
		} else {
			return -1;
		}
		try {
			PreparedStatement pstmt;
			pstmt = this.getConnection().prepareStatement(sql);
			if (sql.contains("ORDER")) {
				sql = sql.substring(0, sql.indexOf("ORDER") - 1);
			}
			if (!sql.toUpperCase().startsWith("SELECT COUNT(*) AS COUNT FROM")) {
				return -1;
			}
			ResultSet rs = pstmt.executeQuery(sql);
			rs.next();
			result = rs.getInt("COUNT");
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return result;
	}

}
