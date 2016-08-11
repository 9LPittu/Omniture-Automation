package com.jcrew.page;

import com.jcrew.pojo.User;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
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

        creditCardNumber.sendKeys(testData.getData("credit.card.number"));
        secuirtyCode.sendKeys(testData.getData("credit.card.cvv"));

        Select month = new Select(expirationMonth);
        month.selectByVisibleText(testData.getData("credit.card.month"));

        Select year = new Select(expirationYear);
        year.selectByVisibleText(testData.getData("credit.card.year"));

        if (isGuest) {
            checkoutUSer = User.getFakeUser();
        } else {
            checkoutUSer = User.getUser(User.NO_DEFAULT);
        }

        nameOnCard.sendKeys(checkoutUSer.getFirstName() + " " + checkoutUSer.getLastName());
        emailReceipt.sendKeys(checkoutUSer.getEmail());
    }

    public void continueCheckout() {
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
}
