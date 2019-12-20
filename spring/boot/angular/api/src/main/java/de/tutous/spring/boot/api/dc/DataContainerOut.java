package de.tutous.spring.boot.api.dc;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonGetter;

import de.tutous.spring.boot.api.MemberOut;
import de.tutous.spring.boot.common.api.Audited;
import de.tutous.spring.boot.common.api.Identified;
import de.tutous.spring.boot.common.type.DataContainerState;
import de.tutous.spring.boot.common.type.DataContainerType;
import de.tutous.spring.boot.common.type.DiagnosticKwp;

public interface DataContainerOut extends DataContainer, Audited, Identified<Long>
{

    @JsonGetter("fileName")
    public String getFileName();

    @JsonGetter("state")
    public Collection<DataContainerState> getState();

    @JsonGetter("members")
    public Iterable<MemberOut> getResourceMembers();
    
    @JsonGetter("vehicleClasses")
    public Iterable<String> getVehicleClasses();
    
    @JsonGetter("type")
    public DataContainerType getType();
    
    @JsonGetter("diagnosticKwp")
    public DiagnosticKwp getDiagnosticKwp();

}
