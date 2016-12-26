package com.jcrew.steps;

import com.jcrew.page.CheckoutShippingEdit;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutShippingEditSteps extends DriverFactory {
    private CheckoutShippingEdit shipping = new CheckoutShippingEdit(getDriver());

    @When("^User continues to Shipping and Gift Options page$")
    public void continue_to_shipping_option() {
    	if(shipping.stateHolder.get("isContinueClicked"))
    		return;
    	
        shipping.continueCheckout();
    }

    @When("User selects a shipping address and continues")
    public void select_shipping_address() {
        shipping.selectAddressFromList();
        shipping.continueCheckout();
    }
    
    @When("User selects a different shipping address and continues")
    public void select_shipping_addressNoDefault() {
        shipping.selectAddressFromListNoDefault();
        shipping.continueCheckout();
    }

    @Then("Verify select shipping address page is displayed")
    public void page_is_displayed() {
        assertTrue("Page is displayed", shipping.isDisplayed());
    }
}
