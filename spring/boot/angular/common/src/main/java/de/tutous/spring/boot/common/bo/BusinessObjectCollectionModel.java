package de.tutous.spring.boot.common.bo;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.tutous.spring.boot.common.api.CollectionModel;

public abstract class BusinessObjectCollectionModel<//
        BOM extends BusinessObjectModel<?, ?>, //
        API> //
        extends //
        RepresentationModel<BusinessObjectCollectionModel<BOM, API>> //
        implements //
        CollectionModel<API>
{

    private Collection<BOM> boms;

    public BusinessObjectCollectionModel(Collection<BOM> boms)
    {
        this.boms = boms;
    }

    @Override
    public Iterable<API> getData()
    {
        return boms.stream().map(bom -> getApiType().cast(bom)).collect(Collectors.toList());
    }

    @JsonIgnore
    public abstract Class<API> getApiType();
    
}
