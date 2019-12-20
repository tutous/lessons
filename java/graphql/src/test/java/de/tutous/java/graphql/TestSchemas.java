package de.tutous.java.graphql;

import java.net.URI;
import java.net.URISyntaxException;

public class TestSchemas {

	public static URI BUILDERTEST;
	public static URI PROJECT;

	static {
		try {
			BUILDERTEST = TestSchemas.class.getResource("/buildertest.graphql").toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	static {
		try {
			PROJECT = TestSchemas.class.getResource("/project.graphql").toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
