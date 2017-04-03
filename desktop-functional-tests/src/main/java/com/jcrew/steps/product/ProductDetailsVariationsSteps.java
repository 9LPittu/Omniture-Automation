package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailsVariations;
import com.jcrew.pojo.Product;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 4/3/17.
 */
public class ProductDetailsVariationsSteps extends DriverFactory {
    private ProductDetailsVariations variations = new ProductDetailsVariations(getDriver());
    private StateHolder stateHolder = StateHolder.getInstance();

    @Then("Verify price matches with category array")
    public void price_matches_category_array() {
        Product product = (Product) stateHolder.get("fromArray");
        assertEquals("Product price matches category array", product.getPrice(), variations.getProductPrice());
    }

    @When("^User selects random variant on the PDP page$")
    public void user_selects_random_variant_on_PDP_Page(){
        variations.selectRandomVariantOnPDP();
    }

    @Then("Verify product variations are displayed")
    public void variations_are_displayed() {
        assertTrue("Product shows variations", variations.areVariationsDisplayed());
    }
}
