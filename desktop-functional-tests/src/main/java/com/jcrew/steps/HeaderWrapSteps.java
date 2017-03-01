package com.jcrew.steps;

import com.jcrew.page.HeaderWrap;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import com.jcrew.utils.StateHolder;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class HeaderWrapSteps extends DriverFactory {
    HeaderWrap header = new HeaderWrap(getDriver());
    TestDataReader reader = TestDataReader.getTestDataReader();

    @When("User clicks on sign in using header")
    public void click_sign_in() {
        header.clickSignIn();
    }

    @When("User opens menu")
    public void user_opens_menu() {

    	header.openMenu();
    }

    @When("User hovers over ([^\"]*)")
    public void user_hovers_over(String icon) {
        header.hoverOverIcon(icon);
    }

    @When("User signs out using header")
    public void sign_out_using_header() {
        header.goToMyDetailsDropDownMenu("Sign Out");
    }

    @When("User goes to ([^\"]*) using header")
    public void user_goes_to_using_header(String item) {
        header.goToMyDetailsDropDownMenu(item);
    }

    @When("User clicks JCrew logo")
    public void user_clicks_jcrew_logo() {
        header.clickLogo();
    }

    @Then("Dropdown should welcome user by first name")
    public void dropdown_should_welcome_user_using_first_name() {
    	StateHolder stateHolder = StateHolder.getInstance();
    	User user = stateHolder.get("userObject");
    	String firstName = user.getFirstName();
        String expectedWelcomeMessage = "Welcome, " + firstName;
        String actualWelcomeMessage = header.getWelcomeMessage();

        assertEquals("First name should match message", expectedWelcomeMessage, actualWelcomeMessage);
    }

    @Then("Verify header contains Sign In visible")
    public void verify_header_contains_sign_in_visible() {
        assertTrue("Header contains a visible sign in", header.isSignInVisible());
    }

    @And("User clicks on search using header")
    public void user_clicks_search_from_header(){
        header.clickSearch();
    }
    @And("User clicks on stores using header")
    public void user_clicks_stores_from_header(){
        header.clickStores();
    }

    @Then("User searches for a random term from list")
    public void user_searches_for_random_term(List<String> terms) {
        int index = Util.randomIndex(terms.size());
        String term = terms.get(index);

        header.searchFor(term);
    }

    @When("User searches for the item ([^\"]*)")
    public void user_enters_search_item(String item) {
        header.searchFor(item);
    }

    @When("User searches specific term ([^\"]*)")
    public void user_search_for_term(String searchTerm) {
        header.searchForSpecificTerm(searchTerm);
    }

    @When("User clicks in bag")
    public void user_clicks_in_bag() {
        header.clickBag();
    }
    @When("User clicks in My Account")
    public void user_clicks_in_myAccount() {
        header.myAccount();
    }
    
    @When("User hovers on My Account")
    public void user_hovers_on_myAccount() {
    	header.hoverOverIcon("my account");
    }
    
    @And("Verify My Account drop down is displayed")
    public void my_account_drop_down_displayed(){
        assertTrue("My Account drop down should display",header.isMyAccountDropdownDisplayed());
    }

    @When("^User clicks on ([^\"]*) link from top nav$")
    public void click_on_given_link_from_top_nav(String Dept) {
    	if (Dept.equalsIgnoreCase("random") || Dept.equalsIgnoreCase("any"))
    		Dept = reader.getCategory().toLowerCase().trim();
        header.clickDeptLinkFromTopNav(Dept);
    }

    @Then("Verify that top nav options contains ([^\"]*)")
    public void verify_top_nav_contains(String option) {
        option = option.toLowerCase();
        List<String> options = header.getTopNavOptions();

        assertTrue("Options in topnav contains", options.contains(option));
    }

    @Then("Verify that top nav contains less or equal to (\\d+) options")
    public void verify_top_nav_contains_less(int allowedOptions) {
        List<String> options = header.getTopNavOptions();

        assertTrue("Page contains " + allowedOptions + " or less Options in topnav", options.size() <= allowedOptions);
    }
    
    @Then("Verify jcrew logo is visible")
    public void is_logo_visible() {
        header.isLogoVisible();
    }
    
    
    @When("User hovers on a random category from list")
    public void user_hovers_on_random_category_from_list(List<String> categories) {
    	header.hoverCategory(categories);
    }

    @When("User hovers on a random category and subcategory from list")
    public void user_hovers_on_random_category_from_list(DataTable categories) {
        List<DataTableRow> row = categories.getGherkinRows();
        DataTableRow selectedRow = row.get(Util.randomIndex(row.size()));
        header.hoverCategory(selectedRow);
    }
    
    @When("User hovers on any random category")
    public void user_hovers_on_any_random_category() {
    	String Category = reader.getCategory().toLowerCase().trim();
    	header.hoverCategory(Category);
    }
    
    @When("User selects ([^\"]*) subcategory array")
    public void user_selects_specific_subcategory_array(String subcategory) {
    	if("random".equalsIgnoreCase(subcategory)) {
    		header.selectSubCategory();
    	} else {
    		header.selectSubCategory(subcategory);
    	}
    }
    
    @When("User hovers on ([^\"]*) category from header")
    public void user_hovers_category_from_header(String category) {
        header.hoverCategory(category);
    }
    
    @When("User selects a random feature page from list")
    public void select_feature_page(DataTable features) {
    	List<DataTableRow> row = features.getGherkinRows();
        DataTableRow selectedRow = row.get(Util.randomIndex(row.size()));

        String category = selectedRow.getCells().get(0);
        String featureName = selectedRow.getCells().get(1);

        header.hoverCategory(category);
        header.selectSubCategory(featureName);
    }
    
    @Then("Verify search drawer is (open|closed)")
    public void verify_search_drawer_state(String expectedState) {
    	expectedState = expectedState.toLowerCase().trim();
    	String actualState = header.getSearchDrawerState().toLowerCase().trim();
    	assertEquals("State of search drawer should match",expectedState, actualState );
    	
    }
    
    @When("User (opens|closes) search drawer$")
    public void users_open_or_close_search_drawer(String action) {
    	String drawerState = header.getSearchDrawerState().toLowerCase().trim();
    	if (action.equalsIgnoreCase("opens")) {
    		if (drawerState.equalsIgnoreCase("closed"))
    			header.openSearchDrawer();
    	} else if (action.equalsIgnoreCase("closes")) {
    		if (drawerState.equalsIgnoreCase("open"))
    			header.closeSearchDrawer();
    	}
    }

}
