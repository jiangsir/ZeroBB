/**
 * idv.jiangsir.Exceptions - ServerException.java
 * 2011/7/31 下午1:27:15
 * nknush-001
 */
package jiangsir.zerobb.Exceptions;

/**
 * @author nknush-001 <br/>
 *         專門轉給前端處理的 Exception
 * 
 */
public class JQueryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JQueryException() {
		super();
	}

	public JQueryException(String message, Throwable cause) {
		super(message, cause);
	}

	public JQueryException(Throwable cause) {
		super(cause);
	}

	public JQueryException(String message) {
		super(message);
	}

}
