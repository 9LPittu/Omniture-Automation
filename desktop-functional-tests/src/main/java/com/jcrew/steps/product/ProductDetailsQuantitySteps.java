package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailsQuantity;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 3/23/17.
 */
public class ProductDetailsQuantitySteps extends DriverFactory {
    private ProductDetailsQuantity quantity = new ProductDetailsQuantity(getDriver());

    @When("User selects random quantity")
    public void user_selects_random_quantity() {
        quantity.selectRandomQty();
    }

    @Then("Verify quantity dropdown is displayed")
    public void quantity_is_displayed() {
        assertTrue("Product quantity is displayed", quantity.isDisplayed());
    }
}
