package de.tutous.spring.boot.common.api;

import com.fasterxml.jackson.annotation.JsonGetter;

public interface Displayed
{

    @JsonGetter("enabled")
    public Boolean getEnabled();
    
}
