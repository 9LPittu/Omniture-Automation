package com.jcrew.steps;

import com.jcrew.page.HamburgerMenu;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class HamburgerMenuSteps extends DriverFactory {

    private HamburgerMenu hamburgerMenu = new HamburgerMenu(getDriver());
    private StateHolder stateHolder = StateHolder.getInstance();

    @Then("^Hamburger Menu ([^\"]*) Link is present$")
    public void hamburger_menu_category_link_is_present(String category) throws Throwable {
        assertTrue(category + " category should have been present", hamburgerMenu.isCategoryPresent(category));
    }

    @When("^User clicks on ([^\"]*) subcategory from ([^\"]*) Category$")
    public void user_clicks_on_subcategory_from_category(String subcategory, String category) throws Throwable {
        hamburgerMenu.click_on_subcategory(subcategory, category);
    }

    @Then("^Closes subcategory hamburger menu$")
    public void closes_subcategory_hamburger_menu() throws Throwable {
        hamburgerMenu.close_subcategory_hamburger_menu();
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
        stateHolder.put("category", category);
    }

    @And("^User clicks on back link$")
    public void user_clicks_on_back_link() {
        hamburgerMenu.click_on_back_link();
    }

    @And("^Goes to sign in page$")
    public void goes_to_sign_in_page() throws Throwable {
        hamburgerMenu.click_on_sign_in_link();
    }

    @And("^Closes hamburger menu$")
    public void closes_hamburger_menu() throws Throwable {
        hamburgerMenu.close_hamburger_menu();
    }

    @And("^Chooses a random category$")
    public void chooses_a_random_category() throws Throwable {
        hamburgerMenu.click_random_category();
    }

    @And("^Chooses a random subcategory$")
    public void chooses_a_random_subcategory() throws Throwable {
        hamburgerMenu.click_random_subcategory();
    }
}
