package com.jcrew.steps.checkout;

import com.jcrew.page.checkout.CheckoutShippingAdd;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutShippingAddSteps extends DriverFactory {
    private CheckoutShippingAdd shipping = new CheckoutShippingAdd(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();

    @Then("Verify Shipping Page is displayed")
    public void shipping_page_displayed() {
    	if (stateHolder.hasKey("isShippingDisabled"))
			return;
    	
        assertTrue("Is shipping address", shipping.isDisplayed());
    }
    
    @Then("Verify Shipping Page url is ([^\"]*)")
    public void review_page_url(String url) {
        String current_url = getDriver().getCurrentUrl();
        assertTrue("Shipping page url is " + url, current_url.contains(url));
    }

    @Then("Verify that Shipping title is Checkout")
    public void verify_title() {
        String title = shipping.getTitle();
        assertTrue("Title is Checkout", "Checkout".equalsIgnoreCase(title));
    }

    @Then("Verify checkout breadcrumb is SHIPPING ADDRESS")
    public void verify_progress() {
        assertEquals("Breadcrumb is SHIPPING ADDRESS", "SHIPPING ADDRESS", shipping.getBreadCrumb());
    }

    @When("^User fills shipping data and continues$")
    public void fill_data_and_continue() {
        shipping.fillShippingData();
        shipping.continueCheckout();
        stateHolder.put("atpAddressType","regular");
    }
    
    @When("^User fills QAS shipping data and continues$")
    public void fill_qas_data_and_continue() {
        shipping.fillQASShippingData();
        shipping.continueCheckout();
        stateHolder.put("atpAddressType","regular");
    }

    @When("^User fills APO shipping data and continues$")
    public void fill_apo_data_and_continue() {
        shipping.fillAPOShippingData();
        shipping.continueCheckout();
        stateHolder.put("atpAddressType","apo");
    }

    @When("^User fills FPO shipping data and continues$")
    public void fill_fpo_data_and_continue() {
        shipping.fillFPOShippingData();
        shipping.continueCheckout();
        stateHolder.put("atpAddressType","fpo");
    }
    
    @When("In Shipping Address Page, user clicks continue")
    public void shipping_address_continue() {
        shipping.continueWithDefaultAddress();
    }
}