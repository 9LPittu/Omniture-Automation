package com.jcrew.steps;
import com.jcrew.page.Footer;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 4/28/2016.
 */
public class FooterSteps extends DriverFactory {
    Footer footer = new Footer(getDriver());

    @Then("^User should see Ship To section in footer$")
    public void verify_ship_to_section_is_displayed(){
        assertTrue("SHIP TO section should be displayed in the footer",footer.isShipToSectionDisplayed());
    }

    @And("^verify country name is displayed in the ship to section of footer$")
    public void verify_country_name_displayed_in_footer(){
        assertTrue("Country name should be displayed in the footer",footer.isCountryNameDisplayedInFooter());
    }

    @And("^verify change link is displayed in the ship to section of footer$")
    public void verify_change_link_displayed_in_footer(){
        assertTrue("Change link should be displayed in the footer",footer.isChangeLinkDisplayedInFooter());
    }

    @Then("^click on change link from footer$")
    public void click_change_link_in_footer(){
        footer.clickChangeLinkInFooter();
    }
}

