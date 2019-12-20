package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.bo.ParameterBO;
import de.tutous.spring.boot.bo.ParameterValueBO;
import de.tutous.spring.boot.domain.ParameterValueEntity;

public class ParameterValueEntityUtil
{

    public static ParameterValueEntity createParameterValueEntity(Long id, ParameterBO parameterBO, String objectText,
            String minWert, String maxWert, String longName, String aufloesung, String physicalUnit)
    {
        return new ParameterValueEntity(id, parameterBO, objectText, minWert, maxWert, longName, aufloesung,
                physicalUnit);
    }

    public static ParameterValueEntity createNewParameterValueEntity(ParameterBO parameterBO, String objectText,
            String minWert, String maxWert, String longName, String aufloesung, String physicalUnit)
    {
        Long id = null;
        return createParameterValueEntity(id, parameterBO, objectText, minWert, maxWert, longName, aufloesung,
                physicalUnit);
    }

    public static ParameterValueEntity createParameterValueEntity(Long id, ParameterBO parameterBO, ParameterValueBO bo)
    {
        return createParameterValueEntity(id, parameterBO, bo.getObjectText(), bo.getMinWert(), bo.getMaxWert(),
                bo.getLongName(), bo.getAufloesung(), bo.getPhysicalUnit());
    }

}
