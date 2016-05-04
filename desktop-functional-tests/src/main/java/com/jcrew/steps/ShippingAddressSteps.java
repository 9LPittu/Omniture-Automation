package com.jcrew.steps;

import com.jcrew.page.ShippingAddress;
import com.jcrew.page.ShoppingBag;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class ShippingAddressSteps extends DriverFactory{
	
	private final ShippingAddress shippingAddressPage = new ShippingAddress(getDriver());
	
	@When("^user fills selected country shipping address$")
    public void user_fills_country_shipping_address() throws Throwable {

        shippingAddressPage.fills_shipping_address_testdata();
        
       // shippingAddressPage.selectCityAndState();

        assertTrue("Billing checkbox should be selected", shippingAddressPage.isBillingAndShippingSameAddress());
    }
	
	@And("^Presses continue button on shipping address$")
    public void presses_continue_button_on_shipping_address() throws Throwable {
        shippingAddressPage.presses_continue_button_on_shipping_address();
    }
}