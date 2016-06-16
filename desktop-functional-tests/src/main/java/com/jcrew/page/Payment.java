package com.jcrew.page;

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

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class Payment {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Payment.class);
    private final WebDriverWait wait;

    @FindBy(id = "payment_page")
    private WebElement paymentPage;
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
    @FindBy(id = "billing-options-submit")
    private WebElement submitButton;
    @FindBy(id = "emailReceipt")
    private WebElement emailReceipt;
    @FindBy(id = "credit-card-billing")
    private WebElement creditCardBilling;



    public Payment(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(paymentPage));
    }

    public boolean isBillingPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        wait.until(ExpectedConditions.visibilityOf(creditCardBilling));
        return creditCardBilling.isDisplayed();
    }


    public void fillPayment() {
        Util.waitForPageFullyLoaded(driver);
        TestDataReader testDataReader = TestDataReader.getTestDataReader();

        creditCardNumber.sendKeys(testDataReader.getData("card.number"));
        securityCode.sendKeys(testDataReader.getData("card.cvv"));

        Select month = new Select(expirationMonth);
        month.selectByVisibleText(testDataReader.getData("card.month"));

        Select year = new Select(expirationYear);
        year.selectByVisibleText(testDataReader.getData("card.year"));

        nameOnCard.sendKeys(testDataReader.getData("card.name"));
        emailReceipt.sendKeys(testDataReader.getData("card.email"));

    }

    public void submitPayment() {
        submitButton.click();
        Util.waitForPageFullyLoaded(driver);
    }


}
