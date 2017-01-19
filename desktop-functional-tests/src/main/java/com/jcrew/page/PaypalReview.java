package com.jcrew.page;

import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaypalReview extends PageObject{
	
	private WebElement continueButton;
	
    public PaypalReview(WebDriver driver){    	
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        PageFactory.initElements(driver, this);
        
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
        continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='confirmButtonTop']")));
    }
    
    public void clickContinue(){
    	wait.until(ExpectedConditions.elementToBeClickable(continueButton));
    	continueButton.click();
    }    
}
