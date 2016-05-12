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

    @Then("^Verify proper currency symbol is displayed on product grid list$")
    public void verify_currency_on_product_gridlist(){
        assertTrue("Currency on product gridlist", productsArray.isCorrectCurrencySymbolonProductGridList());
    }

    @Then("^Verify proper currency symbol is displayed on PDP page$")
    public void verify_currency_on_product_PDP(){
        assertTrue("Currency on product details page",productsArray.isCorrectCurrencySymbolonPDP());
    }

}
