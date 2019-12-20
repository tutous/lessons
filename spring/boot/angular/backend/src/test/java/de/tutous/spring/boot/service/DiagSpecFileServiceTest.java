package de.tutous.spring.boot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import de.tutous.spring.boot.bo.DiagSpecFileBO;
import de.tutous.spring.boot.bo.ModusteilBO;
import de.tutous.spring.boot.bo.ParameterBO;
import de.tutous.spring.boot.bo.ParameterValueBO;
import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.domain.DiagSpecFileEntity;
import de.tutous.spring.boot.domain.DiagSpecFileEntityUtil;
import de.tutous.spring.boot.domain.ModusteilEntity;
import de.tutous.spring.boot.domain.ModusteilEntityUtil;
import de.tutous.spring.boot.domain.ParameterEntity;
import de.tutous.spring.boot.domain.ParameterEntityUtil;
import de.tutous.spring.boot.domain.ParameterValueEntity;
import de.tutous.spring.boot.domain.ParameterValueEntityUtil;
import de.tutous.spring.boot.repository.DiagSpecFileRepo;
import de.tutous.spring.boot.repository.ModusteilRepo;
import de.tutous.spring.boot.repository.ParameterRepo;
import de.tutous.spring.boot.repository.ParameterValueRepo;
import de.tutous.spring.boot.service.DiagSpecFileService;

@ExtendWith(value =
{ MockitoExtension.class })
public class DiagSpecFileServiceTest
{
    @Mock
    private DiagSpecFileRepo mockDiagSpecFileRepository;
    @Mock
    private ModusteilRepo mockModusteilRepo;
    @Mock
    private ParameterRepo mockParameterRepo;
    @Mock
    private ParameterValueRepo mockParameterValueRepo;

    private DiagSpecFileService diagSpecFileService;

    @BeforeEach
    public void setUp() throws Exception
    {
        diagSpecFileService = new DiagSpecFileService(mockDiagSpecFileRepository, mockModusteilRepo, mockParameterRepo,
                mockParameterValueRepo);
    }

    @Test
    public void testCreateNewDiagSpecFile() throws IOException, URISyntaxException
    {
        // given mocked entity
        DiagSpecFileEntity diagSpecFileEntity = DiagSpecFileEntityUtil.createDiagSpecFileEntity((long) 1, "file.txt");
        Mockito.when(mockDiagSpecFileRepository.save(Mockito.any())).thenReturn(diagSpecFileEntity);

        Mockito.when(mockModusteilRepo.save(Mockito.any())).then(new Answer<ModusteilEntity>()
        {
            private Long id = new Long(-1);

            @Override
            public ModusteilEntity answer(InvocationOnMock invocation) throws Throwable
            {
                ModusteilBO bo = invocation.getArgument(0, ModusteilEntity.class);
                return ModusteilEntityUtil.createModusteilEntity(createId(), diagSpecFileEntity, bo);
            }

            private Long createId()
            {
                return ++id;
            }
        });

        Mockito.when(mockParameterRepo.save(Mockito.any())).then(new Answer<ParameterEntity>()
        {
            private Long id = new Long(-1);

            @Override
            public ParameterEntity answer(InvocationOnMock invocation) throws Throwable
            {
                ParameterBO bo = invocation.getArgument(0, ParameterEntity.class);
                return ParameterEntityUtil.createParameterEntity(createId(), null, bo);
            }

            private Long createId()
            {
                return ++id;
            }
        });

        Mockito.when(mockParameterValueRepo.save(Mockito.any())).then(new Answer<ParameterValueEntity>()
        {
            private Long id = new Long(-1);

            @Override
            public ParameterValueEntity answer(InvocationOnMock invocation) throws Throwable
            {
                ParameterValueBO bo = invocation.getArgument(0, ParameterValueEntity.class);
                return ParameterValueEntityUtil.createParameterValueEntity(createId(), null, bo);
            }

            private Long createId()
            {
                return ++id;
            }
        });

        // given expected sizes 
        Collection<Integer> paramExpectedSizes = new ArrayList<Integer>(Arrays.asList(1, 11, 14, 16));

        Collection<Integer> paramValuesExpectedSizes = new ArrayList<Integer>(Arrays.asList(0, 15, 24, 30));

        // when do test 'createNewDiagSpecFile'
        URL url = DiagSpecFileServiceTest.class.getResource("/doors/doors-export.xml");
        byte[] in = Files.readAllBytes(Paths.get(url.toURI()));
        DiagSpecFileBO bo = diagSpecFileService.createNewDiagSpecFile("file.txt", in);

        // then bo is not null
        assertThat(bo).isNotNull();
        // then the expected size are correct
        assertThat(bo.getModusteile().collect()).asList().hasSize(4);
        bo.getModusteile().forEach(mt -> {

            // param sizes
            StreamSupplier<ParameterBO> streamSupplierParams = mt.getParameters();
            Integer paramSize = streamSupplierParams.collect().size();
            assertThat(paramSize).isIn(paramExpectedSizes);
            paramExpectedSizes.remove(paramSize);

            // param values sum sizes
            Integer paramValuesExpectedSize = streamSupplierParams.stream()
                    .map(p -> p.getParameterValues().collect().size()).reduce((p1, p2) -> p1 + p2).get();
            assertThat(paramValuesExpectedSize).isIn(paramValuesExpectedSizes);
            paramValuesExpectedSizes.remove(paramValuesExpectedSize);

        });
    }

}
