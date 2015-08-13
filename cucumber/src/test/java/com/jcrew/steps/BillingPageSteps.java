package com.jcrew.steps;

import com.jcrew.page.BillingPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class BillingPageSteps extends DriverFactory {

    @When("^Fills required payment data in billing page$")
    public void fills_required_payment_data() throws Throwable {

        BillingPage billingPage = new BillingPage(getDriver());

        billingPage.fill_required_payment_data();

        assertTrue("Credit/Debit Payment should be selected", billingPage.isCreditDebitPayment());

        assertTrue("Billing Address similar to Shipping Address should be selected",
                billingPage.isBillingAndShippingAddressEqual());

        billingPage.submit_form();

    }

}
