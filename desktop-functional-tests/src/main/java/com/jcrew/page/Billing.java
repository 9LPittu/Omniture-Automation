package com.jcrew.page;

import com.github.javafaker.Faker;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class Billing {
	
	private static final String NAME_ON_CARD = "John Doe";
	private final WebDriver driver;
    private final Faker faker = new Faker();
    
    @FindBy(id = "credit-card-billing")
    private WebElement creditCardBilling;
    
    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumber;
    
    @FindBy(id = "securityCode")
    private WebElement securityCode;
    
    @FindBy(id = "expirationMonth")
    private WebElement expirationMonth;
    
    @FindBy(id = "expirationYear")
    private WebElement expirationYear;
    
    @FindBy(id = "nameOnCard")
    private WebElement nameOnCard;
    
    @FindBy(id = "emailReceipt")
    private WebElement emailReceipt;
    
    @FindBy(id = "billing-options-submit")
    private WebElement billingOptionsSubmit;
    
	public Billing(WebDriver driver) {
	    this.driver = driver;
	    PageFactory.initElements(this.driver, this);
	}

	public boolean isBillingPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(creditCardBilling));
        return creditCardBilling.isDisplayed();
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

        String strEmailAddress = faker.internet().emailAddress();
        strEmailAddress = strEmailAddress.replace("'","");
        emailReceipt.sendKeys(strEmailAddress);
    }
	
	 public void submit_form() {
	    Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(billingOptionsSubmit));
	    billingOptionsSubmit.click();
	    Util.waitForPageFullyLoaded(driver);
	  }
}
