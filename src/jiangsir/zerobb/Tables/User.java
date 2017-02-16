package jiangsir.zerobb.Tables;

import java.io.Serializable;

import jiangsir.zerobb.Annotations.Persistent;
import jiangsir.zerobb.Exceptions.AlertException;

/**
 *  - User.java
 * 2008/4/29 下午 05:41:54
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -363437329708413101L;

	public enum ROLE {
		GUEST, // 訪客，或未登入者
		USER, // 一般使用者
		DIVISION_LEADER, // 部門管理者、處室主任
		MANAGER, // 一般管理員
		ADMIN; // 管理權限
	}

	public enum DIVISION {
		none(""), //
		admin("管理員"), //
		jiaowu("教務處"), //
		xuewu("學務處"), //
		zongwu("總務處"), //
		fudao("輔導室"), //
		renshi("人事室"), //
		kuaiji("會計室"), //
		lib("圖資中心"), //
		// zhucezu("註冊組"), //
		// shebei("設備組"), //
		// jiankang("健康中心"), //
		// documentation("公文公告區"), //
		// zixun("資訊組"), //
		// jiaoxue("教學組"), //
		// weisheng("學務處衛保組"), //
		teachers("教師研習"), //
		// xuewuhonor("學務處榮譽榜"), //
		principal("校長室"), //
		// schoolnews("校園新聞"), //
		// honoredlist("榮譽榜"), //
		alumni("校友會");//

		private String value;

		DIVISION(final String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	@Persistent(name = "id")
	private Integer id = 0;
	@Persistent(name = "account")
	private String account = "";
	@Persistent(name = "name")
	private String name = "";
	@Persistent(name = "division")
	private DIVISION division = DIVISION.none;
	@Persistent(name = "role")
	private ROLE role = ROLE.GUEST;

	// public static String GROUP_USER = "GroupUser";
	// public static String GROUP_ADMIN = "GroupAdmin";
	// private String usergroup = "GroupUser";

	@Persistent(name = "passwd")
	private String passwd = "";
	@Persistent(name = "email")
	private String email = "";
	@Persistent(name = "homepage")
	private String homepage = "";
	@Persistent(name = "picture")
	private String picture = "";
	@Persistent(name = "description")
	private String description = "";
	@Persistent(name = "headline")
	private Boolean headline = false;
	@Persistent(name = "visible")
	private Boolean visible = true;

	public User() {
	}

	public boolean isNullUser() {
		if (this.getId().equals(new User().getId()) && this.getAccount().equals(new User().getAccount())) {
			return true;
		}
		return false;
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

	public void setAccount(String account) throws AlertException {
		if (account == null || account.equals("")) {
			throw new AlertException("帳號資訊不存在，可能為空。");
		}
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DIVISION getDivision() {
		return division;
	}

	public void setDivision(DIVISION division) {
		this.division = division;
	}

	public void setDivision(String division) {
		this.setDivision(User.DIVISION.valueOf(division));
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) {
			return;
		}
		this.description = description;
	}

	public Boolean getHeadline() {
		return headline;
	}

	public void setHeadline(Boolean headline) {
		this.headline = headline;
	}
	public void setHeadline(String headline) {
		if (headline == null) {
			return;
		}
		this.headline = Boolean.valueOf(headline);
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public void setVisible(String visible) {
		if (visible == null) {
			return;
		}
		this.visible = Boolean.valueOf(visible);
	}

	public ROLE[] getRoles() {
		return ROLE.values();
	}

	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public void setRole(String role) {
		try {
			this.setRole(ROLE.valueOf(role));
		} catch (Exception e) {
			throw new AlertException("角色不存在！" + e.getLocalizedMessage());
		}
	}

	// =================================================================

}
