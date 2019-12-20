package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.domain.DiagSpecFileEntity;

public class DiagSpecFileEntityUtil
{

    public static DiagSpecFileEntity createDiagSpecFileEntity(Long id, String fileName)
    {
        return new DiagSpecFileEntity(id, fileName);
    }

    public static DiagSpecFileEntity createNewDiagSpecFileEntity(String fileName)
    {
        Long id = null;
        return createDiagSpecFileEntity(id, fileName);
    }
}
