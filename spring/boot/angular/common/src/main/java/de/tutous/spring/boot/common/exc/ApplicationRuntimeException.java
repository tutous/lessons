package de.tutous.spring.boot.common.exc;

import org.springframework.http.HttpStatus;

public abstract class ApplicationRuntimeException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ErrorCode<?> errorCode;
	private String[] args;

	public ApplicationRuntimeException(ErrorCode<?> errorCode, String[] args) {
		super();
		this.errorCode = errorCode;
		this.args = args;
	}

	public ApplicationRuntimeException(Throwable cause, ErrorCode<?> errorCode, String[] args) {
		super(cause);
		this.errorCode = errorCode;
		this.args = args;
	}

	@Override
	public String getMessage() {
		return this.errorCode.getMessage(args);
	}

	public <ID> ID getId(Class<ID> type) {
		return type.cast(this.errorCode.getId());
	}

	public HttpStatus getHttpStatus() {
		return this.errorCode.getHttpStatus();
	}

}
