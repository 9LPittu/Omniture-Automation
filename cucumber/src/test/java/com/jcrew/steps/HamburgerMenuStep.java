package com.jcrew.steps;

import com.jcrew.page.HamburgerMenu;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


import static org.junit.Assert.assertTrue;

public class HamburgerMenuStep extends DriverFactory {

    private HamburgerMenu hamburgerMenu = new HamburgerMenu(getDriver());

    @Then("^Hamburger Menu ([^\"]*) Link is present$")
    public void hamburger_menu_category_link_is_present(String category) throws Throwable {
        assertTrue(category + " category should have been present", hamburgerMenu.isCategoryPresent(category));
    }

    @Then("^Subcategory ([^\"]*) Hamburger Menu link is present$")
    public void subcategory_hamburger_menu_link_is_present(String subcategory) {
        assertTrue(subcategory + " should have been present",
                hamburgerMenu.isSubcategoryMenuLinkPresent(subcategory));
    }

    @When("^User clicks on ([^\"]*) subcategory from Women Category$")
    public void user_clicks_on_subcategory_from_women_category(String subcategory) throws Throwable {
        hamburgerMenu.click_on_subcategory(subcategory);
    }

    @Then("^Closes subcategory hamburger menu$")
    public void closes_subcategory_hamburger_menu() throws Throwable {
        hamburgerMenu.close_subcategory_hamburger_menu();
    }
}
