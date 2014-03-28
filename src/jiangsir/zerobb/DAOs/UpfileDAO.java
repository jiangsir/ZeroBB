/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.zerobb.DAOs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.Log;
import jiangsir.zerobb.Tables.Upfile;
import jiangsir.zerobb.Tables.User;

/**
 * @author jiangsir
 * 
 */
public class UpfileDAO extends GeneralDAO<Upfile> {

	private synchronized int executeUpdate(PreparedStatement pstmt) {
		long starttime = System.currentTimeMillis();
		int result = -1;
		try {
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			new LogDAO().insert(new Log(this.getClass(), e));
			e.printStackTrace();
		}
		System.out.println("PSTMT_SQL=" + pstmt.toString() + " 共耗時 "
				+ (System.currentTimeMillis() - starttime) + " ms");
		return result;
	}

	public synchronized int insert(Upfile upfile) throws SQLException,
			IOException {
		String sql = "INSERT INTO upfiles (articleid, filepath, "
				+ "filename, filetmpname, filesize, filetype, `binary`, "
				+ "hitnum, visible) VALUES" + "(?,?,?,?,?, ?,?,?,?)";
		int id = 0;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setInt(1, upfile.getArticleid());
		pstmt.setString(2, upfile.getFilepath());
		pstmt.setString(3, upfile.getFilename());
		pstmt.setString(4, upfile.getFiletmpname());
		pstmt.setLong(5, upfile.getFilesize());
		pstmt.setString(6, upfile.getFiletype());
		pstmt.setBinaryStream(7, upfile.getBinary(), upfile.getBinary()
				.available());
		pstmt.setInt(8, upfile.getHitnum());
		pstmt.setInt(9, upfile.getVisible());
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		rs.next();
		id = rs.getInt(1);
		rs.close();
		pstmt.close();
		upfile.getBinary().close();
		return id;
	}

	// public ArrayList<Upfile> executeQuery(String sql) {
	// return this.executeQuery(sql, Upfile.class);
	// }

	/**
	 * 舊有的資料有很多 filepath 欄位有問題的，比如 http://www.nknush.kh.edu.tw/zerobb_utf8/ 有的是
	 * /zerobb_utf8/
	 */
	public void updateOldfilepath() {
		String sql = "SELECT * FROM upfiles WHERE id<2260";

		for (Upfile upfile : this.executeQuery(sql, Upfile.class)) {
			String filepath = upfile.getFilepath();
			System.out.println("upfileid=" + upfile.getId() + ", filepath="
					+ upfile.getFilepath());
			String[] old_prefix = { "/ZeroBB", "/zerobb_utf8",
					"http://www.nknush.kh.edu.tw/zerobb_utf8" };
			for (int i = 0; i < old_prefix.length; i++) {
				if (filepath.startsWith(old_prefix[i])) {
					filepath = filepath.replace(old_prefix[i], "/zerobb");
					System.out.println("replace!!! -> " + filepath);
					this.execute("UPDATE upfiles SET filepath='" + filepath
							+ "' WHERE id=" + upfile.getId());
				}
			}
		}
	}

	public boolean delete(int upfileid) {
		String sql = "UPDATE `upfiles` SET visible=0 WHERE id=" + upfileid
				+ ";";
		return this.execute(sql);
	}

	public synchronized boolean updateHitnum(int upfileid) {
		String sql = "UPDATE upfiles SET hitnum=hitnum+1 WHERE id=" + upfileid;
		return this.execute(sql);
	}

	public Upfile getUpfile(int upfileid) {
		String sql = "SELECT * FROM upfiles WHERE id=" + upfileid;
		for (Upfile upfile : this.executeQuery(sql, Upfile.class)) {
			return upfile;
		}
		return new Upfile();
	}

	/**
	 * 取得某一 article 的附加檔列表。
	 * 
	 * @param articleid
	 * @return
	 */
	public ArrayList<Upfile> getUpfilesByArticleid(int articleid) {
		// 可以解決要 InsertArticle 卻會有莫名的檔案出現在 “上傳檔案” 的地方。
		// 原因就是有些 upfile 的 articleid 不知什麼原因變成 0，所以在這裡被讀出來了。
		// 現 將 ArticleDAO 跟 upfileDAO 都加上 synchronized 看看能否改善。
		// 所以暫時還是讓它出現。比較容易觀察。
		if (articleid <= 0) {
			return new ArrayList<Upfile>();
		}
		// System.out.println("getupfile, articleid=" + articleid);
		String sql = "SELECT id,articleid,filename,filetype,hitnum FROM upfiles WHERE visible="
				+ Upfile.visible_OPEN + " AND articleid=" + articleid;
		// Iterator<?> it = new BaseDAO().executeQuery(sql, Upfile.class)
		// .iterator();
		return this.executeQuery(sql, Upfile.class);
	}

	/**
	 * 找出所有 Blob 值為 0 的 upfile
	 * 
	 * @return
	 */
	public ArrayList<Upfile> getNullBlobUpfiles(int begin, int end) {
		// SELECT * FROM `upfiles` WHERE LENGTH(`binary`)=0 ORDER BY articleid
		// DESC;
		String endsql = "";
		if (end > 0) {
			endsql = " AND id<" + end;
		}
		String sql = "SELECT * FROM upfiles WHERE LENGTH(`binary`)=0 AND id>"
				+ begin + endsql + " AND visible=1 ORDER BY articleid DESC";
		return this.executeQuery(sql, Upfile.class);
	}

	public InputStream getUpfileInputStream(int id) {
		String sql = "SELECT `binary` FROM upfiles WHERE id=" + id;
		InputStream is = null;
		ResultSet rs;
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// upfile.setBinary(rs.getBlob(8).getBinaryStream());
				is = rs.getBlob(1).getBinaryStream();
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return is;
	}

	/**
	 * 取得擁有這個 upfile 的文章。
	 * 
	 * @param upfileid
	 * @return
	 * @throws DataException
	 */
	public Article getArticle(User session_user, int upfileid)
			throws DataException {
		Upfile upfile = this.getUpfile(upfileid);
		return new ArticleDAO().getArticle(session_user, upfile.getArticleid());
	}

	@Override
	public int update(Upfile upfile) throws SQLException, IOException {
		String SQL = "UPDATE upfiles SET articleid=?, filename=?"
				+ ", filesize=?, filetype=?, `binary`=?, hitnum=?, "
				+ "visible=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = getConnection().prepareStatement(SQL);
		pstmt.setInt(1, upfile.getArticleid());
		pstmt.setString(2, upfile.getFilename());
		pstmt.setLong(3, upfile.getFilesize());
		pstmt.setString(4, upfile.getFiletype());
		pstmt.setBinaryStream(5, upfile.getBinary(), upfile.getBinary()
				.available());
		pstmt.setInt(6, upfile.getHitnum());
		pstmt.setInt(7, upfile.getVisible());
		pstmt.setInt(8, upfile.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}
}
