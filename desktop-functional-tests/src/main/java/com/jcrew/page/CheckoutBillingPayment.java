package com.jcrew.page;

import com.jcrew.pojo.Address;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import java.util.List;

import org.openqa.selenium.By;
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
    @FindBy(id = "securityCode")
    private WebElement securityCode;
    @FindBy(id = "billing-options-submit")
    private WebElement submitButton;    
    @FindBy(id = "main_cont")
    private WebElement main_cont;
    @FindBy(id = "containerBorderLeft")
    private WebElement leftContainer;
    @FindBy(id = "creditCardList")
    private WebElement creditCardList;
    
    private final HeaderWrap header;

    public CheckoutBillingPayment(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(cardForm));
        this.header = new HeaderWrap(driver);
    }

    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");
        logger.debug("Billing address id: {}", bodyId);

        return bodyId.equals("billing");
    }
    
    public boolean isPaymentMethodsPage() {
        wait.until(ExpectedConditions.visibilityOf(main_cont));
        WebElement myAccountBanner = main_cont.findElement(By.tagName("h2"));

        String bannerText = myAccountBanner.getText();
        wait.until(ExpectedConditions.visibilityOf(leftContainer));
        WebElement myOrdersTitle = leftContainer.findElement(By.className("myaccount_list"));

        Country country = (Country) stateHolder.get("context");
        boolean isTheExpectedURL = Util.countryContextURLCompliance(driver, country, "/account/payment_info.jsp");

        return "MY ACCOUNT".equalsIgnoreCase(bannerText) &&
                "My Payment Methods".equalsIgnoreCase(myOrdersTitle.getText()) &&
                isTheExpectedURL;
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
        String currentURl = driver.getCurrentUrl();
        submitButton.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURl)));
        Util.waitForPageFullyLoaded(driver);
    }
    
    private void fillNewCardData() {
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        WebElement newCardDiv = leftContainer.findElement(By.id("AddCreditCard"));
        wait.until(ExpectedConditions.visibilityOf(newCardDiv));

        WebElement cardNumber = newCardDiv.findElement(By.id("ccNumber"));
        cardNumber.sendKeys(testDataReader.getData("card.number"));

        WebElement exMonth = newCardDiv.findElement(By.id("eXmonth"));
        Select exMonthSelect = new Select(exMonth);
        exMonthSelect.selectByValue(testDataReader.getData("card.month"));

        WebElement exYear = newCardDiv.findElement(By.id("eXyear"));
        Select exYearSelect = new Select(exYear);
        exYearSelect.selectByVisibleText(testDataReader.getData("card.year"));

        User user = (User) stateHolder.get("signedUser");

        WebElement cardHolderName = newCardDiv.findElement(By.id("cardholderName"));
        cardHolderName.sendKeys(user.getFirstName() + " " + user.getLastName());
    }
    
    public void addNewPaymentMethod() {
        WebElement addNewCardButton = leftContainer.findElement(
                By.xpath(".//td[@class='paymentcopy']/input[@name='addANewCard']"));
        addNewCardButton.click();

        fillNewCardData();

        WebElement newCardDiv = leftContainer.findElement(By.id("AddCreditCard"));
        wait.until(ExpectedConditions.visibilityOf(newCardDiv));

        addNewBillingAdrress();

        WebElement submit = newCardDiv.findElement(By.name("addCreditCard"));
        submit.click();

        header.reload();
        Util.waitForPageFullyLoaded(driver);
    }

    public int getPaymentMethodsNumber() {
        List<WebElement> methods = creditCardList.findElements(By.tagName("table"));

        List<WebElement> defaultCard = creditCardList.findElements(By.className("shippingcopy"));

        int cards = methods.size() - 2 + defaultCard.size();

        return cards;
    }
    
    private void addNewBillingAdrress() {
        Country countryPojo = (Country) stateHolder.get("context");
        Address address = new Address(countryPojo.getCountry());

        WebElement newCardDiv = leftContainer.findElement(By.id("AddCreditCard"));
        wait.until(ExpectedConditions.visibilityOf(newCardDiv));

        WebElement newAddressButton = newCardDiv.findElement(By.xpath(".//td[@class='formcopy']/a"));
        newAddressButton.click();

        User user = (User) stateHolder.get("signedUser");

        WebElement country = newCardDiv.findElement(By.name("ADDRESS<>country_cd"));
        Select countrySelect = new Select(country);
        countrySelect.selectByValue(user.getCountryCode());

        WebElement firstName = newCardDiv.findElement(By.name("ADDRESS<>firstName"));
        firstName.sendKeys(user.getFirstName());

        WebElement lastName = newCardDiv.findElement(By.name("ADDRESS<>lastName"));
        lastName.sendKeys(user.getFirstName());

        WebElement address1 = newCardDiv.findElement(By.name("ADDRESS<>address1"));
        address1.sendKeys(address.getLine1());

        WebElement address2 = newCardDiv.findElement(By.name("ADDRESS<>address2"));
        address2.sendKeys(address.getLine2());

        WebElement city = newCardDiv.findElement(By.name("ADDRESS<>city"));
        city.sendKeys(address.getCity());

        WebElement state = newCardDiv.findElement(By.name("ADDRESS<>state_cd"));
        Select stateSelect = new Select(state);
        stateSelect.selectByValue(address.getState());

        WebElement zipcode = newCardDiv.findElement(By.name("ADDRESS<>postal"));
        zipcode.sendKeys(address.getZipcode());

        WebElement phone = newCardDiv.findElement(By.name("ADDRESS<>phone"));
        phone.sendKeys(address.getPhone());
    }
}