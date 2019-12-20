package de.tutous.spring.boot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import de.tutous.spring.boot.DCNewBOBuiler;
import de.tutous.spring.boot.bo.DataContainerBO;
import de.tutous.spring.boot.bo.DataContainerNewBO;
import de.tutous.spring.boot.bo.MemberBO;
import de.tutous.spring.boot.bo.VehicleClassBO;
import de.tutous.spring.boot.common.entity.Entities;
import de.tutous.spring.boot.common.session.SessionSupportContext;
import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.common.type.UserRole;
import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.DataContainerEntityUtil;
import de.tutous.spring.boot.domain.MemberEntityUtil;
import de.tutous.spring.boot.domain.RoleEntityUtil;
import de.tutous.spring.boot.domain.UserEntityUtil;
import de.tutous.spring.boot.domain.VehicleClassEntity;
import de.tutous.spring.boot.domain.VehicleClassEntityUtil;
import de.tutous.spring.boot.repository.DataContainerRepo;
import de.tutous.spring.boot.service.DataContainerService;
import de.tutous.spring.boot.service.MemberService;
import de.tutous.spring.boot.service.StorageService;
import de.tutous.spring.boot.service.VehicleClassService;

@ExtendWith(value =
{ MockitoExtension.class })
public class DataContainerServiceTest
{

    static
    {
        SessionSupportContext.set("test0123456789");
    }

    @Mock
    private DataContainerRepo mockDataContainerRepo;
    @Mock
    private StorageService mockStorageService;
    @Mock
    private VehicleClassService mockVehicleClassService;
    @Mock
    private MemberService mockMemberService;
    @Mock
    private MultipartFile mockMultipartFile;

    private DataContainerService dataContainerService;

    @BeforeEach
    public void setUp() throws Exception
    {
        this.dataContainerService = new DataContainerService(mockDataContainerRepo, mockStorageService,
                mockVehicleClassService, mockMemberService);
        Mockito.when(mockMultipartFile.getOriginalFilename()).thenReturn("file");
        Mockito.when(mockMultipartFile.getBytes()).thenReturn("content".getBytes());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAdd() throws Exception
    {

        /**
         * given
         */

        // given new DC
        DataContainerNewBO newDC = createDataContainerNewBO(mockMultipartFile);

        // given return of vehicle classes
        VehicleClassEntity vehicleClassEntity = VehicleClassEntityUtil.createVehicleClass(1, "vc1");
        StreamSupplier<VehicleClassBO> supplierVehicleClasses = () -> {
            Stream.Builder<VehicleClassBO> stream = Stream.builder();
            stream.add(vehicleClassEntity);
            return stream.build();
        };
        // given return of the new DC entity
        DataContainerEntity dataContainerEntity = DataContainerEntityUtil.createDataContainer(new Long(1),
                vehicleClassEntity);
        // given return of members
        StreamSupplier<MemberBO> supplierMembers = () -> {
            Stream.Builder<MemberBO> stream = Stream.builder();
            stream.add(MemberEntityUtil.createMember(new Long(1), UserEntityUtil.createUser(1),
                    RoleEntityUtil.createRole(1, UserRole.DC_ASSIGNEE), dataContainerEntity));
            return stream.build();
        };

        /**
         * mocks
         */

        // given mocked behavior
        //        Mockito.doAnswer(new Answer()
        //        {
        //            public Object answer(org.mockito.invocation.InvocationOnMock invocation) throws Throwable
        //            {
        //                return null;
        //            }
        //        }).when(mockStorageService).saveUpload(UUID.randomUUID(), newDC.getFileName(), newDC.getFileContent());
        Mockito.when(mockVehicleClassService.findByNames(Mockito.any())) //
                .thenReturn(supplierVehicleClasses);
        Mockito.when(mockDataContainerRepo.save(Mockito.any())) //
                .thenReturn(dataContainerEntity);
        Mockito.when(mockMemberService.create(Mockito.any(), Mockito.any(StreamSupplier.class)))//
                .thenReturn(supplierMembers);

        /**
         * when
         */

        // when test add 
        DataContainerBO dataContainerBO = dataContainerService.add(newDC);

        /**
         * than
         */

        // then check attributes
        assertThat(dataContainerBO.getName()).isEqualTo(dataContainerEntity.getName());
        assertThat(dataContainerBO.getDescription()).isEqualTo(dataContainerEntity.getDescription());
        assertThat(dataContainerBO.getResourceMembers().collect().size()).isEqualTo(1);

        // get the original entity
        AtomicReference<DataContainerEntity> reference = new AtomicReference<DataContainerEntity>();
        Entities.toEntity(dataContainerBO, DataContainerEntity.class, entity -> {
            reference.set(entity);
        });
        assertThat(reference.get()).isEqualTo(dataContainerEntity);
        assertThat(reference.get().getMembers().collect().size()).isEqualTo(1);

        /**
         * verify all mocks
         */
        // Mockito.verify(mockStorageService).saveUpload(newDC.getFileName(), newDC.getFileContent());
        Mockito.verify(mockVehicleClassService).findByNames(Mockito.any());
        Mockito.verify(mockDataContainerRepo).save(Mockito.any());
        Mockito.verify(mockMemberService).create(Mockito.any(), Mockito.any(StreamSupplier.class));
    }

    private DataContainerNewBO createDataContainerNewBO(MultipartFile multipartFile) throws Exception
    {

        DataContainerNewBO newDC = DCNewBOBuiler.get().build();
        newDC.setFile(multipartFile);

        return newDC;
    }

}
