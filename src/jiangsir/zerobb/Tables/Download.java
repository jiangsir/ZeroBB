/**
 * idv.jiangsir.objects - Task.java
 * 2008/2/19 下午 04:32:20
 * jiangsir
 */
package jiangsir.zerobb.Tables;

import java.util.Date;

/**
 * @author jiangsir
 * 
 */
public class Download {
	private Integer id;
	private String account = "";
	private String ipfrom = "";
	private Date downloadtime = new Date();
	private String url = "";

	public void setAccount(String account) {
		this.account = account;
	}

	public void setDownloadtime(Date downloadtime) {
		this.downloadtime = downloadtime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIpfrom(String ipfrom) {
		this.ipfrom = ipfrom;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccount() {
		return account;
	}

	public Date getDownloadtime() {
		return downloadtime;
	}

	public Integer getId() {
		return id;
	}

	public String getIpfrom() {
		return ipfrom;
	}

	public String getUrl() {
		return url;
	}

}
