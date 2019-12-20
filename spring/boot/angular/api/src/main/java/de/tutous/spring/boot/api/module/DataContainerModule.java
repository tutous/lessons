package de.tutous.spring.boot.api.module;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonGetter;

import de.tutous.spring.boot.api.MemberOut;

public interface DataContainerModule
{

    @JsonGetter(DataContainerModuleAttrs.name)
    public String getName();

    @JsonGetter(DataContainerModuleAttrs.description)
    public String getDescription();
   
    @JsonGetter(DataContainerModuleAttrs.members)
    public Iterable<MemberOut> getResourceMembers();
    
}
