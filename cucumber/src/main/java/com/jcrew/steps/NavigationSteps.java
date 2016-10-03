package com.jcrew.steps;

import com.jcrew.page.Navigation;
import com.jcrew.pojo.Country;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class NavigationSteps extends DriverFactory {

	private final Logger logger = LoggerFactory.getLogger(NavigationSteps.class);
    private final Navigation navigation = new Navigation(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();
    
    @Given("^User goes to ([^\"]*) page$")
    public void User_goes_to_page(String uri) throws Throwable { 
    	PropertyReader reader = PropertyReader.getPropertyReader();
        
    	Country c = (Country)stateHolder.get("context");
        String homeurl = c.getHomeurl();
        getDriver().navigate().to( homeurl+ uri);
        Util.createWebDriverWait(getDriver()).until(ExpectedConditions.urlContains(uri));
    }

    @Then("^Validate global promo is displaying on top of the page$")
    public void validate_global_promo_is_displaying_on_top_of_the_page() throws Throwable {
        assertTrue("Global Promo should be displaying", navigation.isGlobalPromoDisplayed());
    }

    @Then("^User presses back button$")
    public void user_presses_back_button() throws Throwable {
        getDriver().navigate().back();
        logger.info("Browser is navigated back...");
    }

    @Then("^User is on internal ([^\"]*) page$")
    public void user_is_on_page(String page) {
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String browser = propertyReader.getProperty("browser");
    	
        if(browser.equalsIgnoreCase("phantomjs")){
        	Util.createWebDriverWait(getDriver()).until(ExpectedConditions.urlContains(page));
        	assertTrue("Browser was expected to be at " + page + " and current page is "+getDriver().getCurrentUrl(),
                getDriver().getCurrentUrl().endsWith(page));
        	logger.info("Browser url ends with '{}'", page);
        }
        else{
        	ArrayList<String> tabs = new ArrayList<String> (getDriver().getWindowHandles());
            logger.info("no.of windowhandles; {}", tabs.size());
            
            boolean result = false;
            for (String tab : tabs) {
                logger.info("window handle {}", tab);
                getDriver().switchTo().window(tab);
                logger.debug("Title in handle: {}", getDriver().getTitle());
                
                Util.waitLoadingBar(getDriver());
                String currentUrl = getDriver().getCurrentUrl();
                if(currentUrl.contains(page)){
                	result = true;
                	break;
                }
            }            
            assertTrue("Browser was expected to be at " + page + " and current page is " + getDriver().getCurrentUrl(), result);
        }
    }

    @Then("^external ([^\"]*) page is opened in a different tab$")
    public void user_is_on_external_page(String page) {
        assertTrue("User is not in an expected page in a different tab " + page, navigation.isCurrentUrl(page));
    }
    
    @And("^J.crew credit card ([^\"]*) page is opened in a different tab$")
    public void jcrew_credit_card_page_is_opened_in_different_tab(String expectedUrl){
    	PropertyReader reader = PropertyReader.getPropertyReader();
        if (!reader.getProperty("environment").equalsIgnoreCase("production")){
        	assertTrue("User is not in an expected page in a different tab " + expectedUrl, navigation.isCurrentUrl(expectedUrl));
        }
    }

    @Then("User is on external ([^\"]*) page")
    public void user_is_on_external_page_same_tab(String page) {
        assertTrue("User is not in an expected page in the same tab " + page, navigation.isCurrentUrlInSameTab(page));
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
    
    @And("^page url should contain ([^\"]*)$")
    public void page_url_contains(String url) {
    	Util.createWebDriverWait(getDriver()).until(ExpectedConditions.urlContains(url));
        assertTrue("Page URL should contain " + url,
                getDriver().getCurrentUrl().contains(url));
    }
}
