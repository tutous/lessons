package de.tutous.spring.boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.tutous.spring.boot.api.DcErrorCode;
import de.tutous.spring.boot.bo.DataContainerUpdBO;
import de.tutous.spring.boot.common.entity.Entities;
import de.tutous.spring.boot.domain.DataContainerEntity;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface DataContainerRepo extends CrudRepository<DataContainerEntity, Long>
{

    default DataContainerEntity update(DataContainerUpdBO dataContainerUpd)
    {
        DataContainerEntity entity = getById(dataContainerUpd.getId());
        entity.setName(dataContainerUpd.getName());
        entity.setDescription(dataContainerUpd.getDescription());
        entity.setGeneration(dataContainerUpd.getGeneration());
        entity.setPartNumber(dataContainerUpd.getPartNumber());
        entity.setDiagnosticBus(dataContainerUpd.getDiagnosticBus());
        entity.setDiagnosticAddress(dataContainerUpd.getDiagnosticAddress());
        entity.setTransportProtocol(dataContainerUpd.getTransportProtocol());
        return entity;
    }

    default DataContainerEntity getById(Long id)
    {
        return Entities.get(findById(id), DcErrorCode.DATA_CONTAINER_NOT_FOUND, String.valueOf(id));
    }

    default DataContainerEntity getByName(String name)
    {
        return Entities.get(findByName(name), DcErrorCode.DATA_CONTAINER_NOT_FOUND, String.valueOf(name));
    }

    @Query("SELECT dc FROM DataContainerEntity AS dc WHERE dc.name = :name")
    public Optional<DataContainerEntity> findByName(@Param("name") String name);

    @Query(value = "select dc from DataContainerEntity dc where UPPER(dc.name) like CONCAT('%',UPPER(:name),'%')")
    Iterable<DataContainerEntity> findAllByFilter(@Param("name") String name);

}
