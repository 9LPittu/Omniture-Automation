package com.jcrew.steps;

import com.jcrew.page.VeryPersonalStylistPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class VeryPersonalStylistPageSteps extends DriverFactory {

    @And("^Verify user is on personal stylist page$")
    public void verify_user_is_on_very_personal_stylist_page() throws Throwable {
        VeryPersonalStylistPage stylistPage = new VeryPersonalStylistPage(getDriver());
        assertTrue("User should have been in very personal stylist page", stylistPage.isVeryPersonalStylistPage());
    }

}
