package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "sidecarUser")
    private WebElement emailInput;

    @FindBy(id = "sidecarPassword")
    private WebElement passwordInput;

    @FindBy(className = "btn--signin")
    private WebElement signInButton;

    @FindBy(className = "js-invalid-msg")
    private WebElement invalidSignInMessage;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void input_as_email(String email) {
        emailInput.sendKeys(email);
    }

    public void input_as_password(String password) {
        passwordInput.sendKeys(password);
    }

    public void click_sign_in_button() {
        signInButton.click();
    }

    public String getSignInErrorMessage() {
        return invalidSignInMessage.getText();
    }
}
