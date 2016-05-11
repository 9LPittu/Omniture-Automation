package com.jcrew.steps;

import com.jcrew.page.SearchArray;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class SearchArraySteps extends DriverFactory {
    
	SearchArray productsArray = new SearchArray(getDriver());


    @When("User selects random product from search array")
    public void user_selects_random_product(){
        productsArray.selectRandomProduct();
    }
    
    @Then("User is in search results page")
    public void user_is_in_search_results_page() {

        assertTrue("User should be in search page", productsArray.isSearchPage());
        

    }

}
