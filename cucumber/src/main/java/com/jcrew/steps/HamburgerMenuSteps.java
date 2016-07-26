package com.jcrew.steps;

import com.jcrew.page.HamburgerMenu;
import com.jcrew.util.DriverFactory;

import com.jcrew.util.Util;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HamburgerMenuSteps extends DriverFactory {

    private final HamburgerMenu hamburgerMenu = new HamburgerMenu(getDriver());

    @Then("^Hamburger Menu ([^\"]*) Link is present$")
    public void hamburger_menu_category_link_is_present(String category) {
        assertTrue(Util.getSelectedCountryName() + category + " category should have been present", hamburgerMenu.isCategoryPresent(category));
    }

    @When("^User clicks on ([^\"]*) subcategory from ([^\"]*) Category$")
    public void user_clicks_on_subcategory_from_category(String subcategory, String category) {
        hamburgerMenu.click_on_subcategory(subcategory, category);
    }

    @And("^User clicks on ([^\"]*) from featured this month$")
    public void user_clicks_selection_from_featured_this_month(String selection) {
        hamburgerMenu.click_on_selected_featured_this_month(selection);
    }

    @And("^User clicks on ([^\"]*) subcategory from Sales$")
    public void user_clicks_on_subcategory_from_sales(String subcategory) {
        hamburgerMenu.click_on_sale_subcategory(subcategory);
    }

    @Then("^Closes subcategory hamburger menu$")
    public void closes_subcategory_hamburger_menu() {
        hamburgerMenu.close_subcategory_hamburger_menu();
    }


    @Then("^Menu icon is present$")
    public void hamburger_menu_is_present() {
        assertTrue("Menu icon is present", hamburgerMenu.isHamburgerMenuPresent());
    }

    @Given("^User clicks on hamburger menu$")
    public void user_clicks_on_hamburger_menu() {
        hamburgerMenu.click_on_hamburger_menu();
    }

    @And("^Selects ([^\"]*) Category from hamburger menu$")
    public void selects_category_from_hamburger_menu(String category) {
        hamburgerMenu.click_on_category(category);
    }
    
    @And("^user selects ([^\"]*) category from hamburger menu$")
    public void user_selects_category_from_hamburger_menu(String category) {
    	if(!category.equalsIgnoreCase("any")){
    		hamburgerMenu.click_on_category(category);
    	}
    	else{
    		hamburgerMenu.click_random_category();
    	}
    }

    @And("^User clicks on back link$")
    public void user_clicks_on_back_link() {
        hamburgerMenu.click_on_back_link();
    }

    @And("^Goes to sign in page$")
    public void goes_to_sign_in_page() {
        hamburgerMenu.click_on_sign_in_link();
    }

    @And("^Goes to sign in page from menu$")
    public void goes_to_sign_in_page_from_menu() {
        hamburgerMenu.click_on_sign_in_link_from_hamburger_menu();
    }

    @And("^Closes hamburger menu$")
    public void closes_hamburger_menu() {
        hamburgerMenu.close_hamburger_menu();
    }

    @And("^Chooses a random category$")
    public void chooses_a_random_category() {
        hamburgerMenu.click_random_category();
    }

    @And("^Chooses a random subcategory$")
    public void chooses_a_random_subcategory() {
        hamburgerMenu.click_random_subcategory();
    }

    @And("^User is signed out$")
    public void user_is_signed_out() {
        assertFalse("User is signed out",hamburgerMenu.isUserSignedIn());
    }

    @And("^User selects random tray from available categories$")
    public void selectRandomTrayFromAvailableCategories(DataTable categories){
        List<DataTableRow> row = categories.getGherkinRows();
        DataTableRow selectedRow = row.get(Util.randomIndex(row.size()));

        String category = selectedRow.getCells().get(0);
        String subcategory = selectedRow.getCells().get(1);
        String option = selectedRow.getCells().get(2);

        selects_category_from_hamburger_menu(category);
        user_clicks_on_subcategory_from_category(subcategory,category);
        user_clicks_selection_from_featured_this_month(option);

        new LooksWeLoveSteps().user_Selects_Random_Shop_The_Look_Page(category);

    }
    
    @And("^user selects ([^\"]*) subcategory$")
    public void select_subcategory(String subcategory){
    	if(!subcategory.equalsIgnoreCase("any")){
    		hamburgerMenu.clickSpecificSubcategory(subcategory);
    	}
    	else{
    		hamburgerMenu.click_random_subcategory();
    	}
    }
    
    public void hamburger_menu_user_panel_link(String linkName){
    	assertTrue(Util.getSelectedCountryName() + "User should see '" + linkName + "' in the hamburger menu", hamburgerMenu.isUserPanelLinkPresent(linkName));
    }
    
    public void closeBackLinkInHamburgerMenu(){
    	hamburgerMenu.click_on_back_link();
    }
}
