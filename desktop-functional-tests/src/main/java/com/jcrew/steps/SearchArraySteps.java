package com.jcrew.steps;

import com.jcrew.page.SearchArray;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class SearchArraySteps extends DriverFactory {
    SearchArray productsArray = new SearchArray(getDriver());

    @When("User selects random product from search array")
    public void user_selects_random_product(){
        productsArray.selectRandomProduct();
    }

}
