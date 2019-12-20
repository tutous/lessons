package de.tutous.spring.boot.api;

import com.fasterxml.jackson.annotation.JsonGetter;

import de.tutous.spring.boot.common.api.Identified;

public interface MemberOut extends MemberIn, Identified<Long>
{

    @JsonGetter("fullName")
    public String getFullName();

}
