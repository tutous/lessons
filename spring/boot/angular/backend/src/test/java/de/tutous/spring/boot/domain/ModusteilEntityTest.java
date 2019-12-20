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

@DataJpaTest
@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class ModusteilEntityTest
{

    @Autowired
    private TestDataManager testDataManager;

    private DiagSpecFileEntity diagSpecFile;

    @BeforeEach
    public void setUp()
    {
        diagSpecFile = testDataManager.cerateNewDiagSpecFileEntity("test.txt");
    }

    @Test
    @Rollback
    public void testModusteilEntityCreateAndFind()
    {

        testDataManager.execute(() -> {

            ModusteilEntity entityCreated = testDataManager.cerateNewModusteil(diagSpecFile, "objectHeading",
                    "recordDataId", "longName", "byteReihenfolge");

            String jpql = "select e from ModusteilEntity e where e.id = " + entityCreated.getId();
            TypedQuery<ModusteilEntity> typedQueryModusteilEntity = testDataManager.createQuery(jpql,
                    ModusteilEntity.class);
            ModusteilEntity entityFound = typedQueryModusteilEntity.getSingleResult();

            assertThat(entityCreated).isEqualTo(entityFound);

        });

    }

}
