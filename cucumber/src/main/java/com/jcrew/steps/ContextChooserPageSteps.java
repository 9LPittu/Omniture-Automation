package com.jcrew.steps;

import com.jcrew.page.ContextChooserPage;


import com.jcrew.util.DriverFactory;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class ContextChooserPageSteps extends DriverFactory {

    private final ContextChooserPage contextChooser = new ContextChooserPage(getDriver());

    @And("^User is on context chooser page$")
    public void verify_context_chooser_page_is_displayed() {
        assertTrue("User should be in international context chooser page", contextChooser.isInternationalContextChooserPageDisplayed());
    }

    @And("^([^\"]*) region drawer is displayed")
    public void verify_region_drawer_is_displayed(String region) {
        assertTrue("Region drawer/section should be displayed",contextChooser.isRegionDisplayed(region));
    }
    
    @And("^user should see all regional drawers closed by default$")
    public void user_should_see_all_regional_drawers_closed_by_default(){
    	assertTrue("All regional drawers should be closed by default",contextChooser.isAllRegionalDrawersClosedByDefault());
    }
    
    @And("^expand each regional drawer and verify the countries displayed and only one drawer should be opened$")
    public void verify_countries_displayed_under_regional_drawer(List<String> regions){
    	for(String region:regions){
    		assertTrue("User should see correct countries displayed under region '" + region + "' and one only drawer should be opened at a time", contextChooser.isCountriesDisplayedCorrectlyUnderRegion(region.toUpperCase()));
    	}
    }
    
    @And("^click on \"([^\"]*)\" link from terms section on the context chooser page$")
    public void click_link_from_terms_section_on_context_chooser_page(String linkName){
    	contextChooser.clickLinkFromTermsSectionOnContextChooserPage(linkName);
    }
    
    @And("^click on \"([^\"]*)\" button from FAQ section on the context chooser page$")
    public void click_button_from_faq_section_on_context_chooser_page(String buttonName){
    	contextChooser.clickButtonFromFAQSectionOnContextChooserPage(buttonName);
    }
    
    @And("^click on \"([^\"]*)\" link from FAQ section on the context chooser page$")
    public void click_link_from_faq_section_on_context_chooser_page(String linkName){
    	contextChooser.clickLinkFromFAQSectionOnContextChooserPage(linkName);
    }
    
    @Given("^user selects country at random from context chooser page$")
    public void select_random_country_from_context_chooser_page(){
    	contextChooser.selectRandomCountry();
    }
    
    @Given("^user selects ([^\"]*) at random from context chooser page$")
    public void select_random_country_from_country_group_on_context_chooser_page(String country_group) {
    	contextChooser.selectGroupRandomCountry(country_group);
    }

    @Then("^user should land on country specific home page$")
    public void user_should_land_on_country_specific_home_page(){
    	assertTrue(Util.getSelectedCountryName() + "User should land on country specific home page", contextChooser.isUserOnCountrySpecificHomePage());
    }
    
    @And("^click on 'START SHOPPING' button on country specific home page$")
    public void click_start_shopping_button_country_specific_page(){
    	contextChooser.clickStartShoppingButton();
    }
    
    @When("^user selects randomly an international country$")
    public void user_selects_random_international_country(){
    	contextChooser.selectRandomInternationalCountry();
    }
}