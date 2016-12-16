package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutShippingEdit extends Checkout {

    @FindBy(id = "frmSelectShippingAddress")
    private WebElement shippingForm;
    @FindBy(id = "address-book")
    private WebElement address_book;

    public CheckoutShippingEdit(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(shippingForm));
    }

    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");

        return bodyId.equals("shipping");
    }

    public void continueCheckout() {
        nextStep(shippingForm);
    }

    public void selectAddressFromList() {
        selectAddressFromList(shippingForm);
    }
    
    public void selectAddressFromListNoDefault(){
    	selectAddressFromListNoDefault(shippingForm);
    }
    
    public void selectSpecificShippingAddress(String shippingAddress){
    	
    }
}
