package com.jcrew.steps;

import com.jcrew.page.ArraySearch;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import cucumber.api.java.en.Then;
        import cucumber.api.java.en.When;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriverException;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArraySearchSteps extends DriverFactory {

    ArraySearch searchArray = new ArraySearch(getDriver());
    StateHolder stateHolder = StateHolder.getInstance();
    TestDataReader testDataReader = TestDataReader.getTestDataReader();

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
                    CurrencyChecker.isValid(price));
        }

        List<String> salePrice = searchArray.getSalePrices();
        for(String price : salePrice) {
            assertTrue("Sale price " + price + " matches country context "+countryName,
                    CurrencyChecker.isValid(price));
        }

        List<String> wasPrice = searchArray.getWasPrices();
        for(String price : wasPrice) {
            assertTrue("Was price " + price + " matches country context "+countryName,
                    CurrencyChecker.isValid(price));
        }
    }
    
    @Then("Verify Sale array page is displayed$")
    public void verify_sale_array() {
    	if (!stateHolder.hasKey("secondPromoVerification")) {
			assertTrue("Sale array should be displayed", searchArray.isSalePage());
			
			String expectedSaleTitle = null;
			PropertyReader propertyReader = PropertyReader.getPropertyReader();
       	 	String brand = propertyReader.getProperty("brand");
       	 	switch(brand){
       	 		case "jcrew":
       	 			expectedSaleTitle = "sale"; 
       	 			break;
       	 		case "factory":
       	 			expectedSaleTitle = "crew clearance";
       	 			break;
       	 	}
	    	assertEquals("Sale title should be displayed",expectedSaleTitle, searchArray.getHeaderTitle().toLowerCase());
    	}
    }
    
    @Then("Verify filters are displayed on array page$")
    public void filters_display_on_array_page() {
    	assertTrue("Filters should be displayed on array page ",searchArray.isFiltersDisplayed());	
    }
    
    @Then("Verify ([^\"]*) filter displays ([^\"]*)$")
    public void verify_gender_filter_value(String filterName, String expectedValue) {
    	if (!stateHolder.hasKey("secondPromoVerification")) {
	    	if (expectedValue.equalsIgnoreCase("selected gender"))
	    		expectedValue = stateHolder.get("genderselector");
	    	
	    	expectedValue = expectedValue.toLowerCase().trim();
	    	filterName = filterName.toLowerCase().trim();
	    	
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
    	if (gender.equalsIgnoreCase("random"))
    		gender=testDataReader.getCategory().toLowerCase().trim();
    		
    	searchArray.clickGenderSelector(gender);
    	stateHolder.put("genderselector", gender);
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
    	boolean isPagination = stateHolder.get("pagination");
    	if (isPagination) {	
    		searchArray.selectRandomPageNumberFromPaginationDropdown(pagination);
    	}
    }
    
    @Then("Verify content changes when page number is changed$")
    public void verify_page_content() {
    	boolean isPagination = stateHolder.get("pagination");
    	if (isPagination) {
	    	String firstIteminPreviousPage = (String) stateHolder.get("firstItemNameInArray");
	    	String firstIteminCurrentPage = searchArray.getFirstItemName();
	    	assertTrue("First item in Previous page: " + firstIteminPreviousPage + " and first item in current page: " 
    			+ firstIteminCurrentPage + " should not be same ", !firstIteminPreviousPage.equalsIgnoreCase(firstIteminCurrentPage));
    	}
    }
    
    
    @Then("Verify that filter options contains this list")
    public void filter_options_contains_list(List<String> expectedOptions) {
        List<String> filterOptions = searchArray.getFilterOptions();

        for (String expectedOption:expectedOptions ) {
        	expectedOption = expectedOption.toLowerCase().trim();
            assertTrue("Filter options should contain '" + expectedOption + "'", filterOptions.contains(expectedOption));
        }
    }
    
    
    @When("User refines using any of this options")
    public void refine_option(List<String> options) {
        int index = Util.randomIndex(options.size());
        String option = options.get(index);

        searchArray.filterBy(option.toLowerCase());
    }
    
    @Then("^Verify selected refinement is displayed in header$")
    public void selected_refinement_matches_header() {
        List <String> options = stateHolder.getList("filter");
        
        for (String option:options) {
	        String category = searchArray.getFilterValue(option);
	        List<String> expectedValues = stateHolder.getList("filterValue" + option);
	        String expected;
	
	        if (expectedValues.size() == 1) {
	            expected = expectedValues.get(0);
	        } else {
	            expected = expectedValues.size() + " selected";
	        }
	
	        assertEquals("Search filter: " + option + " should display correct filter value", expected.toLowerCase(), category.toLowerCase());
        }
    }
    
    @Then("^Verify item count matches selected refinement$")
    public void verify_refinement_item_count() {
    	 List <String> options = stateHolder.getList("filter");
    	 
    	//Eliminate Duplicates
     	Set<String> uniqueOptions = new HashSet<>();
     	uniqueOptions.addAll(options);
     	options.clear();
     	options.addAll(uniqueOptions);
    	 
    	 if (options.size() == 1) {
    		 String option = options.get(0);
    		 int actualProductCount = searchArray.getSearchResultsNumber();
    		 	 
	 	       List<Integer> filterCounts = stateHolder.getList("filterCount" + option);

	 	       if (filterCounts.size() == 1) {
	 	    	  int expectedProductCount = filterCounts.get(0);
	 	    	  assertEquals("Number of items on array should be ",expectedProductCount, actualProductCount);
	 	       } else {
	 	    	   Collections.sort(filterCounts);
	 	    	   int minExpectedCount = filterCounts.get(filterCounts.size()-1);
	 	    	   int maxExpectedCount = 0;
	 	    	   for (int filterCount:filterCounts) {
	 	    		  maxExpectedCount = maxExpectedCount + filterCount;
	 	    	   }
	 	    	   
	 	    	  assertTrue("Number of items on array should be >= " + minExpectedCount + " and <=" + maxExpectedCount, actualProductCount >= minExpectedCount && actualProductCount <= maxExpectedCount); 
	 	    			   
	 	       }
    	 } else if (options.size() > 1) {
    		 throw new WebDriverException("This step can not be used when multiple refinements are selected");
    	 } else {
    		 throw new WebDriverException("No Refinement is applied. So, refinement count cannot be verified");    		 
    	 }
    }

    @When("^User selects a second option from previously selected filter$")
    public void select_second_option_from_filter() {
    	List <String> options = stateHolder.getList("filter");
    	
    	int index = Util.randomIndex(options.size());
        String option = options.get(index);

        searchArray.filterBy(option.toLowerCase());
    }
    
    
    @When("User clears ([^\"]*) refinements")
    public void clear_refinements(String option) {
    	option = option.toLowerCase().trim();
    	
		List <String> options = stateHolder.getList("filter");
    	
    	if (options.contains(option))
    		searchArray.clearFilters(option.toLowerCase());
    }
    
    @Then("Verify ([^\"]*) filter is cleared$")
    public void verify_clear_filter(String option) {
    	option = option.toLowerCase().trim();
    	List <String> options = stateHolder.getList("filter");
    	
    	if (options.contains(option)) {
    		String filterValue = searchArray.getFilterValue(option).toLowerCase().trim();
    		assertTrue(option + " filter shoud be cleared" + filterValue, filterValue.isEmpty());
    	}
    }
    
    @When("User removes gender selector$")
    public void remove_gender_selector() {
    	searchArray.removeGenderSelector();
    }
    
    
    @Then("Verify ([^\"]*) filter contains following options$")
    public void verify_filter_content(String filterName, List<String> expectedOptions) {
    	List<String> filterOptions = searchArray.getOptionsFromFilter(filterName);

        for (String expectedOption:expectedOptions ) {
        	expectedOption = expectedOption.toLowerCase().trim();
            assertTrue("Filter options should contain '" + expectedOption + "'", filterOptions.contains(expectedOption));
        }
    	
    }
    
    @Then("Verify selected option on ([^\"]*) filter is ([^\"]*)$") 
    public void verify_filter_value(String filterName, String expectedOption)	{
    	filterName = filterName.toLowerCase().trim();
    	expectedOption = expectedOption.toLowerCase().trim();
    	
    	String actualOption = searchArray.getFilterValue(filterName).toLowerCase().trim();
    	assertEquals("Selected option on the filter: " + filterName + " should match", expectedOption, actualOption);
    }
    
    @When("User clicks on ([^\"]*) option from ([^\"]*) filter$")
    public void select_option_from_filter(String optionName, String filterName) {
    	optionName = optionName.toLowerCase().trim();
    	filterName = filterName.toLowerCase().trim();
    	
    	searchArray.selectFilterOption(filterName,optionName);
    }
    
    
    @When("Verify items in Search array are sorted by Price: (Low to High|High to Low)$")
    public void verify_sort_on_search_array(String sortType) {
    	List<Float> expected = searchArray.getSortableItemPrices();
        Collections.sort(expected);
        if (sortType.equalsIgnoreCase("High to Low"))
        	Collections.reverse(expected);

        List<Float> actual = searchArray.getSortableItemPrices();

        assertEquals("Prices are sorted high to low", expected, actual);
    }
    
}