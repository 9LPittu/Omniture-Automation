package com.jcrew.steps;

import com.jcrew.page.WelcomeMat;
import com.jcrew.pojo.Country;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
public class WelcomeMatSteps extends DriverFactory {
    private WelcomeMat welcomeMat = new WelcomeMat(getDriver());
    private StateHolder holder = StateHolder.getInstance();

    @Then("Verify welcome mat is displayed")
    public void verify_walcome_mat_is_displayed() {
        assertTrue("Welcome mat is displayed with expected elements", welcomeMat.verifyMatElements());
    }

    @Then("Verify country context matches selected country")
    public void verify_country_context_matches_selected_country() {
        Country country = holder.get("context");
        String countryName = country.getName().toLowerCase();
        String countryFlagName = countryName.replace(" ", "");

        assertEquals("Welcome mat country name matches selected country", countryName, welcomeMat.getCountry());
        assertTrue("Welcome mat country flag matches selected country", welcomeMat.getCountryFlagClass().contains(countryFlagName));

    }

    @Then("Verify welcome message")
    public void verify_welcome_message() {
        Country country = holder.get("context");
        String countryCode = country.getCountry();

        String expectedWelcomeMessage;
        if ("CA".equalsIgnoreCase(countryCode)) {
            expectedWelcomeMessage = "Hello, Canada!";
        } else {
            expectedWelcomeMessage = "Around the World";
        }

        assertEquals("Welcome message is expected for this country",
                expectedWelcomeMessage.toLowerCase(),
                welcomeMat.getWelcomeMessage().toLowerCase());
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
