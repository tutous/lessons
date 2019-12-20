package de.tutous.spring.boot.bo;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.hateoas.server.core.Relation;

import de.tutous.spring.boot.api.MemberOut;
import de.tutous.spring.boot.api.dc.DataContainerOut;
import de.tutous.spring.boot.common.bo.AuditedBO;
import de.tutous.spring.boot.common.bo.BusinessObjectModel;
import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.common.type.DataContainerState;
import de.tutous.spring.boot.common.type.DataContainerType;
import de.tutous.spring.boot.common.type.DiagnosticBus;
import de.tutous.spring.boot.common.type.DiagnosticKwp;
import de.tutous.spring.boot.common.type.TransportProtocol;

@Relation(collectionRelation = "data", itemRelation = "dataContainer")
public class DataContainerBOM extends BusinessObjectModel<DataContainerBO, Long> implements DataContainerOut
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public DataContainerBOM(DataContainerBO bo)
    {
        super(bo);
    }

    @Override
    public String getName()
    {
        return getBo().getName();
    }

    @Override
    public String getDescription()
    {
        return getBo().getDescription();
    }

    @Override
    public DataContainerType getType()
    {
        return getBo().getType();
    }

    @Override
    public Long getId()
    {
        return getBo().getId();
    }

    @Override
    public Collection<DataContainerState> getState()
    {
        return Arrays.asList(DataContainerState.IN_PROGRESS);
    }

    @Override
    public Collection<String> getVehicleClasses()
    {
        return getBo().getVehicleClasses().map(vc -> vc.getName()).collect();
    }

    @Override
    public AuditedBO getAuditedInfo()
    {
        return getBo().getAuditedInfo();
    }

    @Override
    public Iterable<MemberOut> getResourceMembers()
    {
        StreamSupplier<MemberBOM> boms = getBo().getResourceMembers().map(bo -> new MemberBOMAssembler().toModel(bo));
        return new MemberBOMSAssembler(getId()).toModel(boms);
    }

    @Override
    public String getGeneration()
    {
        return getBo().getGeneration();
    }

    @Override
    public String getPartNumber()
    {
        return getBo().getPartNumber();
    }

    @Override
    public DiagnosticBus getDiagnosticBus()
    {
        return getBo().getDiagnosticBus();
    }

    @Override
    public String getDiagnosticAddress()
    {
        return getBo().getDiagnosticAddress();
    }

    @Override
    public DiagnosticKwp getDiagnosticKwp()
    {
        return getBo().getDiagnosticKwp();
    }

    @Override
    public TransportProtocol getTransportProtocol()
    {
        return getBo().getTransportProtocol();
    }
    
    @Override
    public String getFileName()
    {
        return getBo().getFileName();
    }

}
