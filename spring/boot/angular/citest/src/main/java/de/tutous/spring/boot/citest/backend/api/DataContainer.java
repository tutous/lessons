package de.tutous.spring.boot.citest.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataContainer
{
    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("partNumber")
    private String partNumber;

    @JsonProperty("diagnosticBus")
    private String diagnosticBus;

    @JsonProperty("diagnosticAddress")
    private String diagnosticAddress;

    @JsonProperty("diagnosticKwp")
    private String diagnosticKwp;

    @JsonProperty("transportProtocol")
    private String transportProtocol;

    @JsonProperty("vehicleClasses")
    private Iterable<String> vehicleClasses;

    @JsonProperty("description")
    private String description;

    @JsonProperty("members")
    private Iterable<Member> members;

    public DataContainer(String name, String type, String generation, String partNumber, String diagnosticBus,
            String diagnosticAddress, String diagnosticKwp, String transportProtocol, Iterable<String> vehicleClasses,
            String description, Iterable<Member> members)
    {
        super();
        this.name = name;
        this.type = type;
        this.generation = generation;
        this.partNumber = partNumber;
        this.diagnosticBus = diagnosticBus;
        this.diagnosticAddress = diagnosticAddress;
        this.diagnosticKwp = diagnosticKwp;
        this.transportProtocol = transportProtocol;
        this.vehicleClasses = vehicleClasses;
        this.description = description;
        this.members = members;
    }
}
