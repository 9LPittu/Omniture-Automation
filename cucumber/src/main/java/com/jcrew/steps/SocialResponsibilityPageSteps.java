package com.jcrew.steps;

import com.jcrew.page.SocialResponsibilityPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class SocialResponsibilityPageSteps extends DriverFactory {


    @And("^Verify user is on social responsibility page$")
    public void verify_user_is_on_social_responsibility_page() throws Throwable {
        SocialResponsibilityPage responsibilityPage = new SocialResponsibilityPage(getDriver());
        assertTrue("User should have been in responsibility page", responsibilityPage.isSocialResponsibilityPage());
    }
}
