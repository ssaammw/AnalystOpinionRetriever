package ca.sammy.exceptions;

/**
 * 
 * @author Sammy
 *
 */
public class UnableToConnectToWebsiteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -96923872912574391L;

	public UnableToConnectToWebsiteException(String msg) {
		super(msg);
	}

	public UnableToConnectToWebsiteException(String msg, Throwable e) {
		super(msg, e);
	}

}
