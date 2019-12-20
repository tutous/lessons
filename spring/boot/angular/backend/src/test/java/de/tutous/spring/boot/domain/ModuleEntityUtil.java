package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.bo.DataContainerBO;
import de.tutous.spring.boot.common.type.ModuleFlag;
import de.tutous.spring.boot.common.type.ModuleState;
import de.tutous.spring.boot.domain.ModuleEntity;

public class ModuleEntityUtil
{

    public static ModuleEntity createModuleEntity(Long id, DataContainerBO dataContainerBO, String name, String description,
            ModuleState state, ModuleFlag flag)
    {
        return new ModuleEntity(id, dataContainerBO, name, description, state, flag);
    }

    public static ModuleEntity createNewModuleEntity(DataContainerBO dataContainerBO, String name, String description,
            ModuleState state, ModuleFlag flag)
    {
        Long id = null;
        return createModuleEntity(id, dataContainerBO, name, description, state, flag);
    }

}
