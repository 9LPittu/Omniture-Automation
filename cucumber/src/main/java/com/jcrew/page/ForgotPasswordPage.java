package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ForgotPasswordPage {

    @FindBy(id = "forgotPassword")
    private WebElement forgotPasswordForm;

    public ForgotPasswordPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isForgotPasswordPage() {
    	try{
    		return forgotPasswordForm.isDisplayed();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
}
