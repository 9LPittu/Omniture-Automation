package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;


public class CheckoutSelectionSteps extends DriverFactory {
	
	private final CheckoutSelection checkoutSelectionPage = new CheckoutSelection(getDriver());
	
	@When("^Selects to checkout as guest$")
    public void selects_to_checkout_as_guest() throws Throwable {
        checkoutSelectionPage.selects_to_checkout_as_guest();
    }
}