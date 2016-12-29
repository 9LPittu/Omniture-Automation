package com.jcrew.page;

import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaypalLogin extends PageObject{
	
	@FindBy(xpath="//section[@id='loginSection']")
	private WebElement loginSection;
	
	@FindBy(xpath="//div[@id='login_emaildiv']/descendant::input[@id='email1']")
	private WebElement paypalEmail;
	
	@FindBy(xpath="//div[@id='login_passworddiv']/descendant::input[@id='password']")
	private WebElement paypalPassword;
	
	@FindBy(xpath = "//*[contains(@id, 'Login')]")
	private WebElement paypalLogin;
	
    public PaypalLogin(WebDriver driver){
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void submitPaypalCredentials(String email, String password){
    	
    	driver.get(driver.getCurrentUrl());
    	
    	//wait.until(ExpectedConditions.visibilityOf(paypalEmail));
    	//paypalEmail.clear();
    	paypalEmail = loginSection.findElement(By.xpath("//div[@id='login_emaildiv']/descendant::input[@id='email']"));
    	paypalEmail.sendKeys(email);
    	logger.info("Entered paypal email address: {}", email);
    	
    	//paypalPassword.clear();
    	paypalPassword = driver.findElement(By.xpath("//div[@id='login_passworddiv']/descendant::input[@id='password']"));
    	paypalPassword.sendKeys(password);
    	logger.info("Entered paypal password: {}", password);
    	
    	paypalLogin.click();
    }    
}
