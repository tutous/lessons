package de.tutous.spring.boot.controller;

import static de.tutous.spring.boot.api.dc.Constants.REQUEST_MAPPING_DC_PATH;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.tutous.spring.boot.api.dc.DataContainer;
import de.tutous.spring.boot.api.dc.MemberFull;
import de.tutous.spring.boot.bo.DataContainerBO;
import de.tutous.spring.boot.bo.DataContainerBOM;
import de.tutous.spring.boot.bo.DataContainerBOMAssembler;
import de.tutous.spring.boot.bo.DataContainerBOMS;
import de.tutous.spring.boot.bo.DataContainerBOMSAssembler;
import de.tutous.spring.boot.bo.DataContainerFilterBO;
import de.tutous.spring.boot.bo.DataContainerNewBO;
import de.tutous.spring.boot.bo.DataContainerUpdBO;
import de.tutous.spring.boot.bo.MemberBO;
import de.tutous.spring.boot.bo.MemberFullBOM;
import de.tutous.spring.boot.bo.MemberFullBOMAssembler;
import de.tutous.spring.boot.bo.MemberFullBOMSAssembler;
import de.tutous.spring.boot.bo.VehicleClassBO;
import de.tutous.spring.boot.common.log.HasLogger;
import de.tutous.spring.boot.common.log.Logger;
import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.common.validation.ParamValidated;
import de.tutous.spring.boot.service.DataContainerService;
import de.tutous.spring.boot.service.MemberService;
import de.tutous.spring.boot.service.VehicleClassService;

@Validated
@RestController
@RequestMapping(REQUEST_MAPPING_DC_PATH)
public class DataContainerController implements HasLogger
{

    private static final Logger LOGGER = Logger.getLogger(DataContainerController.class);

    private DataContainerService dataContainerService;
    private VehicleClassService vehicleClassService;
    private MemberService memberService;

    @Autowired
    public DataContainerController(DataContainerService dataContainerService, VehicleClassService vehicleClassService,
            MemberService memberService)
    {
        this.dataContainerService = dataContainerService;
        this.vehicleClassService = vehicleClassService;
        this.memberService = memberService;
    }

    /**
     * get all DC
     * 
     * @return
     */
    @Secured(
    { "ROLE_USER" })
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<DataContainerBOM>> getAllDc()
    {
        LOGGER.info("get all containers");
        StreamSupplier<DataContainerBO> entities = dataContainerService.findAll();
        ResponseEntity<CollectionModel<DataContainerBOM>> result = ResponseEntity
                .ok(new DataContainerBOMAssembler().toCollectionModel(entities));
        return result;
    }

    /**
     * get DC by id
     * 
     * @param id
     * @return
     */
    @Secured(
    { "ROLE_USER" })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id)
    {
        LOGGER.info("get container {}", id);
        dataContainerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Secured(
    { "ROLE_USER" })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataContainerBOM> getById(@PathVariable("id") Long id)
    {
        LOGGER.info("get container {}", id);
        DataContainerBO bo = dataContainerService.getById(id);
        return ResponseEntity.ok(new DataContainerBOMAssembler().toModel(bo));
    }

    @Secured(
    { "ROLE_USER" })
    @GetMapping(path = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataContainerBOM> getByName(@PathVariable("name") String name)
    {
        LOGGER.info("get container '{}'", name);
        DataContainerBO bo = dataContainerService.getByName(name);
        return ResponseEntity.ok(new DataContainerBOMAssembler().toModel(bo));
    }

    @Secured(
    { "ROLE_USER" })
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataContainerBOMS> filter(@RequestParam(name = "name", required = false) String name)
    {
        DataContainerFilterBO filterBO = new DataContainerFilterBO(name);
        LOGGER.info("filter container {}", filterBO);
        StreamSupplier<DataContainerBO> bos = dataContainerService.filter(new DataContainerFilterBO(name));
        Collection<DataContainerBOM> boms = bos.map(bo -> new DataContainerBOMAssembler().toModel(bo)).collect();
        return ResponseEntity.ok(new DataContainerBOMSAssembler().toModel(boms));
    }

    /**
     * create DC
     * 
     * @param file
     * @param dcIn
     * @return
     */
    @Secured(
    { "ROLE_USER" })
    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataContainerBOM> create(
            @RequestParam(value = DataContainer.PROPERTY_FILE_KEY, required = false) MultipartFile file,
            @ParamValidated(message = "DC not valid") @RequestParam(DataContainer.PROPERTY_DATA_KEY) DataContainerNewBO dataContainerNew)
    {
        LOGGER.info("create new data container");
        // set file
        dataContainerNew.setFile(file);
        // execute service
        DataContainerBO bo = dataContainerService.add(dataContainerNew);
        // assembly to API model
        DataContainerBOM entityModel = new DataContainerBOMAssembler().toModel(bo);
        return ResponseEntity.ok(entityModel);
    }

    /**
     * update DC
     * 
     * @param file
     * @param updBO
     * @return
     */
    @Secured(
    { "ROLE_USER" })
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataContainerBOM> update(
            @PathVariable("id") Long id,
            @RequestParam(value = DataContainer.PROPERTY_FILE_KEY, required = false) MultipartFile file,
            @ParamValidated(message = "DC not valid") @RequestParam(DataContainer.PROPERTY_DATA_KEY) DataContainerUpdBO dataContainerUpd)
    {
        LOGGER.info("update new data container");
        // set file
        dataContainerUpd.setFile(file);
        dataContainerUpd.setId(id);
        // execute service
        DataContainerBO bo = dataContainerService.update(dataContainerUpd);
        // assembly to API model
        DataContainerBOM entityModel = new DataContainerBOMAssembler().toModel(bo);
        return ResponseEntity.ok(entityModel);
    }    

    /*
     * member class 
     * 
     */

    @Secured(
    { "ROLE_USER" })
    @GetMapping(path = "/{id}/member", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<de.tutous.spring.boot.common.api.CollectionModel<MemberFull>> getAllMembersByDc(
            @PathVariable("id") Long id)
    {
        LOGGER.info("get all DC members by id {}", id);
        StreamSupplier<MemberBO> members = memberService.findAllByResourceId(id);
        Collection<MemberFullBOM> memberBoms = members.map(bo -> new MemberFullBOMAssembler().toModel(bo)).collect();
        return ResponseEntity.ok(new MemberFullBOMSAssembler(id).toModel(memberBoms));
    }

    /*
     * vehicle class
     */

    @Secured(
    { "ROLE_USER" })
    @GetMapping(path = "/vc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<String>> getAllVc()
    {
        LOGGER.info("get all vehicle classes");
        StreamSupplier<VehicleClassBO> entities = vehicleClassService.findAll();
        return ResponseEntity.ok(entities.map(vc -> vc.getName()).collect());
    }

    @Secured(
    { "ROLE_USER" })
    @GetMapping(path = "/{id}/vc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<String>> getAllVcByDc(@PathVariable("id") Long id)
    {
        LOGGER.info("get vehicle classes by container {}", id);
        DataContainerBO entity = dataContainerService.getById(id);
        Collection<String> names = entity.getVehicleClasses().map(vc -> vc.getName()).collect();
        return ResponseEntity.ok(names);
    }

}
