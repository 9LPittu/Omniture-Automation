package com.jcrew.steps;

import com.jcrew.page.PaymentMethods;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/11/16.
 */
public class PaymentMethodsSteps extends DriverFactory{
    PaymentMethods paymentMethods = new PaymentMethods(getDriver());

    @Then("Verify user is in Payment Methods page")
    public void user_is_in_payment_methods_page() {
        assertTrue("User is in payments methods page", paymentMethods.isPaymentMethodsPage());
    }

    @When("User adds new payment method")
    public void user_adds_new_payment_method() {
        paymentMethods.addNewPaymentMethod();
    }


    @And("User has (\\d+) payment methods")
    public void userHasPaymentMethods(int methods) {
        assertEquals("User has " + methods + " in My Payment Methods page",
                paymentMethods.getPaymentMethodsNumber(), methods);
    }
}
