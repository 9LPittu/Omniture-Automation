package com.jcrew.page;

import java.util.List;
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
import org.openqa.selenium.support.ui.Select;
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

    @FindBy(id = "sidecarRegisterFirstName")
    private WebElement firstNameField;

    @FindBy(id = "countryList")
    private WebElement countryListDropDown;

    @FindBy(id = "register-form__countryFlagImage" )
    private WebElement countryChooser;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void input_as_email(String email) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(emailInput));
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

    public String getErrorMessage(String fieldLabel) {
        List<WebElement> inputFormElements = driver.findElements(By.className("js-invalid-msg"));
        logger.info("list size{}", inputFormElements.size());
        String errmsg = "";
        switch (fieldLabel) {
            case "first name":
                errmsg = inputFormElements.get(0).getText();
                break;

            case "last name":
                errmsg = inputFormElements.get(1).getText();
                break;

            case "email":
                errmsg = inputFormElements.get(2).getText();
                break;

            case "password":
                errmsg = inputFormElements.get(3).getText();
                break;

        }

        return errmsg;

    }

    public String getEmailErrorMessage() {
        WebElement emailField =  driver.findElement(By.id("unregistered-email"));
        return emailField.findElement(By.className("js-invalid-msg")).getText();
    }



    public void enter_valid_username_and_password() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(signInButton));
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
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(registerSection));
        WebElement createNewAccountLink = registerSection.findElement(By.tagName("a"));
        createNewAccountLink.click();
    }

    public void click_forgot_password_link() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(signInForm));
        WebElement forgotPasswordLink = signInForm.findElement(By.linkText("I forgot my password!"));
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.elementToBeClickable(forgotPasswordLink));
        forgotPasswordLink.click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
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
    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkoutAsGuestButton));  	
    	checkoutAsGuestButton.click();
    }
    
    public void enterEmailAddressOnSignInPage(String emailAddress){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(emailAddressField));
        emailAddressField.sendKeys(emailAddress);
    }
    
    public void enterPasswordOnSignInPage(String password){
    	passwordField.sendKeys(password);
    }
    
    public void click_signInAndCheckOut(){
    	signInAndCheckOut.click();
    }

    public String getRegBenefitsCopyMsg() {
        return registerSection.findElement(By.className("unregistered__msg ")).getText();
    }

    public boolean isFieldWithMaxCharsAllowedDisplayed(String f, String maxchars) {
        String n = f.replaceAll("\\s","").trim();
        String fieldId = "sidecarRegister".concat(n);
        WebElement field = driver.findElement(By.id(fieldId));
        return field.isDisplayed()&& field.getAttribute("maxlength").equals(maxchars);
    }

    public void enter_input(String input, String field) {
        String n = field.replaceAll("\\s","").trim();
        String fieldId = "sidecarRegister".concat(n);
        driver.findElement(By.id(fieldId)).sendKeys(input);
    }

    public void click_create_an_account_button() {
        registerSection.findElement(By.className("js-btn-register")).click();
    }

    public boolean isCountryListBoxDisplyed() {
        return driver.findElement(By.id("countryList")).isDisplayed();
    }

    public String getDefaultCountrySelected() {
        Select select = new Select(countryListDropDown);
        return select.getFirstSelectedOption().getText();
    }

    public void select_and_verify_each_country() {
        Select select = new Select(countryListDropDown);
        int numOfCountries = select.getOptions().size();
        while(numOfCountries >= 1) {
            select.selectByIndex(numOfCountries - 1);
            String countryName = select.getFirstSelectedOption().getText();
            countryName = countryName.replaceAll("\\s", "");
            boolean flag = isCorrespondingCountryFlagDisplayed(countryName);
            logger.info("corresponding country flag is displayed:  {}", flag);
            numOfCountries--;

        }
    }

     public boolean isCorrespondingCountryFlagDisplayed(String countryName) {
        return countryChooser.getAttribute("class").contains(countryName);
    }

    }




