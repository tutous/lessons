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

import de.tutous.spring.boot.conf.JpaTestConfiguration;
import de.tutous.spring.boot.domain.DiagSpecFileEntity;
import de.tutous.spring.boot.domain.ModusteilEntity;
import de.tutous.spring.boot.domain.ParameterEntity;

@DataJpaTest
@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class ParameterEntityTest
{

    @Autowired
    private TestDataManager testDataManager;

    private DiagSpecFileEntity diagSpecFile;
    private ModusteilEntity modusteilEntity;

    @BeforeEach
    public void setUp()
    {
        diagSpecFile = testDataManager.cerateNewDiagSpecFileEntity("test.txt");
        modusteilEntity = testDataManager.cerateNewModusteil(diagSpecFile, "objectHeading", "recordDataId",
                "longName", "byteReihenfolge");
    }

    @Test
    @Rollback
    public void testModusteilEntityCreateAndFind()
    {

        testDataManager.execute(() -> {

            ParameterEntity entityCreated = testDataManager.cerateNewParameterEntity(modusteilEntity,
                    "objectHeading", "longName", "signalName", "bitPositionStart", "bitPositionStop", "defaultwert");

            String jpql = "select e from ParameterEntity e where e.id = " + entityCreated.getId();
            TypedQuery<ParameterEntity> typedQueryParameterEntity = testDataManager.createQuery(jpql,
                    ParameterEntity.class);
            ParameterEntity entityFound = typedQueryParameterEntity.getSingleResult();

            assertThat(entityCreated).isEqualTo(entityFound);

        });

    }

}
