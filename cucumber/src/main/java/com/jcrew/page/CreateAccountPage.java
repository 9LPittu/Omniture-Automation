package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateAccountPage {

    private final WebDriver driver;
    @FindBy(id = "firstName")
    private WebElement firstName;
    @FindBy(id = "lastName")
    private WebElement lastName;
    @FindBy(id = "emailAdd")
    private WebElement email;
    @FindBy(id = "emailAddConf")
    private WebElement confirmEmail;
    @FindBy(id = "passWord")
    private WebElement password;
    @FindBy(id = "passWordConf")
    private WebElement confirmPassword;
    @FindBy(id = "passwordHint")
    private WebElement passwordHint;
    @FindBy(id = "country")
    private WebElement country;
    @FindBy(className = "createAccount_startShopping")
    private WebElement createAccountLink;

    public CreateAccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void fill_account_data() {
        Util.waitForPageFullyLoaded(driver);
        firstName.sendKeys("test first name");
        lastName.sendKeys("test last name");
        long currentTimeInMillis = System.currentTimeMillis();
        email.sendKeys("test-" + currentTimeInMillis + "@example.org");
        confirmEmail.sendKeys("test-" + currentTimeInMillis + "@example.org");
        password.sendKeys("test1234");
        confirmPassword.sendKeys("test1234");
        passwordHint.sendKeys("test1234");

        Select expirationMonthElement = new Select(country);
        expirationMonthElement.selectByVisibleText("United States");

    }

    public void click_create_new_account() {
        createAccountLink.click();
    }
}
