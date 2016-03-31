package com.jcrew.page;

import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class LogIn extends DriverFactory {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(HeaderWrap.class);
    private final WebDriverWait wait;
    private final HeaderWrap header;
    private final User fakeUser = User.getFakeUser();
    private final String pricebookCountries[] = {"UK", "CA", "HK", "FR"};

    @FindBy(id = "sidecarUser")
    private WebElement sidecarUser;
    @FindBy(id = "sidecarPassword")
    private WebElement sidecarPassword;
    @FindBy(xpath = "//button[@class='btn--primary btn--signin js-button-submit']")
    private WebElement signInHereButton;

    @FindBy(xpath = ".//form[@class='register-form']")
    private WebElement registerForm;
    private final String firstNameId = "unregistered-firstName";
    private final String lastNameId = "unregistered-lastName";
    private final String emailId = "unregistered-email";
    private final String passwordId = "registered-password";
    private final String countryId = "countryList";
    private WebElement createAnAccount;

    public LogIn(WebDriver driver) {
        this.driver = driver;
        header = new HeaderWrap(driver);
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.elementToBeClickable(signInHereButton));
    }

    public void signIn() {
        User user = User.getUser();
        logger.info("{}/{}", user.getEmail(), user.getPassword());
        sidecarUser.sendKeys(user.getEmail());
        sidecarPassword.sendKeys(user.getPassword());
        signInHereButton.click();
        header.reload();
    }

    public boolean createAccountFormIsDisplayed() {
        registerForm = wait.until(ExpectedConditions.visibilityOf(registerForm));
        return registerForm.isDisplayed();
    }

    public void clickCreateAccount() {
        createAccountFormIsDisplayed();
        createAnAccount = registerForm.findElement(By.tagName("button"));
        createAnAccount.click();
    }

    private WebElement getNewUserField(String field) {
        createAccountFormIsDisplayed();
        WebElement fieldDiv = null;

        switch (field) {
            case "first name":
                fieldDiv = registerForm.findElement(By.id(firstNameId));
                break;
            case "last name":
                fieldDiv = registerForm.findElement(By.id(lastNameId));
                break;
            case "email":
                fieldDiv = registerForm.findElement(By.id(emailId));
                break;
            case "password":
                fieldDiv = registerForm.findElement(By.id(passwordId));
                break;
            case "country":
                fieldDiv = registerForm;
                break;
            default:
                logger.error("The field is not recognized as an input in the new account form in the log in page");
                break;
        }

        return fieldDiv;
    }

    public String getErrorMessage(String field) {
        String errorMessage = "";
        WebElement fieldDiv = getNewUserField(field);

        if (fieldDiv != null) {
            WebElement spanErrorMessage = fieldDiv.findElement(By.xpath(".//span[@class='js-invalid-msg is-important']"));
            wait.until(ExpectedConditions.visibilityOf(spanErrorMessage));
            errorMessage = spanErrorMessage.getText();
        }

        return errorMessage;
    }

    public boolean hasErrorMessage(String field) {
        boolean isInvalid = false;
        WebElement fieldDiv = getNewUserField(field);

        if (fieldDiv != null) {
            WebElement fieldInput = fieldDiv.findElement(By.tagName("input"));
            isInvalid = fieldInput.getAttribute("class").contains("is-invalid");
        }

        return isInvalid;
    }

    public String getSelectedCountry() {
        createAccountFormIsDisplayed();
        Select countrySelector = new Select(registerForm.findElement(By.id(countryId)));

        return countrySelector.getFirstSelectedOption().getText();
    }

    private void setSelectedCountry(String group) {
        Select countrySelector = new Select(registerForm.findElement(By.id(countryId)));
        int index;

        if("US".equals(group)) {
            countrySelector.selectByValue("US");
        } else if ("PRICEBOOK".equals(group)){
            index = Util.randomIndex(pricebookCountries.length);
            countrySelector.selectByIndex(index);
        } else if ("NON-PRICEBOOK".equals(group)){
            WebElement options = Util.randomIndex(countrySelector.getOptions());

            while(!isNonPricebookCountry(options.getAttribute("value"))){
                options = Util.randomIndex(countrySelector.getOptions());
            }

            String country = options.getText();
            countrySelector.selectByValue(options.getAttribute("value"));
            logger.info("Selected {} as country", country);
            fakeUser.setCountry(country);
        }

    }

    private boolean isNonPricebookCountry(String text){
        List<String> otherCountries = new ArrayList<>(Arrays.asList(pricebookCountries));
        otherCountries.add("US");

        return !otherCountries.contains(text);
    }

    public void fillField(String field, String value) {
        WebElement fieldDiv = getNewUserField(field);
        if (fieldDiv != null) {
            logger.info("Filling field {} with {}", field, value);
            if ("country".equals(field)) {
                setSelectedCountry(value);
            } else {
                WebElement fieldInput = fieldDiv.findElement(By.tagName("input"));
                fieldInput.sendKeys(value);
            }
        }
    }

    public void fillField(String field) {
        String value = "";

        switch (field) {
            case "first name":
                value = fakeUser.getFirstName();
                break;
            case "last name":
                value = fakeUser.getLastName();
                break;
            case "email":
                value = fakeUser.getEmail();
                break;
            case "password":
                value = fakeUser.getPassword();
                break;
            case "country":
                value = fakeUser.getCountry();
                break;
            default:
                logger.error("The field is not recognized in the new account form in the log in page");
                break;
        }

        fillField(field, value);
    }

}
