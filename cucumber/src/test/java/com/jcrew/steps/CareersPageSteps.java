package com.jcrew.steps;

import com.jcrew.page.CareersPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class CareersPageSteps extends DriverFactory {

    @And("^Verify user is on careers page$")
    public void verify_user_is_on_careers_page() throws Throwable {
        CareersPage jobsPage = new CareersPage(getDriver());
        assertTrue("User should have been in jcrew jobs page", jobsPage.isCareersPage());
    }
}
