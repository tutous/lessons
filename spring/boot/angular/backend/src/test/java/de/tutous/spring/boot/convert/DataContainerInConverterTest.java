package de.tutous.spring.boot.convert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tutous.spring.boot.api.DcErrorCode;
import de.tutous.spring.boot.api.dc.DataContainerInNew;
import de.tutous.spring.boot.common.exc.ApplicationRuntimeException;
import de.tutous.spring.boot.convert.DataContainerNewConverter;

public class DataContainerInConverterTest
{

    private Optional<String> optionalDataContainerSuccess;
    private Optional<String> optionalDataContainerFailure;

    @BeforeEach
    public void setUp() throws Exception
    {
        // success
        URL url = DataContainerInConverterTest.class.getResource("/convert/data-container-success.json");
        Path path = Paths.get(url.toURI());
        optionalDataContainerSuccess = Files.readAllLines(path).stream().reduce((l1, l2) -> l1 + l2);
        // failure
        url = DataContainerInConverterTest.class.getResource("/convert/data-container-failure.json");
        path = Paths.get(url.toURI());
        optionalDataContainerFailure = Files.readAllLines(path).stream().reduce((l1, l2) -> l1 + l2);

    }

    @Test
    public void testConvertSuccess() throws JsonProcessingException
    {
        optionalDataContainerSuccess.ifPresent(jsonAsString -> {
            DataContainerInNew dataContainerIn = new DataContainerNewConverter(new ObjectMapper()).convert(jsonAsString);
            System.out.println(dataContainerIn);
        });
    }

    @Test
    public void testConvertFailure()
    {
        optionalDataContainerFailure.ifPresent(jsonAsString -> {
            try
            {
                new DataContainerNewConverter(new ObjectMapper()).convert(jsonAsString).getType();
                fail("ApplicationRuntimeException expected");
            }
            catch (ApplicationRuntimeException exc)
            {
                assertThat(exc.getId(String.class)).isEqualTo(DcErrorCode.INVALID_DATA_CONTAINER.getId());
            }
        });

    }
}
