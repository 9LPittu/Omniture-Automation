package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.page.SearchPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.openqa.selenium.TimeoutException;

import static org.junit.Assert.assertTrue;

public class SearchPageSteps extends DriverFactory {

    private SearchPage searchPage = new SearchPage(getDriver());
    private HomePage homePage = new HomePage(getDriver());

    @Given("User is in search results page")
    public void user_is_in_search_results_page() {

        assertTrue("User should be in search page", searchPage.isSearchPage());

    }

    @And("^Search results are displayed")
    public void search_results_are_valid() throws Throwable {
        try {
            assertTrue("There should be search results", searchPage.areResultsDisplayed());

        } catch (TimeoutException tme) {
            // Sometimes the system hangs on the first click to the search button in input, trying again just
            // in case this happened but now using enter on the search input field.

            homePage.hit_enter_in_search_field();

            assertTrue("There should be search results", searchPage.areResultsDisplayed());
        }
    }
}
