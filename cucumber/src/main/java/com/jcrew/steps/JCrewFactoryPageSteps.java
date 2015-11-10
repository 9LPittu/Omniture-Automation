package com.jcrew.steps;

import com.jcrew.page.JcrewFactoryPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class JCrewFactoryPageSteps extends DriverFactory {

    @And("^Verify user is on the j.crew factory page$")
    public void verify_user_is_on_the_j_crew_factory_page() throws Throwable {
        JcrewFactoryPage jCrewFactoryPage = new JcrewFactoryPage(getDriver());
        assertTrue("User should be in jcrew factory page", jCrewFactoryPage.isJcrewFactoryPage());
    }
}
