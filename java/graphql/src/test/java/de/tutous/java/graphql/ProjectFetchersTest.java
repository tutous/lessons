package de.tutous.java.graphql;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tutous.java.graphql.fetcher.ProjectFetchers;
import graphql.ExecutionResult;
import graphql.GraphQL;

public class ProjectFetchersTest {

	private GraphQL graphQL;
	private ProjectFetchers projectFetchers = ProjectFetchers.get();

	@BeforeEach
	public void setUP() throws IOException {
		graphQL = GraphQLBuilder.create(TestSchemas.PROJECT). //
				type("Query", builder -> {
					return builder.dataFetcher("projectById", projectFetchers.projectById())//
							.dataFetcher("projects", projectFetchers.projects());
				}).build();
	}

	@Test
	public void testProjects() throws IOException {

		StringBuilder query = new StringBuilder();
		query.append("query { projects { ");
		query.append("  name ");
		query.append("  tasks {name status}");
		query.append("  }}");

		ExecutionResult executionResult = graphQL.execute(query.toString());

		System.out.println(executionResult.getData().toString());

	}

	@Test
	public void testProjectById() throws IOException {

		StringBuilder query = new StringBuilder();
		query.append("query findProject($id: ID = 1) {");
		query.append("  project: projectById(id: $id)");
		query.append("  {");
		query.append("    name");
		query.append("    tasks {name status}");
		query.append("  }");
		query.append("}");

		ExecutionResult executionResult = graphQL.execute(query.toString());

		System.out.println(executionResult.getData().toString());

	}
}
