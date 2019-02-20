package com.jcrew.page;

import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaypalLogin extends PageObject {

	public PaypalLogin(WebDriver driver) {
		super(driver);
		Util.waitForPageFullyLoaded(driver);
		PageFactory.initElements(driver, this);
	}

	public void submitPaypalCredentials(String email, String password) {
		Util.wait(4000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
		/*Util.wait(2000);
		WebElement login = driver.findElement(By.xpath("//a[@class='btn full ng-binding']"));
		login.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
		Util.wait(2000);*/
		WebElement emailElement = driver.findElement(By.id("email"));
		emailElement.clear();
		emailElement.sendKeys(email);
		logger.info("Entered paypal email address: {}", email);
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
		Util.wait(1000);
		driver.findElement(By.xpath("//button[@id='btnNext']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
		Util.wait(1000);
		WebElement passwordElement = driver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys(password);
		logger.info("Entered paypal password: {}", password);
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
		Util.wait(1000);
		WebElement loginButton = driver.findElement(By.xpath("//button[@id='btnLogin']"));
		loginButton.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
	}
}
