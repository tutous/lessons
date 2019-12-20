package de.tutous.spring.boot.common.api;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.tutous.spring.boot.common.log.ToString;

public class AppError<C> implements ToString {

	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

	@ToStringProperty
	@JsonProperty("code")
	private C code;
	@ToStringProperty
	@JsonProperty("message")
	private String message;
	@ToStringProperty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonProperty("timestamp")
	private Date timestamp;
	@ToStringProperty
	@JsonProperty("status")
	private int status;
	@ToStringProperty
	@JsonProperty("error")
	private String error;
	@ToStringProperty
	@JsonProperty("path")
	private String path;
	@ToStringProperty
	@JsonProperty("method")
	private String method;
	@ToStringProperty
	@JsonProperty("logid")
	private String logid;

	public AppError(C code, String message, Date timestamp, int status, String error, String path, String method,
			String logid) {
		super();
		this.code = code;
		this.message = message;
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.path = path;
		this.method = method;
		this.logid = logid;
	}
	
	@Override
	public String toString() {
		return propertiesToString();
	}

}
