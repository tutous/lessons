package de.tutous.spring.boot.conf;

import javax.persistence.EntityManager;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.tutous.spring.boot.common.session.SessionSupportContext;
import de.tutous.spring.boot.domain.TestDataManager;

@Profile("jpa-test")
@TestConfiguration()
public class JpaTestConfiguration
{

    static
    {
        SessionSupportContext.set("test0123456789");
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }

    @Bean
    public TestDataManager testDataManager(EntityManager entityManager)
    {
        return new TestDataManager(entityManager);
    }

}
