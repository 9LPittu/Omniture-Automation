package com.jcrew.steps;

import com.jcrew.page.BillingPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BillingPageSteps extends DriverFactory {



    @When("^Fills required payment data$")
    public void fills_required_payment_data() throws Throwable {

        BillingPage billingPage = new BillingPage(driver);

        billingPage.fill_required_payment_data();

        assertTrue("Credit/Debit Payment should be selected", billingPage.isCreditDebitPayment());

        assertTrue("Billing Address similar to Shipping Address should be selected",
                billingPage.isBillingAndShippingAddressEqual());

        billingPage.submit_form();

    }

}
