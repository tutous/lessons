package de.tutous.spring.boot.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import de.tutous.spring.boot.common.bo.AuditedBO;
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
public class DataContainerRepoTest
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
    public void testDataContainerEntityCreateAndFindAndAuditedInfo()
    {

        testDataManager.execute(() -> {

            // given new DC
            VehicleClassEntity vehicleClasses = testDataManager.createNewVehicleClass("vc1");
            DataContainerEntity newDataContainerEntity = DataContainerEntityUtil.createNewDataContainer(vehicleClasses);
            newDataContainerEntity = dataContainerRepo.save(newDataContainerEntity);

            // given found new DC
            Optional<DataContainerEntity> foundDataContainerEntity = dataContainerRepo
                    .findById(newDataContainerEntity.getId());

            // when get audited 
            AuditedBO auditedInfo = newDataContainerEntity.getAuditedInfo();

            // than are default values initialized 
            assertThat(auditedInfo.getCreatedBy()).isEqualTo("unknown");
            assertThat(auditedInfo.getLastModifiedBy()).isEqualTo("unknown");
            assertThat(auditedInfo.getCreatedDate()).isNotNull();
            assertThat(auditedInfo.getCreatedDate()).isEqualTo(auditedInfo.getLastModifiedDate());

            // and found is equals new
            assertThat(foundDataContainerEntity.get()).isEqualTo(newDataContainerEntity);

            assertThat(newDataContainerEntity.getMembers().collect().size()).isEqualTo(0);
            assertThat(newDataContainerEntity.getResourceMembers().collect().size()).isEqualTo(0);

            // test members
            MemberEntity newMemberEntity = MemberEntityUtil.createNewMember(newUserEntity, newRoleEntity, newDCEntity);
            newMemberEntity = memberRepo.save(newMemberEntity);

            newDataContainerEntity.addMember(newMemberEntity);
            newDataContainerEntity.addResourceMember(newMemberEntity);

            testDataManager.persist(newDataContainerEntity);
            foundDataContainerEntity = dataContainerRepo.findById(newDataContainerEntity.getId());

            // add the same member
            newDataContainerEntity.addMember(newMemberEntity);
            newDataContainerEntity.addResourceMember(newMemberEntity);
            
            assertThat(foundDataContainerEntity.get().getMembers().collect().size()).isEqualTo(1);
            assertThat(foundDataContainerEntity.get().getResourceMembers().collect().size()).isEqualTo(1);

        });

    }

}
