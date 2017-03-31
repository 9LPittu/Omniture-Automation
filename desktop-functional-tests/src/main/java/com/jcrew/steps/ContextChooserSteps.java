package com.jcrew.steps;

import com.jcrew.page.ContextChooser;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ContextChooserSteps extends DriverFactory {

    private final ContextChooser contextChooser = new ContextChooser(getDriver());

    @Then("^User is on context chooser page$")
    public void verify_context_chooser_page_is_displayed() {
        assertTrue("User should be in international context chooser page", contextChooser.isInternationalContextChooserPageDisplayed());
    }

    @Then("^([^\"]*) region is displayed")
    public void verify_region_is_displayed(String region) {
        assertTrue("Region section should be displayed",contextChooser.isRegionDisplayed(region));
    }
    

    
    @Then("^Verify countries displayed correctly under each region$")
    public void verify_countries_displayed_under_regions(List<String> regions){
    	for(String region:regions){
    		assertTrue("User should see correct countries displayed under region '" + region + "", contextChooser.isCountriesDisplayedCorrectlyUnderRegion(region.toUpperCase()));
    	}
    }
    
    @When("^Click on \"([^\"]*)\" link from terms section on the context chooser page$")
    public void click_link_from_terms_section_on_context_chooser_page(String linkName){
    	contextChooser.clickLinkFromTermsSectionOnContextChooserPage(linkName);
    }
    
    @When("^Click on SEE ALL FAQ & HELP button from FAQ section on the context chooser page$")
    public void click_button_from_faq_section_on_context_chooser_page(){
    	contextChooser.clickButtonFromFAQSectionOnContextChooserPage();
    }
    
    @When("^Click on borderfree.com link from FAQ section on the context chooser page$")
    public void click_link_from_faq_section_on_context_chooser_page(){
    	contextChooser.clickLinkFromFAQSectionOnContextChooserPage();
    }
    

    @When("^User selects ([^\"]*) at random from context chooser page$")
    public void select_random_country_from_country_group_on_context_chooser_page(String country_group) {
    	contextChooser.selectGroupRandomCountry(country_group);
    }
    
    @When("^User selects ([^\"]*) country from context chooser page$")
    public void select_country_on_context_chooser_page(String country) {
    	contextChooser.selectCountryOnContextChooserPage(country);
    }

    @Then("^User should land on country specific home page$")
    public void user_should_land_on_country_specific_home_page(){
    	contextChooser.isUserOnCountrySpecificHomePage();
    }
    

}