package com.jcrew.steps;

import com.jcrew.page.MyAccountPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyAccountPageSteps extends DriverFactory {

    private final MyAccountPage myAccountPage = new MyAccountPage(getDriver());

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

    @Then("^User clicks on ([^\"]*) link in My Account Page$")
    public void user_clicks_on_link_in_my_account_page(String link) throws Throwable {
        myAccountPage.click_menu_link(link);

    }

    @And("^User should be in ([^\"]*) menu link page$")
    public void User_should_be_in_page(String page) throws Throwable {
        assertTrue("User should have been in menu link " + page,
                myAccountPage.isInMenuLinkPage(page));
    }

    @Then("^User selects an order listed for review$")
    public void user_selects_an_order_listed_for_review() throws Throwable {
        myAccountPage.click_order_for_review();
    }
}
