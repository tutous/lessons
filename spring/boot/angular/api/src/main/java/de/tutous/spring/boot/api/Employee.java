package de.tutous.spring.boot.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;

public interface Employee extends Serializable {

	@JsonGetter("firstName")
	public String getFirstName();

	@JsonGetter("lastName")
	public String getLastName();
	
	@JsonGetter("fullName")
	public String getFullName();
	
	@JsonGetter("email")
	public String getEmail();
}
