package com.jcrew.page;

import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaypalLogin extends PageObject{
	
    public PaypalLogin(WebDriver driver){
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void submitPaypalCredentials(String email, String password){
    	
    	driver.switchTo().frame("injectedUl");
    	
    	WebElement passwordSection = driver.findElement(By.id("passwordSection"));
    	
    	WebElement emailElement = passwordSection.findElement(By.id("email"));
    	emailElement.sendKeys(email);
    	logger.info("Entered paypal email address: {}", email);
    	
    	WebElement passwordElement = passwordSection.findElement(By.id("password"));
    	passwordElement.sendKeys(password);
    	logger.info("Entered paypal password: {}", password);
    	
    	WebElement loginButton = passwordSection.findElement(By.xpath("following-sibling::div[2]/button"));
    	loginButton.click();
    	
    	driver.switchTo().defaultContent();
    	
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
    }    
}
