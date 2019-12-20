package de.tutous.java.graphql;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.StaticDataFetcher;

public class GraphQLBuilderTest {
	private GraphQLBuilder graphQLBuilder;

	@BeforeEach
	public void setUP() throws IOException {
		graphQLBuilder = GraphQLBuilder.create(TestSchemas.BUILDERTEST);
	}

	@Test
	public void testHello() throws IOException {

		GraphQL graphQL = graphQLBuilder
				.type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world"))).build();

		ExecutionResult executionResult = graphQL.execute("{hello}");

		System.out.println(executionResult.getData().toString());

	}

}
