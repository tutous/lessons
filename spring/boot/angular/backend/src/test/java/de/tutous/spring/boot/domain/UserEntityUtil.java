package de.tutous.spring.boot.domain;

import java.util.Random;

import de.tutous.spring.boot.domain.UserEntity;

public class UserEntityUtil
{

    public static UserEntity createUser(Integer id)
    {
        Long index = new Random(10).nextLong();
        return createUser(id, "lastName" + index, "firstName" + index);
    }

    public static UserEntity createUser(Integer id, String firstName, String lastName)
    {
        return new UserEntity(id, lastName, firstName, "test@email.de", true);
    }

    public static UserEntity createNewUser()
    {
        Integer id = null;
        return createUser(id);
    }
}
