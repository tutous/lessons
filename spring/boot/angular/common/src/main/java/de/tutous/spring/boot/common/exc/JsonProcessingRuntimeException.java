package de.tutous.spring.boot.common.exc;

public class JsonProcessingRuntimeException extends ApplicationRuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public JsonProcessingRuntimeException(ErrorCode<?> errorCode, String[] args) {
		super(errorCode, args);
	}

	public JsonProcessingRuntimeException(Throwable exc, ErrorCode<?> errorCode, String[] args) {
		super(exc, errorCode, args);
	}
}
