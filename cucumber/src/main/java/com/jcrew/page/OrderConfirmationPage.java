package com.jcrew.page;

import java.util.concurrent.TimeUnit;

import com.jcrew.util.Util;

import org.junit.rules.Timeout;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderConfirmationPage {
	
	private final WebDriver driver;
	private Logger logger = LoggerFactory.getLogger(OrderConfirmationPage.class);
	private int methodExecutionCntr = 1;

    @FindBy(id = "confirmation-number")
    private WebElement confirmationNumber;
    
    @FindBy(className="order-number")
	private WebElement orderNumber;

    public OrderConfirmationPage(WebDriver driver) {
    	this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isOrderConfirmationPage() {
    	
    	boolean blnFlag = false;    	
    	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    	
    	while(methodExecutionCntr<=5){
    		try{
        		if(confirmationNumber.isDisplayed()){
        			blnFlag = true;
        			break;
        		}
        	}
        	catch(Exception e){
        		methodExecutionCntr++;        		
        		isOrderConfirmationPage();
        	}
    	}
    	
    	driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    	
        return blnFlag;
    }
    
    public boolean verifyOrderNumberGenerated(){
		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(orderNumber));
		 
		 if(orderNumber.isDisplayed()){
			 logger.debug("Order number is generated. Order number is {}", orderNumber.getText().trim());
		 }
		 else{
			 logger.debug("Order number is not generated. Order number is {}");
		 }
		 
		 return orderNumber.isDisplayed();
	 }
}