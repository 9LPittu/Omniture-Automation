package com.jcrew.steps;


import com.jcrew.page.Header;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HeaderSteps extends DriverFactory {
    private Header header = new Header(getDriver());


    @Then("^Verify ([^\"]*) header link is displayed$")
    public void verify_header_link_is_displayed(String headerLink) throws Throwable {
        assertTrue(headerLink + " should have been present", header.isHeaderLinkPresent(headerLink));
    }

    @And("^Search drawer is open$")
    public void search_drawer_is_open() throws Throwable {
        assertTrue("Search box should have been displayed", header.isSearchDrawerOpen());
    }

    @And("^Verify ([^\"]*) header links order is valid, ignore ([^\"]*)$")
    public void verify_order_is_valid(String headerLinks, String ignoredLinks) throws Throwable {
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

        List<String> primaryNavigationLinkNames = header.getPrimaryNavigationLinkNames();

        primaryNavigationLinkNames.removeAll(ignoredLinksList);

        assertEquals("Header Links order is not the expected", headerLinksList, primaryNavigationLinkNames);

    }

    @Then("^Clicks on JCrew Logo$")
    public void clicks_on_JCrew_Logo() throws Throwable {
        header.click_jcrew_logo();
    }
}
