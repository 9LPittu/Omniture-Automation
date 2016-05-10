package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ForgotPassword {

    @FindBy(id = "forgotPassword")
    private WebElement forgotPasswordForm;

    public ForgotPassword(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public boolean isForgotPasswordPage() {
        return forgotPasswordForm.isDisplayed();
    }
}