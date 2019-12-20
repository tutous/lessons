package de.tutous.spring.boot.domain;

import static de.tutous.spring.boot.common.entity.Entities.toEntities;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;

import de.tutous.spring.boot.bo.DataContainerBO;
import de.tutous.spring.boot.bo.DataContainerModuleBO;
import de.tutous.spring.boot.bo.DiagSpecFileBO;
import de.tutous.spring.boot.bo.MemberBO;
import de.tutous.spring.boot.bo.VehicleClassBO;
import de.tutous.spring.boot.common.entity.Entities;
import de.tutous.spring.boot.common.log.Logger;
import de.tutous.spring.boot.common.log.ToString;
import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.common.stream.Streams;
import de.tutous.spring.boot.common.type.DataContainerType;
import de.tutous.spring.boot.common.type.DiagnosticBus;
import de.tutous.spring.boot.common.type.DiagnosticKwp;
import de.tutous.spring.boot.common.type.TransportProtocol;

@Entity
@Validated
@DynamicUpdate
@DiscriminatorValue("data-container")
@Table(name = "DATA_CONTAINER")
@EntityListeners(AuditingEntityListener.class)
public class DataContainerEntity extends ResourceEntity<DataContainerBO> implements DataContainerBO, ToString
{

    private static final Logger LOGGER = Logger.getLogger(DataContainerEntity.class);

    @NotNull
    @Column(name = "NAME")
    @ToStringProperty
    private String name;

    @Column(name = "DESCRIPTION")
    @ToStringProperty
    private String description;

    @NotNull
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    @ToStringProperty
    private DataContainerType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BTLAH_ID")
    @ToStringProperty
    private DiagSpecFileEntity diagSpecFile;

    @NotNull
    @Column(name = "GENERATION")
    @ToStringProperty
    private String generation;

    @NotNull
    @Column(name = "PART_NUMBER")
    @ToStringProperty
    private String partNumber;

    @NotNull
    @Column(name = "DIAGNOSTIC_BUS")
    @Enumerated(EnumType.STRING)
    @ToStringProperty
    private DiagnosticBus diagnosticBus;

    @NotNull
    @Column(name = "DIAGNOSTIC_ADDRESS")
    @ToStringProperty
    private String diagnosticAddress;

    @NotNull
    @Column(name = "DIAGNOSTIC_KWP")
    @Enumerated(EnumType.STRING)
    @ToStringProperty
    private DiagnosticKwp diagnosticKwp;

    @Column(name = "TRANSPORT_PROTOCOL")
    @Enumerated(EnumType.STRING)
    @ToStringProperty
    private TransportProtocol transportProtocol;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "data_container_vehicle_class", //
            joinColumns =
            { @JoinColumn(name = "data_container_id", referencedColumnName = "ID") }, //
            inverseJoinColumns =
            { @JoinColumn(name = "vehicle_class_id", referencedColumnName = "ID") } //
    )
    @ToStringProperty(reader = VehicleClassToStringPropertyReader.class)
    private Set<VehicleClassEntity> vehicleClasses;

    public static class VehicleClassToStringPropertyReader extends ToStringPropertyReader<Set<VehicleClassEntity>>
    {
        @Override
        public String read(Set<VehicleClassEntity> vehicleClasses)
        {
            return Entities.toString(vehicleClasses, new String[]
            { "id", "name" }, VehicleClassEntity::getId, VehicleClassEntity::getName);
        }
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dataContainer")
    private Set<ModuleEntity> modules;

    @OneToMany(mappedBy = "dataContainer", cascade = CascadeType.REMOVE)
    @ToStringProperty(reader = MemberEntityToStringPropertyReader.class)
    private Set<MemberEntity> members;

    /**
     * 
     */
    public DataContainerEntity()
    {
        super();
    }

    /**
     * 
     * @param bo
     */
    public DataContainerEntity(@NotNull DataContainerBO bo)
    {
        this(bo.getName(), bo.getDescription(), bo.getType(), bo.getVehicleClasses().stream(), bo.getGeneration(),
                bo.getPartNumber(), bo.getDiagnosticBus(), bo.getDiagnosticAddress(), bo.getDiagnosticKwp(),
                bo.getTransportProtocol());
    }

    /**
     * all properties, but without id
     * 
     * @param name
     * @param description
     * @param type
     * @param vehicleClasses
     * @param members
     */
    public DataContainerEntity(@NotNull String name, @NotNull String description, @NotNull DataContainerType type,
            @NotNull Stream<VehicleClassBO> vehicleClasses, @NotNull String generation, @NotNull String partNumber,
            @NotNull DiagnosticBus diagnosticBus, @NotNull String diagnosticAddress,
            @NotNull DiagnosticKwp diagnosticKwp, TransportProtocol transportProtocol)
    {
        this(null, name, description, type, vehicleClasses, generation, partNumber, diagnosticBus, diagnosticAddress,
                diagnosticKwp, transportProtocol);
    }

    /**
     * only test
     * 
     * @param id
     * @param name
     * @param description
     * @param type
     */
    protected DataContainerEntity(Long id, @NotNull String name, @NotNull String description,
            @NotNull DataContainerType type, @NotNull Stream<VehicleClassBO> vehicleClasses, @NotNull String generation,
            @NotNull String partNumber, @NotNull DiagnosticBus diagnosticBus, @NotNull String diagnosticAddress,
            @NotNull DiagnosticKwp diagnosticKwp, TransportProtocol transportProtocol)
    {
        super(id, Stream.of(new MemberEntity[] {}));
        this.name = name;
        this.description = description;
        this.type = type;
        this.generation = generation;
        this.partNumber = partNumber;
        this.diagnosticBus = diagnosticBus;
        this.diagnosticAddress = diagnosticAddress;
        this.diagnosticKwp = diagnosticKwp;
        this.transportProtocol = transportProtocol;
        toEntities(vehicleClasses, VehicleClassEntity.class, vc -> addVehilceClass(vc));
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public DataContainerType getType()
    {
        return type;
    }

    public void setType(DataContainerType type)
    {
        this.type = type;
    }

    public DiagSpecFileBO getDiagSpecFile()
    {
        return diagSpecFile.toSafeBO();
    }

    public void setDiagSpecFile(DiagSpecFileBO diagSpecFile)
    {
        Entities.toEntity(diagSpecFile, DiagSpecFileEntity.class, e -> {
            this.diagSpecFile = e;
            this.diagSpecFile.addDataContainer(this);
        });
    }

    public String getGeneration()
    {
        return generation;
    }

    public void setGeneration(String generation)
    {
        this.generation = generation;
    }

    public String getPartNumber()
    {
        return partNumber;
    }

    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }

    public DiagnosticBus getDiagnosticBus()
    {
        return diagnosticBus;
    }

    public void setDiagnosticBus(DiagnosticBus diagnosticBus)
    {
        this.diagnosticBus = diagnosticBus;
    }

    public String getDiagnosticAddress()
    {
        return diagnosticAddress;
    }

    public void setDiagnosticAddress(String diagnosticAddress)
    {
        this.diagnosticAddress = diagnosticAddress;
    }

    public DiagnosticKwp getDiagnosticKwp()
    {
        return diagnosticKwp;
    }

    public void setDiagnosticKwp(DiagnosticKwp diagnosticKwp)
    {
        this.diagnosticKwp = diagnosticKwp;
    }

    public TransportProtocol getTransportProtocol()
    {
        return transportProtocol;
    }

    public void setTransportProtocol(TransportProtocol transportProtocol)
    {
        this.transportProtocol = transportProtocol;
    }

    /*
     * VehilceClassEntity
     */
    public StreamSupplier<VehicleClassBO> getVehicleClasses()
    {
        this.vehicleClasses = check(vehicleClasses);
        return Streams.asStream(this.vehicleClasses, entity -> VehicleClassBO.class.cast(entity));
    }

    public void addVehilceClass(VehicleClassEntity vehilceClass)
    {
        LOGGER.debug("add entity {}", vehilceClass);
        this.vehicleClasses = check(vehicleClasses);
        this.vehicleClasses.add(vehilceClass);
    }

    public void addVehilceClasses(Stream<VehicleClassEntity> vehilceClasses)
    {
        vehilceClasses.forEach(vc -> addVehilceClass(vc));
    }

    public void removeVehilceClass(VehicleClassEntity vehilceClass)
    {
        LOGGER.debug("remove entity {}", vehilceClass);
        this.vehicleClasses = check(vehicleClasses);
        this.vehicleClasses.remove(vehilceClass);
    }

    public void removeVehilceClasses(Stream<VehicleClassEntity> vehilceClasses)
    {
        vehilceClasses.forEach(vc -> removeVehilceClass(vc));
    }

    /*
     * ModuleEntity
     */

    public Set<ModuleEntity> getModules()
    {
        return modules;
    }

    @Override
    public DataContainerBO toSafeBO()
    {
        return toSafeBO(DataContainerBO.class);
    }

    /*
     * DC Member 
     */

    public StreamSupplier<MemberBO> getMembers()
    {
        this.members = check(members);
        return Streams.asStream(members, entity -> MemberBO.class.cast(entity));
    }

    public void addMember(MemberEntity member)
    {
        this.members = check(members);
        this.members.add(member);
    }

    public void removeMember(MemberBO member)
    {
        this.members = check(members);
        this.members.remove(member);
    }

    public void addMembers(Stream<MemberEntity> members)
    {
        members.forEach(m -> addMember(m));
    }

    public void removeMembers(Stream<MemberEntity> members)
    {
        members.forEach(m -> removeMember(m));
    }

    /*
     * Module
     */

    public void addModule(DataContainerModuleBO dataContainerModuleBO)
    {
        this.modules = check(this.modules);
        Entities.toEntity(dataContainerModuleBO, ModuleEntity.class, e -> this.modules.add(e));
    }

    @Override
    public String toString()
    {
        return propertiesToString();
    }

    @Override
    public String getFileName()
    {
        if (Objects.nonNull(diagSpecFile))
        {
            return diagSpecFile.getFileName();
        }
        return null;
    }

    public UUID getFileUuid()
    {
        if (Objects.nonNull(diagSpecFile))
        {
            return diagSpecFile.getUuid();
        }
        return null;
    }

}
