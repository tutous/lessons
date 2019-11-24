package de.tutous.java.graphql;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.UnaryOperator;

import graphql.schema.idl.RuntimeWiring.Builder;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

public class GraphQLBuilder {

	private Builder runtimeWiringBuilder;
	private URI schemaURI;

	private GraphQLBuilder(URI schemaURI) {
		this.schemaURI = schemaURI;
		runtimeWiringBuilder = newRuntimeWiring();
	}

	public static GraphQLBuilder create(URI schemaURI) throws IOException {
		return new GraphQLBuilder(schemaURI);
	}

	public GraphQLBuilder type(String typeName, UnaryOperator<TypeRuntimeWiring.Builder> builderFunction) {
		runtimeWiringBuilder.type(typeName, builderFunction);
		return this;
	}

	public GraphQL build() throws IOException {
		// schema
		String schema = Files.readString(Paths.get(schemaURI));
		TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schema);
		// SchemaGenerator
		GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry,
				runtimeWiringBuilder.build());

		return GraphQL.newGraphQL(graphQLSchema).build();
	}

}
