package de.tutous.spring.boot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import de.tutous.spring.boot.common.type.ModuleFlag;
import de.tutous.spring.boot.common.type.ModuleState;
import de.tutous.spring.boot.conf.JpaTestConfiguration;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.ModuleEntity;
import de.tutous.spring.boot.domain.VehicleClassEntity;

@DataJpaTest
@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class ModuleEntityTest
{

    @Autowired
    private TestDataManager testDataManager;

    private DataContainerEntity newDataContainerEntity;

    @BeforeEach
    public void setUp()
    {
        VehicleClassEntity newVehicleClassEntity = testDataManager.createNewVehicleClass("vc1");
        this.newDataContainerEntity = testDataManager.createNewDataContainer(newVehicleClassEntity);
    }

    @Test
    @Rollback
    public void testModuleEntityCreateAndFind()
    {

        testDataManager.execute(() -> {

            ModuleEntity createdEntity = testDataManager.createNewModuleEntity(newDataContainerEntity, "name",
                    "description", ModuleState.IN_PREPARING, ModuleFlag.UNKNOWN);
            
            String jpql = "select e from ModuleEntity e where e.id = " + createdEntity.getId();
            TypedQuery<ModuleEntity> typedQuery = testDataManager.createQuery(jpql, ModuleEntity.class);

            ModuleEntity foundEntity = typedQuery.getSingleResult();

            assertThat(foundEntity).isEqualTo(createdEntity);
            
        });
    }

}
