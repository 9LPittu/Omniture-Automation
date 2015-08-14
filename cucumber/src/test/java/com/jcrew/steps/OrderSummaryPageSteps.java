package com.jcrew.steps;

import com.jcrew.page.OrderSummaryPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

public class OrderSummaryPageSteps extends DriverFactory {

    @Then("User should be in order summary page")
    public void user_should_be_in_order_summary_page() {
        new OrderSummaryPage(getDriver()).isOrderSummaryPage();
    }
}
