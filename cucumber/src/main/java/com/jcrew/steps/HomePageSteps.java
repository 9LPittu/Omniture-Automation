package com.jcrew.steps;

import com.jcrew.page.Header;
import com.jcrew.page.HomePage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

import java.util.List;

public class HomePageSteps extends DriverFactory {

    private final HomePage homePage = new HomePage(getDriver());
    private final Header header = new Header(getDriver());    
    private final PropertyReader reader = PropertyReader.getPropertyReader();


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

    @Then("^Verify user is in homepage$")
    public void verify_user_is_in_homepage() throws Throwable {
        assertTrue("Global Main Section should be displayed", homePage.isHomePage());
    }

    @And("^User clicks on search close icon")
    public void user_clicks_on_search_close_icon() throws InterruptedException {

        header.click_on_search_close_icon();

        assertTrue("Search drawer should be closed", header.isSearchDrawerClosed());
    }

    @And("^Dresses is populated")
    public void validate_search_term() {
        assertTrue("Dresses should be populated", header.getSearchDrawerTerm().contains("dresses"));
    }

    @And("^Close the email pop up")
    public void close_email_pop_up_if_exists() {
        String browser = reader.getProperty("browser");
        if (!browser.equalsIgnoreCase("phantomjs")) {
            assertTrue("e-mail pop up not displayed", homePage.isEmailPopUpDisplayed());
            homePage.close_email_pop_up();
        }
    }

     @And("Handle the Email Capture pop up$")
     public void handle_email_pop_up() {
         if(!reader.getProperty("browser").equalsIgnoreCase("phantomjs"))
            homePage.handle_email_pop_up();
     }



     @And("^Email pop up is not displayed")
     public void verify_email_pop_up_not_displayed() {
         assertTrue("email pop up is displayed",homePage.isEmailPopUpNotDisplayed());
     }

     @And("Enter the valid email address and submit")
     public void enter_email_address_in_the_email_capture_pop_up() {
         String browser = reader.getProperty("browser");
         if (!browser.equalsIgnoreCase("phantomjs")) {
             homePage.enter_email_address();
             homePage.click_on_the_arrow_button_to_submit();
         }
     }

    @And("^search for \"([^\"]*)\"$")
    public void search_for_item_specified_in_property_file(String propertyName){
    	
    	header.click_on_search_button();    	
    	homePage.searchItemByReadingPropertyFile(propertyName);
    }
    
    @When("^user enters ([^\"]*) in search field$")
    public void user_enters_search_term_in_search_field(String searchTerm){
    	homePage.input_search_term_by_reading_from_properties(searchTerm);
    }
    
    @And("^user clicks on random top nav gender link from below list$")
    public void user_clicks_random_top_nav_gender_link(List<String> gender){
    	String genderName = gender.get(Util.randomIndex(gender.size()));
    	homePage.clickGenderLinkFromTopNav(genderName);
    }
}