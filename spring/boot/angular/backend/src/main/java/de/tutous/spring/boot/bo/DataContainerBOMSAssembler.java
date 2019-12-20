package de.tutous.spring.boot.bo;

import java.util.Collection;

import de.tutous.spring.boot.api.dc.DataContainerOut;
import de.tutous.spring.boot.common.bo.BusinessObjectCollectionModelAssembler;
import de.tutous.spring.boot.controller.DataContainerController;

public class DataContainerBOMSAssembler
        extends BusinessObjectCollectionModelAssembler<DataContainerBOMS, DataContainerBOM, DataContainerOut>
{

    public DataContainerBOMSAssembler()
    {
        super(DataContainerController.class, DataContainerBOMS.class);
    }

    @Override
    public DataContainerBOMS toModel(Collection<DataContainerBOM> boms)
    {
        return new DataContainerBOMS(boms);
    }

}
