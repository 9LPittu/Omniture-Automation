package com.jcrew.steps;

import static org.junit.Assert.assertTrue;
import com.jcrew.page.OrderConfirmationPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class OrderConfirmationPageSteps extends DriverFactory {
	
	private final OrderConfirmationPage orderConfirmation = new OrderConfirmationPage(getDriver());

    @Then("User should be in order confirmation page")
    public void user_should_be_in_order_confirmation_page() {
        assertTrue("User should be in order confirmation page", new OrderConfirmationPage(getDriver()).isOrderConfirmationPage());
    }
    
    @And("verify order number is generated")
	public void verify_order_number_generated() throws InterruptedException{
		assertTrue("Order number should be generated", orderConfirmation.verifyOrderNumberGenerated());
	}	
}
