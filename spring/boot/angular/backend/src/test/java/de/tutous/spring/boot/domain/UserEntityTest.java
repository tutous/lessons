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
import de.tutous.spring.boot.domain.UserEntity;

@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
@DataJpaTest()
public class UserEntityTest
{

    @Autowired
    private TestDataManager testDataManager;

    @BeforeEach
    public void setUp()
    {

    }

    @Test
    @Rollback
    public void testUserEntityCreateAndFind()
    {

        UserEntity newUserEntity = testDataManager.createNewUser();

        // given type query
        String jpql = "select e from UserEntity e where e.id = " + newUserEntity.getId();
        TypedQuery<UserEntity> typedQueryUserEntity = testDataManager.createQuery(jpql, UserEntity.class);

        // when executed query
        UserEntity foundUserEntity = typedQueryUserEntity.getSingleResult();

        // then new equals found
        assertThat(foundUserEntity).isEqualTo(newUserEntity);

    }
}
