package de.tutous.spring.boot.common.api;

import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonGetter;

public abstract interface CollectionModel<API> extends Iterable<API>
{

    @JsonGetter("data")
    public Iterable<API> getData();

    @Override
    default Iterator<API> iterator()
    {
        return getData().iterator();
    }
    
}
