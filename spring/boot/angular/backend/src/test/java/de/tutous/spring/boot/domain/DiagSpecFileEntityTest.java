package de.tutous.spring.boot.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import de.tutous.spring.boot.conf.JpaTestConfiguration;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.DiagSpecFileEntity;
import de.tutous.spring.boot.domain.VehicleClassEntity;

@DataJpaTest
@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class DiagSpecFileEntityTest
{

    @Autowired
    private TestDataManager testDataManager;

    @Test
    public void test()
    {

        testDataManager.execute(() -> {

            DiagSpecFileEntity diagSpecFileEntity = testDataManager.createNewDiagSpecFileEntity("test.txt");
            
            VehicleClassEntity vehicleClassEntity = testDataManager.createNewVehicleClass("VC1");
            
            DataContainerEntity dataContainerEntity1 = testDataManager.createNewDataContainer("DC1",vehicleClassEntity);
            DataContainerEntity dataContainerEntity2 = testDataManager.createNewDataContainer("DC2",vehicleClassEntity);
            
            dataContainerEntity1.setDiagSpecFile(diagSpecFileEntity.toSafeBO());
            dataContainerEntity2.setDiagSpecFile(diagSpecFileEntity.toSafeBO());
            
            testDataManager.persist(dataContainerEntity1);
            testDataManager.persist(dataContainerEntity2);
            
        });

    }

}
