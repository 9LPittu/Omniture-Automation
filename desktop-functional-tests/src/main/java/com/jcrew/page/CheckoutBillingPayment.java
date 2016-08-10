package com.madewell.page;

import com.madewell.pojo.User;
import com.madewell.utils.TestDataReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by nadiapaolagarcia on 5/9/16.
 */
public class CheckoutBillingPayment extends Checkout {

    @FindBy(id = "creditDebitCard")
    private WebElement cardForm;
    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumber;
    @FindBy(id = "securityCode")
    private WebElement secuirtyCode;
    @FindBy(id = "nameOnCard")
    private WebElement nameOnCard;
    @FindBy(id = "emailReceipt")
    private WebElement emailReceipt;
    @FindBy(id = "expirationMonth")
    private WebElement expirationMonth;
    @FindBy(id = "expirationYear")
    private WebElement expirationYear;

    public CheckoutBillingPayment(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(cardForm));
    }

    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");
        logger.debug("Billing address id: {}", bodyId);

        return bodyId.equals("billing");
    }

    public void fillPaymentMethod(String type) {
        TestDataReader testData = TestDataReader.getTestDataReader();
        User checkoutUSer = User.getFakeUser();

        creditCardNumber.sendKeys(testData.getData(type + ".card.number"));
        secuirtyCode.sendKeys(testData.getData(type + ".card.cvv"));

        Select month = new Select(expirationMonth);
        month.selectByVisibleText(testData.getData(type + ".card.month"));

        Select year = new Select(expirationYear);
        year.selectByVisibleText(testData.getData(type + ".card.year"));

        nameOnCard.sendKeys(checkoutUSer.getFirstName() + " " + checkoutUSer.getLastName());

        selectAddressFromList(cardForm);

        String addedCardInfo = testData.getData(type + ".card.month") + " / " + testData.getData(type + ".card.year") +
                checkoutUSer.getFirstName() + " " + checkoutUSer.getLastName();
        stateHolder.put("addedCard", addedCardInfo);
    }

    public void continueCheckout() {
        wait.until(ExpectedConditions.visibilityOf(cardForm));
        nextStep(cardForm);
    }

    public void editPayment() {
        secuirtyCode.sendKeys("156");
        nameOnCard.clear();
        nameOnCard.sendKeys("Edited Card Name");
    }
}
