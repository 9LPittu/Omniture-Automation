package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

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
}
