package de.tutous.spring.boot.common.exc;

public class EntityNotFoundRuntimeException extends ApplicationRuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundRuntimeException(ErrorCode<?> errorCode, String[] args) {
		super(errorCode, args);
	}

}
