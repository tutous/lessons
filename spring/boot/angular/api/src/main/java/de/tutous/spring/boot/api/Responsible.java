package de.tutous.spring.boot.api;

import com.fasterxml.jackson.annotation.JsonGetter;

public interface Responsible
{
    
    @JsonGetter("responsible")
    public boolean isResponsible();

}
