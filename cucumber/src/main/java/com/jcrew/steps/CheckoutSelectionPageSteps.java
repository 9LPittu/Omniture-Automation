package com.jcrew.steps;

import com.jcrew.page.CheckoutSelectionPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class CheckoutSelectionPageSteps extends DriverFactory {


    private final CheckoutSelectionPage checkoutSelectionPage = new CheckoutSelectionPage(getDriver());

    @When("^Selects to checkout as guest$")
    public void selects_to_checkout_as_guest() throws Throwable {
        checkoutSelectionPage.selects_to_checkout_as_guest();

    }

    @Given("^User provides username and password$")
    public void user_provides_username_and_password() throws Throwable {
        checkoutSelectionPage.set_username_and_password();
    }

    @And("^Clicks sign in and checkout$")
    public void clicks_sign_in_and_checkout() throws Throwable {
        checkoutSelectionPage.click_sign_in_and_checkout();
    }
}
