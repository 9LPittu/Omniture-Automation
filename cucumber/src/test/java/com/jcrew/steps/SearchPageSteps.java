package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.page.SearchPage;
import com.jcrew.util.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.TimeoutException;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SearchPageSteps extends DriverFactory {

    private SearchPage searchPage = new SearchPage(getDriver());
    private HomePage homePage = new HomePage(getDriver());

    @Given("User is in search results page")
    public void user_is_in_search_results_page() {

        assertTrue("User should be in search page", searchPage.isSearchPage());

    }

    @And("^Search results are displayed$")
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

    @And("^Gender selectors are displayed$")
    public void gender_results_are_valid() {
        List<String> gender_results = searchPage.areGenderSelectorsDisplayed();
        for (String gender_result : gender_results) {
            assertTrue("There should be gender selectors displayed", gender_result.equals("gender"));

        }
    }

    @And("^Clicks on gender selector$")
    public void user_clicks_on_gender_selector() {
        searchPage.click_on_gender_selector();

    }

    @Then("^User is in gender refine array page$")
    public void user_is_in_gender_with_refine_button_page() {
        assertTrue("User should be in gender with refine page", searchPage.isRefinePage());
    }

    @And("^Refine button is displayed$")
    public void refine_button_is_displayed() {
        assertTrue("Refine button should be displayed", searchPage.isRefineButtonDisplayed());
    }

    @Given("^Refine button is clicked$")
    public void refine_button_is_clicked() throws Throwable {
        searchPage.click_refine_button();
    }

    @And("([^\"]*) filter refinements should appear")
    public void search_filter_options(String filterRefinements) {
        String[] filterRefinementsAsArray = filterRefinements.split(",");
        for (String filterRefinement : filterRefinementsAsArray) {
            String trimmedRefinement = filterRefinement.trim();
            assertTrue("Refinement " + trimmedRefinement + " is not present ",
                    searchPage.isRefinementDisplayed(trimmedRefinement));
        }
    }

    @And("([^\"]*) sort by options should appear")
    public void sort_by_options(String sortByOptions) {
        String [] sortByOptionsAsArray = sortByOptions.split(",");
        for (String sortByOption: sortByOptionsAsArray) {
            String trimmedSortByOption = sortByOption.trim();
            assertTrue("Sort by option " + trimmedSortByOption + " is not present ",
                    searchPage.isSortByOptionDisplayed(trimmedSortByOption));
        }
    }

    @And("^([^\"]*) sort by option should be selected$")
    public void sort_by_option_should_be_selected(String sortByOption) throws Throwable {
        assertTrue(sortByOption + " should have been selected", searchPage.isSortByOptionSelected(sortByOption));
    }
}
