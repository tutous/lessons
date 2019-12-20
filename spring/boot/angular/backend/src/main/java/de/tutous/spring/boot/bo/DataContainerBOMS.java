package de.tutous.spring.boot.bo;

import java.util.Collection;

import de.tutous.spring.boot.api.dc.DataContainerOut;
import de.tutous.spring.boot.common.bo.BusinessObjectCollectionModel;

public class DataContainerBOMS extends BusinessObjectCollectionModel<DataContainerBOM, DataContainerOut>
{

    public DataContainerBOMS(Collection<DataContainerBOM> boms)
    {
        super(boms);
    }

    @Override
    public Class<DataContainerOut> getApiType()
    {
        return DataContainerOut.class;
    }

}
