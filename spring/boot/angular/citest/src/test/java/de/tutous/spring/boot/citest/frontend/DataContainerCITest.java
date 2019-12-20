package de.tutous.spring.boot.citest.frontend;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;

import de.tutous.spring.boot.citest.frontend.FrontendTestManager;
import de.tutous.spring.boot.citest.frontend.SeleniumTest;

public class DataContainerCITest
{

    private static Map<String, String> values = new HashMap<String, String>();
    static
    {
        values.put("name", "DC " + RandomString.make(4));
        values.put("description", "...");
        values.put("type", "ZDC");
        values.put("diagnosticKwp", "UDS");
        values.put("generation", "2019/50");
        values.put("partNumber", "0123456789");
        values.put("diagnosticBus", "CAN");
        values.put("diagnosticAddress", "1A2B");
        values.put("transportProtocol", "TP2_0");
        values.put("vehicleClasses", "VC1");
    }

    @Test
    public void testCreateDC()
    {

        FrontendTestManager.execute(environment -> {

            SeleniumTest seleniumTest = new SeleniumTest(environment.getWebDriver(), environment.getBaseUrl());

            seleniumTest.executeToDcCreate();
            seleniumTest.sendKeysToElements(values);
            // select file
            URL fileUrl = DataContainerCITest.class.getResource("/doors/doors-export1.xml");
            seleniumTest.sendFile(fileUrl.toURI().getPath().substring(1));
            seleniumTest.clickToElement("save");
            seleniumTest.delay(2500);
            
        });

    }

    @Test
    public void testUpateDC()
    {

        FrontendTestManager.execute(environment -> {

            SeleniumTest seleniumTest = new SeleniumTest(environment.getWebDriver(), environment.getBaseUrl());

            seleniumTest.selectDataContainer("mat-checkbox-2-input");
            seleniumTest.clickToElement("dc_edit");
            seleniumTest.sendKeysToElement("name", "DC " + RandomString.make(4));
            seleniumTest.sendKeysToElement("description", "update");
            // select file
            URL fileUrl = DataContainerCITest.class.getResource("/doors/doors-export2.xml");
            seleniumTest.sendFile(fileUrl.toURI().getPath().substring(1));
            seleniumTest.clickToElement("save");
            seleniumTest.delay(2500);
            
        });

    }

    @Test
    public void testDeleteDC()
    {

        FrontendTestManager.execute(environment -> {

            SeleniumTest seleniumTest = new SeleniumTest(environment.getWebDriver(), environment.getBaseUrl());

            seleniumTest.selectDataContainer("mat-checkbox-2-input");

            seleniumTest.clickToElement("dc_delete");
            seleniumTest.clickToElement("confirm");
            seleniumTest.delay(2500);

        });

    }

}
