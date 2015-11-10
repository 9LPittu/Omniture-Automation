package com.jcrew.steps;

import com.jcrew.page.MadewellPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class MadewellPageSteps extends DriverFactory {

    @And("^Verify user is on the madewell page$")
    public void verify_user_is_on_the_madewell_page() throws Throwable {
        MadewellPage madewellPage = new MadewellPage(getDriver());
        assertTrue("User should be in madewell page", madewellPage.isMadewellPage());
    }

}
