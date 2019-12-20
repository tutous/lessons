package de.tutous.spring.boot.bo;

import java.util.UUID;

import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.common.type.DataContainerType;
import de.tutous.spring.boot.common.type.DiagnosticBus;
import de.tutous.spring.boot.common.type.DiagnosticKwp;
import de.tutous.spring.boot.common.type.TransportProtocol;

public interface DataContainerBO extends ResourceBO<DataContainerBO>
{

    String getName();

    String getDescription();

    DataContainerType getType();

    String getFileName();
    
    UUID getFileUuid();
    
    String getGeneration();
    
    String getPartNumber();
    
    DiagnosticBus getDiagnosticBus();
    
    String getDiagnosticAddress();
    
    DiagnosticKwp getDiagnosticKwp();
    
    TransportProtocol getTransportProtocol();

    StreamSupplier<VehicleClassBO> getVehicleClasses();

    @Override
    default DataContainerBO toSafeBO()
    {
        return this;
    }
    
}