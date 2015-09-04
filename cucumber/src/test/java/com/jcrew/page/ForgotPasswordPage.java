package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by 9hvenaga on 9/3/2015.
 */
public class ForgotPasswordPage {

    @FindBy(id = "forgotPassword")
    private WebElement forgotPasswordForm;

    public ForgotPasswordPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public boolean isForgotPasswordPage() {
        return forgotPasswordForm.isDisplayed();
    }
}
