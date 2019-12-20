package de.tutous.spring.boot.api.module;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonGetter;

import de.tutous.spring.boot.common.api.Audited;
import de.tutous.spring.boot.common.api.Identified;
import de.tutous.spring.boot.common.type.ModuleFlag;
import de.tutous.spring.boot.common.type.ModuleState;

public interface DataContainerModuleOut extends DataContainerModule, Audited, Identified<Long>
{

    @JsonGetter(DataContainerModuleAttrs.modusteile)
    public Collection<Modusteil> getModusteile();
    
    @JsonGetter(DataContainerModuleAttrs.lockedMemberId)
    public Long lockedMember();

    @JsonGetter(DataContainerModuleAttrs.state)
    public ModuleState getModuleState();
    
    @JsonGetter(DataContainerModuleAttrs.flag)
    public ModuleFlag getModuleFlag();
}
