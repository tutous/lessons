package de.tutous.spring.boot.citest.backend;

public class BackendTestEnvironment
{

    private String userName;
    private String userPwd;
    private String baseUrl;

    public BackendTestEnvironment(String baseUrl, String userName, String userPwd)
    {
        super();
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getUserPwd()
    {
        return userPwd;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }
}
