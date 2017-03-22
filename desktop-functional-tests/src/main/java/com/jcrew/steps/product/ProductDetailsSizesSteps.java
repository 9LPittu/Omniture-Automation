package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailsSizes;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertFalse;

/**
 * Created by ngarcia on 3/22/17.
 */
public class ProductDetailsSizesSteps extends DriverFactory {
    private ProductDetailsSizes sizes = new ProductDetailsSizes(getDriver());

    @Then("Verify that page contains a selected size")
    public void has_selected_size() {
        assertFalse("Size field is not empty", sizes.getSelectedSize().isEmpty());
    }

    @When("User selects random size")
    public void user_selects_random_size() {
        sizes.selectRandomSize();
    }

}
