package com.jcrew.steps;

import com.jcrew.page.Header;
import com.jcrew.page.HeaderWrap;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.Util;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

public class HeaderSteps extends DriverFactory {
	private final Header header = new Header(getDriver());

    @Given("^User clicks on item bag$")
    public void user_clicks_on_item_bag() throws Throwable {
        header.click_item_bag();
    }
    
    @Then("^Verify ([^\"]*) header link is displayed$")
    public void verify_header_link_is_displayed(String headerLink) throws Throwable {
        if (!headerLink.equalsIgnoreCase("BAG")) {
            assertTrue(headerLink + " should have been present", header.isHeaderLinkPresent(headerLink));
        }
        else {
            assertTrue(headerLink + " should have been present", header.isBagLinkDisplaying());
        }
    }
    
    @And("^User presses search button$")
    public void presses_search_button() throws Throwable {
        header.click_on_search_button();
    }
    
    @And("^Search drawer is open$")
    public void search_drawer_is_open() throws Throwable {
        assertTrue("Search box should have been displayed", header.isSearchDrawerOpen());
    }
    
    @Then("Verify stores button link$")
    public void verify_stores_button_link() {
        assertTrue("Verify stores link", header.getStoresButtonLink().contains("stores.jcrew.com/"));
    }
    
    @Then("^Verify header bag icon is displayed$")
    public void verify_header_bag_icon_is_displayed() throws Throwable {
        assertTrue("Bag icon should have been present", header.isHeaderBagIconPresent());
    }
    
    @Then("Verify bag button link$")
    public void verify_bag_button_link() {
        assertTrue("Verify bag link", header.getBagButtonLink().contains("/checkout2/shoppingbag.jsp?sidecar=true"));
    }
}
