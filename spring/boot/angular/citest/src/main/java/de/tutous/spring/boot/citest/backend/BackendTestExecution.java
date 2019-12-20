package de.tutous.spring.boot.citest.backend;

@FunctionalInterface
public interface BackendTestExecution
{

    public void execute(BackendTestEnvironment environment) throws Exception;

}
