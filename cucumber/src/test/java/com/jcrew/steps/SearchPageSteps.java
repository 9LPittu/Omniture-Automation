package com.jcrew.steps;

import com.jcrew.page.SearchPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import static org.junit.Assert.assertTrue;

public class SearchPageSteps extends DriverFactory {

    private SearchPage searchPage = new SearchPage(driver);

    @Given("User is in search results page")
    public void user_is_in_search_results_page() {

        assertTrue("User should be in search page", searchPage.isSearchPage());

    }

    @And("^Search results are displayed")
    public void search_results_are_valid() throws Throwable {
        assertTrue("There should be search results", searchPage.areResultsDisplayed());
    }
}
