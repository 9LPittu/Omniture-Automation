package com.jcrew.steps;

import com.jcrew.page.ShoppingBag;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 7/20/16.
 */
public class ShoppingBagSteps extends DriverFactory {
    ShoppingBag shoppingBag = new ShoppingBag(getDriver());

    @Then("User is in shopping bag page$")
    public void verify_user_is_in_bag_page() {

        assertTrue("User should be in shopping bag page", shoppingBag.isShoppingBagPage());
    }

    @When("User clicks check out button")
    public void user_clicks_check_out_button() {
        shoppingBag.clickCheckoutButton();
    }

    @Then("Verify that shopping bag has expected context")
    public void verify_that_shopping_bag_has_expected_context() {
        assertTrue("Shopping bag has the expected context", shoppingBag.verifyContext());
    }

}
