package de.tutous.spring.boot.citest.frontend;

import static de.tutous.spring.boot.citest.frontend.KonditorPage.DATA_CONTAINER_CREATE;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTest
{

    private WebDriver webDriver;
    private String baseUrl;

    public SeleniumTest(WebDriver webDriver, String baseUrl)
    {
        this.webDriver = webDriver;
        this.baseUrl = baseUrl;
    }

    public void executeToDcCreate()
    {
        delay(2500);
        webDriver.get(baseUrl);
        WebElement btnDcCreate = webDriver.findElement(By.id("dc_create"));
        btnDcCreate.click();
        WebElement dataContainerEdit = webDriver.findElement(By.id(DATA_CONTAINER_CREATE.toString()));
        Assertions.assertThat(dataContainerEdit).isNotNull();
    }

    public void sendKeysToElement(String element, CharSequence... keysToSend)
    {
        delay(100);
        WebElement webElement = webDriver.findElement(By.id(element));
        Assertions.assertThat(webElement).isNotNull();
        String type = webElement.getAttribute("type");
        if ("text".equals(type))
        {
            webElement.clear();
        }
        webElement.sendKeys(keysToSend);
    }

    public void sendKeysToElements(Map<String, String> values)
    {
        values.entrySet().forEach(e -> sendKeysToElement(e.getKey(), e.getValue()));
    }

    public void clickToElement(String element)
    {
        delay(500);
        WebElement webElement = webDriver.findElement(By.id(element));
        Assertions.assertThat(webElement).isNotNull();
        Assertions.assertThat(webElement.isEnabled()).isEqualTo(true);
        webElement.click();
    }

    public void delay(int time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            // ignore
        }
    }

    public void selectDataContainer(String id)
    {
        delay(2500);
        webDriver.get(baseUrl);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 5);
        ExpectedCondition<WebElement> condition = visibilityOfElementLocated(By.id(id));
        WebElement dcInputCheckBox = webDriverWait.until(condition);
        Actions actions = new Actions(webDriver);
        actions.moveToElement(dcInputCheckBox).build();
        dcInputCheckBox.sendKeys(" ");
    }

    public void sendFile(String localFilePath)
    {
        WebElement fileInput = webDriver.findElement(By.xpath("//input[@type=\"file\"]"));
        fileInput.sendKeys(localFilePath);
    }

}
