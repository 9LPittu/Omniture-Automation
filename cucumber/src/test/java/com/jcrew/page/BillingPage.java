package com.jcrew.page;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class BillingPage {

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
        PageFactory.initElements(driver, this);
    }

    public void fill_required_payment_data() {

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
