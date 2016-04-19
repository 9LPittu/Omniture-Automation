package com.jcrew.steps;

import com.jcrew.page.ProductsArray;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductArraySteps extends DriverFactory {
    ProductsArray productsArray = new ProductsArray(getDriver());

    @When("User selects random product from array")
    public void user_selects_random_product(){
        productsArray.selectRandomProduct();
    }

    @Then("Verify context in the array page")
    public void verify_that_all_products_in_first_page_contains_expected_cucrrency() {
        assertTrue("all products in first page contains expected currency", productsArray.verifyContext());
    }
}
