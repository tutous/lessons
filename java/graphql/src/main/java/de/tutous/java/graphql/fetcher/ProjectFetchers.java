package de.tutous.java.graphql.fetcher;

import java.util.Collection;
import java.util.Optional;

import de.tutous.java.graphql.domain.Project;
import de.tutous.java.graphql.domain.ProjectRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class ProjectFetchers {

	private ProjectRepository projectRepository = ProjectRepository.get();

	private final DataFetcher<Optional<Project>> projectById = new DataFetcher<Optional<Project>>() {

		@Override
		public Optional<Project> get(DataFetchingEnvironment environment) throws Exception {
			String id = environment.getArgument("id");
			return projectRepository.findById(id);
		}
	};

	private final DataFetcher<Collection<Project>> projects = new DataFetcher<Collection<Project>>() {

		@Override
		public Collection<Project> get(DataFetchingEnvironment environment) throws Exception {
			return projectRepository.findAll();
		}
	};

	private ProjectFetchers() {

	}

	public static ProjectFetchers get() {
		return new ProjectFetchers();
	}

	public DataFetcher<Optional<Project>> projectById() {
		return projectById;
	}

	public DataFetcher<Collection<Project>> projects() {
		return projects;
	}

}
