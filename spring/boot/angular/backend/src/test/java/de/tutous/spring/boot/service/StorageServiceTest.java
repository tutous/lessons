package de.tutous.spring.boot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.tutous.spring.boot.bo.DiagSpecFileBO;
import de.tutous.spring.boot.domain.DiagSpecFileEntityUtil;
import de.tutous.spring.boot.props.DcStorageProperties;
import de.tutous.spring.boot.service.DiagSpecFileService;
import de.tutous.spring.boot.service.StorageService;

@ExtendWith(value =
{ MockitoExtension.class })
public class StorageServiceTest
{

    private StorageService storageService;
    private DcStorageProperties properties;

    @Mock
    private DiagSpecFileService mockDiagSpecFileService;

    @BeforeEach
    public void setUp()
    {
        String uploadDir = System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "target";
        properties = new DcStorageProperties();
        properties.setUploadDir(uploadDir);
        storageService = new StorageService(mockDiagSpecFileService, properties);
    }

    @Test
    public void testSaveUpload() throws IOException
    {

        // given new file
        String fileName = "test.txt";
        byte[] fileContent = "content".getBytes();

        // given new DiagSpecFileBO, because file not exist
        DiagSpecFileBO diagSpecFileBO = DiagSpecFileEntityUtil.createNewDiagSpecFileEntity(fileName);
        Mockito.when(mockDiagSpecFileService.findByFileName(fileName)).thenReturn(Optional.empty());
        Mockito.when(mockDiagSpecFileService.createNewDiagSpecFile(fileName, fileContent)).thenReturn(diagSpecFileBO);

        // when save new file
        storageService.saveDoorsFile(fileName, "content".getBytes());

        // then saved content expected
        byte[] expectedFileContent = Files
                .readAllBytes(Paths.get(properties.getUploadDir(), diagSpecFileBO.getUuid().toString(), fileName));

        assertThat(expectedFileContent).isNotNull();
        assertThat(expectedFileContent).isEqualTo(fileContent);

    }

}
