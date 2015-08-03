package com.jcrew.steps;

import com.jcrew.page.HomePage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class HomePageSteps extends DriverFactory {

    private HomePage homePage = new HomePage(driver);

    @When("^Selects a category$")
    public void selects_a_category() {
        homePage.selects_a_category();
    }


    @Then("^JCrew Logo is present$")
    public void jcrew_logo_is_present() throws Throwable {
        assertTrue("JCrew logo must be present", homePage.isJCrewLogoPresent());
    }


    @Then("^Department Links are present$")
    public void department_links_are_present() throws Throwable {
        assertTrue("Women should be present", homePage.isWomenDepartmentPresent());
        assertTrue("Men should be present", homePage.isMenDepartmentPresent());
        assertTrue("Boys should be present", homePage.isBoysDepartmentPresent());
        assertTrue("Girls should be present", homePage.isGirlsDepartmentPresent());
    }

    @Then("^Stores Link is present$")
    public void stores_link_is_present() throws Throwable {
        assertTrue("Stores link should be present", homePage.isStoresLinkPresent());
    }

    @Then("^Hamburger menu is present$")
    public void hamburger_menu_is_present() throws Throwable {
        assertTrue("Hamburger menu is present", homePage.isHamburgerMenuPresent());
    }

    @Given("^User clicks on hamburger menu$")
    public void user_clicks_on_hamburger_menu() throws Throwable {
        homePage.click_on_hamburger_menu();
    }

    @Then("^Hamburger Menu Links are present$")
    public void hamburger_menu_links_are_present() throws Throwable {
        assertTrue("Women hamburger menu link should be present", homePage.isHamburgerMenuWomenLinkPresent());
        assertTrue("Men hamburger menu link should be present", homePage.isHamburgerMenuMenLinkPresent());
        assertTrue("Boys hamburger menu link should be present", homePage.isHamburgerMenuBoysLinkPresent());
        assertTrue("Girls hamburger menu link should be present", homePage.isHamburgerMenuGirlsLinkPresent());
        assertTrue("Wedding hamburger menu link should be present", homePage.isHamburgerMenuWeddingLinkPresent());
        assertTrue("Sale hamburger menu link should be present", homePage.isHamburgerMenuSaleLinkPresent());
        assertTrue("Blog hamburger menu link should be present", homePage.isHamburgerMenuBlogLinkPresent());
    }

    @And("^Selects Women Category from hamburger menu$")
    public void selects_women_category_from_hamburger_menu() throws Throwable {
        homePage.click_on_women_category_from_hamburger_menu();
    }

    @And("^Selects Shirts and Tops from Women Category in hamburger menu$")
    public void selects_shirts_and_tops_from_hamburger_menu() throws Throwable {

        homePage.click_on_shirts_and_tops_from_women_category_in_hamburger_menu();

    }

    @And("^User presses search button$")
    public void presses_search_button() throws Throwable {
        homePage.click_on_search_button();
    }

    @And("^Enters (\\w+) to the search field$")
    public void enters_a_search_term_to_the_input_field(String searchTerm) throws Throwable {
        homePage.input_search_term(searchTerm);
    }

    @And("^Hits enter in search field$")
    public void hits_enter_in_search_field() throws Throwable {
        homePage.hit_enter_in_search_field();
    }
}
