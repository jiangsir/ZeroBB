/**
 * idv.jiangsir.utils - ZjException.java
 * 2010/9/15 下午1:30:49
 * nknush-001
 */
package jiangsir.zerobb.Exceptions;

/**
 * @author nknush-001
 * 
 */
public class AccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4800234202163628817L;

	public AccessException() {
		super();
	}

	public AccessException(Throwable cause) {
		super(cause.getLocalizedMessage(), cause);
	}

	public AccessException(AccessCause cause) {
		super(cause.getText_message() + ": debug:" + cause.getDebug_message(),
				cause);
	}

	/**
	 * @param string
	 */
	public AccessException(String session_account, String string) {
		super(string);
		// Logger logger = Logger.getLogger(this.getClass().getName());
		// logger.log(Level.WARNING, "ZjException: " + string);
	}

}
