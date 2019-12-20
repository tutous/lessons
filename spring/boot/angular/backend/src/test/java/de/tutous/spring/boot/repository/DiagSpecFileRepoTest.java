package de.tutous.spring.boot.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import de.tutous.spring.boot.conf.JpaTestConfiguration;
import de.tutous.spring.boot.domain.DiagSpecFileEntity;
import de.tutous.spring.boot.domain.TestDataManager;
import de.tutous.spring.boot.repository.DiagSpecFileRepo;

@DataJpaTest()
@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class DiagSpecFileRepoTest
{

    @Autowired
    private TestDataManager testDataManager;

    @Autowired
    private DiagSpecFileRepo diagSpecFileRepo;

    private DiagSpecFileEntity diagSpecFileEntity;

    @BeforeEach
    public void setUp()
    {
        diagSpecFileEntity = testDataManager.createNewDiagSpecFileEntity("test.txt");
    }

    @Test
    public void testFindByFileName()
    {

        Optional<DiagSpecFileEntity> entityNotFound = diagSpecFileRepo.findByFileName("_test.txt");
        assertThat(entityNotFound.isPresent()).isEqualTo(false);
        
        Optional<DiagSpecFileEntity>  entityFound = diagSpecFileRepo.findByFileName("test.txt");
        assertThat(entityFound.isPresent()).isEqualTo(true);

    }

}
