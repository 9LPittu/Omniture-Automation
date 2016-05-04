package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/30/16.
 */
public class HomePageSteps extends DriverFactory{
    HomePage homePage = new HomePage(getDriver());

    @Then("Verify user is in homepage")
    public void verify_user_is_in_hompage(){
        assertTrue("user is in homepage",homePage.isHomePage());
    }

    @Then("Verify user is in international homepage")
    public void verify_user_is_in_international_hompage(){
        assertTrue("user is in homepage",homePage.isHomePage());
        assertTrue("homepage matches expected country", homePage.verifyInternational());
    }

    @When("User closes email capture")
    public void user_closes_email_capture() {
        homePage.closeEmailCapture();
    }

    @And("^Enters ([^\"]*) to the search field$")
    public void enters_a_search_term_to_the_input_field(String searchTerm) throws Throwable {
        homePage.input_search_term(searchTerm);
    }
    
    @And("^Clicks on search button for input field$")
    public void clicks_on_search_button_from_field() throws Throwable {
        homePage.click_on_search_button_for_input_field();
    }
}
