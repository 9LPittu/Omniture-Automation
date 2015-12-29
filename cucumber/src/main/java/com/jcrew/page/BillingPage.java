package com.jcrew.page;

import com.github.javafaker.Faker;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class BillingPage {

    private static final String NAME_ON_CARD = "John Doe";
    private final WebDriver driver;
    private final Faker faker = new Faker();

    @FindBy(id = "creditCardNumber")
    private
    WebElement creditCardNumber;

    @FindBy(id = "securityCode")
    private
    WebElement securityCode;

    @FindBy(id = "expirationMonth")
    private
    WebElement expirationMonth;

    @FindBy(id = "expirationYear")
    private
    WebElement expirationYear;

    @FindBy(id = "nameOnCard")
    private
    WebElement nameOnCard;

    @FindBy(id = "emailReceipt")
    private
    WebElement emailReceipt;

    @FindBy(id = "creditDebitPayment")
    private
    WebElement creditDebitPayment;

    @FindBy(id = "address-1")
    private
    WebElement billingShippingAddressEqual;

    @FindBy(id = "billing-options-submit")
    private
    WebElement billingOptionsSubmit;

    @FindBy(id = "credit-card-billing")
    private
    WebElement creditCardBilling;

    public BillingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void fill_required_payment_data() {

        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(creditCardNumber));

        creditCardNumber.sendKeys("4111-1111-1111-1111");
        securityCode.sendKeys("485");

        Select expirationMonthElement = new Select(expirationMonth);
        expirationMonthElement.selectByVisibleText("12");


        Select expirationYearElement = new Select(expirationYear);
        expirationYearElement.selectByVisibleText("2024");

        nameOnCard.sendKeys(NAME_ON_CARD);
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

    public boolean isBillingPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(creditCardBilling));
        return creditCardBilling.isDisplayed();
    }
    
    public void enterCreditCardDetails(String cardName){
    	TestDataReader testDataReader = new TestDataReader();
    	String creditCardDetails = testDataReader.getData(cardName);   	

    	//Enter credit card number
    	creditCardNumber.sendKeys(creditCardDetails.split(";")[0]);
    	
    	//Enter security code
    	securityCode.sendKeys(creditCardDetails.split(";")[1]);
    	
    	//select expiration month
    	Select expirationMonthElement = new Select(expirationMonth);
        expirationMonthElement.selectByVisibleText(creditCardDetails.split(";")[2]);

    	//select expiration year
        Select expirationYearElement = new Select(expirationYear);
        expirationYearElement.selectByVisibleText(creditCardDetails.split(";")[3]);
        
        //Enter name on card
        nameOnCard.sendKeys(creditCardDetails.split(";")[4]);
    }
    
    public void enterEmailAddressOnBillingPage(String emailAddress){
    	emailReceipt.sendKeys(emailAddress);
    }
}
