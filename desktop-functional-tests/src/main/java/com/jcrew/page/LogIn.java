package com.jcrew.page;

import com.google.common.base.Predicate;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class LogIn extends DriverFactory {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(LogIn.class);
    private final WebDriverWait wait;
    private final HeaderWrap header;
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final User fakeUser = User.getNewFakeUser();
    private final User knownUser = User.getUser();
    private final String signupForEmails_HK_Text = "J.Crew would like to use your contact details to send you " +
            "information about our fashion apparel, accessories, shoes, bags, jewelry, electronics, health and " +
            "beauty products and decoration and home goods, as well as upcoming collections, sales, promotions and " +
            "special events. We may also, for the same purpose, share your personal data with our group of companies " +
            "and affiliates. Your consent is required for us to do so.";
    private final String signupForEmails_ZZ_Text = "I want to receive marketing communications, including emails, " +
            "from J.Crew and its family of brands.";

    @FindBy(id = "sidecarUser")
    private WebElement sidecarUser;
    @FindBy(id = "sidecarPassword")
    private WebElement sidecarPassword;
    @FindBy(xpath = "//button[@class='btn--primary btn--signin js-button-submit']")
    private WebElement signInHereButton;
    @FindBy(id = "js-intl-email-optin")
    private WebElement internationalEmailOption;

    @FindBy(xpath = ".//form[@class='register-form']")
    private WebElement registerForm;

    @FindBy(className = "signin-form")
    private WebElement signInForm;

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
        logger.info("{}/{}", knownUser.getEmail(), knownUser.getPassword());
        sidecarUser.sendKeys(knownUser.getEmail());
        sidecarPassword.sendKeys(knownUser.getPassword());
        signInHereButton.click();
        header.reload();
        stateHolder.put("signedUser", knownUser);
    }

    public boolean hasExpectedPattern(String pattern) {
        Country country = (Country) stateHolder.get("context");

        return Util.countryContextURLCompliance(driver, country, pattern);
    }

    public boolean createAccountFormIsDisplayed() {
        Country country = (Country) stateHolder.get("context");
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        boolean expectedURL = Util.countryContextURLCompliance(driver, country, testDataReader.getData("page.login"));

        registerForm = wait.until(ExpectedConditions.visibilityOf(registerForm));
        return registerForm.isDisplayed() & expectedURL;
    }

    public void clickCreateAccount() {
        fakeUser.setCountry(getSelectedCountry());
        fakeUser.setCountryCode(getSelectedCountryValue());
        stateHolder.put("signedUser", fakeUser);

        createAccountFormIsDisplayed();
        createAnAccount = registerForm.findElement(By.tagName("button"));

        wait.until(ExpectedConditions.elementToBeClickable(createAnAccount));
        String url =  driver.getCurrentUrl();
        createAnAccount.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));

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

    public String getErrorMessage(final String field) {
        String errorMessage = "";
        WebElement fieldDiv = getNewUserField(field);

        if (fieldDiv != null) {
            wait.until(new Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver driver) {
                    return hasErrorMessage(field);
                }
            });
            WebElement spanErrorMessage = fieldDiv.findElement(By.xpath(".//span[@class='js-invalid-msg is-important']"));
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

    public boolean selectedCountryMatchesContext() {
        Country country = (Country) stateHolder.get("context");
        String selectedCountry = getSelectedCountry();

        return country.getName().equalsIgnoreCase(selectedCountry);
    }

    public String getSelectedCountryValue() {
        createAccountFormIsDisplayed();
        Select countrySelector = new Select(registerForm.findElement(By.id(countryId)));

        return countrySelector.getFirstSelectedOption().getAttribute("value");
    }

    private void setSelectedCountryByGroup(String group) {
        TestDataReader testData = TestDataReader.getTestDataReader();
        String country = testData.getRandomCountry(group);

        Select countrySelector = new Select(registerForm.findElement(By.id(countryId)));
        countrySelector.selectByValue(country);

        logger.info("Selected {} as country", getSelectedCountry());
        fakeUser.setCountry(getSelectedCountry());
    }

    public void fillField(String field, String value) {
        WebElement fieldDiv = getNewUserField(field);
        if (fieldDiv != null) {
            logger.info("Filling field {} with {}", field, value);
            if ("country".equals(field)) {
                setSelectedCountryByGroup(value);
            } else {
                WebElement fieldInput = fieldDiv.findElement(By.tagName("input"));
                fieldInput.sendKeys(value);
            }
        }
    }

    public void fillField(String field, boolean useFakeUser) {
        String value = "";
        User user;

        if (useFakeUser)
            user = fakeUser;
        else
            user = knownUser;

        switch (field) {
            case "first name":
                value = user.getFirstName();
                break;
            case "last name":
                value = user.getLastName();
                break;
            case "email":
                value = user.getEmail();
                break;
            case "password":
                value = user.getPassword();
                break;
            case "country":
                value = user.getCountry();
                break;
            default:
                logger.error("The field is not recognized in the new account form in the log in page");
                break;
        }

        fillField(field, value);
    }

    public boolean hasIntlEmailOptMessage() {
        boolean result = false;
        List<WebElement> optMessageDiv = internationalEmailOption.findElements(By.id("signupForEmailsContainer"));

        if (optMessageDiv.size() == 1) {
            String messageText = optMessageDiv.get(0).getText();
            if ("HK".equals(getSelectedCountryValue()))
                result = messageText.equals(signupForEmails_HK_Text);
            else
                result = messageText.equals(signupForEmails_ZZ_Text);
        }

        return result;

    }

    public boolean checkIntlEmailOptMessageForContext() {
        boolean result = false;
        Country country = (Country) stateHolder.get("context");
        String country_code = country.getCountry();

        List<WebElement> optMessageDiv = internationalEmailOption.findElements(By.id("signupForEmailsContainer"));

        if (optMessageDiv.size() == 1) {
            result = !"US".equals(country_code);

            String messageText = optMessageDiv.get(0).getText();
            if ("HK".equals(country_code))
                result &= messageText.equals(signupForEmails_HK_Text);
            else
                result &= messageText.equals(signupForEmails_ZZ_Text);

        } else if (optMessageDiv.size() == 0) {
            result = "US".equals(country_code);
        }

        return result;

    }

    public void setSelectedCountryByValue(String value) {
        Select countrySelector = new Select(registerForm.findElement(By.id(countryId)));

        if ("random".equalsIgnoreCase(value)) {
            WebElement options = Util.randomIndex(countrySelector.getOptions());
            value = options.getAttribute("value");
        } else if ("other".equalsIgnoreCase(value)) {
            WebElement options = Util.randomIndex(countrySelector.getOptions());
            value = options.getAttribute("value");

            while (value.equals("US") || value.equals("UK")) {
                options = Util.randomIndex(countrySelector.getOptions());
                value = options.getAttribute("value");
            }

        }

        countrySelector.selectByValue(value);
    }

    public void click_forgot_password_link() {
        wait.until(ExpectedConditions.visibilityOf(signInForm));
        WebElement forgotPasswordLink = signInForm.findElement(By.linkText("I forgot my password!"));
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink));

        forgotPasswordLink.click();

    }

}
