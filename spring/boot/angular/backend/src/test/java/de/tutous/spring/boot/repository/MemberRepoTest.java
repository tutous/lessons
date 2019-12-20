package de.tutous.spring.boot.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Optional;

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
import de.tutous.spring.boot.domain.DataContainerEntityUtil;
import de.tutous.spring.boot.domain.MemberEntity;
import de.tutous.spring.boot.domain.MemberEntityUtil;
import de.tutous.spring.boot.domain.RoleEntity;
import de.tutous.spring.boot.domain.TestDataManager;
import de.tutous.spring.boot.domain.UserEntity;
import de.tutous.spring.boot.domain.VehicleClassEntity;
import de.tutous.spring.boot.repository.DataContainerRepo;
import de.tutous.spring.boot.repository.MemberRepo;

@DataJpaTest()
@ContextConfiguration(classes =
{ JpaTestConfiguration.class })
@ActiveProfiles("jpa-test")
public class MemberRepoTest
{

    @Autowired
    private TestDataManager testDataManager;

    @Autowired
    private DataContainerRepo dataContainerRepo;

    @Autowired
    private MemberRepo memberRepo;

    private DataContainerEntity newDCEntity;
    private UserEntity newUserEntity;
    private RoleEntity newRoleEntity;

    @BeforeEach
    public void setUp()
    {
        VehicleClassEntity vehicleClasses = testDataManager.createNewVehicleClass("vc1");
        newDCEntity = DataContainerEntityUtil.createNewDataContainer(vehicleClasses);
        newDCEntity = dataContainerRepo.save(newDCEntity);
        newUserEntity = testDataManager.createNewUser();
        newRoleEntity = testDataManager.createNewRole(UserRole.DC_ASSIGNEE);
    }

    @Test
    @Rollback
    public void testMemberEntityCreateAndFind()
    {
        testDataManager.execute(() -> {

            // given new member
            MemberEntity newMemberEntity = MemberEntityUtil.createNewMember(newUserEntity, newRoleEntity, newDCEntity);
            newMemberEntity = memberRepo.save(newMemberEntity);

            int size = newMemberEntity.getDataContainer().getMembers().collect().size();
            assertThat(size).isEqualTo(1);
            assertThat(newMemberEntity.getDataContainer().getMembers().findFirst().get()).isEqualTo(newMemberEntity);

            // when execute find
            Optional<MemberEntity> foundMemberEntity = memberRepo.findById(newMemberEntity.getId());

            // than new is equals found
            assertThat(foundMemberEntity.get()).isEqualTo(newMemberEntity);

            assertThat(newDCEntity.getMembers().collect().size()).isEqualTo(1);

        });

    }

    @Test
    @Rollback
    public void testFindAllByResourceId()
    {
        testDataManager.execute(() -> {

            // given new member
            MemberEntity newMemberEntity = MemberEntityUtil.createNewMember(newUserEntity, newRoleEntity, newDCEntity);
            newMemberEntity = memberRepo.save(newMemberEntity);
            newDCEntity.addResourceMember(newMemberEntity);
            testDataManager.persist(newDCEntity);
            
            Collection<MemberEntity> foundMembers = memberRepo.findAllByResourceId(newDCEntity.getId());
            
            assertThat(foundMembers.size()).isEqualTo(1);
            
        });
    }

}
