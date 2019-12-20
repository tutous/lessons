package de.tutous.spring.boot.citest.frontend;

import static de.tutous.spring.boot.citest.frontend.KonditorPage.DATA_CONTAINER_DETAIL_VIEW;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import de.tutous.spring.boot.citest.frontend.FrontendTestManager;
import de.tutous.spring.boot.citest.frontend.SeleniumTest;

public class DataContainerDetailViewCITest
{

    @Test
    public void testDCDetailView()
    {

        FrontendTestManager.execute(environment -> {

            SeleniumTest seleniumTest = new SeleniumTest(environment.getWebDriver(), environment.getBaseUrl());

            seleniumTest.selectDataContainer("mat-checkbox-2-input");
            seleniumTest.clickToElement("dc_open");
            seleniumTest.delay(2500);
            
            WebElement dataContainerDetailView = environment.getWebDriver().findElement(By.id(DATA_CONTAINER_DETAIL_VIEW.toString()));
            Assertions.assertThat(dataContainerDetailView).isNotNull();
            
            seleniumTest.delay(2500);   
        });

    }

}
