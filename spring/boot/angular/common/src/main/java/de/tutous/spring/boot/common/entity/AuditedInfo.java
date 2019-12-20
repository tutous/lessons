package de.tutous.spring.boot.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import de.tutous.spring.boot.common.bo.AuditedBO;
import de.tutous.spring.boot.common.log.ToString;

@Embeddable
public class AuditedInfo implements AuditedBO, ToString {

	@CreatedBy
	@Column(name = "CREATED_BY")
	@ToStringProperty
	private String createdBy;

	@CreatedDate
	@Column(name = "CREATED_ON")
	@ToStringProperty
	private Date createdDate;

	@LastModifiedBy
	@Column(name = "MODIFIED_BY")
	@ToStringProperty
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "MODIFIED_ON")
	@ToStringProperty
	private Date lastModifiedDate;

	@Override
    public String getCreatedBy() {
		return createdBy;
	}

	@Override
    public Date getCreatedDate() {
		return createdDate;
	}

	@Override
    public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	@Override
    public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

    @Override
    public String toString()
    {
        return propertiesToString();
    }
}
