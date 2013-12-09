/**
 * idv.jiangsir.Objects - Message.java
 * 2008/12/19 下午 01:23:42
 * jiangsir
 */
package jiangsir.zerobb.Tables;

import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jiangsir
 * 
 */
public class Message {
	public final static int MessageType_INFOR = 0;
	public final static int MessageType_ALERT = 1;
	public final static int MessageType_ERROR = 2;
	private String Type = "";
	private String PlainTitle = "";
	private String ResourceTitle = "";
	private String PlainMessage = "";
	private StackTraceElement[] StackTrace = null;
	private String ResourceMessage = "";
	private String[] ResourceParam = {};
	private LinkedHashMap<String, String> Links = new LinkedHashMap<String, String>();

	public Message() {
		this.setType(Message.MessageType_INFOR);
	}

	public Message(HttpServletRequest request) {
		this.setType(Message.MessageType_INFOR);
		// String uri = request.getMethod() + " " + request.getRequestURI();
		// if (request.getQueryString() != null) {
		// uri += "?" + request.getQueryString();
		// }
	}

	public Message(int type, String plaintitle) {
		this.setType(type);
		this.setPlainTitle(plaintitle);
	}

	public Message(int type, String plaintitle, String PlainMessage) {
		this.setType(type);
		this.setPlainTitle(plaintitle);
		this.setPlainMessage(PlainMessage);
	}

	public Message(HttpServletRequest request, Throwable throwable) {
		this(request);
		this.setType(Message.MessageType_INFOR);
		this.setPlainTitle(throwable.getLocalizedMessage());
		this.setStackTrace(throwable.getStackTrace());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public LinkedHashMap<String, String> getLinks() {
		if (Links.size() == 0) {
			Links.put("javascript:history.back();", "上一頁");
		}
		return Links;
	}

	public void setLinks(String key, String value) {
		Links.put(key, value);
	}

	public String getPlainMessage() {
		return PlainMessage;
	}

	public void setPlainMessage(String plainMessage) {
		PlainMessage = plainMessage;
	}

	public String getPlainTitle() {
		return PlainTitle;
	}

	public void setPlainTitle(String plainTitle) {
		PlainTitle = plainTitle;
	}

	public StackTraceElement[] getStackTrace() {
		return StackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		StackTrace = stackTrace;
	}

	public String getResourceTitle() {
		return ResourceTitle;
	}

	public void setResourceTitle(String resourceTitle) {
		ResourceTitle = resourceTitle;
	}

	public String getType() {
		return Type;
	}

	public void setType(int type) {
		if (type == Message.MessageType_INFOR) {
			Type = "INFOR";
		} else if (type == Message.MessageType_ALERT) {
			Type = "ALERT";
		} else if (type == Message.MessageType_ERROR) {
			Type = "ERROR";
		}
	}

	public String getResourceMessage() {
		return ResourceMessage;
	}

	/**
	 * 可放入以 , 隔開的多個 Resource Message
	 * 
	 * @param resourceMessage
	 */
	public void setResourceMessage(String resourceMessage) {
		ResourceMessage = resourceMessage;
	}

	public String[] getResourceParam() {
		return ResourceParam;
	}

	public void setResourceParam(String[] resourceParam) {
		ResourceParam = resourceParam;
	}

}
