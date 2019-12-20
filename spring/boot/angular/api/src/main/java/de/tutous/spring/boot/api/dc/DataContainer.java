package de.tutous.spring.boot.api.dc;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;

import de.tutous.spring.boot.common.type.DiagnosticBus;
import de.tutous.spring.boot.common.type.TransportProtocol;

public interface DataContainer extends Serializable
{

    public final static String PROPERTY_DATA_KEY = "data";
    public final static String PROPERTY_FILE_KEY = "file";

    @JsonGetter(DataContainerAttrs.name)
    public String getName();

    @JsonGetter(DataContainerAttrs.generation)
    public String getGeneration();

    @JsonGetter(DataContainerAttrs.partNumber)
    public String getPartNumber();

    @JsonGetter(DataContainerAttrs.diagnosticBus)
    public DiagnosticBus getDiagnosticBus();

    @JsonGetter(DataContainerAttrs.diagnosticAddress)
    public String getDiagnosticAddress();

    @JsonGetter(DataContainerAttrs.transportProtocol)
    public TransportProtocol getTransportProtocol();

    @JsonGetter(DataContainerAttrs.description)
    public String getDescription();

    @JsonGetter(DataContainerAttrs.vehicleClasses)
    public Iterable<String> getVehicleClasses();

}
