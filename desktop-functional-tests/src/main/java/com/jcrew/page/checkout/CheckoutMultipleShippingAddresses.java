package com.jcrew.page.checkout;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.jcrew.utils.Util;

public class CheckoutMultipleShippingAddresses extends Checkout {

    @FindBy(id = "frmMultiShippingAddress")
    private WebElement multiShippingAddressForm;
    

    public CheckoutMultipleShippingAddresses(WebDriver driver) {
        super(driver);
        
        if(stateHolder.hasKey("isShippingDisabled"))
			return;

        wait.until(ExpectedConditions.visibilityOf(multiShippingAddressForm));
    }

    @Override
    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("shippingMulti");
    }
    
    public void multiShippingAddressSelection(List<String> shippingAddressesList){
    	
    	List<WebElement> shippingAddressDropdown = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("shipAddress")));
    	
    	if(shippingAddressesList.size()>0){
    		//multiple specific shipping address selection
    		for(int i=0;i<shippingAddressesList.size();i++){
    			boolean isAddressSelected = false;
    			Select select = new Select(shippingAddressDropdown.get(i));
    			List<WebElement> options = select.getOptions();
    			for(WebElement option:options){
    				String optionText = option.getText();
    				if(optionText.toLowerCase().contains(shippingAddressesList.get(i).toLowerCase())){
    					select.selectByVisibleText(optionText);
    					isAddressSelected = true;
    				}
    			}
    			
    			if(!isAddressSelected){
    				String errorMsg = "Failed to select shipping address '" + shippingAddressesList.get(i) + "'";
    				Util.e2eErrorMessagesBuilder(errorMsg);
    				throw new WebDriverException(errorMsg);
    			}else{
    				logger.info("Shipping address selected: {}", shippingAddressesList.get(i));
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
    
    public void continueCheckout() {
    	nextStep(multiShippingAddressForm);
    }
}