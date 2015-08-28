package com.jcrew.steps;


import com.jcrew.page.Header;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

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
}
