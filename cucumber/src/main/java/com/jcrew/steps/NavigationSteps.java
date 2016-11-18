package com.jcrew.steps;

import com.jcrew.page.Header;
import com.jcrew.page.HomePage;
import com.jcrew.page.Navigation;
import com.jcrew.page.ProductDetailPage;
import com.jcrew.page.SearchPage;
import com.jcrew.pojo.Country;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class NavigationSteps extends DriverFactory {

	private final Logger logger = LoggerFactory.getLogger(NavigationSteps.class);
    private final Navigation navigation = new Navigation(getDriver());
    private final Header header = new Header(getDriver());
    private final HomePage homePage = new HomePage(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();
    TestDataReader testDataReader = TestDataReader.getTestDataReader();
    
    @Given("^User goes to ([^\"]*) page$")
    public void User_goes_to_page(String uri) throws Throwable { 
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
    	
        if(page.equalsIgnoreCase("/r/size-charts") && !browser.equalsIgnoreCase("phantomjs")){
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
        else{
        	Util.createWebDriverWait(getDriver()).until(ExpectedConditions.urlContains(page));
        	assertTrue("Browser was expected to be at " + page + " and current page is "+getDriver().getCurrentUrl(),
                getDriver().getCurrentUrl().endsWith(page));
        	logger.info("Browser url ends with '{}'", page);
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
    
    @When("^User navigates to product ([^\"]*) with multiple colors and multiple sizes$")
    public void search_product_from_reading_testdata(String sequenceNum) {
    	searchAndNavigateToPdp(testDataReader.getData("multiple.colors.multiple.sizes.item" + sequenceNum));
    }
    
    @When("User searches for a random search term")
    public void user_searches_for_a_random_search_term() {
        String term = testDataReader.getSearchWord();
        searchAndNavigateToPdp(term);
    }
    
    private void search(String searchTerm){
    	header.click_on_search_button();
        homePage.input_search_term(searchTerm);
        homePage.click_on_search_button_for_input_field(); 
    }
    
    @When("^User navigates to backordered product$")
    public void navigate_backordered() {
    	searchAndNavigateToPdp(testDataReader.getData("back.order.item"));
        
        ProductDetailPage pdp = new ProductDetailPage(getDriver());
        pdp.select_color(testDataReader.getData("back.order.color"));
        pdp.select_size(testDataReader.getData("back.order.size"));
        
        stateHolder.put("backorderedItem", testDataReader.getData("back.order.item"));
    }
    
    @When("^User navigates to only few left product$")
    public void navigate_only_few_left() {
    	searchAndNavigateToPdp(testDataReader.getData("few.left.item"));
        
    	ProductDetailPage pdp = new ProductDetailPage(getDriver());
        pdp.select_color(testDataReader.getData("few.left.color"));
        pdp.select_size(testDataReader.getData("few.left.size"));
        
        stateHolder.put("fewLeftItem", testDataReader.getData("few.left.item"));
    }
    
    @When("^User navigates to monogram product$")
    public void navigate_monogram() {
    	searchAndNavigateToPdp(testDataReader.getData("monogram.item"));
        
    	ProductDetailPage pdp = new ProductDetailPage(getDriver());
        pdp.select_color(testDataReader.getData("monogram.color"));
        pdp.select_size(testDataReader.getData("monogram.size"));
        
        stateHolder.put("monogramItem", testDataReader.getData("monogram.item"));
    }
    @When("^User navigates to regular product$")
    public void navigate_regular_item() {
    	searchAndNavigateToPdp(testDataReader.getData("regular.item"));
        
        ProductDetailPage pdp = new ProductDetailPage(getDriver());
        pdp.select_color(testDataReader.getData("regular.item.color"));
        pdp.select_size(testDataReader.getData("regular.item.size"));
        
        stateHolder.put("regularItem", testDataReader.getData("regular.item"));
    }
    
    public void searchAndNavigateToPdp(String searchItem){
    	search(searchItem);
    	
    	if(getDriver().getCurrentUrl().contains("r/search")){
         	SearchPage searchPage = new SearchPage(getDriver());
         	searchPage.selectRandomProduct();
        }
    }
}
