/**
 * idv.jiangsir.Exceptions - ServerException.java
 * 2011/7/31 下午1:27:15
 * nknush-001
 */
package jiangsir.zerobb.Exceptions;

import jiangsir.zerobb.Exceptions.Alert.TYPE;

/**
 * @author nknush-001
 * 
 */
public class AlertException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Alert alert = new Alert();

	// public AlertException() {
	// super();
	// }

	// public AlertException(String message, Throwable cause) {
	// super(message, cause);
	// alert = new Alert(message, cause);
	// }

	// public DataException(Throwable cause) {
	// super(cause);
	// }

	public AlertException(Alert alert) {
		super(alert.getTitle(), alert);
		this.alert = alert;
	}

	public AlertException(String message) {
		super(message, new Alert(TYPE.DATAERROR, message, "", "", null));
		this.alert = new Alert(TYPE.DATAERROR, message, "", "", null);
	}

	public AlertException(String message, Alert alert) {
		super(message, alert);
		this.alert = alert;
	}

	public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

}
