package com.jcrew.page;

import com.github.javafaker.Faker;
import com.jcrew.pojo.Address;
import com.jcrew.pojo.User;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class BillingPage extends Checkout {

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
    
    @FindBy(className = "button-submit")
    private WebElement continueCheckout;
    
    @FindBy(id = "address-entry-new")
    private WebElement newAddressEntry;
    
    @FindBy(id = "shipping-address")
    private WebElement shippingForm;
    
    @FindBy(id = "payment_page")
    private WebElement payment_page;
    
    @FindBy(id = "method-container")
    private WebElement method_container;
    
    @FindBy(id="wallet")
    private WebElement wallet_container;
    
    @FindBy(id = "creditDebitCard")
    private WebElement cardForm;
    
    public BillingPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
    
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");
        logger.debug("Billing address id: {}", bodyId);

        return bodyId.equals("billing");
    }
    
    public boolean isBillingPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(payment_page));
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("billing");
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
        Util.waitLoadingBar(driver);
    }

    public boolean isBillingPage() {
    	try{
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
    		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(creditCardBilling));
    		return creditCardBilling.isDisplayed();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
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
    	Util.scrollToElement(driver, addNewBillingAddress);
    	saveContinueButtonOnBillingPage.click();
    	Util.waitLoadingBar(driver);
    }
    
    public void clickAddNewBillingAddress(){
    	addNewBillingAddress.click();
    	Util.waitLoadingBar(driver);
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
    	Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(qasVerification)));
    	return qasVerification.isDisplayed();
    }
    
    public void clickUseAddressAsEnteredButton(){    	
    	Util.waitWithStaleRetry(driver, checkYourAddress_UseAddressAsEntered);
    	Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkYourAddress_UseAddressAsEntered));
    	checkYourAddress_UseAddressAsEntered.click();
    }
    
    public void presses_continue_button_on_Billingpage() {
        continueCheckout.click();
        Util.waitForPageFullyLoaded(driver);
    }
    
    public void addNewBillingAddress() {
        WebElement label = newAddressEntry.findElement(By.tagName("label"));
        Util.scrollToElement(driver, label);
        label.click();
    }
    
    public void fillFormData() {
        Address address = new Address("billing");
        User user = User.getFakeUser();

        addNewBillingAddress_FirstName.sendKeys(user.getFirstName());
        addNewBillingAddress_LastName.sendKeys(user.getLastName());
        addNewBillingAddress_address1.sendKeys(address.getLine1());
        addNewBillingAddress_address2.sendKeys(address.getLine2());
        addNewBillingAddress_zipcode.sendKeys(address.getZipcode());
        addNewBillingAddress_PhoneNumber.sendKeys(address.getPhone());

        WebElement usState = shippingForm.findElement(By.id("dropdown-us-city-state"));
        wait.until(ExpectedConditions.visibilityOf(usState));
    }
    
    public void fillPaymentMethod(boolean isGuest) {
        TestDataReader testData = TestDataReader.getTestDataReader();
        User checkoutUSer;

        creditCardNumber.sendKeys(testData.getData("card.number"));
        securityCode.sendKeys(testData.getData("card.cvv"));

        Select month = new Select(expirationMonth);
        month.selectByVisibleText(testData.getData("card.month"));

        Select year = new Select(expirationYear);
        year.selectByVisibleText(testData.getData("card.year"));

        if (isGuest) {
            checkoutUSer = User.getFakeUser();
        } else {
            checkoutUSer = User.getUser(User.NO_DEFAULT);
        }

        nameOnCard.sendKeys(checkoutUSer.getFirstName() + " " + checkoutUSer.getLastName());
        emailReceipt.sendKeys(checkoutUSer.getEmail());
    }
    
    public void fillPaymentMethod(String type) {
        TestDataReader testData = TestDataReader.getTestDataReader();
        User checkoutUSer = User.getFakeUser();

        creditCardNumber.sendKeys(testData.getData(type + ".card.number"));
        securityCode.sendKeys(testData.getData(type + ".card.cvv"));

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
    
    public List<String> getAcceptedCards() {
        WebElement cardsContainer = creditCardBilling.findElement(By.className("credit-card-icons-id"));
        List<WebElement> methods = cardsContainer.findElements(By.className("credit-card-icon"));
        List<String> methodsString = new ArrayList<>(methods.size());

        for (WebElement method : methods) {
            String methodString = method.getAttribute("class");
            methodString = methodString.replace("credit-card-icon cc-", "");
            methodString = methodString.replace("-id", "");

            methodsString.add(methodString);
        }

        return methodsString;
    }
    
    public List<String> getPaymentMethods() {
        List<WebElement> methods = method_container.findElements(By.className("form-radio-set"));
        List<String> methodsString = new ArrayList<>(methods.size());

        for (WebElement method : methods) {
            String methodString = method.getText().trim();
            methodsString.add(methodString);
        }

        return methodsString;
    }
    
    public String getPromoDiscount() {
        return getSummaryText("promo");
    }
    
    public void SelectPaymentMethodNoDefault() {
    	List<WebElement> paymentMethodRadioButtons = wallet_container.findElements(By.xpath(".//input[@class='address-radio' and not(@checked='')]"));
    	int randomIndex = Util.randomIndex(paymentMethodRadioButtons.size());
    	
    	paymentMethodRadioButtons.get(randomIndex).click();
    	Util.waitLoadingBar(driver);
    	
    	WebElement selectedPaymentMethodLabel = paymentMethodRadioButtons.get(randomIndex).findElement(By.xpath(".//parent::label"));    	
        String paymentMethod = selectedPaymentMethodLabel.getText();
        logger.debug("Selected payment method: {}", paymentMethod);

        stateHolder.put("selectedPaymentMethod", paymentMethod) ;
    }
    
    public List<String> getCards() {
        wait.until(ExpectedConditions.visibilityOf(payment_page));
        List<WebElement> cards = payment_page.findElements(
                By.xpath(".//span[contains(@class,'wallet-brand')]/ancestor::label"));
        List<String> cardsInfo = new ArrayList<>(cards.size());

        for(WebElement card : cards) {
            logger.debug("I have a card with this info:\n{}", card.getText());
            cardsInfo.add(card.getText());
        }

        return cardsInfo;
    }
    
    public void editCard() {
        List<WebElement> cards = payment_page.findElements(
                By.xpath(".//span[contains(@class,'wallet-brand')]/ancestor::label"));

        WebElement cardToBeEdited = cards.get(cards.size() - 1);
        WebElement editButton = cardToBeEdited.findElement(
                By.xpath(".//following-sibling::span/a[@class='item-edit']"));

        editButton.click();
    }
    
    public void editPayment() {
    	securityCode.sendKeys("156");
        nameOnCard.clear();
        nameOnCard.sendKeys("Edited Card Name");
    }
    
    public void removeCard(String type) {
        WebElement cardElement = wait.until(ExpectedConditions.elementToBeClickable(payment_page.findElement(
                By.xpath(".//span[contains(@class,'wallet-brand') and text()='" + type + "']"))));
        cardElement.click();
        Util.waitLoadingBar(driver);

        WebElement cardInfoElement = cardElement.findElement(By.xpath(".//ancestor::label"));
        String cardInfo = cardInfoElement.getText();
        stateHolder.put("removedCard", cardInfo);
        logger.debug("Removed card:\n{}", cardInfo);
        
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
				   cardElement.findElement(
				   By.xpath(".//ancestor::label/following-sibling::span/a[@class='item-remove']"))));

        removeButton.click();
        Util.waitLoadingBar(driver);

        //checking one more time if the payment method is removed
        //In phantomjs, card is not removing. So, using alternate way to delete the payment method
    	try{
    		cardElement = wait.until(ExpectedConditions.elementToBeClickable(payment_page.findElement(
                    By.xpath(".//span[contains(@class,'wallet-brand') and text()='" + type + "']"))));
    		
    		if(cardElement.isDisplayed()){
	    		 removeButton = wait.until(ExpectedConditions.elementToBeClickable(
	    				 				   cardElement.findElement(
	    				 				   By.xpath(".//ancestor::label/following-sibling::span/a[@class='item-remove']"))));
	    		 
	    		 driver.get(removeButton.getAttribute("href"));
	    		 Util.waitLoadingBar(driver);
    		}
    	}
        catch(Exception e){
        	logger.debug("Remove button is no longer displayed for '{}' card", type);
        }
    }
}