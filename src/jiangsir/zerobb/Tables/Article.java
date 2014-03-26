package jiangsir.zerobb.Tables;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import jiangsir.zerobb.DAOs.UpfileDAO;
import jiangsir.zerobb.DAOs.UserDAO;
import jiangsir.zerobb.Exceptions.DataException;

/**
 *  - User.java
 * 2008/4/29 下午 05:41:54
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class Article {

	private Integer id = 0;
	private String account = "";
	private String title = "";
	private User.DIVISION division = User.DIVISION.none;

	// public static final String infos[] = { "一般", "重要", "頭條" };
	// public static final String info_GENERAL = infos[0];
	// public static final String info_IMPORTANT = infos[1];
	// public static final String info_HEADLINE = infos[2];

	public enum INFO {
		一般, //
		重要, //
		頭條; //
	}

	private INFO info = INFO.一般;

	public static final String[] types = { "default", "hyperlink" };
	public static final int type_DEFAULT = 0;
	public static final int type_HYPERLINK = 1;
	private String type = Article.types[type_DEFAULT];
	private String hyperlink = "#";
	private String content = "";
	private Integer hitnum = 0;
	private Timestamp postdate = new Timestamp(new java.util.Date().getTime());
	private Timestamp outdate = new Timestamp(new java.util.Date().getTime());

	private Long sortable = Calendar.getInstance().getTimeInMillis();
	public static final Boolean visible_TRUE = true;
	public static final Boolean visible_FALSE = false;
	private Boolean visible = true;

	// =======================================================

	public Article() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? this.account : account;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws DataException {
		if (title == null || "".equals(title)) {
			throw new DataException("標題必須填寫，不可空白。");
		}
		this.title = title;
	}

	public User.DIVISION getDivision() {
		return division;
	}

	public void setDivision(User.DIVISION division) {
		this.division = division;
	}

	public void setDivision(String division) {
		this.setDivision(User.DIVISION.valueOf(division));
	}

	public INFO getInfo() {
		return info;
	}

	public void setInfo(INFO info) {
		this.info = info;
	}

	public void setInfo(String info) {
		this.setInfo(INFO.valueOf(info));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? this.type : type;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink == null ? this.hyperlink : hyperlink;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? this.content : content;
	}

	public Integer getHitnum() {
		return hitnum;
	}

	public void setHitnum(Integer hitnum) {
		this.hitnum = hitnum == null ? this.hitnum : hitnum;
	}

	public Timestamp getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) throws DataException {
		try {
			this.setPostdate(Timestamp.valueOf(postdate));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new DataException(e.getLocalizedMessage());
		}
	}

	public void setPostdate(Timestamp postdate) {
		this.postdate = postdate;
	}

	public Timestamp getOutdate() {
		return outdate;
	}

	public void setOutdate(String outdate) throws DataException {
		try {
			this.setOutdate(Timestamp.valueOf(outdate));
		} catch (IllegalArgumentException e) {
			throw new DataException(e.getLocalizedMessage());
		}
	}

	public void setOutdate(Timestamp outdate) {
		if (Math.abs(outdate.getTime() - new java.util.Date().getTime()) < 20 * 60 * 1000) {
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DATE, now.get(Calendar.DATE) + 7);
			// 如果時間有誤，就直接預設 7 天後即可。
			this.outdate = new Timestamp(now.getTimeInMillis());
		} else {
			this.outdate = outdate;
		}
	}

	/**
	 * 顯示有效期限還有多久
	 */
	public String getAvailable() {
		long countdown = this.getOutdate().getTime()
				- new java.util.Date().getTime();
		long min = (countdown - (countdown % 60000)) / 60000;
		long hour = (min - (min % 60)) / 60;
		long day = (hour - (hour % 24)) / 24;
		String result = "";
		if (day > 0) {
			result += day + "天";
		}
		if (hour > 0) {
			result += hour % 24 + "小時";
		}
		if (min > 0) {
			result += min % 60 + "分";
		}
		return result;
	}

	// =====================================================================

	public Long getSortable() {
		return sortable;
	}

	public void setSortable(Long sortable) {
		this.sortable = sortable;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public ArrayList<Upfile> getUpfiles() {
		return new UpfileDAO().getUpfiles(id);
	}

	public User getUser() {
		return new UserDAO().getUserByAccount(account);
	}

	public boolean isNullArticle() {
		Article newarticle = new Article();
		if (this.getId().equals(newarticle.getId())
				&& this.getAccount().equals(newarticle.getAccount())
				&& this.getTitle().equals(newarticle.getTitle())) {
			return true;
		}
		return false;
	}

	public boolean isUpdatable(CurrentUser currentUser) throws DataException {
		if (currentUser == null || currentUser.isNullUser()) {
			throw new DataException("您可能尚未登入。");
		}
		if (this.isNullArticle()) {
			throw new DataException("找不到這個文章！");
		}
		if ((currentUser.getRole() == User.ROLE.ADMIN || currentUser
				.getAccount().equals(getAccount()))) {
			return true;
		}
		return false;
	}

}
