package com.jcrew.steps;

import com.jcrew.page.MyAccountPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyAccountPageSteps extends DriverFactory {

    private MyAccountPage myAccountPage = new MyAccountPage(getDriver());

    @Then("^User is in My Account page$")
    public void user_is_in_My_Account_page() throws Throwable {
        assertTrue("User should be in My Account Page", myAccountPage.isInAccountPage());
    }

    @And("^Verifies page displays My Account title$")
    public void Verifies_page_displays_My_Account_title() throws Throwable {
        assertEquals("Header is not what is expected", "MY ACCOUNT", myAccountPage.getMyAccountHeader());
    }

    @And("^Validates link ([^\"]*) is displayed in My Account Page$")
    public void validates_link_is_displayed_in_My_Account_Page(String link) throws Throwable {
        assertTrue(link + " link should have been present", myAccountPage.isMenuLinkPresent(link));
    }
}
