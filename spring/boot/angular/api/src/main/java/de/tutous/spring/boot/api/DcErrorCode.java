package de.tutous.spring.boot.api;

import org.springframework.http.HttpStatus;

import de.tutous.spring.boot.common.exc.ErrorCode;

public enum DcErrorCode implements ErrorCode<String> {

	/** data container error codes from 100 to 199 */
	INVALID_DATA_CONTAINER("100", "invalid DC %s", HttpStatus.BAD_REQUEST),
	DATA_CONTAINER_NOT_FOUND("101", "DC not found %s", HttpStatus.NOT_FOUND),
	/** */
    INCORRECT_FILE_STORAGE("102", "can't save file %s", HttpStatus.CONFLICT),
    INCORRECT_FILE_DELETE("103", "can't delete file %s", HttpStatus.CONFLICT),
	/** vehicle classes error codes from 200 to 299 */
	VEHICLE_CLASS_NOT_FOUND("200", "vehicle class not found %s", HttpStatus.NOT_FOUND),
	/** */
	VEHICLES_CLASS_NOT_FOUND("201", "vehicle classes not found %s", HttpStatus.NOT_FOUND),
	/** user error codes from 300 to 399 */
	USER_NOT_FOUND("300", "person not found %s", HttpStatus.NOT_FOUND),
	/** role error codes from 400 to 499 */
	ROLE_NOT_FOUND("400", "role not found %s", HttpStatus.NOT_FOUND),
	/** role error codes from 500 to 599 */
    MEMBER_NOT_FOUND("500", "member not found %s", HttpStatus.NOT_FOUND);

	private String id;
	private String message;
	private HttpStatus httpStatus;

	private DcErrorCode(String id, String message, HttpStatus httpStatus) {
		this.id = id;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getMessage(String[] args) {
		return String.format(message, toSaveArgs(args));
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
