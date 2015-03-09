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
public class TagDAO extends GeneralDAO<Tag> {

    public synchronized int insert(Tag tag) {
	String sql = "INSERT INTO tags (tagname, tagtitle, descript, visible) "
		+ "VALUES (?,?,?,?)";
	int id = 0;
	try {
	    PreparedStatement pstmt = getConnection().prepareStatement(sql,
		    Statement.RETURN_GENERATED_KEYS);
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
	String sql = "SELECT * FROM tags ORDER BY id ASC ";
	return this.executeQuery(sql, Tag.class);
    }

    public Tag getTag(String tagname) {
	String sql = "SELECT * FROM tags WHERE tagname='" + tagname + "'";
	for (Tag tag : this.executeQuery(sql, Tag.class)) {
	    return tag;
	}
	return new Tag();
    }

    @Override
    public int update(Tag t) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public boolean delete(int i) {
	// TODO Auto-generated method stub
	return false;
    }
}
