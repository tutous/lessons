package de.tutous.spring.boot.common.bo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

public abstract class BusinessObjectModel<//
        BO extends BusinessObject<BO, ID>, //
        ID extends Serializable> //
        extends //
        RepresentationModel<BusinessObjectModel<BO, ID>> //
        implements BusinessObjectRepresentationModel<BO>
{

    private BO bo;

    public BusinessObjectModel(BO bo)
    {
        super();
        this.bo = bo;
    }

    public BO getBo()
    {
        return bo;
    }

}
