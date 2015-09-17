package com.jcrew.steps;

import com.jcrew.page.BillingPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class BillingPageSteps extends DriverFactory {

    private final BillingPage billingPage = new BillingPage(getDriver());

    @When("^Fills required payment data in billing page$")
    public void fills_required_payment_data() throws Throwable {

        billingPage.fill_required_payment_data();

    }

    @And("^Verify user is in billing page$")
    public void verify_user_is_in_billing_page() throws Throwable {
        assertTrue("User should be in billing page", billingPage.isBillingPage());
    }

    @And("^Submits payment data in billing page$")
    public void Submits_payment_data_in_billing_page() throws Throwable {
        billingPage.submit_form();
    }
}
