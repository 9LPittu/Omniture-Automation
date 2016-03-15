package com.jcrew.page;

import com.github.javafaker.Faker;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
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
    
    @FindBy(xpath="//span[@class='form-label' and text()='Add New Card']")
    private WebElement addNewCardOnBillingPage;
    
    @FindBy(xpath="//a[@id='submit-new-shipping-address' and text()='Save & Continue']")
    private WebElement saveContinueButtonOnBillingPage;
    
    @FindBy(xpath=".//*[@id='address-new']/span[2]")
    private WebElement addNewBillingAddress;
    
    @FindBy(id="countryAM")
    private WebElement addNewBillingAddress_Country;
    
    @FindBy(id="firstNameAM")
    private WebElement addNewBillingAddress_FirstName;
    
    @FindBy(id="lastNameAM")
    private WebElement addNewBillingAddress_LastName;
    
    @FindBy(id="address1")
    private WebElement addNewBillingAddress_address1;
    
    @FindBy(id="address2")
    private WebElement addNewBillingAddress_address2;
    
    @FindBy(id="zipcode")
    private WebElement addNewBillingAddress_zipcode;
    
    @FindBy(css=".form-dropdown.city-state-id")
    private WebElement addNewBillingAddress_CityStateDropdown;
    
    @FindBy(id="phoneNumAM")
    private WebElement addNewBillingAddress_PhoneNumber;
    
    @FindBy(xpath="//a[@id='submit-new-shipping-address' and text()='Save']")
    private WebElement addNewBillingAddress_Save;
    
    @FindBy(id="qasPageTitle")
    private WebElement qasVerification;
    
    @FindBy(xpath=".//a[@class='button-submit' and text()='Use Address as Entered']")
    private WebElement checkYourAddress_UseAddressAsEntered;

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
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(billingOptionsSubmit));
        billingOptionsSubmit.click();
        Util.waitForPageFullyLoaded(driver);
    }

    public boolean isBillingPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(creditCardBilling));
        return creditCardBilling.isDisplayed();
    }
    
    public void enterCreditCardDetails(String cardName){
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();
    	String creditCardDetails = testDataReader.getData(cardName);

        Util.waitWithStaleRetry(driver,creditCardNumber);

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
    
    public void clickAddNewCardOnBillingPage(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(addNewCardOnBillingPage));
    	addNewCardOnBillingPage.click();
    }
    
    public void clickSaveAndContinueButton(){
    	saveContinueButtonOnBillingPage.click();
    }
    
    public void clickAddNewBillingAddress(){
    	addNewBillingAddress.click();
    }
    
    public void selectCountryOnNewBillingAddressForm(String country){
        Util.waitWithStaleRetry(driver,addNewBillingAddress_Country);
    	Select list = new Select(addNewBillingAddress_Country);
    	list.selectByVisibleText(country);
    }
    
    public void enterFirstNameOnNewBillingAddressForm(){
    	addNewBillingAddress_FirstName.sendKeys(faker.name().firstName());
    }
    
    public void enterLastNameOnNewBillingAddressForm(){
    	addNewBillingAddress_LastName.sendKeys(faker.name().lastName());
    }
    
    public void enterAddressLine1OnAddNewBillingAddressForm(String address1){
    	addNewBillingAddress_address1.sendKeys(address1);
    }
    
    public void enterAddressLine2OnAddNewBillingAddressForm(String address2){
    	addNewBillingAddress_address2.sendKeys(address2);
    }
    
    public void enterZipCodeOnAddNewBillingAddressForm(String zipCode){
    	addNewBillingAddress_zipcode.sendKeys(zipCode);
    	Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(addNewBillingAddress_CityStateDropdown));
    }
    
    public void enterPhoneNumberOnAddNewBillingAddressForm(){
    	addNewBillingAddress_PhoneNumber.sendKeys(faker.phoneNumber().phoneNumber());
    }
    
    public void clickSaveInAddNewBillingAddressForm(){    	
    	addNewBillingAddress_Save.click();
    }
    
    public boolean isQASVerificationDisplayed(){
    	Util.waitWithStaleRetry(driver,qasVerification);
    	return qasVerification.isDisplayed();
    }
    
    public void clickUseAddressAsEnteredButton(){    	
    	Util.waitWithStaleRetry(driver, checkYourAddress_UseAddressAsEntered);
    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkYourAddress_UseAddressAsEntered));
    	checkYourAddress_UseAddressAsEntered.click();
    }
}