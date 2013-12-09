/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.zerobb.DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import jiangsir.zerobb.Beans.ArticleBean;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.Article_Tag;
import jiangsir.zerobb.Tables.Log;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

/**
 * @author jiangsir
 * 
 */
public class ArticleDAO extends GeneralDAO<Article> {

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

	/**
	 * @deprecated
	 * @param article
	 * @return
	 */
	public synchronized int insert_PSTMT(Article article) {
		// code = utils.intoSQL(code);
		// errmsg 要預先給一個 "" 否則無法寫如資料庫
		String sql = "INSERT INTO articles (account, title, "
				+ "info, type, hyperlink, content, hitnum, "
				+ "postdate, outdate, visible) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?)";
		int articleid = 0;
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, article.getAccount());
			pstmt.setString(2, article.getTitle());
			pstmt.setString(3, article.getInfo());
			pstmt.setString(4, article.getType());
			pstmt.setString(5, article.getHyperlink());
			pstmt.setString(6, article.getContent());
			pstmt.setInt(7, article.getHitnum());
			pstmt.setTimestamp(8,
					new Timestamp(article.getPostdate().getTime()));
			pstmt.setTimestamp(9, new Timestamp(article.getOutdate().getTime()));
			pstmt.setBoolean(10, article.getVisible());
			this.executeUpdate(pstmt);
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

	/**
	 * 將文章設成隱藏, 並不真的刪除
	 * 
	 * @param articleid
	 * @return
	 */
	public boolean delete(int articleid) {
		String sql = "UPDATE articles SET visible=0 WHERE id=" + articleid;
		return execute(sql);
	}

	/**
	 * 將文章到期日設為現在，就達到強迫過期的目的
	 * 
	 * @param articleid
	 * @return
	 * @deprecated
	 */
	public boolean outdate(int articleid) {
		String sql = "UPDATE articles SET outdate='"
				+ Utils.parseDatetime(new java.util.Date().getTime())
				+ "' WHERE id=" + articleid;
		return execute(sql);
	}

	public ArrayList<Article> searchArticles(String keyword, int page,
			int pagesize) {
		String SQL = "SELECT * FROM articles WHERE visible=? AND (id LIKE ? OR title LIKE ? "
				+ " OR content LIKE ?) ORDER BY id DESC LIMIT "
				+ ((page - 1) * pagesize < 0 ? 0 : (int) (page - 1) * pagesize)
				+ "," + pagesize;
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(SQL);
			pstmt.setInt(1, Article.visible_VIEW);
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setString(3, "%" + keyword + "%");
			pstmt.setString(4, "%" + keyword + "%");
			return executeQuery(pstmt, Article.class);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Article>();
		}
	}

	/**
	 * 頭條有不同的排序方式，不以 id 來排序
	 * 
	 * @return
	 */
	public ArrayList<Article> getHeadlines() {
		String sql = "SELECT * FROM articles WHERE visible="
				+ Article.visible_VIEW + " AND postdate<='"
				+ Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
				+ "' AND  outdate>='"
				+ Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
				+ "' AND info=" + Article.info_HEADLINE
				+ " ORDER BY postdate DESC LIMIT 0,10";
		return executeQuery(sql, Article.class);
	}

	/**
	 * 列出有效期限內的 article
	 * 
	 * @param by
	 * @param page
	 * @return
	 */
	public ArrayList<Article> getArticles(String[] infos, String division,
			int page, int pagesize) {
		String info = "";
		for (int i = 0; infos != null && i < infos.length; i++) {
			// try {
			// infos[i] = new String(infos[i].getBytes("ISO-8859-1"), "UTF-8");
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			if (i == 0) {
				info += " info='" + infos[i] + "'";
			} else {
				info += " OR info='" + infos[i] + "'";
			}
		}
		if (info != "") {
			info = " AND (" + info + ")";
		}
		String account = "";
		if (division != null && !"".equals(division)) {
			ArrayList<User> users = new UserDAO().getUserByDivision(division);
			for (User user : users) {
				if (account.equals("")) {
					account += " account='" + user.getAccount() + "'";
				} else {
					account += " OR account='" + user.getAccount() + "'";
				}
			}

			if (account != "") {
				account = " AND (" + account + ")";
			}
		}
		String by = info + account;
		String sql = "SELECT * FROM articles WHERE visible="
				+ Article.visible_VIEW + " AND postdate<='"
				+ Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
				+ "' AND  outdate>='"
				+ Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
				+ "' " + by + " ORDER BY sortable DESC, postdate DESC LIMIT "
				+ ((page - 1) * pagesize < 0 ? 0 : (int) (page - 1) * pagesize)
				+ "," + pagesize;
		// ArrayList<ArticleBean> articleBeans = new ArrayList<ArticleBean>();
		// for (Article article : executeQuery(sql, Article.class)) {
		// articleBeans.add((ArticleBean) article);
		// }
		return executeQuery(sql, Article.class);
	}

	/**
	 * 用 tagname 來取得 article
	 * 
	 * @param tagname
	 * @param page
	 * @param pagesize
	 * @return
	 */
	public ArrayList<Article> getArticles(String[] tagname, int page,
			int pagesize) {
		ArrayList<Article> articles = new ArrayList<Article>();
		for (Article_Tag tag : new Article_TagDAO().getArticle_Tags(tagname,
				page, pagesize)) {
			Article article = this.getArticle(tag.getArticleid());
			if (article != null) {
				articles.add(article);
			}
		}
		return articles;
	}

	public ArrayList<Article> getAllArticles() {
		String sql = "SELECT * FROM articles";
		return executeQuery(sql, Article.class);
	}

	/**
	 * 列出所有 visible 的 article, 包含未來 and 過期
	 * 
	 * @param by
	 * @param page
	 * @return
	 */
	public ArrayList<ArticleBean> getAllArticles(String by, int page,
			int pagesize) {
		if (by == null || by.equals("") || by.equals("all")) {
			by = " ";
		} else {
			by = " AND account='" + by + "'";
		}
		String sql = "SELECT * FROM articles WHERE visible="
				+ Article.visible_VIEW + by + " ORDER BY id DESC LIMIT "
				+ (page - 1) * pagesize + "," + pagesize;
		ArrayList<ArticleBean> articleBeans = new ArrayList<ArticleBean>();
		for (Article article : executeQuery(sql, Article.class)) {
			articleBeans.add(new ArticleBean(article));
		}
		return articleBeans;
	}

	/**
	 * 列出過期的 article
	 * 
	 * @param by
	 * @param page
	 * @return
	 */
	public ArrayList<Article> getOutdateArticles(String[] infos,
			String division, int page) {
		String info = "";
		for (int i = 0; infos != null && i < infos.length; i++) {
			if (i == 0) {
				info += " info='" + infos[i] + "'";
			} else {
				info += " OR info='" + infos[i] + "'";
			}
		}
		if (info != "") {
			info = " AND (" + info + ")";
		}
		String account = "";

		if (division != null && !"".equals(division)) {
			ArrayList<User> users = new UserDAO().getUserByDivision(division);
			for (User user : users) {
				if (account.equals("")) {
					account += " account='" + user.getAccount() + "'";
				} else {
					account += " OR account='" + user.getAccount() + "'";
				}
			}

			if (account != "") {
				account = " AND (" + account + ")";
			}
		}

		String by = info + account;
		System.out.println("by=" + by);
		String sql = "SELECT * FROM articles WHERE visible="
				+ Article.visible_VIEW + " AND  outdate<'"
				+ Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
				+ "'" + by + " ORDER BY id DESC LIMIT " + (page - 1)
				* ENV.getPAGESIZE() + "," + ENV.getPAGESIZE();
		// ArrayList<ArticleBean> articleBeans = new ArrayList<ArticleBean>();
		// for (Article article : executeQuery(sql, Article.class)) {
		// articleBeans.add((ArticleBean) article);
		// }
		return executeQuery(sql, Article.class);
	}

	// public int updateHitnum(int articleid) {
	// String sql = "UPDATE articles SET hitnum=hitnum+1 WHERE id="
	// + articleid;
	// execute(sql);
	// return 0;
	// }

	// public void updatePostdate(int articleid) {
	// String sql = "UPDATE articles SET postdate='"
	// + Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
	// + "' WHERE id=" + articleid;
	// this.execute(sql);
	// }

	public Article getArticle(int articleid) {
		String sql = "SELECT * FROM articles WHERE id=" + articleid;
		for (Article article : executeQuery(sql, Article.class)) {
			return article;
		}
		return null;
	}

	/**
	 * 
	 * @param articleid
	 * @return
	 * @throws DataException
	 */
	public Article getArticle(User session_user, int articleid)
			throws DataException {
		Article article = new Article();
		String sql = "SELECT * FROM articles WHERE id=" + articleid;

		for (Article a : executeQuery(sql, Article.class)) {
			article = a;
			break;
		}

		if (session_user == null) {
			if (article.getVisible() == false) {
				throw new DataException("本公告已設定為不顯示！");
			}
			if (article.getOutdate().before(Calendar.getInstance().getTime())) {
				throw new DataException("本公告已經過期，請登入發文者身份才能瀏覽。");
			}
			if (article.getPostdate().after(Calendar.getInstance().getTime())) {
				throw new DataException("本公告尚未發佈。");
			}
		} else if (session_user.getUsergroup().equals(User.GROUP_USER)) {
			if (article.getVisible() == false) {
				throw new DataException("本公告已設定為不顯示！");
			}
		}
		return article;
	}

	@Override
	public synchronized int insert(Article article) throws SQLException {
		String sql = "INSERT INTO articles (account, title, "
				+ "info, type, hyperlink, content, hitnum, "
				+ "postdate, outdate, sortable, visible) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?)";
		int articleid = 0;
		PreparedStatement pstmt = getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, article.getAccount());
		pstmt.setString(2, article.getTitle());
		pstmt.setString(3, article.getInfo());
		pstmt.setString(4, article.getType());
		pstmt.setString(5, article.getHyperlink());
		pstmt.setString(6, article.getContent());
		pstmt.setInt(7, article.getHitnum());
		pstmt.setTimestamp(8, article.getPostdate());
		pstmt.setTimestamp(9, article.getOutdate());
		pstmt.setLong(10, article.getSortable());
		pstmt.setBoolean(11, article.getVisible());
		this.executeUpdate(pstmt);
		ResultSet rs = pstmt.getGeneratedKeys();
		rs.next();
		articleid = rs.getInt(1);
		rs.close();
		pstmt.close();
		return articleid;
	}

	@Override
	public int update(Article article) throws SQLException {
		String SQL = "UPDATE articles SET account=?, title=?"
				+ ", info=?, type=?, hyperlink=?, content=?, hitnum=?, "
				+ "postdate=?, outdate=?, sortable=?, visible=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = getConnection().prepareStatement(SQL);
		pstmt.setString(1, article.getAccount());
		pstmt.setString(2, article.getTitle());
		pstmt.setString(3, article.getInfo());
		pstmt.setString(4, article.getType());
		pstmt.setString(5, article.getHyperlink());
		pstmt.setString(6, article.getContent());
		pstmt.setInt(7, article.getHitnum());
		pstmt.setTimestamp(8, article.getPostdate());
		pstmt.setTimestamp(9, article.getOutdate());
		pstmt.setLong(10, article.getSortable());
		pstmt.setBoolean(11, article.getVisible());
		pstmt.setInt(12, article.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

}
