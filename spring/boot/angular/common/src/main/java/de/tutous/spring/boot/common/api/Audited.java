package de.tutous.spring.boot.common.api;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import de.tutous.spring.boot.common.bo.AuditedBO;

public interface Audited extends Serializable {

	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

	@JsonGetter("createdBy")
	public default String getCreatedBy() {
		return getAuditedInfo().getCreatedBy();
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonGetter("createdOn")
	public default Date getCreatedOn() {
		return getAuditedInfo().getCreatedDate();
	}

	@JsonGetter("modifiedBy")
	public default String getModifiedBy() {
		return getAuditedInfo().getLastModifiedBy();
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	@JsonGetter("modifiedOn")
	public default Date getModifiedAt() {
		return getAuditedInfo().getLastModifiedDate();
	}

	@JsonIgnore
	public AuditedBO getAuditedInfo();

}
