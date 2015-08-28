package com.jcrew.steps;

import com.jcrew.page.Navigation;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NavigationSteps extends DriverFactory {

    private Navigation navigation = new Navigation(getDriver());

    @Given("^User goes to ([^\"]*) page$")
    public void User_goes_to_page(String uri) throws Throwable {
        PropertyReader reader = PropertyReader.getPropertyReader();
        String environment = reader.getEnvironment();
        WebDriver driver = getDriver();
        String testingPage = environment + uri;
        driver.navigate().to(testingPage);
    }

    @And("^Search Link is present$")
    public void search_link_is_present() throws Throwable {
        assertTrue("Search button should be displayed",
                navigation.isSearchLinkDisplayed());
    }

    @Then("^Validate global promo is displaying on top of the page$")
    public void validate_global_promo_is_displaying_on_top_of_the_page() throws Throwable {
        assertTrue("Global Promo should be displaying", navigation.isGlobalPromoDisplayed());
    }

    @And("^Bag Link is present$")
    public void Bag_Link_is_present() throws Throwable {
        assertTrue("Bag Link should be displaying", navigation.isBagLinkDisplaying());
    }

    @And("^User presses search button$")
    public void presses_search_button() throws Throwable {
        navigation.click_on_search_button();
    }


    @Then("^Stores Link is present$")
    public void stores_link_is_present() throws Throwable {
        assertTrue("Stores link should be present", navigation.isStoresLinkPresent());
    }

    @And("^Closes hamburger menu$")
    public void Closes_hamburger_menu() throws Throwable {
        navigation.close_hamburger_menu();
    }

    @Then("^User presses back button$")
    public void user_presses_back_button() throws Throwable {
        getDriver().navigate().back();
    }

    @Then("^User is on ([^\"]*) page$")
    public void user_is_on_page(String page) {
        assertTrue("Browser was expected to be at " + page,
                getDriver().getCurrentUrl().endsWith(page));
    }

    @Then("^User is on external page ([^\"]*)$")
    public void user_is_on_external_page(String page) {
        assertEquals("User is not in an expected page", page, getDriver().getCurrentUrl());
    }

    @Then("^User clicks on stores link$")
    public void user_clicks_on_stores_link() throws Throwable {
        navigation.click_on_stores_link();
    }

    @Then("^User should be in shopping bag page$")
    public void user_should_be_in_shopping_bag_page() throws Throwable {
        user_is_on_page("/checkout2/shoppingbag.jsp?sidecar=true");
    }

    @Then("^Verify ([^\"]*) Department Link is present$")
    public void verify_department_link_is_present(String department) throws Throwable {
        assertTrue(department + " link should have been present", navigation.isDepartmentLinkPresent(department));
    }
}
