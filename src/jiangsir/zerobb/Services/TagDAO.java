/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.zerobb.Services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jiangsir.zerobb.Tables.Tag;

/**
 * @author jiangsir
 * 
 */
public class TagDAO extends SuperDAO<Tag> {

	public synchronized int insert(Tag tag) {
		String sql = "INSERT INTO tags (tagname, tagtitle, descript, visible) " + "VALUES (?,?,?,?)";
		int id = 0;
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, tag.getTagname());
			pstmt.setString(2, tag.getTagtitle());
			pstmt.setString(3, tag.getDescript());
			pstmt.setBoolean(4, tag.getVisible());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 取得目前系統中所有 tag
	 * 
	 */
	public ArrayList<Tag> getTags() {
		String sql = "SELECT * FROM tags WHERE visible=true ORDER BY id ASC ";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			return this.executeQuery(pstmt, Tag.class);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Tag>();
		}
	}

	public Tag getTag(String tagname) {
		String sql = "SELECT * FROM tags WHERE tagname='" + tagname + "'";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			for (Tag tag : this.executeQuery(pstmt, Tag.class)) {
				return tag;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Tag();
	}

	@Override
	public int update(Tag t) {
		return 0;
	}

	@Override
	protected boolean delete(long i) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
