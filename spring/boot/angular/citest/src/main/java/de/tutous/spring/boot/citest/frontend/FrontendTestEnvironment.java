package de.tutous.spring.boot.citest.frontend;

import org.openqa.selenium.WebDriver;

public class FrontendTestEnvironment
{

    private WebDriver driver;
    private String baseUrl;

    public FrontendTestEnvironment(WebDriver driver, String baseUrl)
    {
        super();
        this.driver = driver;
        this.baseUrl = baseUrl;
    }

    public WebDriver getWebDriver()
    {
        return driver;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }
}
