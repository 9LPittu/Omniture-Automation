package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.page.SearchPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.TimeoutException;

import java.util.List;

import static org.junit.Assert.*;

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
        String[] sortByOptionsAsArray = sortByOptions.split(",");
        for (String sortByOption : sortByOptionsAsArray) {
            String trimmedSortByOption = sortByOption.trim();
            assertTrue("Sort by option " + trimmedSortByOption + " is not present ",
                    searchPage.isSortByOptionDisplayed(trimmedSortByOption));
        }
    }

    @And("^([^\"]*) sort by option should be selected$")
    public void sort_by_option_should_be_selected(String sortByOption) throws Throwable {
        assertTrue(sortByOption + " should have been selected", searchPage.isSortByOptionSelected(sortByOption));
    }

    @And("^Selects a product with no sale price$")
    public void clicks_on_a_product_no_sale_price() {
        assertTrue("Product should be selected", searchPage.click_on_the_product());
    }

    @And("^User is in corresponding valid pdp$")
    public void user_is_in_valid_pdp() {
        assertEquals("Product name is not the same", "Girls' Nellystella� Chloe dress", searchPage.getProductName());
        assertEquals("Product price is not the same", "$124.00", searchPage.getProductPrice());
    }

    @And("^User is in valid pdp for sale product$")
    public void user_is_in_valid_sale_pdp() {
        assertEquals("Product name is not the same", "Girls' Loulie dress in poplin", searchPage.getProductName());
        assertTrue("Product price is not the same", searchPage.getProductPrice().contains("$148.00"));
        assertTrue("Product sale price is not the same", searchPage.getProductSalePrice().contains("$119.99"));
    }

    @And("^User scrolls down the page$")
    public void user_scrolls_down() throws InterruptedException {
        assertTrue("not scrolled down", searchPage.scroll_down_the_page());
    }

    @And("^User scrolls up the page$")
    public void user_scrolls_up() {
        assertTrue("not scrolled down", searchPage.scroll_up_the_page());

    }

    @And("^Product details are displayed$")
    public void product_details_are_displayed() {
        assertEquals("product name is not the same", "Girls' tulle corsage dress", searchPage.getProductName());
    }

    @And("^Verify amount of items displayed is (\\d+)$")
    public void verify_amount_of_items_displayed_is(int itemNumber) throws Throwable {
        assertEquals("Number of items should be " + itemNumber,
                itemNumber, searchPage.getProductArrayCount());

    }

    @And("^Verify (\\d+) available colors for ([^\"]*) are displayed$")
    public void verify_products_with_available_colors_for_product_id_are_displayed(int numberOfProducts, String productId) throws Throwable {

        assertEquals("Number of colors for product did not match",
                numberOfProducts, searchPage.getAllProductsDisplayedFor(productId).size());

    }

    @And("^Click on ([^\"]*) refinement$")
    public void click_on_refinement(String refinement) throws Throwable {
        searchPage.click_refinement(refinement);
    }

    @Then("^Validate ([^\"]*) option is selected under ([^\"]*) refinement$")
    public void validate_option_is_selected_under_refinement(String option, String refinement) throws Throwable {

        assertTrue(option + " Option should have been selected for refinement " + refinement,
                searchPage.isOptionSelectedForRefinementWithAccordionOpen(option, refinement));

    }

    @Then("^Select ([^\"]*) option from ([^\"]*) refinement$")
    public void select_option_from_refinement(String option, String refinement) throws Throwable {
        searchPage.select_option_from_refinement(option, refinement);
    }

    @And("^Verify ([^\"]*) option is displayed as selected for ([^\"]*) refinement")
    public void verify_option_is_unselected_and_option_is_displayed_as_selected_for(
            String optionSelected, String refinement) throws Throwable {

        assertTrue("Accordion should have displayed expected selected option",
                searchPage.isOptionSelectedForRefinementWithAccordionClosed(optionSelected, refinement));

    }

    @Then("^Validate ([^\"]*) option is NOT selected under ([^\"]*) refinement$")
    public void validate_option_is_not_selected_under_refinement(String option, String refinement) throws Throwable {

        assertFalse(option + " Option should have been selected for refinement " + refinement,
                searchPage.isOptionSelectedForRefinementWithAccordionOpen(option, refinement));

    }
}
    

