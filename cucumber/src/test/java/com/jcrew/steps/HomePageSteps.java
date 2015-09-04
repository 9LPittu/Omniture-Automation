package com.jcrew.steps;

import com.jcrew.page.HamburgerMenu;
import com.jcrew.page.Header;
import com.jcrew.page.HomePage;
import com.jcrew.page.Navigation;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HomePageSteps extends DriverFactory {

    private HomePage homePage = new HomePage(getDriver());
    private Navigation navigation = new Navigation(getDriver());
    private HamburgerMenu hamburgerMenu = new HamburgerMenu(getDriver());
    private Header header = new Header(getDriver());


    @Then("^JCrew Logo is present$")
    public void jcrew_logo_is_present() throws Throwable {
        assertTrue("JCrew logo must be present", navigation.isJCrewLogoPresent());
    }


    @Then("^Hamburger menu is present$")
    public void hamburger_menu_is_present() throws Throwable {
        assertTrue("Hamburger menu is present", hamburgerMenu.isHamburgerMenuPresent());
    }

    @Given("^User clicks on hamburger menu$")
    public void user_clicks_on_hamburger_menu() throws Throwable {
        hamburgerMenu.click_on_hamburger_menu();
    }

    @And("^Selects ([^\"]*) Category from hamburger menu$")
    public void selects_category_from_hamburger_menu(String category) throws Throwable {
        hamburgerMenu.click_on_category(category);
    }

    @And("^Selects Shirts and Tops from Women Category in hamburger menu$")
    public void selects_shirts_and_tops_from_hamburger_menu() throws Throwable {

        hamburgerMenu.click_on_shirts_and_tops_from_women_category_in_hamburger_menu();

    }

    @And("^Enters ([^\"]*) to the search field$")
    public void enters_a_search_term_to_the_input_field(String searchTerm) throws Throwable {
        homePage.input_search_term(searchTerm);
    }

    @When("^Enters yellow dresses to the search field$")
    public void enters_yellow_dresses_to_the_search_field() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        homePage.input_yellow_dresses();
    }

    @And("^Hits enter in search field$")
    public void hits_enter_in_search_field() throws Throwable {
        homePage.hit_enter_in_search_field();
    }

    @And("^Goes to sign in page$")
    public void goes_to_sign_in_page() throws Throwable {
        hamburgerMenu.click_on_sign_in_link();
    }

    @And("^Clicks on search button for input field$")
    public void clicks_on_search_button_from_field() throws Throwable {
        homePage.click_on_search_button_for_input_field();
    }

    @And("^User clicks on hamburger menu from women pdp$")
    public void user_clicks_on_hamburger_menu_from_women_pdp() {
        homePage.click_on_women_pdp_hamburger_menu();
    }

    @And("^User clicks on back link$")
    public void user_clicks_on_back_link() {
        hamburgerMenu.click_on_back_link();
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


}