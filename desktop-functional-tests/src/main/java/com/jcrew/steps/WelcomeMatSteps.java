package com.jcrew.steps;

import com.jcrew.page.WelcomeMat;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
public class WelcomeMatSteps extends DriverFactory {
    WelcomeMat welcomeMat = new WelcomeMat(getDriver());

    @Then("Verify welcome mat is displayed")
    public void verify_walcome_mat_is_displayed() {
        assertTrue("Welcome mat is displayed with expected elements", welcomeMat.verifyMatElements());
    }

    @Then("Verify country context matches selected country")
    public void verify_country_context_matches_selected_country() {
        assertTrue("Welcome mat country matches selected country", welcomeMat.verifyCountryContext());

    }

    @Then("Verify welcome message")
    public void verify_welcome_message() {
        assertTrue("Welcome message is expected for this country", welcomeMat.verifyWelcomeMessage());
    }

    @When("User clicks on Start Shopping")
    public void user_clicks_on_start_shopping() {
        welcomeMat.clickStartShopping();
    }

    @When("User clicks on Take me to US site")
    public void user_clicks_on_take_me_to_US_site() {
        welcomeMat.goToUsSite();
    }
}
