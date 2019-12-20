package de.tutous.spring.boot.citest.backend.api;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataContainerUtil
{

    public static String createDataContainerAsJson() throws JsonProcessingException
    {
        DataContainer dataContainer = new DataContainer("name", // 
                "ZDC", //
                "gen01", //
                "pn01", //
                "LIN", //
                "fx09", //
                "UDS", //
                "ISOTP", //
                Arrays.asList("ABC"), // 
                "...", //
                Arrays.asList(new Member("DC_SUPERVISOR", 1)));
        return new ObjectMapper().writeValueAsString(dataContainer);
    }

}
