package de.tutous.spring.boot.bo;

import de.tutous.spring.boot.common.bo.BusinessObject;

public class DataContainerFilterBO implements BusinessObject<DataContainerFilterBO, BusinessObject.VoidSerializable>
{

    private String name;

    public DataContainerFilterBO(String name)
    {
        super();
        this.name = name;
    }

    @Override
    public BusinessObject.VoidSerializable getId()
    {
        throw new UnsupportedOperationException("get id is not supported");
    }

    @Override
    public DataContainerFilterBO toSafeBO()
    {
        return this;
    }

    public String getName()
    {
        return name;
    }

}
