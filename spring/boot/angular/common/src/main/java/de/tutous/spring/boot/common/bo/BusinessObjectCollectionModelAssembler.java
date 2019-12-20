package de.tutous.spring.boot.common.bo;

import java.util.Collection;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import de.tutous.spring.boot.common.stream.StreamSupplier;

public abstract class BusinessObjectCollectionModelAssembler<//
        BOMS extends BusinessObjectCollectionModel<BOM, API>, // model wrapper
        BOM extends BusinessObjectModel<?, ?>,API>
        extends RepresentationModelAssemblerSupport<Collection<BOM>, BusinessObjectCollectionModel<BOM, API>>
{

    @SuppressWarnings("unchecked")
    public BusinessObjectCollectionModelAssembler(Class<?> controllerClass, Class<BOMS> resourceType)
    {
        super(controllerClass, (Class<BusinessObjectCollectionModel<BOM, API>>) resourceType);
    }

    public BOMS toModel(StreamSupplier<BOM> bos)
    {
        return toModel(bos.collect());
    }

    @Override
    public abstract BOMS toModel(Collection<BOM> bos);

}
