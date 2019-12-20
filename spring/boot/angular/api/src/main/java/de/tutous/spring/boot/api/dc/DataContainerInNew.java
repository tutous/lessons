package de.tutous.spring.boot.api.dc;

import com.fasterxml.jackson.annotation.JsonGetter;

import de.tutous.spring.boot.api.MemberIn;
import de.tutous.spring.boot.common.type.DataContainerType;
import de.tutous.spring.boot.common.type.DiagnosticKwp;

public interface DataContainerInNew extends DataContainerInUpd
{

    /**
     * MemberIn has to be {@code ? extends MemberIn}.
     * It is an implementation of a BusinessObject.  
     * 
     * @return
     */
    @JsonGetter(DataContainerAttrs.members)
    public Iterable<? extends MemberIn> getResourceMembers();
    
    @JsonGetter(DataContainerAttrs.type)
    public DataContainerType getType();
    
    @JsonGetter(DataContainerAttrs.diagnosticKwp)
    public DiagnosticKwp getDiagnosticKwp();
    
}
