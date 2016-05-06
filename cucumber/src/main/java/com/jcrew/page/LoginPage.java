package com.jcrew.page;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.UsersHub;
import com.jcrew.util.Util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {
	
	private final StateHolder stateHolder = StateHolder.getInstance();
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

    @FindBy(xpath = ".//*[@id='frmGuestCheckOut']/descendant::a[text()='Check Out as a Guest']")
    private WebElement checkoutAsGuestButton;

    @FindBy(id = "loginUser")
    private WebElement emailAddressField;

    @FindBy(id = "loginPassword")
    private WebElement passwordField;

    @FindBy(css = ".button-general.button-submit")
    private WebElement signInAndCheckOut;

    @FindBy(id = "main_inside")
    private WebElement myAccountContainer;

    @FindBy(id = "sidecarRegisterFirstName")
    private WebElement firstNameField;

    @FindBy(id = "countryList")
    private WebElement countryListDropDown;

    @FindBy(id = "register-form__countryFlagImage")
    private WebElement countryChooser;

    Faker faker = new Faker();

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
        WebElement emailInvalidMsg = Util.createWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(By.className("js-invalid-msg")));
        return emailInvalidMsg.getText();
    }

    public void enter_valid_username_and_password() throws ClassNotFoundException, SQLException {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(signInButton));
        PropertyReader reader = PropertyReader.getPropertyReader();
        
        String username = null;
        String password = null;
        if(reader.getProperty("environment").equalsIgnoreCase("ci")){
        	username = reader.getProperty("checkout.signed.in.username");
        	password = reader.getProperty("checkout.signed.in.password");
        }
        else{
        	UsersHub userHub = new UsersHub();
        	userHub.retrieveUserCredentialsFromDBAndStoreInMap();
        	username = (String) stateHolder.get("sidecarusername");
        	password = (String) stateHolder.get("sidecaruserpassword");
        }
        
        input_as_email(username);
        input_as_password(password);
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
        String url = driver.getCurrentUrl();
        forgotPasswordLink.click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
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

    public void clickCheckoutAsGuest() throws InterruptedException {
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkoutAsGuestButton));
        checkoutAsGuestButton.click();
    }

    public void enterEmailAddressOnSignInPage(String emailAddress) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(emailAddressField));
        if (emailAddress.equalsIgnoreCase("any")) {
            PropertyReader reader = PropertyReader.getPropertyReader();
            emailAddress = reader.getProperty("checkout.signed.in.username");
        }
        emailAddressField.sendKeys(emailAddress);
    }

    public void enterPasswordOnSignInPage(String password) {
        if (password.equalsIgnoreCase("corresponding")) {
            PropertyReader reader = PropertyReader.getPropertyReader();
            password = reader.getProperty("checkout.signed.in.password");
        }
        passwordField.sendKeys(password);
    }

    public void click_signInAndCheckOut() {
        signInAndCheckOut.click();
    }

    public String getRegBenefitsCopyMsg() {
        return registerSection.findElement(By.className("unregistered__msg ")).getText();
    }

    public boolean isFieldWithMaxCharsAllowedDisplayed(String f, String maxchars) {
        String n = f.replaceAll("\\s", "").trim();
        String fieldId = "sidecarRegister".concat(n);
        WebElement field = driver.findElement(By.id(fieldId));
        return field.isDisplayed() && field.getAttribute("maxlength").equals(maxchars);
    }

    public void enter_input(String input, String field) {
        String n = field.replaceAll("\\s", "").trim();
        String fieldId = "sidecarRegister".concat(n);
        String fieldInput = "";
        switch (input) {
            case "random first name":
                fieldInput = faker.name().firstName();
                break;
            case "random last name":
                fieldInput = faker.name().lastName();
                break;
            case "random email":
                fieldInput = faker.internet().emailAddress().replace("'", "");
                break;
            case "random password":
                fieldInput = faker.name().fullName().replaceAll("\\s", "");
                logger.info("password generated is : {}", fieldInput);
                break;
            default:
                fieldInput = input;
        }
        driver.findElement(By.id(fieldId)).sendKeys(fieldInput);
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

    public void select_each_country_and_verify_corresponding_flag_is_displayed() {
        Select select = new Select(countryListDropDown);
        int numOfCountries = select.getOptions().size();
        while (numOfCountries >= 1) {
            select.selectByIndex(numOfCountries - 1);
            String countryName = select.getFirstSelectedOption().getText();
            countryName = countryName.replaceAll("\\s", "");
            boolean flag = isCorrespondingCountryFlagDisplayed(countryName);
            logger.info("corresponding " + countryName + " flag is displayed:  {}", flag);
            numOfCountries--;

        }
    }

    public boolean select_top10_country_and_verify_corresponding_flag_is_displayed() {
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        String top10countriesString = testDataReader.getData("countries.by.name");
        String  top10countries[] = top10countriesString.split(";");

        Select select = new Select(countryListDropDown);
        boolean flag = true;

        for (String country : top10countries) {
            select.selectByVisibleText(country);
            country = country.replaceAll("\\s", "").toLowerCase();
            flag &= isCorrespondingCountryFlagDisplayed(country);
            logger.info("corresponding " + country + " flag is displayed:  {}", flag);
        }
        return flag;
    }

    public void select_any_random_country() {
        Select select = new Select(countryListDropDown);
        int size = select.getOptions().size();
        int randomIndex = Util.randomIndex(size);
        select.selectByIndex(randomIndex);
    }

    public boolean isCorrespondingCountryFlagDisplayed(String countryName) {
        String countryChooserClass = countryChooser.getAttribute("class");
        countryChooserClass = countryChooserClass.toLowerCase();

        logger.debug("Flag class: {}", countryChooserClass);

        return countryChooserClass.contains(countryName);
    }

    public boolean isOptCheckBoxDisplayed() {
        Select select = new Select(countryListDropDown);
        select.selectByVisibleText("United States");
        try {
            WebElement chkbox = driver.findElement(By.id("sidecarRegisterCheckbox"));
            return chkbox.isDisplayed();
        } catch (NoSuchElementException ne) {
            logger.info("check box is not present");
            return false;
        }
    }

}




