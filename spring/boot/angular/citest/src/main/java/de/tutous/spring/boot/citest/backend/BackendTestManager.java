package de.tutous.spring.boot.citest.backend;

public class BackendTestManager
{

    public static void execute(MemberRole memberRole, BackendTestExecution execution)
    {
        try
        {
            execution.execute(new BackendTestEnvironment("http://localhost:8080/konditor", "user", "user"));
        }
        catch (AssertionError assertionError)
        {
            throw assertionError;
        }
        catch (Exception cause)
        {
            throw new AssertionError("execute error", cause);
        }
    }

}
