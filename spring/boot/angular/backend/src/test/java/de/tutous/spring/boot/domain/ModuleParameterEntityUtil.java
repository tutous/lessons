package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.bo.DataContainerModuleBO;
import de.tutous.spring.boot.domain.ModuleParameterEntity;

public class ModuleParameterEntityUtil
{

    public static ModuleParameterEntity createModuleParameterEntity(Long id, DataContainerModuleBO module, String name,
            String rdid, String modusteilName)
    {
        return new ModuleParameterEntity(id, module, name, rdid, modusteilName);
    }

    public static ModuleParameterEntity createNewModuleParameterEntity(DataContainerModuleBO module, String name,
            String rdid, String modusteilName)
    {
        Long id = null;
        return createModuleParameterEntity(id, module, name, rdid, modusteilName);
    }
}
