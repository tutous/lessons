package de.tutous.spring.boot.common.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface BusinessObjectRepresentationModel<BO extends BusinessObject<BO, ?>>
{

    @JsonIgnore
    public BO getBo();

}
