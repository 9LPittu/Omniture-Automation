package com.jcrew.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        
        if(stateHolder.hasKey("isShippingAddressContinueClicked") || stateHolder.hasKey("isShippingDisabled"))
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
    
    public void selectMultipleShippingAddressRadioButton(){
    	selectMultipleShippingAddressRadioButton(shippingForm);    	
    }
}
