package com.jcrew.steps;


import com.jcrew.page.Header;
import com.jcrew.page.HomePage;
import com.jcrew.page.SubcategoryPage;
import com.jcrew.util.DatabaseReader;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HeaderSteps extends DriverFactory {

	private final Header header = new Header(getDriver());
	private final HomePage homePage = new HomePage(getDriver());
    private final SubcategoryPage subcategory = new SubcategoryPage(getDriver());


    @And("^Search Link is present$")
    public void search_link_is_present() throws Throwable {
        assertTrue("Search button should be displayed",
                header.isSearchLinkDisplayed());
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

    @And("Verify J crew breadcrumb is not displayed$")
    public void jcrew_breadcrumb_not_displayed() {
        assertTrue("Jcrew bread crumb should not be displayed", header.isJcrewBreadCrumbNotDisplayed());
    }


    @And("User is in ([^\"]*) gender landing page$")
    public void verify_user_is_in_gender_landing_page(String gender) {
        assertTrue("User should be in gender landing page", header.isGenderLandingPage(gender));
    }

    @And("Verify Embedded header is displayed$")
    public void verify_embedded_header_is_present() {
        assertTrue("Embedded header section should be present",header.isEmbeddedHeaderSectionDisplayed());
    }

    @Then("Verify all header items are correctly displayed$")
    public void verify_header_is_correctly_displayed() {
        assertTrue("All menu items are displayed",header.isAllMenuItemsDisplayed());
    }

    @Then("Verify bag button link$")
    public void verify_bag_button_link() {
        assertTrue("Verify bag link", header.getBagButtonLink().contains("/checkout2/shoppingbag.jsp?sidecar=true"));
    }

    @Then("Verify stores button link$")
    public void verify_stores_button_link() {
        assertTrue("Verify stores link", header.getStoresButtonLink().contains("stores.jcrew.com/"));
    }
    
    @When("^\"([^\"]*)\" is run and search for item fetched from DB$")
    public void sql_query_is_executed_and_item_is_searched(String propertyName) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
    	
    	DatabaseReader dbReader = new DatabaseReader();
    	String itemCode = dbReader.executeSQLQueryAgainstDB(propertyName);
    	
    	header.click_on_search_button();
    	
    	homePage.input_search_term(itemCode);
    	homePage.click_on_search_button_for_input_field();
    	
    	subcategory.selectRandomItemFromArrayPage();
    }
}
