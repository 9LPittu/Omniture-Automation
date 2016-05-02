package com.jcrew.steps;

import com.jcrew.page.ContextChooserPage;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ContextChooserPageSteps extends DriverFactory {

    private final ContextChooserPage contextChooser = new ContextChooserPage(getDriver());

    @And("^User is on context chooser page$")
    public void verify_context_chooser_page_is_displayed() {
        assertTrue("User should be in international context chooser page", contextChooser.isInternationalContextChooserPageDisplayed());
    }

    @And("^([^\"]*) region is displayed")
    public void verify_region_is_displayed(String region) {
        assertTrue("Region section should be displayed",contextChooser.isRegionDisplayed(region));
    }
    

    
    @And("^Verify countries displayed correctly under each region$")
    public void verify_countries_displayed_under_regions(List<String> regions){
    	for(String region:regions){
    		assertTrue("User should see correct countries displayed under region '" + region + "", contextChooser.isCountriesDisplayedCorrectlyUnderRegion(region.toUpperCase()));
    	}
    }
    
    @And("^Click on \"([^\"]*)\" link from terms section on the context chooser page$")
    public void click_link_from_terms_section_on_context_chooser_page(String linkName){
    	contextChooser.clickLinkFromTermsSectionOnContextChooserPage(linkName);
    }
    
    @And("^Click on \"([^\"]*)\" button from FAQ section on the context chooser page$")
    public void click_button_from_faq_section_on_context_chooser_page(String buttonName){
    	contextChooser.clickButtonFromFAQSectionOnContextChooserPage(buttonName);
    }
    
    @And("^Click on \"([^\"]*)\" link from FAQ section on the context chooser page$")
    public void click_link_from_faq_section_on_context_chooser_page(String linkName){
    	contextChooser.clickLinkFromFAQSectionOnContextChooserPage(linkName);
    }
    
    @Given("^User selects country at random from context chooser page$")
    public void select_random_country_from_context_chooser_page(){
    	contextChooser.selectRandomCountry();
    }
    
    @Given("^User selects ([^\"]*) at random from context chooser page$")
    public void select_random_country_from_country_group_on_context_chooser_page(String country_group) {
    	contextChooser.selectGroupRandomCountry(country_group);
    }

    @Then("^User should land on country specific home page$")
    public void user_should_land_on_country_specific_home_page(){
    	contextChooser.isUserOnCountrySpecificHomePage();
    }
    
    @And("^click on 'START SHOPPING' button on country specific home page$")
    public void click_start_shopping_button_country_specific_page(){
    	contextChooser.clickStartShoppingButton();
    }
}