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
	
	
	@FindBy(xpath="//a[@id='eGiftCard']/img")
	private WebElement eGiftCardImage;
	
	@FindBy(xpath="//div[@id='egiftPanel']")
	private WebElement eGiftPanel;
	
	@FindBy(id="senderNameEgc")
	private WebElement senderName;
	
	@FindBy(id="RecipientNameEgc")
	private WebElement recipientName;
	
	@FindBy(id="emailAddressEgc")
	private WebElement recipientEmailAddress;
	
	@FindBy(xpath=".//div[@class='calendarPanel']/input[@id='date']")
	private WebElement dateToBeSent;
	
	@FindBy(xpath=".//textarea[@id='textAreaMessage']")
	private WebElement giftMessage;
	
	@FindBy(id="submitEgift")
	private WebElement addToBag; 
	
	
    public GiftCards(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        
        wait.until(ExpectedConditions.visibilityOf(eGiftCardImage));
    }
    
    public void addEgiftCardToBag(String giftCardAmountValue){
    	
    	eGiftCardImage.click();
    	
    	//choose amount
    	wait.until(ExpectedConditions.visibilityOf(eGiftPanel));
    	
    	WebElement giftCardAmount = null;
    	if(giftCardAmountValue.equalsIgnoreCase("any")){
    		List<WebElement> giftCardAmounts = eGiftPanel.findElements(By.xpath(".//a[contains(@id,'amount')]"));
    		int randomIndex = Util.randomIndex(giftCardAmounts.size());
    		giftCardAmount = giftCardAmounts.get(randomIndex);    		
    	}
    	else{
    		try{
    			giftCardAmount = eGiftPanel.findElement(By.xpath(".//a[@id='amount'" + giftCardAmountValue +"]"));
    		}
    		catch(NoSuchElementException e){
    			logger.error("Invalid e-gift card amount - {}", giftCardAmountValue);    			
    			throw new WebDriverException("Invalid e-gift card amount - " + giftCardAmountValue);
    		}
    	}
    	
    	logger.info("Gift amount selected: {}", giftCardAmount.getText().trim());
    	giftCardAmount.click();
    	
    	//Enter sender name
    	User sender = User.getNewFakeUser();
    	String senderNameValue = sender.getFirstName();
    	senderName.sendKeys(senderNameValue);
    	logger.info("Sender name entered: {}", senderNameValue);
    	stateHolder.put("giftCardSenderName", senderNameValue);
    	
    	//Enter recipient name
    	User recipient = User.getNewFakeUser();
    	String recipientNameValue = recipient.getFirstName();
    	recipientName.sendKeys(recipientNameValue);
    	logger.info("Recipient name entered: {}", recipientNameValue);
    	stateHolder.put("giftCardRecipientName", recipientNameValue);
    	
    	//Enter recipient email address
    	String recipientEmail = recipient.getEmail();
    	recipientEmailAddress.sendKeys(recipientEmail);
    	logger.info("Recipient email address entered: {}", recipientEmail);
    	stateHolder.put("giftCardRecipientEmail", recipientEmail);
    	
    	//enter date
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, 1);
    	String tomorrowDate = dateFormat.format(calendar.getTime());
    	dateToBeSent.sendKeys(tomorrowDate);
    	logger.info("Date to be sent entered: {}", tomorrowDate);
    	stateHolder.put("giftCardDateSent", tomorrowDate);    	
    	
    	//enter gift message
    	giftMessage.sendKeys("This is automation gift message");    	
    	
    	try{
    		Util.scrollToElement(driver, addToBag);
    		addToBag.click();
    	}
    	catch(Exception e){
    		JavascriptExecutor jse = (JavascriptExecutor)driver;
    		jse.executeScript("arguments[0].click();", addToBag);
    	}
    	
		Util.waitLoadingBar(driver);
    }
}