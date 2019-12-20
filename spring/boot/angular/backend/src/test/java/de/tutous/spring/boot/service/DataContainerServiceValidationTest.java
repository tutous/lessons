package de.tutous.spring.boot.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import de.tutous.spring.boot.DCNewBOBuiler;
import de.tutous.spring.boot.bo.DataContainerNewBO;
import de.tutous.spring.boot.conf.DataContainerServiceValidationConf;
import de.tutous.spring.boot.conf.DataContainerServiceValidationConf.DataContainerRepoTestImpl;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.repository.DataContainerRepo;
import de.tutous.spring.boot.service.DataContainerService;

@ActiveProfiles("dc-validation-test")
@SpringBootTest()
@ContextConfiguration(classes =
{ DataContainerServiceValidationConf.class })
public class DataContainerServiceValidationTest
{

    @Autowired
    private DataContainerService dataContainerService;

    @Autowired
    private DataContainerRepoTestImpl dataContainerRepoTestImpl;

    private DataContainerRepo mockDataContainerRepo;

    @BeforeEach
    public void setUp()
    {
        mockDataContainerRepo = dataContainerRepoTestImpl.getMockDataContainerRepo();
    }

    //@Test
    public void testAdd() throws Exception
    {

        DataContainerNewBO dc = DCNewBOBuiler.get().build();

        when(mockDataContainerRepo.findByName(dc.getName())).thenReturn(Optional.of(new DataContainerEntity()));

        try
        {

            dataContainerService.add(dc);
            fail("failed validation is expected");

        }
        catch (ConstraintViolationException exception)
        {
            assertTrue(true);
        }

    }

}
