package com.jcrew.steps;

import com.jcrew.page.CheckoutBillingAddress;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 5/9/16.
 */
public class CheckoutBillingAddressSteps extends DriverFactory {

    private CheckoutBillingAddress billing = new CheckoutBillingAddress(getDriver());

    @Then("Verify Billing Address page is displayed")
    public void is_shipping_options() {
        assertTrue("Is billing address page", billing.isDisplayed());
    }

    @When("User fills billing address and continues")
    public void save_billing_address() {
        billing.fillFormData();
        billing.continueCheckout();
    }
}
