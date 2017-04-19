package com.jcrew.page.checkout;

import com.jcrew.pojo.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 5/17/16.
 */
public class CheckoutMergeCart extends Checkout {

    @FindBy(id = "registered")
    private WebElement registered;
    @FindBy(id = "mergedCartActionTop")
    private WebElement actionsTop;
    @FindBy(id = "userMergeCart")
    private WebElement form;
    @FindBy(className="item-egc")
    private WebElement giftCardElement;

    public CheckoutMergeCart(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(registered));
    }

    @Override
    public boolean isDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(form));

        return form.isDisplayed();
    }

    public String getUserName() {
        String subtitle = getSubtitle().toLowerCase();
        subtitle = subtitle.replace("welcome back", "");
        subtitle = subtitle.replace("!", "");

        return subtitle.trim();
    }

    public void saveWishlist() {
        wait.until(ExpectedConditions.visibilityOf(actionsTop));
        WebElement wishList = actionsTop.findElement(By.xpath(".//a[2]"));
        wishList.click();
    }

    public void addItems() {
        wait.until(ExpectedConditions.visibilityOf(actionsTop));
        WebElement addItemsToBag = actionsTop.findElement(By.xpath(".//a[1]"));
        addItemsToBag.click();

		List<Product> recentlyAdded =stateHolder.getList("toBag");
		List<Product> previouslyAdded = stateHolder.getList("userBag");
        recentlyAdded.addAll(previouslyAdded);

        stateHolder.remove("userBag");
        stateHolder.put("toBag", recentlyAdded);
    }
    
    public WebElement getButtonElementInMergeCartPage(String mergeCartElementName){
    	WebElement element = null;
    	wait.until(ExpectedConditions.visibilityOf(actionsTop));
    	
    	switch(mergeCartElementName.toUpperCase()){
    		case "SAVE TO WISHLIST & CONTINUE":
    			element = actionsTop.findElement(By.xpath(".//a[2]"));
    			break;
    		case "ADD ITEMS TO BAG & REVIEW ORDER":
    			element = actionsTop.findElement(By.xpath(".//a[1]"));
    			break;
    	}
    	
    	return element;
    }
    
public String getItemStatusFromMergeCart(String itemType){
    	
    	String itemCode = "";
    	switch(itemType.toUpperCase()){
    		case "FEW LEFT":
    			itemCode = stateHolder.get("fewLeftItem");
    			break;
    		case "REGULAR":
    			itemCode = stateHolder.get("regularItem");
    			break;    
    		case "BACKORDERED":
    			itemCode = stateHolder.get("backorderedItem");
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