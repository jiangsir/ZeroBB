package jiangsir.zerobb.Tables;

import java.io.InputStream;

import jiangsir.zerobb.Services.ArticleDAO;
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
public class Upfile {

	private Integer id = 0;
	private Integer articleid = 0;
	private String filepath = "";
	private String filename = "";
	private String filetmpname = "";
	private Long filesize = 0L;
	private String filetype = "";
	private InputStream binary;
	private Integer hitnum = 0;
	public static final Integer visible_OPEN = 1;
	public static final Integer visible_HIDE = 0;
	private Integer visible = visible_OPEN;

	// =================================

	// 指定 zerobb 3.0 by JSP 之前的 upfileid, 必須要用不同的方式存取
	public static final int OLD_UPFILDID = 5980;
	// 在 下面這個 upfileid 之後，zerobb 的附加檔案改用 blob 來儲存
	public static final int FILE_UPFILDID = 10000;

	public Upfile() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleid() {
		return articleid;
	}

	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletmpname() {
		return filetmpname;
	}

	public void setFiletmpname(String filetmpname) {
		this.filetmpname = filetmpname;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public Integer getHitnum() {
		return hitnum;
	}

	public void setHitnum(Integer hitnum) {
		this.hitnum = hitnum;
	}

	public InputStream getBinary() {
		return binary;
	}

	public void setBinary(InputStream binary) {
		this.binary = binary;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public String getINNER_PATH() {
		return System.getProperty("file.separator") + "upfiles"
				+ System.getProperty("file.separator");
	}

	public String getINNER_FILENAME() {
		String sub = filename.substring(filename.lastIndexOf("."));
		return this.getArticleid()
				+ "_"
				+ this.getId()
				+ "_"
				+ new ArticleDAO().getArticleById(this.getArticleid())
						.getAccount() + sub;
	}

	/**
	 * 判斷是否可以由 google viewer 顯示。如： doc, docx, pdf 等的文件。
	 */
	public boolean isIsGoogleViewer() {
		if (this.getFiletype().equals("application/pdf")
				|| this.getFiletype().contains("word")
				|| this.getFiletype().contains("powerpoint")
				|| this.getFiletype().contains("excel")) {
			return true;
		}
		return false;
	}

	/**
	 * 判斷是否是圖片
	 * 
	 * @return
	 */
	public boolean isIsImage() {
		if (this.getFiletype().startsWith("image/")) {
			return true;
		}
		return false;
	}

	// =====================================================================

}
