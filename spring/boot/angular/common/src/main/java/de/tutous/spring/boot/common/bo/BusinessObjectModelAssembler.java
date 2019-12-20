package de.tutous.spring.boot.common.bo;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import de.tutous.spring.boot.common.stream.StreamSupplier;

public abstract class BusinessObjectModelAssembler<//
        BOM extends BusinessObjectModel<BO, ID>, // model wrapper
        BO extends BusinessObject<BO, ID>, // bo type
        ID extends Serializable> // bo id type
        extends RepresentationModelAssemblerSupport<BO, BusinessObjectModel<BO, ID>>
{

    @SuppressWarnings("unchecked")
    public BusinessObjectModelAssembler(Class<?> controllerClass, Class<BOM> resourceType)
    {
        super(controllerClass, (Class<BusinessObjectModel<BO, ID>>) resourceType);
    }

    @SuppressWarnings("unchecked")
    public CollectionModel<BOM> toCollectionModel(Collection<BO> bos, Link... links)
    {
        CollectionModel<BOM> model = (CollectionModel<BOM>) super.toCollectionModel(bos);
        model.add(links);
        return model;
    }

    public CollectionModel<BOM> toCollectionModel(StreamSupplier<BO> bos, Link... links)
    {
        return this.toCollectionModel(bos.collect(), links);
    }

    @Override
    public abstract BOM toModel(BO entity);

}
