package de.tutous.spring.boot.resource;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.tutous.spring.boot.bo.DataContainerBOM;
import de.tutous.spring.boot.bo.DataContainerBOMAssembler;
import de.tutous.spring.boot.domain.DataContainerEntityUtil;

public class DataContainerModelAssemblerTest
{

    @Test
    public void test() throws JsonProcessingException
    {

        DataContainerBOM model = new DataContainerBOMAssembler()
                .toModel(DataContainerEntityUtil.createDataContainer(new Long(1)));

        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        System.out.println(om.writeValueAsString(model));

    }

}
