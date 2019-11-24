package de.tutous.test.selenium;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class ChromeDriverFirstTest {

	private WebDriver driver;

	@BeforeEach
	public void createDriver() {
		driver = new ChromeDriver();
	}

	@AfterEach
	public void quitDriver() {
		driver.quit();
	}

	@Test
	public void testGoogleSearch() {
		driver.get("http://localhost:4200/");
		WebElement itemOne = driver.findElement(By.id("item-one"));
		Assertions.assertThat(itemOne.getText()).isEqualTo("Item 1");
	}
}
