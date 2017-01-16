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

public class CheckoutGiftBox extends Checkout {

    @FindBy(name="frm_gift_options")
    private WebElement giftOptionsForm;
	
	@FindBy(id = "giftOptionsReturn")
    private WebElement giftOptionsReturn;
    
    @FindBy(className="checkout-container")
    private WebElement checkoutContainer;
    
    public CheckoutGiftBox(WebDriver driver) {
        super(driver);        

        wait.until(ExpectedConditions.visibilityOf(giftOptionsReturn));
    }

    @Override
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("gift");
    }
    
    public void continueCheckout() {
        nextStep(giftOptionsForm);
    }

    
    public void selectGiftBoxesForItems(String giftBoxSelectionType){
    	
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
    	
    	List<WebElement> multipleShipping = checkoutContainer.findElements(By.className("gift-multiship"));
    	
    	//If single address was selected on the 'Shipping Address' page
    	if(multipleShipping.size()==0){
    		List<WebElement> items = getItems();
    		if(items.size() > 8){
    			String message = "Only 4 items per gift box can be placed. Number of items per gift box is crossed. Change the test data and try again.";
    			Util.e2eErrorMessagesBuilder(message);
        		throw new WebDriverException(message);
    		}
    		
    		//Divide the items to the 2 gift boxes
    		int firstGiftBoxItemsCount = items.size()/2;
    		
    		//first gift box
    		for(int i=1;i<firstGiftBoxItemsCount-1;i++){
    			List<WebElement> giftBoxDropdown = getGiftBoxDropdownElements(items.get(i));
        		if(giftBoxDropdown.size()==0){
        			addNewGiftBox(items.get(i));
        		}else{
        			selectExistingGiftBox(items.get(i), "1");
        		}
    		}
    		
    		//second gift box
    		for(int j=firstGiftBoxItemsCount;j<items.size()-1;j++){
    			if(j==firstGiftBoxItemsCount){
    				addNewGiftBox(items.get(j));
    			}else{
    				selectExistingGiftBox(items.get(j), "2");
    			}
    		}    		
    	}else{
    		//If multiple shipping addresses are selected
    		List<WebElement> giftOptions = checkoutContainer.findElements(By.id("gift-options"));
    		for(int i=0;i<giftOptions.size();i++){
    			List<WebElement> itemsInGiftOption = giftOptions.get(i).findElements(By.xpath(".//div[@class='item-row clearfix']"));
    			for(int j=0;j<itemsInGiftOption.size();j++){
    				if(j==0 || itemsInGiftOption.size()==1){
    					addNewGiftBox(itemsInGiftOption.get(j));
    				}else{
    					selectExistingGiftBox(itemsInGiftOption.get(j), "1");
    				}
    			}
    		}
    	}
    }
    
    public void enterGiftBoxMessages(){
    	List<WebElement> giftBoxMessageElements = checkoutContainer.findElements(By.xpath(".//textarea[contains(@id,'giftbox-msg')]"));
    	
    	for(int i=0;i<giftBoxMessageElements.size();i++){
    		String giftBoxMessage = "Automated Gift Box Message " + (i+1);
    		giftBoxMessageElements.get(i).sendKeys(giftBoxMessage);
    		logger.debug("Gift Box Message entered is '{}'", giftBoxMessage);
    	}
    }
}