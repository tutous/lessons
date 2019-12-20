package de.tutous.spring.boot.common.bo;

import java.util.Date;

public interface AuditedBO
{

    String getCreatedBy();

    Date getCreatedDate();

    String getLastModifiedBy();

    Date getLastModifiedDate();

}