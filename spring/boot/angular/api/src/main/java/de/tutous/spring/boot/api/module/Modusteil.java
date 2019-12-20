package de.tutous.spring.boot.api.module;

import com.fasterxml.jackson.annotation.JsonGetter;

import de.tutous.spring.boot.common.api.Identified;

public interface Modusteil extends Identified<String>
{

    @JsonGetter("rdid")
    public String getId();

    @JsonGetter("name")
    public String getName();

}
