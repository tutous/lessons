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
import de.tutous.spring.boot.domain.ParameterValueEntity;

@DataJpaTest
@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class ParameterValueEntityTest
{

    @Autowired
    private TestDataManager testDataManager;

    private DiagSpecFileEntity diagSpecFile;
    private ModusteilEntity modusteilEntity;
    private ParameterEntity parameterEntity;

    @BeforeEach
    public void setUp()
    {
        diagSpecFile = testDataManager.cerateNewDiagSpecFileEntity("test.txt");
        modusteilEntity = testDataManager.cerateNewModusteil(diagSpecFile, "objectHeading", "recordDataId", "longName",
                "byteReihenfolge");
        parameterEntity = testDataManager.cerateNewParameterEntity(modusteilEntity, "objectHeading", "longName",
                "signalName", "bitPositionStart", "bitPositionStop", "defaultwert");
    }

    @Test
    @Rollback
    public void testModusteilEntityCreateAndFind()
    {

        testDataManager.execute(() -> {

            ParameterValueEntity entityCreated = testDataManager.cerateNewParameterValueEntity(parameterEntity,
                    "objectText", "minWert", "maxWert", "longName", "aufloesung", "physicalUnit");

            String jpql = "select e from ParameterValueEntity e where e.id = " + entityCreated.getId();
            TypedQuery<ParameterValueEntity> typedQueryParameterValueEntity = testDataManager.createQuery(jpql,
                    ParameterValueEntity.class);
            ParameterValueEntity entityFound = typedQueryParameterValueEntity.getSingleResult();

            assertThat(entityCreated).isEqualTo(entityFound);

        });

    }

}
