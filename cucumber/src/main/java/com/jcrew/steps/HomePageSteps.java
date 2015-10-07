package com.jcrew.steps;

import com.jcrew.page.Header;
import com.jcrew.page.HomePage;
import com.jcrew.util.DatabaseReader;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Reporting;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.*;
public class HomePageSteps extends DriverFactory {

    private HomePage homePage = new HomePage(getDriver());
    private Header header = new Header(getDriver());
    
    private Scenario scenario;

    
    @Before
    public void getScenarioObject(Scenario s){
    	this.scenario = s;
    }


    @Then("^JCrew Logo is present$")
    public void jcrew_logo_is_present() throws Throwable {
        assertTrue("JCrew logo must be present", header.isJCrewLogoPresent());
    }

    @And("^Enters ([^\"]*) to the search field$")
    public void enters_a_search_term_to_the_input_field(String searchTerm) throws Throwable {
        homePage.input_search_term(searchTerm);
    }

    @And("^Hits enter in search field$")
    public void hits_enter_in_search_field() throws Throwable {
        homePage.hit_enter_in_search_field();
    }


    @And("^Clicks on search button for input field$")
    public void clicks_on_search_button_from_field() throws Throwable {
        homePage.click_on_search_button_for_input_field();
    }

    @And("^User clicks on hamburger menu from women pdp$")
    public void user_clicks_on_hamburger_menu_from_women_pdp() {
        homePage.click_on_women_pdp_hamburger_menu();
    }

    @Then("^Verify user is in homepage$")
    public void verify_user_is_in_homepage() throws Throwable {
        assertTrue("Global Main Section should be displayed", homePage.isHomePage());

    }

    @And("^User clicks on search close icon")
    public void user_clicks_on_search_close_icon() throws InterruptedException {

        header.click_on_search_close_icon();

        assertFalse("Search drawer should be closed", header.isSearchDrawerOpen());
    }

    @And("^Dresses is populated")
    public void validate_search_term() {
        assertTrue("Dresses should be populated", header.getSearchDrawerTerm().contains("dresses"));
    }
    
    @Then("select \"([^\"]*)\" from left nav")
    public void select_category_from_left_nav(String category){
    	homePage.selectCategoryFromLeftNav(category);   	
    	

    }
    
    @Then("select \"([^\"]*)\" from subcategories")
    public void select_subcategory_from_subcategories(String subCategory){
    	homePage.selectSubCategoryFromLeftNav(subCategory);  	
    	

    }
    
    @And("^Enter backorder item from database to the search field")
    public void enters_a_backorder_Item_the_input_field() throws Throwable {
    	String SearchTerm;
    	String strQuery;
    	strQuery = "select variant from JCBRNQA_STORE.jc_web_inventory where sellable_oh_qty = 0 and sellable_oh_rtl = 0 and sellable_br_qty > 1 and sellable_oo_qty > 1";
    	ResultSet rs=DatabaseReader.GetData(strQuery);
    	//rs.first();
    	//while(rs.next())  
		//System.out.println(rs.getString(1));
    	rs.next();
    	SearchTerm =rs.getString(1).substring(0, 5);
    	
    	System.out.println(SearchTerm);
    	
    	  homePage.input_search_term(SearchTerm);
    }
  
}