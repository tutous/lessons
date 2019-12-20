package de.tutous.spring.boot.convert;

import static de.tutous.spring.boot.api.DcErrorCode.INVALID_DATA_CONTAINER;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.tutous.spring.boot.bo.DataContainerNewBO;
import de.tutous.spring.boot.common.exc.JsonProcessingRuntimeException;

@Component
public class DataContainerNewConverter implements Converter<String, DataContainerNewBO>
{

    public static final String ERROR_MESSAGE = "Failed to convert field 'data' to required type 'DataContainerNewBO'";
    private ObjectMapper objectMapper;

    @Autowired
    public DataContainerNewConverter(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public DataContainerNewBO convert(String jsonAsString)
    {
        try
        {
            DataContainerNewBO dataContainerNewBO = objectMapper.readValue(jsonAsString, DataContainerNewBO.class);
            return dataContainerNewBO;
        }
        catch (IOException e)
        {
            String msg = ERROR_MESSAGE;
            throw new JsonProcessingRuntimeException(e, INVALID_DATA_CONTAINER, new String[]
            { msg });
        }
    }

}
