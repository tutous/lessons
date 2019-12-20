package de.tutous.spring.boot.domain;

import de.tutous.spring.boot.domain.DataContainerEntity;
import de.tutous.spring.boot.domain.MemberEntity;
import de.tutous.spring.boot.domain.RoleEntity;
import de.tutous.spring.boot.domain.UserEntity;

public class MemberEntityUtil
{

    public static MemberEntity createMember(Long id, UserEntity userEntity, RoleEntity roleEntity,
            DataContainerEntity dataContainerEntity)
    {
        return new MemberEntity(id, userEntity, roleEntity, userEntity.getFullName(), roleEntity.getUserRole(),
                dataContainerEntity);
    }

    public static MemberEntity createNewMember(UserEntity userEntity, RoleEntity roleEntity,
            DataContainerEntity dataContainerEntity)
    {
        Long id = null;
        return createMember(id, userEntity, roleEntity, dataContainerEntity);
    }

}
