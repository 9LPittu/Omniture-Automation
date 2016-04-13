package com.jcrew.steps;

import com.jcrew.page.ProductsArray;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductArraySteps extends DriverFactory {
    ProductsArray productsArray = new ProductsArray(getDriver());

    @When("User selects random product from array")
    public void user_selects_random_product(){
        productsArray.selectRandomProduct();
    }

}
