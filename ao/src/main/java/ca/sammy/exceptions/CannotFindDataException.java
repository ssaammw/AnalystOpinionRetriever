package ca.sammy.exceptions;

/**
 * 
 * @author Sammy
 *
 */
public class CannotFindDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3325541952608200766L;

	public CannotFindDataException(String msg) {
		super(msg);
	}

	public CannotFindDataException(String msg, Throwable e) {
		super(msg, e);
	}
}
