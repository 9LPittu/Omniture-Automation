package com.jcrew.steps;

import com.jcrew.page.Header;
import com.jcrew.page.HomePage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class HomePageSteps extends DriverFactory {

    private final HomePage homePage = new HomePage(getDriver());
    private final Header header = new Header(getDriver());


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

    @And("^Verify page source contains ([^\"]*)$")
    public void validate_page_source_contains_given_var(String var) {
        assertTrue("page source should contain "+var+"", homePage.isGivenVarPresentInSourceCode(var));
    }

    @And("^Get the ([^\"]*) value$")
    public void get_s_account_value(String var) {
       assertTrue("s_account should have a text value", !(homePage.getSAccountValue(var).isEmpty()));
    }

    @And("^Validate the ([^\"]*) value in production to be ([^\"]*)$")
    public void validate_s_account_value(String expected,String var) {
        assertEquals("s_account value is not as expected", expected, homePage.getSAccountValue(var));
    }


}