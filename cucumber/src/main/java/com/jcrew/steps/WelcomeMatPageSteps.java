package com.jcrew.steps;

import com.jcrew.page.WelcomeMatPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Util;

import cucumber.api.java.en.*;

import static org.junit.Assert.*;

/**
 * Created by 9hvenaga on 4/15/2016.
 */
public class WelcomeMatPageSteps extends DriverFactory {

    private final WelcomeMatPage welcomemat = new WelcomeMatPage(getDriver());

    @And("Welcome mat page is displayed")
    public void verify_welcome_mat_page_is_displayed() {
        assertTrue(Util.getSelectedCountryName() + "Welcome mat page should be displayed",welcomemat.isWelcomeMatDisplayed());
    }

    @And("Welcome mat page is not displayed$")
    public void verify_welcome_mat_is_not_displayed() {
        assertTrue(Util.getSelectedCountryName() + "Welcome mat page should not be displayed",welcomemat.isWelcomeMatNotDisplayed());
    }

    @And("^JCrew Logo is present on the welcome mat$")
    public void verify_jcrew_logo_displayed_on_welcome_mat() {
        assertTrue(Util.getSelectedCountryName() + "Jcrew logo should be displayed  on the welcome mat",welcomemat.isJcrewLogoDisplayed());
    }

    @And("^Welcome mat header message is displayed as ([^\"]*) for canada, ([^\"]*) for the ROW$")
    public void verify_welcome_mat_header_message(String msg1, String msg2) {
        assertTrue(Util.getSelectedCountryName() + "Corresponding welcome message header should be displayed", welcomemat.isWelcomeHeaderMessageDisplayed(msg1, msg2));
    }

    @And("^Welcome mat content is displayed$")
    public void verify_welcome_mat_body_content() {
        assertTrue(Util.getSelectedCountryName() + "Corresponding body message should be displayed", welcomemat.isWelcomeMatContentDisplayed());
    }

    @And("^User clicks on ([^\"]*) on welcome mat")
    public void user_clicks_on_start_shopping_button(String link) {
        welcomemat.click_on_start_shopping(link);

    }

    @And("^Corresponding country name and flag is displayed$")
    public void verify_corresponding_country_flag_name_displayed_on_welcome_mat() {
        assertTrue(Util.getSelectedCountryName() + "Corresponding flag and name should be displayed on the welcome mat", welcomemat.isFlagAndNameDisplayedCorrectly());

    }

    @And("^User is in initial international random page")
    public void verify_user_is_in_previously_selected_international_random_page() {
        assertTrue(Util.getSelectedCountryName() + "User should  be in initial international random page", welcomemat.isInInitialInternationalPage());
    }
}
