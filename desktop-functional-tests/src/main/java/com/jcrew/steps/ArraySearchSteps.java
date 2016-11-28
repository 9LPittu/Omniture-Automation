package com.jcrew.steps;

import com.jcrew.page.ArraySearch;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.Then;
        import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArraySearchSteps extends DriverFactory {

    ArraySearch searchArray = new ArraySearch(getDriver());
    StateHolder stateHolder = StateHolder.getInstance();


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
    
    @Then("Verify Sale array page is displayed$")
    public void verify_sale_array() {
    	if (!stateHolder.hasKey("secondPromoVerification")) {
	    	boolean isSearchArray = searchArray.isSalePage();
	    	
	    	String saleTitle = searchArray.getHeaderTitle();
	    	boolean isSaleTitle = saleTitle.equalsIgnoreCase("sale");
	    	
	    	assertTrue("Sale array should be displayed",isSearchArray && isSaleTitle);
    	}
    }
    
    @Then("Verify filters are displayed on array page$")
    public void filters_display_on_array_page() {
    	assertTrue("Filters should be displayed on array page ",searchArray.isFiltersDisplayed());	
    }
    
    @Then("Verify ([^\"]*) filter displays ([^\"]*)$")
    public void verify_gender_filter_value(String filterName, String expectedValue) {
    	if (!stateHolder.hasKey("secondPromoVerification")) {
	    	filterName = filterName.toLowerCase().trim();
	    	expectedValue = expectedValue.toLowerCase().trim();
	    	
	    	if (filterName.equalsIgnoreCase("gender"))
	    		filterName="shop for";
	    	
	    	String actualValue = searchArray.getFilterValue(filterName).toLowerCase().trim();
	    	assertEquals(filterName + " filter should match ", expectedValue , actualValue);	
    	}	
    }
    
    @Then("Verify following gender selectors are displayed$")
    public void verify_gender_selectors(List<String> expectedGenderSelectors){
    	List<String> actualGenderSelectors = searchArray.getGenderSelectors();
    	
    	for (String gender:expectedGenderSelectors) {
    		gender = gender.toLowerCase().trim();
    		assertTrue("Gender Selector " + gender + " should be displayed", actualGenderSelectors.contains(gender) );	
    	}
    }
    
    @Then("User clicks on ([^\"]*) gender selector$")
    public void select_gender_selector(String gender) {
    	searchArray.clickGenderSelector(gender);
    }
    
    @Then("Verify that search result number is greater than (\\d+)")
    public void search_results_number_greater_than(int greaterThan) {
    	if (!stateHolder.hasKey("secondPromoVerification")) {
	    	int searchResults = searchArray.getSearchResultsNumber();
	
	        assertTrue("Results number is greater than " + greaterThan, searchResults > greaterThan);
    	}    
    }
    
    @Then("Verify ([^\"]*) pagination is displayed on array page")
    public void pagination_displayed_array_page(String position){  	
    	assertTrue(position + " pagination should be displayed when items on the page are more than 60", searchArray.isPaginationDisplayed(position));
    }
    
    @Then("Verify page (\\d+) is selected in ([^\"]*) pagination")
    public void verify_page_number(int expectedPageNumber, String position) {
    	boolean isPagination = stateHolder.get("pagination");
    	if (isPagination) {
	    	int selectedPageNumber = searchArray.getPageNumber(position);
	    	assertEquals("Page numbers should match ", expectedPageNumber , selectedPageNumber);
    	} 
    }
    
    @Then("Verify ([^\"]*) pagination arrow is displayed on ([^\"]*) pagination$") 
    public void verify_pagination_arrow_display(String name,String position) {
    	boolean isPagination = stateHolder.get("pagination");
    	if (isPagination) {
	    	name = name.toLowerCase().trim();
	    	assertTrue(name + "pagination arrow should be displayed in the " + position + " pagination", searchArray.isPaginationArrowDisplayed(name,position));
    	}
    }
    
    @Then("Verify ([^\"]*) pagination arrow in ([^\"]*) is in ([^\"]*) state") 
    public void verify_pagination_arrow_on_array(String name, String position, String state) {
    	boolean isPagination = stateHolder.get("pagination");
    	if (isPagination) {
	    	name = name.toLowerCase().trim();
	    	state = state.toLowerCase().trim();
	    	String actualState = searchArray.getPaginationArrowState(name,position);
	    	assertEquals(name + "pagination arrow state should match", state , actualState);
    	}
    } 
    
    
    @When("User clicks on ([^\"]*) pagination arrow from ([^\"]*)") 
    public void click_on_pagination_arrow(String name, String position) {
    	boolean isPagination = stateHolder.get("pagination");
    	if (isPagination) {
	    	name = name.toLowerCase().trim();
	    	int currentPageNumber = searchArray.getPageNumber(position);
	    	stateHolder.put("currentPageNumber", currentPageNumber);
	    	
	    	searchArray.selectPaginationArrowOrLink(name,position,"arrow");
    	}
    } 
    
    @When("User clicks on ([^\"]*) pagination link from ([^\"]*)") 
    public void click_on_pagination_link(String name, String position) {
    	boolean isPagination = stateHolder.get("pagination");
    	if (isPagination) {
	    	name = name.toLowerCase().trim();
	    	int currentPageNumber = searchArray.getPageNumber(position);
	    	stateHolder.put("currentPageNumber", currentPageNumber);
	    	
	    	searchArray.selectPaginationArrowOrLink(name,position,"link");
    	}
    }
    
    @Then("Verify selected page number ([^\"]*) by (\\d+)") 
    public void verify_pagination_arrow_on_array(String action, int count) {
    	boolean isPagination = stateHolder.get("pagination");
    	if (isPagination) {	
    		int expectedPage;
	    	int previousPage = stateHolder.get("currentPageNumber");
	    	
    		action = action.toLowerCase().trim();
	    	if (action.equalsIgnoreCase("increases")) {
	    		expectedPage = previousPage + count;
	    	} else {
	    		expectedPage = previousPage - count;
	    	}
	    	
	    	int actualPage = searchArray.getPageNumber("header");
	    	assertEquals("Page numbers should match", expectedPage , actualPage);
	    	
	    	verify_page_content();
    	}
    } 

    @When("^User selects random page number from ([^\"]*) pagination dropdown$")
    public void select_page_number_from_pagaination_dropdown(String pagination){
    	searchArray.selectRandomPageNumberFromPaginationDropdown(pagination);
    }
    
    @Then("Verify content changes when page number is changed$")
    public void verify_page_content() {
    	String firstIteminPreviousPage = (String) stateHolder.get("firstItemNameInArray");
    	String firstIteminCurrentPage = searchArray.getFirstItemName();
    	assertTrue("First item in Previous page: " + firstIteminPreviousPage + " and first item in current page: " 
    			+ firstIteminCurrentPage + " should not be same ", !firstIteminPreviousPage.equalsIgnoreCase(firstIteminCurrentPage));
    }
    
    
    @Then("Verify that filter options contains this list")
    public void filter_options_contains_list(List<String> expectedOptions) {
        List<String> filterOptions = searchArray.getFilterOptions();

        for (String expectedOption:expectedOptions ) {
        	expectedOption = expectedOption.toLowerCase().trim();
            assertTrue("Filter options should contain '" + expectedOption + "'", filterOptions.contains(expectedOption));
        }
    }
}