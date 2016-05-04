package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;


public class OrderConfirmationSteps extends DriverFactory {
	
	private final OrderConfirmation orderConfirmation = new OrderConfirmation(getDriver());

	@Then("User should be in order confirmation page")
    public void user_should_be_in_order_confirmation_page() {
        assertTrue("User should be in order confirmation page", orderConfirmation.isOrderConfirmationPage());
    }
}