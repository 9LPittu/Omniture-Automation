package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.Util;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;

import static org.junit.Assert.assertTrue;

import java.util.List;

public class HamburgerMenuSteps extends DriverFactory {
	
	private final HamburgerMenu hamburgerMenu = new HamburgerMenu(getDriver());
	

	@Given("^User clicks on hamburger menu$")
    public void user_clicks_on_hamburger_menu() {
        hamburgerMenu.click_on_hamburger_menu();
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
	
	@And("^user selects ([^\"]*) subcategory$")
    public void select_subcategory(String subcategory){
    	if(!subcategory.equalsIgnoreCase("any")){
    		hamburgerMenu.clickSpecificSubcategory(subcategory);
    	}
    	else{
    		hamburgerMenu.click_random_subcategory();
    	}
    }
	
	@Then("^Hamburger Menu ([^\"]*) Link is present$")
    public void hamburger_menu_category_link_is_present(String category) {
        assertTrue(category + " category should have been present", hamburgerMenu.isCategoryPresent(category));
    }
	
	@And("^Closes hamburger menu$")
    public void closes_hamburger_menu() {
        hamburgerMenu.close_hamburger_menu();
    }
	

	 
	 @And("^User clicks on ([^\"]*) subcategory from Sales$")
	    public void user_clicks_on_subcategory_from_sales(String subcategory) {
	        hamburgerMenu.click_on_sale_subcategory(subcategory);
	 }
	 
	 @And("^User selects random tray from available categories$")
	 public void selectRandomTrayFromAvailableCategories(DataTable categories){
        List<DataTableRow> row = categories.getGherkinRows();
        DataTableRow selectedRow = row.get(Util.randomIndex(row.size()));

        String category = selectedRow.getCells().get(0);
        String subcategory = selectedRow.getCells().get(1);
        String option = selectedRow.getCells().get(2);

       //selects_category_from_hamburger_menu(category);
        user_clicks_on_subcategory_from_category(subcategory,category);
        user_clicks_selection_from_featured_this_month(option);

        new LooksWeLoveSteps().user_Selects_Random_Shop_The_Look_Page(category);
	 }
	 
	 @When("^User clicks on ([^\"]*) subcategory from ([^\"]*) Category$")
	 public void user_clicks_on_subcategory_from_category(String subcategory, String category) {
	     hamburgerMenu.click_on_subcategory(subcategory, category);
	 }
	 
	 @And("^User clicks on ([^\"]*) from featured this month$")
	 public void user_clicks_selection_from_featured_this_month(String selection) {
	    hamburgerMenu.click_on_selected_featured_this_month(selection);
	 }
}
