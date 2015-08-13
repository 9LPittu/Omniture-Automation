package com.jcrew.page;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BillingPage {

    private final WebDriver driver;
    private Faker faker = new Faker();

    @FindBy(id = "creditCardNumber")
    WebElement creditCardNumber;

    @FindBy(id = "securityCode")
    WebElement securityCode;

    @FindBy(id = "expirationMonth")
    WebElement expirationMonth;

    @FindBy(id = "expirationYear")
    WebElement expirationYear;

    @FindBy(id = "nameOnCard")
    WebElement nameOnCard;

    @FindBy(id = "emailReceipt")
    WebElement emailReceipt;

    @FindBy(id = "creditDebitPayment")
    WebElement creditDebitPayment;

    @FindBy(id = "address-1")
    WebElement billingShippingAddressEqual;

    @FindBy(id = "billing-options-submit")
    WebElement billingOptionsSubmit;

    public BillingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void fill_required_payment_data() {

        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(creditCardNumber));

        creditCardNumber.sendKeys("4111-1111-1111-1111");
        securityCode.sendKeys("485");

        Select expirationMonthElement = new Select(expirationMonth);
        expirationMonthElement.selectByVisibleText("12");


        Select expirationYearElement = new Select(expirationYear);
        expirationYearElement.selectByVisibleText("2024");

        nameOnCard.sendKeys(faker.letterify(faker.name().fullName()));
        emailReceipt.sendKeys(faker.internet().emailAddress());

    }

    public boolean isCreditDebitPayment() {
        return creditDebitPayment.isSelected();
    }

    public boolean isBillingAndShippingAddressEqual() {
        return billingShippingAddressEqual.isSelected();
    }

    public void submit_form() {
        billingOptionsSubmit.click();
    }
}
