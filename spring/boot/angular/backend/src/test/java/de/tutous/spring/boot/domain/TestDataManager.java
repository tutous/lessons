package de.tutous.spring.boot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.tutous.spring.boot.bo.DataContainerBO;
import de.tutous.spring.boot.bo.DataContainerModuleBO;
import de.tutous.spring.boot.bo.DiagSpecFileBO;
import de.tutous.spring.boot.bo.ModusteilBO;
import de.tutous.spring.boot.bo.ParameterBO;
import de.tutous.spring.boot.bo.VehicleClassBO;
import de.tutous.spring.boot.common.entity.AbstractEntity;
import de.tutous.spring.boot.common.log.Logger;
import de.tutous.spring.boot.common.type.ModuleFlag;
import de.tutous.spring.boot.common.type.ModuleState;
import de.tutous.spring.boot.common.type.UserRole;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.DiagSpecFileEntity;
import de.tutous.spring.boot.domain.MemberEntity;
import de.tutous.spring.boot.domain.ModuleEntity;
import de.tutous.spring.boot.domain.ModuleParameterEntity;
import de.tutous.spring.boot.domain.ModusteilEntity;
import de.tutous.spring.boot.domain.ParameterEntity;
import de.tutous.spring.boot.domain.ParameterValueEntity;
import de.tutous.spring.boot.domain.RoleEntity;
import de.tutous.spring.boot.domain.UserEntity;
import de.tutous.spring.boot.domain.VehicleClassEntity;

public class TestDataManager
{

    private static final Logger LOGGER = Logger.getLogger(TestDataManager.class);

    private EntityManager entityManager;

    public TestDataManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    private List<AbstractEntity<?>> newEntities = new ArrayList<AbstractEntity<?>>();

    private List<AbstractEntity<?>> mergedEntities = new ArrayList<AbstractEntity<?>>();

    public static interface Execute
    {
        void doTest();
    }

    /*
     * 
     */

    public DataContainerEntity createDataContainer(Long id, VehicleClassBO... vehicleClasses)
    {
        return merge(DataContainerEntityUtil.createDataContainer(id, vehicleClasses));
    }

    public DataContainerEntity createNewDataContainer(VehicleClassBO... vehicleClasses)
    {
        return persist(DataContainerEntityUtil.createNewDataContainer(vehicleClasses));
    }

    public DataContainerEntity createNewDataContainer(String name, VehicleClassBO... vehicleClasses)
    {
        return persist(DataContainerEntityUtil.createNewDataContainer(name, vehicleClasses));
    }

    /*
     * 
     */

    public VehicleClassEntity createVehicleClass(Integer id, String name)
    {
        return merge(VehicleClassEntityUtil.createVehicleClass(id, name));
    }

    public VehicleClassEntity createNewVehicleClass(String name)
    {
        return persist(VehicleClassEntityUtil.createNewVehicleClass(name));
    }

    /*
     * 
     */

    public UserEntity createUser(Integer id)
    {
        return merge(UserEntityUtil.createUser(id));
    }

    public UserEntity createNewUser()
    {
        return persist(UserEntityUtil.createNewUser());
    }

    /*
     * 
     */

    public RoleEntity createRole(Integer id, UserRole userRole)
    {
        return merge(RoleEntityUtil.createRole(id, userRole));
    }

    public RoleEntity createNewRole(UserRole userRole)
    {
        return persist(RoleEntityUtil.createNewRole(userRole));
    }

    /*
     * 
     */

    public MemberEntity createMember(Long id, UserEntity userEntity, RoleEntity roleEntity,
            DataContainerEntity dataContainerEntity)
    {
        return merge(MemberEntityUtil.createMember(id, userEntity, roleEntity, dataContainerEntity));
    }

    public MemberEntity createNewMember(UserEntity userEntity, RoleEntity roleEntity,
            DataContainerEntity dataContainerEntity)
    {
        return persist(MemberEntityUtil.createNewMember(userEntity, roleEntity, dataContainerEntity));
    }

    /*
     * 
     */

    public ModusteilEntity cerateNewModusteil(DiagSpecFileBO diagSpecFileBO, String objectHeading, String recordDataId,
            String longName, String byteReihenfolge)
    {
        return persist(ModusteilEntityUtil.createNewModusteilEntity(diagSpecFileBO, objectHeading, recordDataId,
                longName, byteReihenfolge));
    }

    /*
     * 
     */

    public DiagSpecFileEntity cerateNewDiagSpecFileEntity(String fileName)
    {
        return persist(DiagSpecFileEntityUtil.createNewDiagSpecFileEntity(fileName));
    }

    /*
     * 
     */

    public ParameterEntity cerateNewParameterEntity(ModusteilBO modusteilBO, String objectHeading, String longName,
            String signalName, String bitPositionStart, String bitPositionStop, String defaultwert)
    {
        return persist(ParameterEntityUtil.createNewParameterEntity(modusteilBO, objectHeading, longName, signalName,
                bitPositionStart, bitPositionStop, defaultwert));
    }

    /*
     * 
     */

    public ParameterValueEntity cerateNewParameterValueEntity(ParameterBO parameterBO, String objectText,
            String minWert, String maxWert, String longName, String aufloesung, String physicalUnit)
    {
        return persist(ParameterValueEntityUtil.createNewParameterValueEntity(parameterBO, objectText, minWert, maxWert,
                longName, aufloesung, physicalUnit));
    }

    /*
     * 
     */

    public DiagSpecFileEntity createNewDiagSpecFileEntity(String fileName)
    {
        return persist(DiagSpecFileEntityUtil.createNewDiagSpecFileEntity(fileName));
    }

    /*
     * 
     */

    public ModuleEntity createNewModuleEntity(DataContainerBO dataContainerBO, String name, String description,
            ModuleState state, ModuleFlag flag)
    {
        return persist(ModuleEntityUtil.createNewModuleEntity(dataContainerBO, name, description, state, flag));
    }

    /*
     * 
     */

    public ModuleParameterEntity createNewModuleParameterEntity(DataContainerModuleBO module, String name, String rdid,
            String modusteilName)
    {
        return persist(ModuleParameterEntityUtil.createNewModuleParameterEntity(module, name, rdid, modusteilName));
    }

    /*
     * 
     */
    public <T extends AbstractEntity<?>> T persist(T entity)
    {
        entityManager.persist(entity);
        entityManager.flush();
        if (!newEntities.contains(entity))
        {
            newEntities.add(entity);
        }
        return entity;
    }

    public <T extends AbstractEntity<?>> T merge(T entity)
    {
        entityManager.merge(entity);
        mergedEntities.add(entity);
        return entity;
    }

    public <T extends AbstractEntity<?>> TypedQuery<T> createQuery(String qlString, Class<T> resultClass)
    {
        return entityManager.createQuery(qlString, resultClass);
    }

    /**
     * clear only the entity collections without entity delete
     */
    public void clear()
    {
        newEntities.clear();
        mergedEntities.clear();
    }

    /**
     * create new instances of entity collections without entity delete
     */
    public void begin()
    {
        newEntities = new ArrayList<AbstractEntity<?>>();
        mergedEntities = new ArrayList<AbstractEntity<?>>();
    }

    /**
     * delete all new entities and clear all collections
     */
    public void finished()
    {
        removeNewEntities();
        mergedEntities.clear();
    }

    public void execute(Execute execute)
    {
        try
        {
            begin();
            execute.doTest();
        }
        finally
        {
            finished();
        }
    }

    private void removeNewEntities()
    {
        assertThat(entityManager).isNotNull();
        Collections.reverse(newEntities);
        newEntities.stream().forEach(entity -> {
            try
            {
                LOGGER.info("remove entity {}", entity);
                entityManager.remove(entity);
            }
            catch (Exception exc)
            {
                LOGGER.error("can't remove entity {}", entity);
            }
            finally
            {
                LOGGER.info("finished");
            }
        });
        newEntities.clear();
    }

}
