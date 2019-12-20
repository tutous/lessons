package de.tutous.spring.boot;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.tutous.spring.boot.bo.DataContainerNewBO;

public class DCNewBOBuiler
{

    private String data;

    private DCNewBOBuiler()
    {
        this.data = new StringBuilder()//
                .append("{\"name\": \"DC\", ") //
                .append("\"type\": \"ZDC\", ") //
                .append("\"members\":") //
                .append("  [ { \"userId\": 1, \"userRole\": \"DC_SUPERVISOR\" }  ], ")//
                .append("\"description\": \"text\", ") //
                .append("\"vehicleClasses\": [ \"VC1\" ], ") //
                .append("\"generation\": \"2019/50\", ") //
                .append("\"partNumber\": \"12345678912\", ") //
                .append("\"diagnosticBus\": \"CAN\", ") //
                .append(" \"diagnosticAddress\": \"1A2D\", ") //
                .append("\"diagnosticKwp\": \"UDS\", ") //
                .append("\"transportProtocol\": \"TP2_0\" }") //
                .toString();
    }

    public static DCNewBOBuiler get()
    {
        return new DCNewBOBuiler();
    }

    public DCNewBOBuiler replace(String from, String to)
    {
        data = data.replaceFirst(from, to);
        return this;
    }

    public String getJson() throws Exception
    {
        return data;
    }

    public DataContainerNewBO build() throws Exception
    {
        DataContainerNewBO bo = new ObjectMapper().readValue(data, DataContainerNewBO.class);
        return bo;
    }

}
