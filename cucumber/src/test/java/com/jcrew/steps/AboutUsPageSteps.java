package com.jcrew.steps;

import com.jcrew.page.AboutUsPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class AboutUsPageSteps extends DriverFactory {


    @And("^Verify user is on about us page$")
    public void Verify_user_is_on_about_us_page() throws Throwable {
        final AboutUsPage aboutUsPage = new AboutUsPage(getDriver());
        assertTrue("User should be in about us page", aboutUsPage.isAboutUsPage());
    }
}
