package jiangsir.zerobb.Tables;

import java.sql.Date;

import jiangsir.zerobb.Tools.Utils;

public class Log {
	private Integer id;
	private String uri = "";
	private String account = "Unknown User";
	private String ipaddr = "Unknown IP";
	private String exceptiontype = "";
	private String exception = "";
	private Date exceptiontime = new Date(new java.util.Date().getTime());

	public Log(Class<?> theclass, Exception exception) {
		this.setUri(theclass.getSimpleName());
		this.setExceptiontype(exception.getLocalizedMessage());
		this.setException(Utils.printStackTrace(exception));
	}

	public Log(Class<?> theclass, String account, String ipaddr,
			Exception exception) {
		this.setUri(theclass.getSimpleName());
		this.setAccount(account);
		this.setIpaddr(ipaddr);
		this.setExceptiontype(exception.getLocalizedMessage());
		this.setException(Utils.printStackTrace(exception));
	}

	public Log(String uri, String account, String ipaddr, String exceptiontype,
			Exception e) {
		this.setUri(uri);
		this.setAccount(account);
		this.setIpaddr(ipaddr);
		this.setExceptiontype(exceptiontype);
		this.setException(Utils.printStackTrace(e));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		if (uri != null && uri.length() > 100) {
			uri = uri.substring(uri.length() - 100);
		}
		this.uri = uri;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		if (account != null) {
			this.account = account;
		}
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getExceptiontype() {
		return exceptiontype;
	}

	public void setExceptiontype(String exceptiontype) {
		if (exceptiontype != null && exceptiontype.length() > 200) {
			exceptiontype = exceptiontype.substring(0, 200);
			exceptiontype += "訊息太長(>200)，到此省略...";
		}
		this.exceptiontype = exceptiontype;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		if (exception != null && exception.length() > 100000) {
			exception = exception.substring(0, 10000);
			exception += "訊息太長(>100000)，到此省略...";
		}
		this.exception = exception;
	}

	public Date getExceptiontime() {
		return exceptiontime;
	}

	public void setExceptiontime(Date exceptiontime) {
		this.exceptiontime = exceptiontime;
	}

}
