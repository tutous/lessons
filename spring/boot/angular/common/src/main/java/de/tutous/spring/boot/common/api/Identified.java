package de.tutous.spring.boot.common.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;

public interface Identified<T extends Serializable> extends Serializable {

	@JsonGetter("id")
	public T getId();

}
