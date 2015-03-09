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
import java.util.List;

import jiangsir.zerobb.Tables.Article_Tag;
import jiangsir.zerobb.Tables.Log;
import jiangsir.zerobb.Tables.Tag;
import jiangsir.zerobb.Tools.ENV;

/**
 * @author jiangsir
 * 
 */
public class Article_TagDAO extends GeneralDAO<Article_Tag> {

	private synchronized int executeUpdate(PreparedStatement pstmt) {
		long starttime = System.currentTimeMillis();
		int result = -1;
		try {
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			new LogDAO().insert(new Log(this.getClass(), e));
			e.printStackTrace();
		}
		System.out.println(ENV.logHeader() + "PSTMT_SQL=" + pstmt.toString()
				+ " 共耗時 " + (System.currentTimeMillis() - starttime) + " ms");
		return result;
	}

	public synchronized int insert(Article_Tag tag) throws SQLException {
		String sql = "INSERT INTO article_tags (articleid, tagname) VALUES (?,?)";
		int id = 0;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setInt(1, tag.getArticleid());
		pstmt.setString(2, tag.getTagname());
		this.executeUpdate(pstmt);
		ResultSet rs = pstmt.getGeneratedKeys();
		rs.next();
		id = rs.getInt(1);
		rs.close();
		pstmt.close();
		return id;
	}

	/**
	 * 用 articleid 來取得該文章的所有 tagname
	 * 
	 * @param articleid
	 * @return
	 */
	public ArrayList<String> getArticle_TagNames(int articleid) {
		ArrayList<String> tags = new ArrayList<String>();
		String sql = "SELECT * FROM article_tags WHERE articleid=" + articleid;
		for (Article_Tag tag : this.executeQuery(sql, Article_Tag.class)) {
			tags.add(tag.getTagname());
		}
		return tags;
	}

	// /**
	// * 用 articleid 取得該 article 的所有 Article_Tag
	// *
	// * @param articleid
	// * @return
	// */
	// public ArrayList<Article_Tag> getArticle_Tags(int articleid) {
	// ArrayList<Article_Tag> tags = new ArrayList<Article_Tag>();
	// String sql = "SELECT * FROM article_tags WHERE articleid=" + articleid;
	// for (Article_Tag tag : this.executeQuery(sql, Article_Tag.class)) {
	// tags.add(tag);
	// }
	// return tags;
	// }

	public ArrayList<Article_Tag> getAllArticle_Tag() {
		String sql = "SELECT * FROM article_tags";
		return this.executeQuery(sql, Article_Tag.class);
	}

	/**
	 * 修改文章時 article_tag 先全部刪除。再加入新的 tags
	 * 
	 * @param articleid
	 */
	public void removeArticle_Tags(int articleid) {
		String sql = "DELETE FROM article_tags WHERE articleid=" + articleid;
		this.execute(sql);
	}

	/**
	 * 用 tagname 取出所有相同 tagname 的 tag
	 * 
	 * @param articleid
	 * @return
	 */
	protected ArrayList<Article_Tag> getArticle_Tags(String[] tagname,
			int page, int pagesize) {
		String tagnamestring = "WHERE ";
		for (int i = 0; i < tagname.length; i++) {
			tagnamestring += (i == 0 ? "" : "OR") + "tagname='" + tagname[i]
					+ "'";
		}
		String sql = "SELECT * FROM article_tags " + tagnamestring
				+ " ORDER BY articleid DESC LIMIT "
				+ ((page - 1) * pagesize < 0 ? 0 : (int) (page - 1) * pagesize)
				+ "," + pagesize;
		return this.executeQuery(sql, Article_Tag.class);
	}

	/**
	 * 取得單一公告內的所屬分類。
	 * 
	 * @param articleid
	 * @return
	 */
	public List<Tag> getTags(int articleid) {
		ArrayList<Tag> list = new ArrayList<Tag>();
		String sql = "SELECT * FROM article_tags WHERE articleid=" + articleid;
		TagDAO tagDao = new TagDAO();
		for (Article_Tag article_tag : this
				.executeQuery(sql, Article_Tag.class)) {
			list.add(tagDao.getTag(article_tag.getTagname()));
		}
		return list;
	}

	@Override
	public int update(Article_Tag t) {
		return 0;
	}

	@Override
	public boolean delete(int articleid) {
		String sql = "DELETE FROM article_tags WHERE articleid=" + articleid;
		return this.execute(sql);
	}

	public boolean delete(Article_Tag tag) {
		String sql = "DELETE FROM article_tags WHERE id=" + tag.getId();
		return this.execute(sql);
	}
}
