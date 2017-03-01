package com.jcrew.page;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.jcrew.pojo.User;
import com.jcrew.utils.Util;

public class GiftCards  extends PageObject {	
	
	@FindBy(id="cardTypePanel")
	private WebElement cardTypePanel;
	
	@FindBy(xpath=".//div[@class='calendarPanel']/input[@id='date']")
	private WebElement dateToBeSent;
	
	private String cardType;	
	private WebElement cardPanel;
	
    public GiftCards(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        
        wait.until(ExpectedConditions.visibilityOf(cardTypePanel));
    }
    
    public void addEgiftCardToBag(String giftCardAmountValue){
    	
    	selectCardType("e-Gift Card");
    	
    	//choose amount
    	selectGiftAmount(giftCardAmountValue);
    	
    	//Enter sender name
    	enterSenderName("any ");
    	
    	//Enter recipient name
    	enterRecipientName("any ");
    	
    	//Enter recipient email address
    	enterRecipientEmailAddress("any ");
    	
    	//enter date
    	enterDateToBeSent();
    	
    	//enter gift message
    	enterGiftMessage("This is automation gift message", "Gift Message");
    	
    	clickAddtoBag();
    }
    
    public boolean isDisplayed() {
    	return true;
    }
    
    public void selectCardType(String cardTypeName){
    	WebElement cardImage = cardTypePanel.findElement(By.xpath(".//h4[" + Util.xpathGetTextLower + "='" + cardTypeName.toLowerCase() + "']/preceding-sibling::a/img"));
    	cardImage.click();
    	cardType = cardTypeName.toLowerCase();
    	
    	if(cardType.contains("classic")){
    		cardPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='classicPanel']")));
    	}else{
    		cardPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='egiftPanel']")));
    	}
    	
    	stateHolder.put("giftCardType", cardTypeName);
    }
    
    public void selectGiftAmount(String giftAmount){
    	//choose amount    	
   		WebElement amountList = wait.until(ExpectedConditions.visibilityOf(cardPanel.findElement(By.xpath("descendant::form/div/div"))));
    	
    	WebElement giftCardAmount = null;
    	if(giftAmount.equalsIgnoreCase("any")){
    		List<WebElement> giftCardAmounts = amountList.findElements(By.xpath(".//a[contains(@id,'amount')]"));
    		int randomIndex = Util.randomIndex(giftCardAmounts.size());
    		giftCardAmount = giftCardAmounts.get(randomIndex);    		
    	}
    	else{
    		try{
    			giftCardAmount = amountList.findElement(By.xpath(".//a[@id='amount" + giftAmount +"']"));
    		}
    		catch(NoSuchElementException e){
    			logger.error("Invalid e-gift card amount - {}", giftAmount);    			
    			throw new WebDriverException("Invalid e-gift card amount - " + giftAmount);
    		}
    	}
    	
    	logger.info("Gift amount selected: {}", giftCardAmount.getText().trim());
    	giftCardAmount.click();    	
    }
    
    public void enterSenderName(String senderNameText){
    	
    	WebElement senderInfoPanel = cardPanel.findElement(By.id("senderInfoPanel"));

    	WebElement senderNameElement = null;
    	if(cardType.contains("classic")){
    		senderNameElement = senderInfoPanel.findElement(By.id("senderName"));
    	}
    	else{
    		senderNameElement = senderInfoPanel.findElement(By.id("senderNameEgc"));
    	}
    	
    	if(senderNameText.contains("any ")){
        	User sender = User.getNewFakeUser();
        	senderNameText = sender.getFirstName();  
        	senderNameText = senderNameText.replaceAll("'", "");
    	}
    	
    	senderNameElement.sendKeys(senderNameText);
    	logger.info("Sender name entered: {}", senderNameText);
    	stateHolder.put("giftCardSenderName", senderNameText);
    }
    
    private WebElement getRecipientPanelElement(){
    	return cardPanel.findElement(By.id("recipientInfoPanel"));
    }
    
    public void enterRecipientName(String recipientNameText){
    	
    	WebElement recipientInfoPanel = getRecipientPanelElement();

    	WebElement recipientNameElement = null;
    	if(cardType.contains("classic")){
    		recipientNameElement = recipientInfoPanel.findElement(By.id("RecipientName"));
    	}
    	else{
    		recipientNameElement = recipientInfoPanel.findElement(By.id("RecipientNameEgc"));
    	}
    	
    	if(recipientNameText.contains("any ")){
        	User user = User.getNewFakeUser();
        	recipientNameText = user.getFirstName();
        	recipientNameText = recipientNameText.replaceAll("'", "");
    	}
    	
    	recipientNameElement.sendKeys(recipientNameText);
    	logger.info("Recipient name entered: {}", recipientNameText);
    	stateHolder.put("giftCardRecipientName", recipientNameText);
    }
    
    public void enterRecipientEmailAddress(String recipientEmailAddressText){
    	
    	WebElement recipientInfoPanel = getRecipientPanelElement();
    	
    	WebElement recipientEmailAddressElement = null;
    	if(cardType.contains("classic")){
    		recipientEmailAddressElement = recipientInfoPanel.findElement(By.id("emailAddress"));
    	}
    	else{
    		recipientEmailAddressElement = recipientInfoPanel.findElement(By.id("emailAddressEgc"));
    	}
    	
    	if(recipientEmailAddressText.contains("any ")){
        	User user = User.getNewFakeUser();
        	recipientEmailAddressText = user.getEmail();
    	}
    	
    	recipientEmailAddressElement.sendKeys(recipientEmailAddressText);
    	logger.info("Recipient Email Address entered: {}", recipientEmailAddressText);
    	stateHolder.put("giftCardRecipientEmail", recipientEmailAddressText);
    }
    
    public void enterDateToBeSent(){
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, 1);
    	String tomorrowDate = dateFormat.format(calendar.getTime());
    	dateToBeSent.sendKeys(tomorrowDate);
    	logger.info("Date to be sent entered: {}", tomorrowDate);
    	stateHolder.put("giftCardDateSent", tomorrowDate);
    }
    
    public void enterGiftMessage(String message, String fieldName){
    	
    	WebElement giftMessagePanel = cardPanel.findElement(By.id("giftMessagePanel"));
    	
    	WebElement giftMessageElement=null;
    	switch(fieldName.toLowerCase()){
    		case "line 1":
    			giftMessageElement = giftMessagePanel.findElement(By.id("text1Message"));
    			break;
    		case "line 2":
    			giftMessageElement = giftMessagePanel.findElement(By.id("text2Message"));
    			break;
    		case "gift message":
    			giftMessageElement = giftMessagePanel.findElement(By.id("textAreaMessage"));
    			break;
    		default:
    			throw new WebDriverException(fieldName + " is not recognized on the gift cards page");
    	}
    	
    	//enter gift message
    	giftMessageElement.sendKeys(message);
    	logger.info("Message for {} field is entered as: {}", fieldName, message);
    }
    
    public void clickAddtoBag(){
    	WebElement addToBagButton = null;
    	if(cardType.contains("classic")){
    		addToBagButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitClassic")));
    	}
    	else{
    		addToBagButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitEgift")));
    	}
    	
    	try{
    		Util.scrollToElement(driver, addToBagButton);
    		addToBagButton.click();
    	}
    	catch(WebDriverException e){
    		JavascriptExecutor jse = (JavascriptExecutor)driver;
    		jse.executeScript("arguments[0].click();", addToBagButton);
    	}
    	
		Util.waitLoadingBar(driver);
		driver.navigate().refresh();
    }
}