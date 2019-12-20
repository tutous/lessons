package de.tutous.spring.boot.service;

import static de.tutous.spring.boot.common.entity.Entities.findRemoved;
import static de.tutous.spring.boot.common.entity.Entities.toEntities;
import static de.tutous.spring.boot.common.entity.Entities.toSafeBO;
import static de.tutous.spring.boot.common.entity.Entities.toSafeBOs;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import de.tutous.spring.boot.bo.DataContainerBO;
import de.tutous.spring.boot.bo.DataContainerFilterBO;
import de.tutous.spring.boot.bo.DataContainerNewBO;
import de.tutous.spring.boot.bo.DataContainerUpdBO;
import de.tutous.spring.boot.bo.DiagSpecFileBO;
import de.tutous.spring.boot.bo.MemberBO;
import de.tutous.spring.boot.bo.VehicleClassBO;
import de.tutous.spring.boot.common.log.Logger;
import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.MemberEntity;
import de.tutous.spring.boot.domain.VehicleClassEntity;
import de.tutous.spring.boot.repository.DataContainerRepo;
import de.tutous.spring.boot.validation.DataContainerNewBOValidated;

@Validated
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DataContainerService
{

    private static final Logger LOGGER = Logger.getLogger(DataContainerService.class);

    private DataContainerRepo dataContainerRepo;
    private StorageService storageService;
    private VehicleClassService vehicleClassService;
    private MemberService memberService;

    @Autowired
    public DataContainerService(DataContainerRepo dataContainerRepo, StorageService storageService,
            VehicleClassService vehicleClassService, MemberService memberService)
    {
        this.dataContainerRepo = dataContainerRepo;
        this.storageService = storageService;
        this.vehicleClassService = vehicleClassService;
        this.memberService = memberService;
    }

    /**
     * ...
     * 
     * @param newDC
     * @return
     */
    public DataContainerBO add(@DataContainerNewBOValidated() DataContainerNewBO newDC)
    {

        LOGGER.info("add new data container {}", newDC);

        // vehicle classes and members as stream of safe BOs
        Stream<VehicleClassBO> vehicleClasses = vehicleClassService
                .findByNames(() -> newDC.getVehicleClasses().stream()).stream();

        // create data container
        DataContainerEntity dcEntity = dataContainerRepo.save(new DataContainerEntity(
                /** name */
                newDC.getName(),
                /** description */
                newDC.getDescription(),
                /** type */
                newDC.getType(),
                /** vehicle classes */
                vehicleClasses,
                /** generation */
                newDC.getGeneration(),
                /** partNumber */
                newDC.getPartNumber(),
                /** diagnosticBus */
                newDC.getDiagnosticBus(),
                /** diagnosticAddress */
                newDC.getDiagnosticAddress(),
                /** diagnosticKwp */
                newDC.getDiagnosticKwp(),
                /** transprotProtocol */
                newDC.getTransportProtocol()));

        // create members to DC
        StreamSupplier<MemberBO> memberBOs = memberService.create(dcEntity.toSafeBO(),
                () -> newDC.getResourceMembers().stream());
        // add each member as a resource member to the resource
        dcEntity.addResourceMembers(toEntities(memberBOs.stream(), MemberEntity.class));

        // save upload
        if (newDC.isFile())
        {
            dcEntity.setDiagSpecFile(storageService.saveDoorsFile(newDC.getFileName(), newDC.getFileContent()));
        }

        return dcEntity.toSafeBO();
    }

    /**
     * with error
     * 
     * @param id
     * @return
     */
    public DataContainerBO getById(Long id)
    {
        return dataContainerRepo.getById(id).toSafeBO();
    }

    /**
     * without error
     * 
     * @param id
     * @return
     */
    public Optional<DataContainerBO> findById(Long id)
    {
        return toSafeBO(dataContainerRepo.findById(id), DataContainerBO.class);
    }

    /**
     * with error
     * 
     * @param name
     * @return
     */
    public DataContainerBO getByName(String name)
    {
        return dataContainerRepo.getByName(name).toSafeBO();
    }

    /**
     * without error
     * 
     * @param name
     * @return
     */
    public Optional<DataContainerBO> findByName(String name)
    {
        return toSafeBO(dataContainerRepo.findByName(name), DataContainerBO.class);
    }

    /**
     * ...
     * 
     * @return
     */
    public StreamSupplier<DataContainerBO> findAll()
    {
        return toSafeBOs(dataContainerRepo.findAll(), DataContainerBO.class);
    }

    /**
     * ...
     * 
     * @param filter
     * @return
     */
    public StreamSupplier<DataContainerBO> filter(DataContainerFilterBO filter)
    {
        return toSafeBOs(dataContainerRepo.findAllByFilter(filter.getName()), DataContainerBO.class);
    }

    /**
     * ...
     * 
     * @param id
     */
    public void delete(Long id)
    {
        DataContainerBO dataContainerBO = getById(id);
        dataContainerRepo.deleteById(id);
        if (Objects.nonNull(dataContainerBO.getFileName()))
        {
            storageService.tryDeleteDoorsFile(dataContainerBO.getUuid(), dataContainerBO.getFileName());
        }
    }

    /**
     * ...
     * 
     * @param dataContainerUpd
     * @return
     */
    public DataContainerBO update(DataContainerUpdBO dcUpd)
    {
        // update entity
        DataContainerEntity entity = dataContainerRepo.update(dcUpd);
        // update vehicle classes
        StreamSupplier<VehicleClassBO> allDcVehicleClasses = vehicleClassService
                .findByNames(() -> dcUpd.getVehicleClasses().stream());
        Stream<VehicleClassBO> removeVehicleClasses = findRemoved(entity.getVehicleClasses(), allDcVehicleClasses);
        entity.removeVehilceClasses(toEntities(removeVehicleClasses, VehicleClassEntity.class));
        Stream<VehicleClassBO> addDcVehicleClasses = allDcVehicleClasses.stream();
        entity.addVehilceClasses(toEntities(addDcVehicleClasses, VehicleClassEntity.class));
        // save new file upload
        String newFile = dcUpd.getFileName();
        String currentFile = entity.getFileName();
        if (dcUpd.isFile() && !newFile.equals(currentFile))
        {
            byte[] content = dcUpd.getFileContent();
            entity.setDiagSpecFile(storageService.saveDoorsFile(newFile, content));
            storageService.tryDeleteDoorsFile(entity.getUuid(), currentFile);
        }
        return entity.toSafeBO();
    }

}
