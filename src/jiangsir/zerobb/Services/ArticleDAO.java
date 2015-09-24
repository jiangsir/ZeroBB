/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package jiangsir.zerobb.Services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Factories.ArticleFactory;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tables.User.DIVISION;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.Utils;

/**
 * @author jiangsir
 * 
 */
public class ArticleDAO extends SuperDAO<Article> {

	// private synchronized int executeUpdate(PreparedStatement pstmt) {
	// long starttime = System.currentTimeMillis();
	// int result = -1;
	// try {
	// result = pstmt.executeUpdate();
	// } catch (SQLException e) {
	// new LogDAO().insert(new Log(this.getClass(), e));
	// e.printStackTrace();
	// }
	// System.out.println(ENV.logHeader() + "PSTMT_SQL=" + pstmt.toString()
	// + " 共耗時 " + (System.currentTimeMillis() - starttime) + " ms");
	// return result;
	// }

	/**
	 * 將文章設成隱藏, 並不真的刪除
	 * 
	 * @param articleid
	 * @return
	 */
	public int delete(int articleid) {
		String sql = "UPDATE articles SET visible=0 WHERE id=?";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, articleid);
			return this.executeUpdate(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public ArrayList<Article> searchArticles(String keyword, int page,
			int pagesize) {
		String SQL = "SELECT * FROM articles WHERE visible=? AND (id LIKE ? OR title LIKE ? "
				+ " OR content LIKE ?) ORDER BY id DESC LIMIT "
				+ ((page - 1) * pagesize < 0 ? 0 : (int) (page - 1) * pagesize)
				+ "," + pagesize;
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(SQL);
			pstmt.setBoolean(1, Article.visible_TRUE);
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
		String sql = "SELECT * FROM articles WHERE visible=?"
				+ " AND postdate<=? AND  outdate>=? AND info=? ORDER BY postdate DESC LIMIT 0,10";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			pstmt.setBoolean(1, true);
			pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
			pstmt.setString(4, Article.INFO.頭條.name());

			return this.executeQuery(pstmt, Article.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Article>();
	}

	/**
	 * 列出有效期限內的 article
	 * 
	 * @param by
	 * @param page
	 * @return
	 */
	protected ArrayList<Article> getArticles(Article.INFO[] infos,
			DIVISION division, int page, int pagesize) {
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
		if (division != null && division != User.DIVISION.none) {
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
				+ Article.visible_TRUE + " AND postdate<='"
				+ Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
				+ "' AND  outdate>='"
				+ Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
				+ "' " + by + " ORDER BY sortable DESC, postdate DESC LIMIT "
				+ ((page - 1) * pagesize < 0 ? 0 : (int) (page - 1) * pagesize)
				+ "," + pagesize;
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			return this.executeQuery(pstmt, Article.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Article>();
	}

	/**
	 * 用 tagname 來取得 article
	 * 
	 * @param tagname
	 * @param page
	 * @param pagesize
	 * @return
	 */
	protected ArrayList<Article> getArticlesByTagnames(Article.INFO[] infos,
			String[] tagnames, int page, int pagesize) {
		String sql_infos = "";
		if (infos != null && infos.length > 0) {
			sql_infos += " AND (";
			for (Article.INFO info : infos) {
				if (!sql_infos.contains("info")) {
					sql_infos += "info='" + info + "'";
				} else {
					sql_infos += " OR info='" + info + "'";
				}
			}
			sql_infos += ")";
		}

		String sql_tagnames = "";
		if (tagnames == null || tagnames.length == 0) {

		} else {
			sql_tagnames += " AND (";
			for (String tagname : tagnames) {
				if (!sql_tagnames.contains("tagname")) {
					sql_tagnames += "tagname='" + tagname + "'";
				} else {
					sql_tagnames += " OR tagname='" + tagname + "'";
				}
			}
			sql_tagnames += ")";
		}
		String sql = "SELECT * FROM articles, article_tags WHERE "
				+ "articles.id=article_tags.articleid " + sql_infos
				+ sql_tagnames + " ORDER BY sortable DESC LIMIT "
				+ (page - 1) * pagesize + "," + pagesize;
		try {
			return this.executeQuery(this.getConnection().prepareStatement(sql),
					Article.class);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Article>();
		}
		// ArrayList<Article> articles = new ArrayList<Article>();
		// for (Article_Tag tag : new Article_TagDAO().getArticle_Tags(tagnames,
		// page, pagesize)) {
		// Article article = this.getArticleById(tag.getArticleid());
		// if (article != null) {
		// articles.add(article);
		// }
		// }
		// return articles;
	}

	protected ArrayList<Article> getArticlesByRules(TreeSet<String> rules,
			String orderby, int page) {
		StringBuffer sql = new StringBuffer(5000);
		sql.append("SELECT * FROM articles ");
		sql.append(this.makeRules(rules, orderby, page));
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql.toString());
			return this.executeQuery(pstmt, Article.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Article>();
	}

	protected ArrayList<Article> getAllArticles() {
		String sql = "SELECT * FROM articles";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			return this.executeQuery(pstmt, Article.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Article>();
	}

	/**
	 * 列出所有 visible 的 article, 包含未來 and 過期
	 * 
	 * @param by
	 * @param page
	 * @return
	 */
	public ArrayList<Article> getAllArticles(String by, int page,
			int pagesize) {
		if (by == null || by.equals("") || by.equals("all")) {
			by = " ";
		} else {
			by = " AND account='" + by + "'";
		}
		String sql = "SELECT * FROM articles WHERE visible="
				+ Article.visible_TRUE + by + " ORDER BY id DESC LIMIT "
				+ (page - 1) * pagesize + "," + pagesize;
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			return executeQuery(pstmt, Article.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Article>();
	}

	/**
	 * 列出過期的 article
	 * 
	 * @param by
	 * @param page
	 * @return
	 */
	public ArrayList<Article> getOutdateArticles(Article.INFO[] infos,
			User.DIVISION division, int page) {
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
				+ Article.visible_TRUE + " AND  outdate<'"
				+ Utils.parseDatetime(Calendar.getInstance().getTimeInMillis())
				+ "'" + by + " ORDER BY id DESC LIMIT "
				+ (page - 1) * ENV.getPAGESIZE() + "," + ENV.getPAGESIZE();
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			return executeQuery(pstmt, Article.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Article>();
	}

	public Article getArticleById(int articleid) {
		String sql = "SELECT * FROM articles WHERE id=" + articleid;
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			for (Article article : executeQuery(pstmt, Article.class)) {
				return article;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ArticleFactory.getNullArticle();
	}

	public Article getArticleById(String articleid) {
		if (articleid == null || !articleid.matches("[0-9]+")) {
			return ArticleFactory.getNullArticle();
		}
		return this.getArticleById(Integer.parseInt(articleid));
	}

	/**
	 * 
	 * @param articleid
	 * @return
	 * @throws DataException
	 */
	public Article getArticle(CurrentUser currentUser, int articleid)
			throws DataException {
		Article article = this.getArticleById(articleid);
		// String sql = "SELECT * FROM articles WHERE id=" + articleid;
		//
		// for (Article a : executeQuery(sql, Article.class)) {
		// article = a;
		// break;
		// }

		if (currentUser == null) {
			if (article.getVisible() == false) {
				throw new DataException("本公告已設定為不顯示！");
			}
			if (article.getOutdate().before(Calendar.getInstance().getTime())) {
				throw new DataException("本公告已經過期，請登入發文者身份才能瀏覽。");
			}
			if (article.getPostdate().after(Calendar.getInstance().getTime())) {
				throw new DataException("本公告尚未發佈。");
			}
		} else if (currentUser.getRole() == User.ROLE.USER) {
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
		PreparedStatement pstmt = getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, article.getAccount());
		pstmt.setString(2, article.getTitle());
		pstmt.setString(3, article.getInfo().name());
		pstmt.setString(4, article.getType());
		pstmt.setString(5, article.getHyperlink());
		pstmt.setString(6, article.getContent());
		pstmt.setInt(7, article.getHitnum());
		pstmt.setTimestamp(8, article.getPostdate());
		pstmt.setTimestamp(9, article.getOutdate());
		pstmt.setLong(10, article.getSortable());
		pstmt.setBoolean(11, article.getVisible());
		return this.executeInsert(pstmt);
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
		pstmt.setString(3, article.getInfo().name());
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
		return result;
	}

	@Override
	protected boolean delete(long i) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
