package de.tutous.spring.boot.citest.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Member
{
    @JsonProperty("userRole")
    public String userRole;

    @JsonProperty("userId")
    public Integer userId;

    public Member(String userRole, Integer userId)
    {
        super();
        this.userRole = userRole;
        this.userId = userId;
    }
}
