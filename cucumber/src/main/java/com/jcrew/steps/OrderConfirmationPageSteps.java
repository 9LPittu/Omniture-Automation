package com.jcrew.steps;

import static org.junit.Assert.assertTrue;

import com.jcrew.page.OrderConfirmationPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Reporting;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

public class OrderConfirmationPageSteps extends DriverFactory {
	
	private final OrderConfirmationPage orderConfirmation = new OrderConfirmationPage(getDriver());
	
	private Scenario scenario;

    
	@Before
	public void getScenarioObject(Scenario s){
	  this.scenario = s;
	}

    @Then("User should be in order confirmation page")
    public void user_should_be_in_order_confirmation_page() {
        assertTrue("User should be in order confirmation page", new OrderConfirmationPage(getDriver()).isOrderConfirmationPage());
    }
    
    @And("verify order number is generated")
	public void verify_order_number_generated() throws InterruptedException{
		assertTrue("Order number should be generated", orderConfirmation.verifyOrderNumberGenerated());

		

	}	
}
