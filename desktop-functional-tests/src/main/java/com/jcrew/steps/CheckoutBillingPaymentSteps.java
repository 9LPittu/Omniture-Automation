package com.jcrew.steps;

import com.jcrew.page.CheckoutBillingPayment;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 5/9/16.
 */
public class CheckoutBillingPaymentSteps extends DriverFactory {

    private CheckoutBillingPayment billing = new CheckoutBillingPayment(getDriver());

    @Then("Verify Billing Payment page is displayed")
    public void is_shipping_options() {
        assertTrue("Is billing address page", billing.isDisplayed());
    }

    @Then("^Verify account Billing payment page is displayed$")
    public void is_account_payment_method() {
        assertTrue("Is payment method page", billing.isPaymentMethodsPage());
    }

    @When("User fills billing payment with ([^\"]*) and continues")
    public void save_billing_address(String cardType) {
        billing.fillPaymentMethod(cardType);
        billing.continueCheckout();
    }
    
    @When("User adds new payment method")
    public void user_adds_new_payment_method() {
    	billing.addNewPaymentMethod();
    }

    @When("User edits billing payment information and continues")
    public void edit_billing_payment() {
        billing.editPayment();
        billing.continueCheckout();
    }
    
    @When("User fills payment method and continue")
    public void user_fills_payment_method_and_continue() {
        billing.fillPayment();
        billing.submitPayment();
    }
    
    @And("User has (\\d+) payment methods")
    public void userHasPaymentMethods(int methods) {
        assertEquals("User has " + methods + " in My Payment Methods page",
        		billing.getPaymentMethodsNumber(), methods);
    }
    
    @When("In Payment page, user clicks continue")
    public void payment_continue() {
    	billing.submitPayment();
    }
}
