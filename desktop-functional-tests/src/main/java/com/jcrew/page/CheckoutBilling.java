package com.jcrew.page;

import com.jcrew.pojo.User;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 5/9/16.
 */
public class CheckoutBilling extends Checkout {

    @FindBy(id = "method-container")
    private WebElement method_container;
    @FindBy(id = "credit-card-billing")
    private WebElement card_container;
    @FindBy(id="wallet")
    private WebElement wallet_container;
    @FindBy(id = "payment_page")
    private WebElement payment_page;
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
    @FindBy(id = "address-entry-new")
    private WebElement newAddressEntry;
    
    @FindBy(id="paypalPayment")
    private WebElement paypalRadioButton;

    public CheckoutBilling(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(payment_page));
    }

    @Override
    public boolean isDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(payment_page));
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("billing");
    }

    public void fillPaymentMethod(boolean isGuest) {
        TestDataReader testData = TestDataReader.getTestDataReader();
        User checkoutUSer;

        creditCardNumber.sendKeys(testData.getData("card.number"));
        secuirtyCode.sendKeys(testData.getData("card.cvv"));

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
        
        if (isGuest)
        	emailReceipt.sendKeys(checkoutUSer.getEmail());
    }
    
    public void continueCheckout() {    	
    	if(stateHolder.hasKey("isBillingContinueClicked"))
    		return;
    	
        nextStep(payment_page);
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

    public List<String> getAcceptedCards() {
        WebElement cardsContainer = card_container.findElement(By.className("credit-card-icons-id"));
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

    public String getPromoDiscount() {
        return getSummaryText("promo");
    }

    public void addNewBillingAddress() {
        WebElement label = newAddressEntry.findElement(By.tagName("label"));
        Util.scrollToElement(driver, label);
        label.click();
    }

    public void addNewCard() {
        WebElement addNewCardElement = payment_page.findElement(By.id("address-new"));
        addNewCardElement.click();
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

    public void editCard() {
        List<WebElement> cards = payment_page.findElements(
                By.xpath(".//span[contains(@class,'wallet-brand')]/ancestor::label"));

        WebElement cardToBeEdited = cards.get(cards.size() - 1);
        WebElement editButton = cardToBeEdited.findElement(
                By.xpath(".//following-sibling::span/a[@class='item-edit']"));

        editButton.click();
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
    
    public void selectSpecificPaymentMethod(String paymentMethodName){
    	
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();
    	String cardNumber = testDataReader.getData(paymentMethodName.toLowerCase() + ".card.number");
    	String lastFourDigitsOfCardNum = cardNumber.substring(cardNumber.length() - 4);
    	
    	String cardDisplayName = testDataReader.getData(paymentMethodName.toLowerCase() + ".display.name");
    	
    	List<WebElement> paymentMethodElements = wallet_container.findElements(By.xpath(".//span[contains(@class,'wallet-brand') and " + Util.xpathGetTextLower + "='" 
    											 + cardDisplayName.toLowerCase() + "']/following-sibling::span[contains(@class,'wallet-line')"
    											 + " and contains(normalize-space(.),'" + lastFourDigitsOfCardNum + "')]"));
    	
   		WebElement paymentRadioButton = paymentMethodElements.get(0).findElement(By.xpath("preceding-sibling::input[@class='address-radio']"));
   		paymentRadioButton.click();
    }
    
    public void fillPaymentCardDetails(String paymentMethodName){
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();
    	
    	creditCardNumber.sendKeys(testDataReader.getData(paymentMethodName.toLowerCase() + ".card.number"));
        secuirtyCode.sendKeys(testDataReader.getData(paymentMethodName.toLowerCase() + ".security.code"));

        Select month = new Select(expirationMonth);
        month.selectByVisibleText(testDataReader.getData(paymentMethodName.toLowerCase() + ".expiration.month"));

        Select year = new Select(expirationYear);
        year.selectByVisibleText(testDataReader.getData(paymentMethodName.toLowerCase() + ".expiration.year"));

        User checkoutUser = User.getFakeUser();

        nameOnCard.sendKeys(checkoutUser.getFirstName().replaceAll("'", "") + " " + checkoutUser.getLastName().replaceAll("'", ""));
        emailReceipt.sendKeys(checkoutUser.getEmail());
    }
    
    public void clickTwoCardsPayment(){
    	WebElement payWithTwoCardsElement = payment_page.findElement(By.xpath(".//a[contains(@class,'item-link-submit') and contains(text(),'pay with two cards')]"));
    	payWithTwoCardsElement.click();
    	wait.until(ExpectedConditions.visibilityOf(payment_page.findElement(By.xpath(".//a[contains(@class,'item-link-submit') and contains(text(),'pay with one card')]"))));
    	
    	List<WebElement> numberofCardsAvailable = payment_page.findElements(By.xpath(".//li[contains(@id, 'cardId')]"));
    	stateHolder.put("numberofCardsAvailable", numberofCardsAvailable.size());
    }
    
    public void splitPayment(String paymentMethod1 , String paymentMethod2){
    	TestDataReader testDataReader = TestDataReader.getTestDataReader();
    	String cardShortName1 = testDataReader.getData(paymentMethod1.toLowerCase() + ".short.name"); 
    	String cardShortName2 = testDataReader.getData(paymentMethod2.toLowerCase() + ".short.name");
    	
    	String cardNumber1 = testDataReader.getData(paymentMethod1.toLowerCase() + ".card.number");
    	String cardNumber2 = testDataReader.getData(paymentMethod2.toLowerCase() + ".card.number");
    	
    	String lastFourDigitsOfCardNum1 = cardNumber1.substring(cardNumber1.length() - 4);
    	String lastFourDigitsOfCardNum2 = cardNumber2.substring(cardNumber2.length() - 4);
    	
    	WebElement splitPaymentForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-multitender")));
    	
    	int numberofCardsAvailable = stateHolder.get("numberofCardsAvailable");
    	
    	String valueToBeSelected;
    	Select select;
    	List<WebElement> paymentDropdowns = splitPaymentForm.findElements(By.xpath(".//select[contains(@id, 'distributionCard')]"));
    	
    	if(numberofCardsAvailable > 2){    		
    		valueToBeSelected = cardShortName1.toUpperCase() + " ending in " + lastFourDigitsOfCardNum1;
    		select = new Select(paymentDropdowns.get(0));
    		select.selectByVisibleText(valueToBeSelected);
    	}
    	
    	valueToBeSelected = cardShortName2.toUpperCase() + " ending in " + lastFourDigitsOfCardNum2;
		select = new Select(paymentDropdowns.get(1));
		select.selectByVisibleText(valueToBeSelected);
    	
    	String orderTotal = stateHolder.get("total");
    	Double dblOrderTotal = Double.parseDouble(orderTotal.replaceAll("[^\\d.]*", ""));
    	dblOrderTotal = dblOrderTotal/2;
    	
    	WebElement secondAmountElement = splitPaymentForm.findElement(By.id("secondAmount"));
    	secondAmountElement.clear();
    	secondAmountElement.sendKeys(dblOrderTotal.toString());
    	secondAmountElement.sendKeys(Keys.TAB);
    	
    	splitPaymentForm.findElement(By.id("multiTenderDistributionSubmit")).click();    	
    	wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("multiTenderDistributionSubmit")));
    }
    
    public void selectPaypalRadioButton(){
    	paypalRadioButton.click();
    	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//p[@class='page-msg']")));
    }
}
