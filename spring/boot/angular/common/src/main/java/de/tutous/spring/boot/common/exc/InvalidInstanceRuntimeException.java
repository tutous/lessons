package de.tutous.spring.boot.common.exc;

public class InvalidInstanceRuntimeException extends ApplicationRuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInstanceRuntimeException(ErrorCode<?> errorCode, String[] args) {
		super(errorCode, args);
	}

}
