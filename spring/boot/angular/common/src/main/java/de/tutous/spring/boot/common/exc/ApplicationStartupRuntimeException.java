package de.tutous.spring.boot.common.exc;

public class ApplicationStartupRuntimeException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ApplicationStartupRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationStartupRuntimeException(String message) {
		super(message);
	}

}
