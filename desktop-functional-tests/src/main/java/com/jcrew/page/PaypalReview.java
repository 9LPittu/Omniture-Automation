package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaypalReview extends PageObject{
	
	@FindBy(xpath = "//input[contains(@id, 'Continue') or @id = 'confirmButtonTop')]")
	private WebElement continueButton;
	
    public PaypalReview(WebDriver driver){
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        PageFactory.initElements(driver, this);
        
        wait.until(ExpectedConditions.visibilityOf(continueButton));
    }
    
    public void clickContinue(){
    	continueButton.click();
    }    
}
