package de.tutous.spring.boot.citest.frontend;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriverService.Builder;

public class FrontendTestManager
{

    private static final URL executionUrl = FrontendTestManager.class.getResource("/chromedriver/chromedriver.exe");

    public static void execute(FrontendTestExecution execution)
    {
        WebDriver webDriver = null;
        try
        {
            System.setProperty("webdriver.chrome.driver", executionUrl.getPath());
            Builder chromeDriverBuilder = new ChromeDriverService.Builder();
            chromeDriverBuilder.usingPort(8081);
            webDriver = new ChromeDriver(chromeDriverBuilder.build());
            execution.execute(new FrontendTestEnvironment(webDriver, "http://localhost:8080/konditor"));
        }
        catch (AssertionError assertionError)
        {
            throw assertionError;
        }
        catch (Exception cause)
        {
            throw new AssertionError("execute error", cause);
        }
        finally
        {
            if (java.util.Objects.nonNull(webDriver))
            {
                webDriver.quit();
            }
        }
    }

}
