package de.tutous.java.graphql.domain;

import java.util.ArrayList;
import java.util.Collection;

public class Project {

	private String id;
	private String name;
	private String description;
	private Collection<Task> tasks = new ArrayList<Task>();

	public Project() {

	}

	public Project(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public Collection<Task> getTasks() {
		return tasks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
