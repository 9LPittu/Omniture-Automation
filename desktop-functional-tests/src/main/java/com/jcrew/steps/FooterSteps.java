package com.jcrew.steps;
import com.jcrew.page.Footer;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by 9hvenaga on 4/28/2016.
 */
public class FooterSteps extends DriverFactory {
    Footer footer = new Footer(getDriver());

    @Then("^User should see Ship To section in footer$")
    public void verify_ship_to_section_is_displayed(){
        assertTrue("SHIP TO section should be displayed in the footer",footer.isShipToSectionDisplayed());
    }

    @Then("^Verify country name is displayed in the ship to section of footer$")
    public void verify_country_name_displayed_in_footer(){
        assertTrue("Country name should be displayed in the footer",footer.isCountryNameDisplayedInFooter());
        assertEquals("Country name should be US","united states",footer.getCountryNameInFooter());
    }

    @Then("^Verify change link is displayed in the ship to section of footer$")
    public void verify_change_link_displayed_in_footer(){
        assertTrue("Change link should be displayed in the footer",footer.isChangeLinkDisplayedInFooter());
    }

    @When("^Click on change link from footer$")
    public void click_change_link_in_footer(){
        footer.clickChangeLinkInFooter();
    }

    @Then("^Verify selected country is in footer$")
    public void user_should_see_selected_country_in_footer(){
        assertTrue("User should see selected country name in the footer",footer.isCorrectCountryNameDisplayedInFooter());
    }
    
    @Then("^Verify ([^\"]*) link is displayed under ([^\"]*) in footer$")
    public void verify_link_in_footer(String footerLink, String footerHeaderName) {
        assertTrue(footerLink + " link is displayed under "+footerHeaderName+"section in footer", footer.isLinkDisplayedInAccordion(footerLink, footerHeaderName));
    }
    
    @When("^User clicks on ([^\"]*) link under ([^\"]*) in footer$")
    public void click_link_in_footer(String footerLink, String accordionName){
    	footer.clickFooterLinkFromDrawer(footerLink, accordionName);
    }
    @And("^Verify user is navigated to url ([^\"]*) on same page$")
    public void user_navigated_to(String pageURL){
        String current_url = getDriver().getCurrentUrl();
       assertTrue("Current page url contains" + pageURL+" and current url is "+current_url, current_url.contains(pageURL));
    }
}