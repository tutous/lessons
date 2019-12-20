package de.tutous.java.graphql.domain;

public class Task {
	private String id;
	private String name;
	private String description;
	private Status status;

	public Task(String id, String name, String description, Status status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
	}

}
