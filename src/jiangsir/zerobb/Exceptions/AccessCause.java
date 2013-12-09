/**
 * idv.jiangsir.Exceptions - GeneralCause.java
 * 2011/8/13 下午2:52:00
 * nknush-001
 */
package jiangsir.zerobb.Exceptions;

/**
 * @author nknush-001
 * 
 */
public class AccessCause extends Throwable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TYPE_ALERT = "ALERT";
	public static final String TYPE_INFORMATION = "INFORMATION";
	public static final String TYPE_WARNING = "WARNING";
	public static final String TYPE_ERROR = "ERROR";
	public static final String TYPE_SEVERE = "SEVERE";
	private String type = TYPE_WARNING;
	private String session_account = "";
	private String resource_message = "";
	private String text_message = "";
	private String debug_message = "";

	public AccessCause(String type, String sessionAccount,
			String resourceMessage, String textMessage, String debugMessage) {
		super();
		this.type = type;
		session_account = sessionAccount;
		resource_message = resourceMessage;
		text_message = textMessage;
		debug_message = debugMessage;
	}

	@Override
	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}

	public String getSession_account() {
		return session_account;
	}

	public void setSession_account(String sessionAccount) {
		session_account = sessionAccount;
	}

	public String getResource_message() {
		return resource_message;
	}

	public void setResource_message(String resourceMessage) {
		resource_message = resourceMessage;
	}

	public String getText_message() {
		return text_message;
	}

	public void setText_message(String textMessage) {
		text_message = textMessage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDebug_message() {
		return debug_message;
	}

	public void setDebug_message(String debugMessage) {
		debug_message = debugMessage;
	}

}
