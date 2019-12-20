package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.bo.ModusteilBO;
import de.tutous.spring.boot.bo.ParameterBO;
import de.tutous.spring.boot.domain.ParameterEntity;

public class ParameterEntityUtil
{

    public static ParameterEntity createParameterEntity(Long id, ModusteilBO modusteilBO, String objectHeading,
            String longName, String signalName, String bitPositionStart, String bitPositionStop, String defaultwert)
    {
        return new ParameterEntity(id, modusteilBO, objectHeading, longName, signalName, bitPositionStart,
                bitPositionStop, defaultwert);
    }

    public static ParameterEntity createNewParameterEntity(ModusteilBO modusteilBO, String objectHeading, String longName,
            String signalName, String bitPositionStart, String bitPositionStop, String defaultwert)
    {
        Long id = null;
        return createParameterEntity(id, modusteilBO, objectHeading, longName, signalName, bitPositionStart,
                bitPositionStop, defaultwert);
    }

    public static ParameterEntity createParameterEntity(Long id, ModusteilBO modusteilBO, ParameterBO bo)
    {
        return createParameterEntity(id, modusteilBO, bo.getObjectHeading(), bo.getLongName(), bo.getSignalName(),
                bo.getBitPositionStart(), bo.getBitPositionStop(), bo.getDefaultwert());
    }

}
