package com.jcrew.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.jcrew.utils.Util;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutShippingEdit extends Checkout {

    @FindBy(id = "frmSelectShippingAddress")
    private WebElement shippingForm;
    @FindBy(id = "address-book")
    private WebElement address_book;
    
    @FindBy(id="frmMultiShippingAddress")
    private WebElement multiShippingAddressForm;

    public CheckoutShippingEdit(WebDriver driver) {
        super(driver);
        
        if(stateHolder.hasKey("isShippingDisabled"))
			return;

        wait.until(ExpectedConditions.visibilityOf(shippingForm));
    }

    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("shipping");
    }

    public void continueCheckout() {
    	try{
    		if(shippingForm.isDisplayed())
    			nextStep(shippingForm);
    	}    		
    	catch(NoSuchElementException nsee){
			nextStep(multiShippingAddressForm);
    	}
    }
    
    public void selectAddressFromList() {
        selectAddressFromList(shippingForm);
    }
    
    public void selectAddressFromListNoDefault(){
    	selectAddressFromListNoDefault(shippingForm);
    }
    
    public void selectSpecificShippingAddress(String addressLine1){
    	List<WebElement> radioButtons = address_book.findElements(By.xpath(".//span[contains(@class,'address-line') and contains(normalize-space(.),'" + addressLine1 + 
    												"')]/preceding-sibling::input[@class='address-radio']"));
    	
    	if(radioButtons.size()==0){
    		String errorMessage = "'" + addressLine1 + "' address is not found on the Shipping Address page";
    		Util.e2eErrorMessagesBuilder(errorMessage);
    		throw new WebDriverException(errorMessage);
    	}
    	
    	radioButtons.get(0).click();
    	logger.debug("Selected address: {}", addressLine1);
    }
    
    public void selectMultipleShippingAddresses(String[] arrShippingAddresses){
    	WebElement multipleAddressRadioBtn = shippingForm.findElement(By.id("multiShippingAddresses"));
    	multipleAddressRadioBtn.click();
    	
    	continueCheckout();
    	
    	List<WebElement> shippingAddressDropdown = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("shipAddress")));
    	
    	if(arrShippingAddresses.length>0){
    		//multiple specific shipping address selection
    		for(int i=0;i<arrShippingAddresses.length;i++){
    			boolean isAddressSelected = false;
    			Select select = new Select(shippingAddressDropdown.get(i));
    			List<WebElement> options = select.getOptions();
    			for(WebElement option:options){
    				String optionText = option.getText();
    				if(optionText.toLowerCase().contains(arrShippingAddresses[i].toLowerCase())){
    					select.selectByVisibleText(optionText);
    					isAddressSelected = true;
    				}
    			}
    			
    			if(!isAddressSelected){
    				String errorMsg = "Failed to select shipping address '" + arrShippingAddresses[i] + "'";
    				Util.e2eErrorMessagesBuilder(errorMsg);
    				throw new WebDriverException(errorMsg);
    			}else{
    				logger.info("Shipping address selected: {}", arrShippingAddresses[i]);
    			}
    		}
    	}else{
    		//multiple random shipping address selection
    		for(int i=0;i<shippingAddressDropdown.size();i++){
    			Select select = new Select(shippingAddressDropdown.get(i));
    			int randomIndex = Util.randomIndex(select.getOptions().size());
    			select.selectByIndex(randomIndex);
    			logger.info("Shipping address selected: {}", select.getFirstSelectedOption().getText());
    		}
    	}
    }
}
