package com.jcrew.steps;

import com.jcrew.page.HamburgerMenu;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

public class HamburgerMenuStep extends DriverFactory {

    private HamburgerMenu hamburgerMenu = new HamburgerMenu(getDriver());

    @Then("^Hamburger Menu ([^\"]*) Link is present$")
    public void hamburger_menu_category_link_is_present(String category) throws Throwable {
        assertTrue(category + " category should have been present", hamburgerMenu.isCategoryPresent(category));
    }
}
