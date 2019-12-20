package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.common.type.UserRole;
import de.tutous.spring.boot.domain.RoleEntity;

public class RoleEntityUtil
{

    public static RoleEntity createRole(Integer id, UserRole userRole)
    {
        return new RoleEntity(id, userRole);
    }

    public static RoleEntity createNewRole(UserRole userRole)
    {
        Integer id = null;
        return createRole(id, userRole);
    }
}
