package com.jcrew.steps;

import com.jcrew.page.WelcomeMatPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.*;

import static junit.framework.Assert.assertTrue;

/**
 * Created by 9hvenaga on 4/15/2016.
 */
public class WelcomeMatPageSteps extends DriverFactory {

    private final WelcomeMatPage welcomemat = new WelcomeMatPage(getDriver());

    @And("Welcome mat page is displayed")
    public void verify_welcome_mat_page_is_displayed() {
        assertTrue("Welcome mat page should be displayed",welcomemat.isWelcomeMatDisplayed());
    }

    @And("Welcome mat page is not displayed$")
    public void verify_welcome_mat_is_not_displayed() {
        assertTrue("Welcome mat page should not be displayed",welcomemat.isWelcomeMatNotDisplayed());
    }
}
