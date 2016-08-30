package com.jcrew.steps;

import com.jcrew.page.CheckoutQAS;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 6/2/16.
 */
public class CheckoutQASSteps extends DriverFactory {
    private CheckoutQAS qas = new CheckoutQAS(getDriver());

    @Then("Verify QAS page is displayed")
    public void qas_page_displayed() {
        assertTrue("QAS page is displayed", qas.isDisplayed());
    }

    @When("User selects a suggested address and continues")
    public void select_suggested_address_continue() {
        qas.selectSuggested();
        qas.clickContinue();
    }
}
