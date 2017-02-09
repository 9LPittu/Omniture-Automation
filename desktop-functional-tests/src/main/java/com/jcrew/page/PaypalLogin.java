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
    	
    	int cntr = 0;
    	do{
    		WebElement emailElement = passwordSection.findElement(By.id("email"));
    		emailElement.clear();
        	emailElement.sendKeys(email);
        	if(emailElement.getText().equalsIgnoreCase(email)){
        		logger.info("Entered paypal email address: {}", email);
        		break;
        	}else{
        		cntr++;
        	}        	
    	}while(cntr<=2);
    	
    	cntr = 0;
    	do{
    		WebElement passwordElement = passwordSection.findElement(By.id("password"));
    		passwordElement.clear();
    		passwordElement.sendKeys(password);
        	if(passwordElement.getText().equalsIgnoreCase(password)){
        		logger.info("Entered paypal password: {}", password);
        		break;
        	}else{
        		cntr++;
        	}
    	}while(cntr<=2);
    	
    	WebElement loginButton = passwordSection.findElement(By.xpath("following-sibling::div[2]/button"));
    	loginButton.click();
    	
    	driver.switchTo().defaultContent();
    	
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
    }
}
