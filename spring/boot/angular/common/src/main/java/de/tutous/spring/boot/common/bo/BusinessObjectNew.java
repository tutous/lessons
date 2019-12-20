package de.tutous.spring.boot.common.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.tutous.spring.boot.common.bo.BusinessObject.VoidSerializable;

public class BusinessObjectNew implements BusinessObject<BusinessObjectNew, VoidSerializable>
{

    @Override
    @JsonIgnore
    public VoidSerializable getId()
    {
        return null;
    }

    @Override
    public BusinessObjectNew toSafeBO()
    {
        throw new UnsupportedOperationException("is not implemented");
    }

}
