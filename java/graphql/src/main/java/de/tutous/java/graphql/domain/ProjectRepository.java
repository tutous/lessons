package de.tutous.java.graphql.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProjectRepository {

	private static Map<String, Project> mappedProjects = new HashMap<String, Project>();
	static {
		mappedProjects.put("1", new Project("1", "Project 1", "..."));
		mappedProjects.put("2", new Project("2", "Project 2", "..."));
		mappedProjects.put("3", new Project("3", "Project 3", "..."));
		mappedProjects.put("4", new Project("4", "Project 4", "..."));
	}

	private ProjectRepository() {
		mappedProjects.get("1").addTask(new Task("1", "Task 1", "...", Status.STARTED));
	}

	public static ProjectRepository get() {
		return new ProjectRepository();
	}

	public Optional<Project> findById(String id) {
		if (mappedProjects.containsKey(id)) {
			return Optional.of(mappedProjects.get(id));
		} else {
			return Optional.empty();
		}
	}

	public Collection<Project> findAll() {
		return Collections.unmodifiableCollection(mappedProjects.values());
	}

}
