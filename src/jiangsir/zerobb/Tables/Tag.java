package jiangsir.zerobb.Tables;

/**
 *  - User.java
 * 2008/4/29 下午 05:41:54
 * jiangsir
 */

/**
 * @author jiangsir
 * 
 */
public class Tag {

	private Integer id = 0;
	private String tagname = "";
	private String tagtitle = "";
	private String descript = "";
	private Boolean visible = true;

	// =======================================================

	public Tag() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public String getTagtitle() {
		return tagtitle;
	}

	public void setTagtitle(String tagtitle) {
		this.tagtitle = tagtitle;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
