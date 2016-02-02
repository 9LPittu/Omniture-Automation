package com.jcrew.steps;


import com.jcrew.page.Header;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HeaderSteps extends DriverFactory {
    private final Header header = new Header(getDriver());


    @And("^Search Link is present$")
    public void search_link_is_present() throws Throwable {
        assertTrue("Search button should be displayed",
                header.isSearchLinkDisplayed());
    }

    @Then("^Verify ([^\"]*) header link is displayed$")
    public void verify_header_link_is_displayed(String headerLink) throws Throwable {
        assertTrue(headerLink + " should have been present", header.isHeaderLinkPresent(headerLink));
    }

    @Then("^Verify header bag icon is displayed$")
    public void verify_header_bag_icon_is_displayed() throws Throwable {
        assertTrue("Bag icon should have been present", header.isHeaderBagIconPresent());
    }


    @And("^Search drawer is open$")
    public void search_drawer_is_open() throws Throwable {
        assertTrue("Search box should have been displayed", header.isSearchDrawerOpen());
    }

    @And("^User presses search button$")
    public void presses_search_button() throws Throwable {
        header.click_on_search_button();
    }

    @And("^Verify ([^\"]*) header links including bag order is valid, ignore ([^\"]*)$")
    public void verify_order_including_bag_is_valid(String headerLinks, String ignoredLinks) throws Throwable {
        String[] headerLinksArray = headerLinks.split(",");
        String[] ignoredLinksArray = ignoredLinks.split(",");
        List<String> ignoredLinksList = new ArrayList<>();
        List<String> headerLinksList = new ArrayList<>();

        for (String ignoredLink : ignoredLinksArray) {
            ignoredLinksList.add(ignoredLink.trim());
        }

        ignoredLinksList.add("");

        for (String headerLink : headerLinksArray) {
            String headerLinkTrimmed = headerLink.trim();
            headerLinksList.add(headerLinkTrimmed);
        }

        headerLinksList.add(header.getBagIconLinkText());

        List<String> primaryNavigationLinkNames = header.getPrimaryNavigationLinkNames();

        primaryNavigationLinkNames.removeAll(ignoredLinksList);

        assertEquals("Header Links order is not correct.", headerLinksList, primaryNavigationLinkNames);

    }

    @Then("^Stores Link is present$")
    public void stores_link_is_present() throws Throwable {
        assertTrue("Stores link should be present", header.isStoresLinkPresent());
    }

    @Then("^User clicks on stores link$")
    public void user_clicks_on_stores_link() throws Throwable {
        header.click_on_stores_link();
    }

    @Then("^Clicks on JCrew Logo$")
    public void clicks_on_JCrew_Logo() throws Throwable {
        header.click_jcrew_logo();
    }

    @Given("^User clicks on item bag$")
    public void user_clicks_on_item_bag() throws Throwable {
        header.click_item_bag();
    }

    @And("^Bag Link is present$")
    public void Bag_Link_is_present() throws Throwable {
        assertTrue("Bag Link should be displaying", header.isBagLinkDisplaying());
    }

    @And("^Clicks on ([^\"]*) Breadcrumb$")
    public void clicks_on_breadcrumb(String breadcrumb) throws Throwable {
        header.click_breadcrumb(breadcrumb);
    }

    @And("User is in gender landing page$")
    public void verify_user_is_in_gender_landing_page() {
        assertTrue("User should be in gender landing page", header.isGenderLandingPage());
    }
}
