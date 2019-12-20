package de.tutous.spring.boot.api;

import com.fasterxml.jackson.annotation.JsonGetter;

import de.tutous.spring.boot.common.type.UserRole;

public interface MemberIn extends Responsible
{

    @JsonGetter("userRole")
    public UserRole getUserRole();

    @JsonGetter("userId")
    public Integer getUserId();

    default boolean isResponsible()
    {
        return getUserRole().isResponsible();
    }

}
