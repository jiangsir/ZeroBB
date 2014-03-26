package jiangsir.zerobb.Tables;

import jiangsir.zerobb.Annotations.Persistent;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Tools.ENV;

/**
 *  - User.java
 * 2008/4/29 下午 05:41:54
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class User {
	public enum ROLE {
		ADMIN, // 管理權限
		MANAGER, // 一般管理員
		USER, // 一般使用者
		GUEST; // 訪客，或未登入者
	}

	public enum DIVISION {
		教務處, //
		學務處, //
		總務處, //
		輔導室, //
		人事室, //
		會計室, //
		圖資中心, //
		註冊組, //
		設備組, //
		健康中心, //
		公文公告區, //
		資訊組, //
		教學組, //
		學務處衛保組, //
		教師研習, //
		學務處榮譽榜, //
		校長室, //
		校園新聞, //
		榮譽榜, //
		校友會;//
	}

	@Persistent(name = "id")
	private Integer id = 0;
	@Persistent(name = "account")
	private String account = "";
	@Persistent(name = "name")
	private String name = "";
	@Persistent(name = "division")
	private String division = "";
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
	@Persistent(name = "description")
	private String description = "";
	@Persistent(name = "headline")
	private Boolean headline = false;
	@Persistent(name = "visible")
	private Boolean visible = true;

	public User() {
	}

	public boolean isNullUser() {
		if (this.getId().equals(new User().getId())
				&& this.getAccount().equals(new User().getAccount())) {
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

	public void setAccount(String account) throws DataException {
		if (account == null || account.equals("")) {
			throw new DataException("帳號資訊不存在，可能為空。");
		}
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}

	public String getDivisionName() {
		if (ENV.divisionmap.containsKey(division)) {
			return ENV.divisionmap.get(division);
		} else {
			return division;
		}
	}

	public void setDivision(String division) {
		this.division = division;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getHeadline() {
		return headline;
	}

	public void setHeadline(Boolean headline) {
		this.headline = headline;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	// public String getUsergroup() {
	// return usergroup;
	// }
	//
	// public void setUsergroup(String usergroup) {
	// this.usergroup = usergroup;
	// }

	// /**
	// * 這個 privilege 結合了 Group 裡的權限加上 extraprvilege
	// *
	// * @return
	// */
	// public String getPrivilege() {
	// return ENV.context.getInitParameter(usergroup);
	// }

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
			throw new DataException("角色不存在！" + e.getLocalizedMessage());
		}
	}

}
