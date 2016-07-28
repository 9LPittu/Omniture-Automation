package com.jcrew.steps;

import com.jcrew.page.ArraySearch;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.Then;
        import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArraySearchSteps extends DriverFactory {

    ArraySearch searchArray = new ArraySearch(getDriver());


    @When("User selects random product from array")
    public void user_selects_random_product() {
        searchArray.selectRandomProduct();
    }

    @Then("User is in search results page")
    public void user_is_in_search_results_page() {
        assertTrue("User should be in search page", searchArray.isSearchPage());
    }

    @Then("^Verify proper currency symbol is displayed on search grid list$")
    public void verify_currency_on_product_gridlist(){
        String countryName = searchArray.country.getName();
        List<String> listPrice = searchArray.getPrices();
        for(String price : listPrice) {
            assertTrue("List price " + price + " matches country context "+countryName,
                    CurrencyChecker.isValid(price, searchArray.country));
        }

        List<String> salePrice = searchArray.getSalePrices();
        for(String price : salePrice) {
            assertTrue("Sale price " + price + " matches country context "+countryName,
                    CurrencyChecker.isValid(price, searchArray.country));
        }

        List<String> wasPrice = searchArray.getWasPrices();
        for(String price : wasPrice) {
            assertTrue("Was price " + price + " matches country context "+countryName,
                    CurrencyChecker.isValid(price, searchArray.country));
        }
    }

}