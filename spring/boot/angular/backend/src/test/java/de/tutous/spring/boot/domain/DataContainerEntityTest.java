package de.tutous.spring.boot.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import de.tutous.spring.boot.common.log.Logger;
import de.tutous.spring.boot.conf.JpaTestConfiguration;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.VehicleClassEntity;

@DataJpaTest
@ContextConfiguration(classes = { JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class DataContainerEntityTest {

	private static final Logger LOGGER = Logger.getLogger(DataContainerEntityTest.class);

	@Autowired
	private TestDataManager testDataManager;

    @Test
	@Rollback
	public void testDataContainerEntityCreateAndFind() {
    	testDataManager.execute(() -> {

			VehicleClassEntity classEntity = testDataManager.createNewVehicleClass("vc1");
			DataContainerEntity newEntity = testDataManager.createNewDataContainer(classEntity);

			String jpql = "select e from DataContainerEntity e where e.id = " + newEntity.getId();
			TypedQuery<DataContainerEntity> typedQuery = testDataManager.createQuery(jpql, DataContainerEntity.class);

			DataContainerEntity foundEntity = typedQuery.getSingleResult();

			assertThat(foundEntity).isEqualTo(newEntity);

    	});
	}

	@Test
	@Rollback
	public void testDataContainerEntityConstraintUniqueName() {

		testDataManager.execute(() -> {

			VehicleClassEntity classEntity = testDataManager.createNewVehicleClass("vc1");
			testDataManager.createNewDataContainer("UniqueName", classEntity);

			try {
				testDataManager.createNewDataContainer("UniqueName", classEntity);

				fail("Unique constraint violation expected.");
			} catch (PersistenceException exc) {
				if (exc.getCause() instanceof ConstraintViolationException ) {
					assertTrue(true);
				} else {
					fail("Unique constraint violation expected.");
				}
			}
		});

	}
	
	@Test
	@Rollback
	public void testDataContainerEntityConstraintNotNullName() {

		testDataManager.execute(() -> {
			String name = null;
			VehicleClassEntity classEntity = testDataManager.createNewVehicleClass("vc1");

			try {
				testDataManager.createNewDataContainer(name, classEntity);

				fail("Unique constraint violation expected.");
			} catch (javax.validation.ConstraintViolationException exc) {
				assertTrue(true);
			}
		});
	}

}
