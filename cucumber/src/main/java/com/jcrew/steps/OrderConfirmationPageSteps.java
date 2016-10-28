package com.jcrew.steps;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import com.jcrew.page.OrderConfirmationPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class OrderConfirmationPageSteps extends DriverFactory {

    private OrderConfirmationPage orderConfirmation;
    
    private boolean isProduction = false;
    
    public OrderConfirmationPageSteps() {
        PropertyReader properties = PropertyReader.getPropertyReader();
        String environment = properties.getProperty("environment");

        if("production".equalsIgnoreCase(environment)) {
            isProduction = true;
        } else {
        	orderConfirmation = new OrderConfirmationPage(getDriver());
        }
    }

    @Then("User should be in order confirmation page")
    public void user_should_be_in_order_confirmation_page() {
        assertTrue(Util.getSelectedCountryName() + "User should be in order confirmation page", orderConfirmation.isOrderConfirmationPage());
    }
    
    @And("verify order number is generated")
	public void verify_order_number_generated() throws InterruptedException{
		assertTrue(Util.getSelectedCountryName() + "Order number should be generated", orderConfirmation.verifyOrderNumberGenerated());
	}
    
    @Then("Verify Gift message is ([^\"]*)")
    public void gift_message(String message) {
        if(!isProduction) {
            String messageInPage = orderConfirmation.getGitfMessage();

            assertEquals("Confirmation page has the expected gift message", message.toLowerCase(), messageInPage.toLowerCase());
        }
    }
}
