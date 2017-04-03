package com.jcrew.steps.header;

import com.jcrew.page.header.HeaderLogo;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by ngarcia on 3/2/17.
 */
public class HeaderLogoSteps extends DriverFactory {
    private HeaderLogo logo = new HeaderLogo(getDriver());

    @When("User clicks (JCrew|Factory) logo")
    public void user_clicks_jcrew_logo(String brand) {
        logo.clickLogo();
    }

    @Then("Verify (jcrew|factory) logo is visible")
    public void is_logo_visible(String brand) {
        logo.isLogoVisible();
    }

    @When("User hovers (JCrew|Factory) logo")
    public void user_hovers_jcrew_logo(String brand) {
        logo.hoverLogo();
    }
}
