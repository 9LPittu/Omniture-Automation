package com.jcrew.page;

import com.jcrew.util.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoginPage {

    @FindBy(id = "sidecarUser")
    private WebElement emailInput;

    @FindBy(id = "sidecarPassword")
    private WebElement passwordInput;

    @FindBy(className = "btn--signin")
    private WebElement signInButton;

    @FindBy(className = "js-invalid-msg")
    private WebElement invalidSignInMessage;

    @FindBy(id = "sidecarRemember")
    private WebElement keepMeSignedInCheckBox;

    @FindBy(id = "main_inside")
    private WebElement welcomeName;

    @FindBy(id = "c-nav__userpanel")
    private WebElement myaccountRef;

    @FindBy(css = "#c-nav__userpanel > a")
    private WebElement myaccountlink;

    private final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
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
    public void enter_valid_username_and_password() {
        PropertyReader reader = PropertyReader.getPropertyReader();
        input_as_email(reader.readProperty("checkout.signed.in.username"));
        input_as_password(reader.readProperty("checkout.signed.in.password"));
    }

    public boolean isCheckBoxEnabled() {
        return keepMeSignedInCheckBox.isEnabled();
    }

    public boolean isMyAccountLinkPresent() {

        return myaccountlink.isDisplayed();
    }

    public boolean isMyAccountInDesktop() {
        WebElement diffmyaccount = new WebDriverWait(driver, 10).
                until(ExpectedConditions.elementToBeClickable(By.id("c-header__userpanelrecognized")));
        diffmyaccount.click();
        return diffmyaccount.findElement(By.xpath("//*[@id='c-nav__userpanel']/dl/dd[2]/a")).isDisplayed();

    }

    public void click_my_account_link() {
        myaccountlink.click();
    }

    public void disable_checkbox() {
        keepMeSignedInCheckBox.click();
    }

}

