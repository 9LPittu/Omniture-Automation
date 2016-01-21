package com.jcrew.page;

import java.util.concurrent.TimeUnit;

import com.jcrew.util.PropertyReader;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    private final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final WebDriver driver;
    
    @FindBy(id = "sidecarUser")
    private WebElement emailInput;
    @FindBy(id = "sidecarPassword")
    private WebElement passwordInput;
    @FindBy(tagName = "button")
    private WebElement signInButton;
    @FindBy(className = "js-invalid-msg")
    private WebElement invalidSignInMessage;
    @FindBy(id = "sidecarRemember")
    private WebElement keepMeSignedInCheckBox;
    @FindBy(id = "c-nav__userpanel")
    private WebElement myaccountRef;
    @FindBy(css = "#c-nav__userpanel > a")
    private WebElement myAccountLink;
    @FindBy(className = "signin-form")
    private WebElement signInForm;
    @FindBy(className = "c-signin-unregistered")
    private WebElement registerSection;
    
    @FindBy(xpath=".//*[@id='frmGuestCheckOut']/descendant::a[text()='Check Out as a Guest']")
    private WebElement checkoutAsGuestButton;
    
    @FindBy(id="loginUser")
    private WebElement emailAddressField;
    
    @FindBy(id="loginPassword")
    private WebElement passwordField;
    
    @FindBy(css=".button-general.button-submit")
    private WebElement signInAndCheckOut;
    
    @FindBy(id = "main_inside")
    private WebElement myAccountContainer;

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
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(signInButton));
        signInButton.click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myAccountContainer));
    }

    public String getSignInErrorMessage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(invalidSignInMessage));
        return invalidSignInMessage.getText();
    }

    public void enter_valid_username_and_password() {
        PropertyReader reader = PropertyReader.getPropertyReader();
        input_as_email(reader.getProperty("checkout.signed.in.username"));
        input_as_password(reader.getProperty("checkout.signed.in.password"));
    }

    public boolean isCheckBoxEnabled() {
        return keepMeSignedInCheckBox.isEnabled();
    }

    public boolean isMyAccountLinkForMobileDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myAccountLink));
        return myAccountLink.isDisplayed();
    }

    public boolean isMyAccountInDesktop() {
        WebElement myAccountLinkFromDesktop = getMyAccountLinkForDesktop();
        return myAccountLinkFromDesktop.isDisplayed();

    }

    private WebElement getMyAccountLinkForDesktop() {
        WebElement myAccountLinkMenu = Util.createWebDriverWait(driver).
                until(ExpectedConditions.elementToBeClickable(By.id("c-header__userpanelrecognized")));
        myAccountLinkMenu.click();

        return myAccountLinkMenu.findElement(By.xpath("//*[@id='c-nav__userpanel']/dl/dd[2]/a"));
    }

    public void click_my_account_link_mobile() {
        isMyAccountLinkForMobileDisplayed();
        myAccountLink.click();
    }

    public void disable_checkbox() {
        keepMeSignedInCheckBox.click();
    }

    public void focus_password_field() {
        passwordInput.sendKeys(" ");
    }

    public boolean isSignInButtonEnabled() {
        return signInButton.isEnabled();
    }

    public void click_my_account_link_desktop() {
        WebElement myAccountLinkFromDesktop = getMyAccountLinkForDesktop();
        myAccountLinkFromDesktop.click();
    }

    public void click_create_new_account() {
        WebElement createNewAccountLink = registerSection.findElement(By.tagName("a"));
        createNewAccountLink.click();
    }

    public void click_forgot_password_link() {
        WebElement forgotPasswordLink = signInForm.findElement(By.linkText("I forgot my password!"));
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.elementToBeClickable(forgotPasswordLink));
         forgotPasswordLink.click();
    }

    public boolean isPageLoaded() {
        boolean result;
        try {
            result = Util.createWebDriverWait(driver).until(
                    ExpectedConditions.elementToBeClickable(signInButton)).isDisplayed();
        } catch (StaleElementReferenceException sere) {

            result = isPageLoaded();
        }

        return result;
    }
    
    public void clickCheckoutAsGuest() throws InterruptedException{
    	
    	//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkoutAsGuestButton));  	
    	checkoutAsGuestButton.click();
    	
    	//driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public void enterEmailAddressOnSignInPage(String emailAddress){
    	//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(emailAddressField));
        emailAddressField.sendKeys(emailAddress);
    	//driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }
    
    public void enterPasswordOnSignInPage(String password){
    	passwordField.sendKeys(password);
    }
    
    public void click_signInAndCheckOut(){
    	signInAndCheckOut.click();
    }
}