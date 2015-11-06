package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.page.ProductDetailPage;
import com.jcrew.page.SalePage;
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
    private SalePage salePage = new SalePage(getDriver());
    private HomePage homePage = new HomePage(getDriver());
    private ProductDetailPage productDetailPage = new ProductDetailPage(getDriver());

    @Given("User is in search results page")
    public void user_is_in_search_results_page() {

        assertTrue("User should be in search page", searchPage.isSearchPage());

    }

    @And("User is in sale results page")
    public void user_is_in_sale_results_page() {
        //assertTrue("User should be in sale result page", salePage.isSalePage());
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

    @And("^User clicks on ([^\"]*) selector$")
    public void user_clicks_on_gender_selector(String gender) {
        searchPage.click_on_gender_selector(gender);

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

    @And("^([^\"]*) should be selected by default$")
    public void sort_by_option_should_be_selected(String sortByOption) throws Throwable {
        assertTrue(sortByOption + " should have been selected", searchPage.isSortByOptionSelected(sortByOption));
    }

    @And("^User scrolls down the page$")
    public void user_scrolls_down() throws InterruptedException {
        assertTrue("not scrolled down", searchPage.scroll_down_the_page());
    }

    @And("^User scrolls up the page$")
    public void user_scrolls_up() {
        assertTrue("not scrolled down", searchPage.scroll_up_the_page());

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

    @Then("^Select ([^\"]*) single option from ([^\"]*) refinement$")
    public void select_option_from_refinement(String option, String refinement) throws Throwable {
        searchPage.select_option_from_refinement(option, refinement);
    }

    @Then("^Select ([^\"]*) multiple option from ([^\"]*) refinement$")
    public void select_option_from_the_multiple_select_refinement(String option, String refinement) throws Throwable {
        searchPage.select_option_from_multiple_select_refinement(option, refinement);
    }

    @And("^Verify ([^\"]*) value is displayed next to ([^\"]*) refinement")
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

    @And("^Verify ([^\"]*) refinement drawer remains open$")
    public void verify_refinement_drawer_remains_open(String refinement) throws Throwable {
        assertTrue("Drawer should be open", searchPage.isRefinementOpen(refinement));
    }

    @Then("^Click on ([^\"]*) refinement close drawer icon$")
    public void click_on_refinement_close_drawer_icon(String refinement) throws Throwable {
        searchPage.click_refinement_close_drawer(refinement);
    }

    @Then("^Click on done button for refinement filter menu$")
    public void click_on_done_button_for_refinement_filter_menu() throws Throwable {
        searchPage.click_refinement_menu_done_button();
    }

    @Then("^Verify number of results is less than (\\d+)$")
    public void verify_number_of_results_is_less_than(int resultNumber) throws Throwable {
        assertTrue("Results should be less than " + resultNumber,
                resultNumber > searchPage.getCurrentNumberOfResults());
    }

    @And("^Verify ([^\"]*) option breadcrumb is created$")
    public void verify_option_breadcrumb_is_created(String option) throws Throwable {
        assertTrue(option + " option breadcrumb should have been displayed",
                searchPage.isBreadcrumbDisplayedFor(option));
    }

    @And("^User selects a product with no sale price and verifies is in corresponding valid pdp$")
    public void User_selects_a_product_with_no_sale_price_and_verifies_is_in_corresponding_valid_pdp() throws Throwable {
        String productName = searchPage.click_on_no_sale_price_product();
        assertEquals("PDP and clicked product do not have the same name", productName,
                productDetailPage.getProductNameFromPDP());
    }
    
    @And("^User selects ([^\"]*) category$")
    public void User_selects_sale_category(String saleCategory) throws Throwable{
    	salePage.selectSaleCategory(saleCategory);
    }
    
    @Then("^verify SALE page is displayed$")
    public void verify_sale_page_displayed(){
    	assertTrue("Sale page should be displayed",salePage.isSalePageDisplayed());
    }
    
    @And("^verify REFINE button is displayed$")
    public void verify_refine_button_displayed(){
    	assertTrue("Refine button should be displayed",salePage.isRefineButtonDisplayed());
    }
    
    @And("^verify default filter name displayed is ([^\"]*)$")
    public void verify_default_filter_displayed(String category){
    	assertTrue("Selected category name should be displayed",salePage.verifySelectedCategory(category));
    }
    
    @Then("^click on REFINE button$")
    public void click_refine_button(){
    	salePage.clickRefineButton();
    }
    
    @And("^verify refinement page is displayed$")
    public void verify_refinement_page_displayed(){
    	salePage.verifyRefinementPageDisplayed();
    }
    
    @Then("^verify NEW IN SALE checkbox is selected by default$")
    public void verify_new_in_sale_checkbox_selected_default(){
    	salePage.verifyNewInSaleCheckboxSelectedByDefault();
    }
    
    @Then("^verify ([^\"]*) filter displayed on refinement page$")
    public void verify_filternames_on_refinement_Page(String filterName){    	
    	assertTrue("Filter should be displayed on the refinement page", salePage.verifyFilterNameDisplayed(filterName));
    }
    
    @Then("^verify accordion drawer is displayed when ([^\"]*) filter is clicked$")
    public void verify_accordion_drawer_displayed_when_filter_clicked(String filter){
    	assertTrue("Accordian drawer should be displayed when clicked on filter",salePage.verifyAccordianDrawerOpenedWhenFilterClicked(filter));
    }
    
    @Then("^verify ([^\"]*) accordion drawer is not displayed$")
    public void verifyAccordianDrawerNotDisplayed(String filter){
    	assertTrue("Accordian drawer should not displayed for the " + filter + " filter",salePage.verifyAccordianNotDisplayed(filter));
    }
    
    @Then("^verify first sort option is ([^\"]*)$")
    public void verify_first_sort_option(String firstSortOption){
    	assertTrue("Verify Sort section first option is NEW IN SALE",salePage.verifySortSectionFirstOption(firstSortOption));
    }
    
    @Then("^verify first option NEW IN SALE is selected by default$")
    public void verify_first_sort_option_selected_by_default(){
    	assertTrue("Verify sort section first option NEW IN SALE is selected by default", salePage.verifyNewInSaleCheckboxSelectedByDefault());
    }
}