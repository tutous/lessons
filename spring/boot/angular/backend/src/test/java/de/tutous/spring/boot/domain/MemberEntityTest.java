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

import de.tutous.spring.boot.common.type.UserRole;
import de.tutous.spring.boot.conf.JpaTestConfiguration;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.MemberEntity;
import de.tutous.spring.boot.domain.RoleEntity;
import de.tutous.spring.boot.domain.VehicleClassEntity;

@DataJpaTest
@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class MemberEntityTest
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
    public void testMemberEntityCreateAndFind()
    {
        try
        {
            testDataManager.begin();

            /**
             * simple member test
             */

            RoleEntity roleEntity = testDataManager.createNewRole(UserRole.DC_ASSIGNEE);

            // given new member
            MemberEntity newMemberEntity = createMember(true, roleEntity);

            // given type query
            String jpql = "select e from MemberEntity e where e.id = " + newMemberEntity.getId();
            TypedQuery<MemberEntity> typedQueryMemberEntity = testDataManager.createQuery(jpql, MemberEntity.class);

            // when executed query
            MemberEntity foundMemberEntity = typedQueryMemberEntity.getSingleResult();

            // then new equals found
            assertThat(foundMemberEntity).isEqualTo(newMemberEntity);

            /**
             * data container members ref tests
             */

            // given members data container as typed query
            jpql = "select e from DataContainerEntity e where e.id = " + newMemberEntity.getDataContainer().getId();
            TypedQuery<DataContainerEntity> typedQueryDataContainerEntity = testDataManager.createQuery(jpql,
                    DataContainerEntity.class);

            // when executed
            DataContainerEntity foundDataContainerEntity = typedQueryDataContainerEntity.getSingleResult();

            // then new equals found 
            assertThat(foundDataContainerEntity).isEqualTo(newMemberEntity.getDataContainer());
            // resource members
            assertThat(foundDataContainerEntity.getResourceMembers().findFirst().get())
                    .isEqualTo(newMemberEntity.getDataContainer().getResourceMembers().findFirst().get());
            // all members
            assertThat(foundDataContainerEntity.getMembers().findFirst().get())
                    .isEqualTo(newMemberEntity.getDataContainer().getMembers().findFirst().get());

        }
        finally
        {

            testDataManager.finished();

        }

    }

    @Test
    @Rollback
    public void testMemberEntityAddMember()
    {
        try
        {
            testDataManager.begin();

            /**
             * simple member test
             */

            RoleEntity roleEntity = testDataManager.createNewRole(UserRole.DC_ASSIGNEE);

            // given new members
            MemberEntity newMemberEntity1 = createMember(false, roleEntity);
            MemberEntity newMemberEntity2 = createMember(false, roleEntity);

            // then size is equals 0
            assertThat(newDataContainerEntity.getResourceMembers().collect().size()).isEqualTo(0);
            // then size is equals 2
            assertThat(newDataContainerEntity.getMembers().collect().size()).isEqualTo(2);

            // when add as resource
            newDataContainerEntity.addResourceMember(newMemberEntity1);
            newDataContainerEntity.addResourceMember(newMemberEntity2);

            // then size is equals 2
            assertThat(newDataContainerEntity.getResourceMembers().collect().size()).isEqualTo(2);

        }
        finally
        {

            testDataManager.finished();

        }

    }

    private MemberEntity createMember(boolean asResourceMember, RoleEntity roleEntity)
    {

        MemberEntity newMemberEntity = testDataManager.createNewMember(testDataManager.createNewUser(), roleEntity,
                newDataContainerEntity);

        if (asResourceMember)
        {
            newDataContainerEntity.addResourceMember(newMemberEntity);
        }
        return newMemberEntity;
    }

}
