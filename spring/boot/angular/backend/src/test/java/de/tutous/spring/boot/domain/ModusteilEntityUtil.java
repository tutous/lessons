package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.bo.DiagSpecFileBO;
import de.tutous.spring.boot.bo.ModusteilBO;
import de.tutous.spring.boot.domain.ModusteilEntity;

public class ModusteilEntityUtil
{

    public static ModusteilEntity createModusteilEntity(Long id, DiagSpecFileBO diagSpecFileBO, String objectHeading,
            String recordDataId, String longName, String byteReihenfolge)
    {
        return new ModusteilEntity(id, diagSpecFileBO, objectHeading, recordDataId, longName, byteReihenfolge);
    }

    public static ModusteilEntity createNewModusteilEntity(DiagSpecFileBO diagSpecFileBO, String objectHeading, String recordDataId,
            String longName, String byteReihenfolge)
    {
        Long id = null;
        return createModusteilEntity(id, diagSpecFileBO, objectHeading, recordDataId, longName, byteReihenfolge);
    }

    public static ModusteilEntity createModusteilEntity(Long id, DiagSpecFileBO diagSpecFileBO, ModusteilBO bo)
    {
        return createModusteilEntity(id, diagSpecFileBO, bo.getObjectHeading(), bo.getRecordDataId(), bo.getLongName(),
                bo.getByteReihenfolge());
    }

}
