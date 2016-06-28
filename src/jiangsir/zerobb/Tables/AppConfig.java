/**
 * idv.jiangsir.objects - Contest.java
 * 2008/2/19 下午 04:32:20
 * jiangsir
 */
package jiangsir.zerobb.Tables;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeSet;

import jiangsir.zerobb.Annotations.Persistent;
import jiangsir.zerobb.Annotations.Property;
import jiangsir.zerobb.Services.TagDAO;
import jiangsir.zerobb.Tools.IpAddress;
import jiangsir.zerobb.Tools.StringTool;

/**
 * @author jiangsir
 * 
 */
public class AppConfig {
	@Persistent(name = "id")
	private Long id = 0L;
	@Property(key = "Title")
	@Persistent(name = "title")
	private String Title = "A Title for Your Site";
	@Property(key = "Header")
	@Persistent(name = "header")
	private String Header = "Header";
	@Property(key = "PageSize")
	@Persistent(name = "pagesize")
	private int pagesize = 20;
	@Property(key = "DefaultLogin")
	@Persistent(name = "defaultlogin")
	private String defaultlogin = "Login";
	@Property(key = "AuthDomains")
	@Persistent(name = "authdomains")
	private TreeSet<String> authdomains = new TreeSet<String>();
	@Property(key = "client_id")
	@Persistent(name = "client_id")
	private String client_id = "";
	@Property(key = "client_secret")
	@Persistent(name = "client_secret")
	private String client_secret = "";

	@Property(key = "redirect_uri")
	@Persistent(name = "redirect_url")
	private String redirect_uri = "";

	@Property(key = "signinip")
	@Persistent(name = "signinip")
	private TreeSet<IpAddress> signinip = new TreeSet<IpAddress>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			add(new IpAddress("127.0.0.1"));
		}
	};
	@Property(key = "announcement")
	@Persistent(name = "announcement")
	private String announcement = "";
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	// =====================================================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getHeader() {
		return Header;
	}

	public void setHeader(String header) {
		Header = header;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pageSize) {
		pagesize = pageSize;
	}

	public void setPagesize(String pageSize) {
		this.setPagesize(Integer.parseInt(pageSize));
	}

	public String getDefaultlogin() {
		return defaultlogin;
	}

	public void setDefaultlogin(String defaultLogin) {
		defaultlogin = defaultLogin;
	}

	public TreeSet<String> getAuthdomains() {
		return authdomains;
	}

	public void setAuthdomains(TreeSet<String> authDomains) {
		authdomains = authDomains;
	}

	public void setAuthdomains(String authDomains) {
		if (authDomains == null || "".equals(authDomains)) {
			return;
		}
		this.setAuthdomains(StringTool.String2TreeSet(authDomains));
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		if (redirect_uri == null) {
			return;
		}
		this.redirect_uri = redirect_uri;
	}

	public TreeSet<IpAddress> getSigninip() {
		return signinip;
	}

	public void setSigninip(TreeSet<IpAddress> ips) {
		if (ips == null) {
			return;
		}
		this.signinip = ips;
	}

	public void setSigninip(String signinip) {
		if (signinip == null) {
			return;
		}
		TreeSet<IpAddress> ips = new TreeSet<IpAddress>();
		for (String ip : StringTool.String2TreeSet(signinip)) {
			ips.add(new IpAddress(ip));
		}
		this.setSigninip(ips);
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		if (announcement == null) {
			return;
		}
		this.announcement = announcement.trim();
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	// ===================================================================
	public ArrayList<Tag> getTags() {
		return new TagDAO().getTags();
	}

}
