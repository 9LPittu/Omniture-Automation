package com.jcrew.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.jcrew.utils.Util;

public class CheckoutGiftBoxes extends Checkout {

    @FindBy(id = "giftOptionsReturn")
    private WebElement giftOptionsReturn;
    
    @FindBy(className="checkout-container")
    private WebElement checkoutContainer;
    
    public CheckoutGiftBoxes(WebDriver driver) {
        super(driver);        

        wait.until(ExpectedConditions.visibilityOf(giftOptionsReturn));
    }

    @Override
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("gift");
    }
    
    public void selectGiftBoxesforItems(String giftBoxSelectionType){
    	
    	List<WebElement> newGiftBoxButtons = checkoutContainer.findElements(By.xpath(".//a[@class='item-button item-box']"));
    	if(newGiftBoxButtons.size()==0){
    		String message = "Items added to the shopping bag cannot be gift wrapped!!!";
    		Util.e2eErrorMessagesBuilder(message);
    		throw new WebDriverException(message);
    	}
    	
    	if(!giftBoxSelectionType.equalsIgnoreCase("ALL-ITEMS-IN-TWO-GIFTBOXES")){
    		//click on 'New Gift Box' button for first item
    		newGiftBoxButtons.get(0).click();
    		logger.debug("'New Gift Box' button is clicked...");
    	}
    	
    	switch(giftBoxSelectionType.toUpperCase()){
    		case "ALL-ITEMS-IN-ONE-GIFTBOX":
    			  placeAllItemsInOneGiftBox();
    			  break;
    		case "EACH-ITEM-IN-DIFFERENT-GIFTBOX":
    			  placeEachItemInDifferentGiftBox();
    			  break;
    		case "ONLY-ONE-ITEM-IN-GIFTBOX-OTHER-ITEMS-NO-GIFTBOX":
    			  placeOnlyOneItemInGiftBox();
    			  break;
    		case "ALL-ITEMS-IN-TWO-GIFTBOXES":
    			  placeAllItemsInTwoGiftBoxes();
    			  break;
    	}
    }
    
    private List<WebElement> getItems(){
    	List<WebElement> items = checkoutContainer.findElements(By.xpath(".//div[@class='item-row clearfix']"));
    	return items;
    }
    
    private List<WebElement> getGiftBoxDropdownElements(WebElement itemRowElement){
    	List<WebElement> giftBoxDropdownElements = itemRowElement.findElements(By.name("giftBoxKit"));
    	return giftBoxDropdownElements;
    }
    
    private void addNewGiftBox(WebElement parentElement){
    	WebElement newGiftBoxButton = parentElement.findElement(By.xpath(".//a[@class='item-button item-box']"));
    	newGiftBoxButton.click();
		logger.debug("'New Gift Box' button is clicked...");
    }
    
    private void selectExistingGiftBox(WebElement parentElement, String value){
    	WebElement giftBoxDropdown = parentElement.findElement(By.name("giftBox"));
    	Select select = new Select(giftBoxDropdown);
    	select.selectByValue(value);
		logger.debug("Gift box dropdown value selected: {}", select.getFirstSelectedOption().getText());
    }
    
    private void placeAllItemsInOneGiftBox(){
    	
    	List<WebElement> items = getItems();
    	if(items.size()>4){
    		String message = "Attempt is made to place more than 4 items in gift box. More than 4 items cannot be placed in one gift box. Reduce the number of items per gift box and please try again.";
    		Util.e2eErrorMessagesBuilder(message);
    		throw new WebDriverException(message);
    	}
    	
    	for(int i=1;i<items.size();i++){
    		List<WebElement> giftBoxDropdown = getGiftBoxDropdownElements(items.get(i));
    		if(giftBoxDropdown.size()==0){
    			//item with different shipping address
    			addNewGiftBox(items.get(i));
    		}else{
    			//item with same shipping address
    			selectExistingGiftBox(items.get(i), "1");
    		}
    	}
    }
    
    private void placeEachItemInDifferentGiftBox(){
    	List<WebElement> items = getItems();
    	for(int i=1;i<items.size();i++){
   			addNewGiftBox(items.get(i));
    	}
    }
    
    private void placeOnlyOneItemInGiftBox(){
    	List<WebElement> items = getItems();
    	for(int i=1;i<items.size();i++){
    		List<WebElement> giftBoxDropdown = getGiftBoxDropdownElements(items.get(i));
    		if(giftBoxDropdown.size()==0){
    			//item with different shipping address
    			addNewGiftBox(items.get(i));
    		}
    	}
    }
    
    private void placeAllItemsInTwoGiftBoxes(){
    	
    }
}