package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutSelectionPage {

    @FindBy(xpath = ".//*[@id='frmGuestCheckOut']/a")
    private WebElement checkoutAsGuestLink;

    @FindBy(id = "loginUser")
    private WebElement usernameInputField;

    @FindBy(id = "loginPassword")
    private WebElement passwordInputField;

    @FindBy(css = "#userSignIn > .button-submit")
    private WebElement signInAndCheckoutLink;

    public CheckoutSelectionPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void selects_to_checkout_as_guest() {

        checkoutAsGuestLink.click();
    }

    public void set_username_and_password(String username, String password) {
        usernameInputField.sendKeys(username);
        passwordInputField.sendKeys(password);
    }

    public void click_sign_in_and_checkout() {
        signInAndCheckoutLink.click();
    }
}
