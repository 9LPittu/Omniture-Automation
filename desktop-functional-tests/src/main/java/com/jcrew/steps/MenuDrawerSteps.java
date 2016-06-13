package com.jcrew.steps;

import com.jcrew.page.MenuDrawer;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class MenuDrawerSteps extends DriverFactory {
    MenuDrawer menuDrawer = new MenuDrawer(getDriver());

    @When("User selects random category from list")
    public void user_selects_a_random_category_from_list(List<String> categories) {
        menuDrawer.selectCategory(categories);
    }

    @When("^User selects random subcategory array$")
    public void user_selects_random_subcategory_array() {
        menuDrawer.selectSubCategory();
    }

    @When("^User selects ([^\"]*) subcategory array$")
    public void user_selects_specific_subcategory_array(String subcategory) {
        menuDrawer.selectSubCategory(subcategory);
    }

    @When("User goes back to categories menu")
    public void user_goes_back_to_categories_menu() {
        menuDrawer.goBackToLevel1();
    }

    @When("User goes to sale landing page")
    public void user_goes_to_sale_landing_page() {
        menuDrawer.openSaleLandingPage();
    }

    @When("User goes to home using menu drawer")
    public void user_goes_to_home_using_drawer() {
        menuDrawer.goHome();
    }

    @When("User selects ([^\"]*) category from menu")
    public void user_selects_category_from_menu(String category) {
        menuDrawer.selectCategory(category);
    }

    @Then("Verify menu drawer is displayed")
    public void verify_drawer_is_displayed() {
        assertTrue("Drawer is open", menuDrawer.isDrawerOpen());
    }

    @Then("Verify menu drawer title is ([^\"]*)")
    public void verify_drawer_title(String title){
        title = title.toLowerCase();
        String menuTitle = menuDrawer.getDrawerTitle();

        assertEquals("Menu displayed matches expected title", title, menuTitle);
    }
}
