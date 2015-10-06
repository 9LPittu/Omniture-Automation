package com.jcrew.steps;

import com.jcrew.page.OrderConfirmationPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

public class OrderConfirmationPageSteps extends DriverFactory {

    @Then("User should be in order confirmation page")
    public void user_should_be_in_order_confirmation_page() {
        new OrderConfirmationPage(getDriver()).isOrderConfirmationPage();
    }
}
