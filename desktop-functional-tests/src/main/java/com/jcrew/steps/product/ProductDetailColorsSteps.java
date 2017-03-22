package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailColors;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertFalse;

/**
 * Created by ngarcia on 3/22/17.
 */
public class ProductDetailColorsSteps extends DriverFactory {
    private ProductDetailColors colors = new ProductDetailColors(getDriver());

    @When("User selects random color")
    public void user_selects_random_color() {
        colors.selectRandomColor();
    }

    @Then("Verify that page contains a selected color")
    public void has_selected_color() {
        assertFalse("Color field is not empty", colors.getSelectedColor().isEmpty());
    }
}
