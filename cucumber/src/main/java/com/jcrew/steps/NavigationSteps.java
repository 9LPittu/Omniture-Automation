package com.jcrew.steps;

import com.jcrew.page.Navigation;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
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

    @Then("^Validate global promo is displaying on top of the page$")
    public void validate_global_promo_is_displaying_on_top_of_the_page() throws Throwable {
        assertTrue("Global Promo should be displaying", navigation.isGlobalPromoDisplayed());
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
        assertTrue("User is not in an expected page " + page, navigation.isCurrentUrl(page));
    }

    @Then("^Verify ([^\"]*) Department Link is present$")
    public void verify_department_link_is_present(String department) throws Throwable {
        assertTrue(department + " link should have been present", navigation.isDepartmentLinkPresent(department));
    }

    @And("^Move to mobile site$")
    public void move_to_mobile_site() throws Throwable {
        String currentUrl = getDriver().getCurrentUrl();
        String mobileSite = currentUrl + "&isMobile=true";
        getDriver().get(mobileSite);
    }
}
