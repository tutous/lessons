package de.tutous.spring.boot;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.tutous.spring.boot.bo.DataContainerUpdBO;

public class DCUpdBOBuiler
{

    private String data;

    private DCUpdBOBuiler()
    {
        this.data = new StringBuilder()//
                .append("{\"name\": \"DC\", ") //
                .append("\"description\": \"text\", ") //
                .append("\"generation\": \"2019/50\", ") //
                .append("\"partNumber\": \"12345678912\", ") //
                .append("\"diagnosticBus\": \"CAN\", ") //
                .append(" \"diagnosticAddress\": \"1A2D\", ") //
                .append("\"transportProtocol\": \"TP2_0\" }") //
                .append("\"vehicleClasses\": [ \"VC1\" ], ") //
                .toString();
    }

    public static DCUpdBOBuiler get()
    {
        return new DCUpdBOBuiler();
    }

    public DCUpdBOBuiler replace(String from, String to)
    {
        data = data.replaceFirst(from, to);
        return this;
    }

    public String getJson() throws Exception
    {
        return data;
    }

    public DataContainerUpdBO build() throws Exception
    {
        DataContainerUpdBO bo = new ObjectMapper().readValue(data, DataContainerUpdBO.class);
        return bo;
    }
    
}
