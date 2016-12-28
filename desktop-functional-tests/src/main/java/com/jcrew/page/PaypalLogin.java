package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaypalLogin extends PageObject{
	
	@FindBy(name="login_email")
	private WebElement paypalEmail;
	
	@FindBy(name="login_password")
	private WebElement paypalPassword;
	
	@FindBy(xpath = "//input[contains(@id, 'Login')]")
	private WebElement paypalLogin;
	
    public PaypalLogin(WebDriver driver){
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        PageFactory.initElements(driver, this);
        
        Util.createWebDriverWait(driver, 180).until(ExpectedConditions.visibilityOf(paypalEmail));
    }
    
    public void submitPaypalCredentials(String email, String password){
    	paypalEmail.clear();
    	paypalEmail.sendKeys(email);
    	logger.info("Entered paypal email address: {}", email);
    	
    	paypalPassword.clear();
    	paypalPassword.sendKeys(password);
    	logger.info("Entered paypal password: {}", password);
    	
    	paypalLogin.click();
    }    
}
