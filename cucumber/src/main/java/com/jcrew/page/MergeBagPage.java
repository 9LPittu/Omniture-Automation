package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MergeBagPage extends Checkout {

    private final WebDriver driver;

    @FindBy(id = "userMergeCart")
    private WebElement form;
    
    @FindBy(id = "mergedCartActionTop")
    private WebElement mergeCartActionTop;
    
    @FindBy(xpath="//*[@id='mergedCartActionTop']/a[1]")
    private WebElement saveToWishlistAndContinue;
    
    @FindBy(xpath="//*[@id='mergedCartActionTop']/a[2]")
    private WebElement addItemsToBagAndReviewOrder;
    
    @FindBy(className="item-egc")
    private WebElement giftCardElement;

    public MergeBagPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @Override
    public boolean isDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(form));

        return form.isDisplayed();
    }

    public boolean isMergeBagPage() {
        boolean result;
        try {
            result = mergeCartActionTop.isDisplayed();
        } catch (NoSuchElementException nse) {
            result = false;
        }
        return result;
    }

    public void click_save_to_wishlist_and_continue_checkout() {
        final WebElement saveToWishlistAndContinueCheckoutButton = mergeCartActionTop.
                findElement(By.className("button-submit"));

        saveToWishlistAndContinueCheckoutButton.click();

    }
    
    public boolean isSaveToWishlistAndContinueDisplayed(){
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(saveToWishlistAndContinue));
        return saveToWishlistAndContinue.isDisplayed();
    }
    
    public boolean isAddItemsToBagAndReviewOrderDisplayed(){
    	return addItemsToBagAndReviewOrder.isDisplayed();
    }
    
    public void clickAddItemsToBagAndReviewOrder(){
    	addItemsToBagAndReviewOrder.click();
    }
    
    public String getUserName() {
        String subtitle = getSubtitle().toLowerCase();
        subtitle = subtitle.replace("welcome back", "");
        subtitle = subtitle.replace("!", "");

        return subtitle.trim();
    }
    
public String getItemStatusFromMergeCart(String itemType){
    	
    	String itemCode = "";
    	switch(itemType.toUpperCase()){
    		case "FEW LEFT":
    			itemCode = (String) stateHolder.get("fewLeftItem");
    			break;
    		case "REGULAR":
    			itemCode = (String) stateHolder.get("regularItem");
    			break;    
    		case "BACKORDERED":
    			itemCode = (String) stateHolder.get("backorderedItem");
    			break;
    	}
    	
    	WebElement itemCodeElement = form.findElement(By.xpath(".//span[contains(@class,'item-value') and text()='" + itemCode.toUpperCase() + "']"));    	
    	WebElement itemStatusElement = itemCodeElement.findElement(By.xpath(".//ancestor::li/following-sibling::li[@class='item-status']"));
    	String status = itemStatusElement.getText().trim();
    	return status;
    }
    
    public String getEgiftCardSenderName(){
    	WebElement senderNameElement = giftCardElement.findElement(By.xpath(".//ul[@class='item-description']/li[1]/span"));
    	String senderName = senderNameElement.getText().trim();
    	return senderName;
    }
    
    public String getEgiftCardRecipientName(){
    	WebElement recipientNameElement = giftCardElement.findElement(By.xpath(".//ul[@class='item-description']/li[2]/span"));
    	String recipientName = recipientNameElement.getText().trim();
    	return recipientName;
    }
    
    public String getEgiftCardRecipientEmailAddress(){
    	WebElement recipientEmailElement = giftCardElement.findElement(By.xpath(".//ul[@class='item-description']/li[3]/span"));
    	String recipientEmail = recipientEmailElement.getText().trim();
    	return recipientEmail;
    }
    
    public String getEgiftCardDateSent(){
    	WebElement dateSentElement = giftCardElement.findElement(By.xpath(".//ul[@class='item-description']/li[4]/span"));
    	String dateSent = dateSentElement.getText().trim();
    	return dateSent;
    }
}