package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.pojo.Country;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class UserNavigationSteps extends DriverFactory {
    TestDataReader testDataReader = TestDataReader.getTestDataReader();
    private final UserNavigation navigation = new UserNavigation(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();
    private WebDriver driver = getDriver();    

    @When("User adds to bag a random product using a main category")
    public void users_add_random_product() {
        user_navigates_to_a_pdp();

        select_product_and_add_to_bag();

    }

    @When("User navigates to a subcategory from main category")
    public void user_navigates_to_subcategory_from_main_category() {
        String category = testDataReader.getCategory();
        String subCategotySection =testDataReader.getSubCategorySection(category);

        HeaderWrap header = new HeaderWrap(driver);
        header.hoverCategory(category);
        header.selectSubCategory();
        Util.waitLoadingBar(driver);
    }


    @When("User navigates to a pdp")
    public void user_navigates_to_a_pdp () {

        user_navigates_to_subcategory_from_main_category();

        ArrayCategory productsArray = new ArrayCategory(driver);
        productsArray.selectRandomProduct("any");

    }

    @When("User searches for a random search term")
    public void user_searches_for_a_random_search_term() {
        String term = testDataReader.getSearchWord();

        HeaderWrap header = new HeaderWrap(driver);
        header.searchFor(term);

    }

    @When("User adds to bag a random product from a search")
    public void users_add_random_product_from_search() {
        user_searches_for_a_random_search_term();

        ArraySearch searchArray = new ArraySearch(driver);
        searchArray.selectRandomProduct();

        select_product_and_add_to_bag();
    }

    @When("User navigates to a random sale page")
    public void user_navigates_to_a_random_sale_page() {
        HeaderWrap header = new HeaderWrap(driver);
        header.hoverCategory("sale");
        header.selectASaleSubCategory();
    }

    @When("User navigates to specific ([^\"]*) sale page")
    public void user_navigates_specific_sale_page(String specific) {
        MenuDrawer menuDrawer = new MenuDrawer(driver);
        menuDrawer.openSaleLandingPage();

        SaleLanding saleLandingPage = new SaleLanding(driver);
        saleLandingPage.click_on_sale_subcategory(specific);

    }

    @When("User adds to bag a random product from sale")
    public void users_add_random_product_from_sale() {
        user_navigates_to_a_random_sale_page();

        ArraySearch searchArray = new ArraySearch(driver);
        searchArray.selectRandomProduct();

        select_product_and_add_to_bag();
    }

    @When("User adds selected product to bag")
    public void select_product_and_add_to_bag() {
        ProductDetails productDetails = new ProductDetails(driver);
        productDetails.selectRandomColor();
        productDetails.selectRandomSize();
        productDetails.selectRandomQty();
        productDetails.addToBag();

        HeaderWrap header = new HeaderWrap(driver);
        header.waitUntilNoCheckOutDropdown();
    }

    @Then("Verify international page url")
    public void verify_pdp_url() {
        StateHolder stateHolder = StateHolder.getInstance();
        Country country = (Country) stateHolder.get("context");

        assertTrue("Is an array url", Util.countryContextURLCompliance(driver, country));
    }

    @Then("^User is on internal ([^\"]*) page$")
    public void user_is_on_page(String page) {
        Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains(page));
        assertTrue("Browser was expected to be at " + page + " and current page is "+driver.getCurrentUrl(),
                driver.getCurrentUrl().endsWith(page));
    }

    @Then("^External ([^\"]*) page is opened in a different tab$")
    public void user_is_on_external_page(String page) {
        assertTrue("User is not in an expected page in a different tab " + page, navigation.isCurrentUrl(page));
    }

    @When("^User presses browser back button$")
    public void user_presses_back_button() throws Throwable {
        String url = driver.getCurrentUrl();
        driver.navigate().back();
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }

    @Then("Verify country code in the url for international countries")
    public void user_should_see_country_code_in_url_for_international_countries(){
    	
        Country country = (Country)stateHolder.get("context");
        WebDriver driver = getDriver();
        assertTrue("Country code for'" + country.getName() + "' should be displayed in the url except United States",
                Util.countryContextURLCompliance(driver, country));
    }


    //This step is added in here to select the first product only if the page currently visited is a search array page.
    // Keeping the implementation this way will avoid the exception in the search array constructor where we are waiting
    //for the visibilty of the specific search element in that page.Exception handling in that constructor will break other things.
    @When("^User selects first product from search results$")
    public void user_selects_first_product_from_search_results(){
        WebDriver driver = getDriver();
        String currentUrl = driver.getCurrentUrl();

        if(currentUrl.contains("/r/search/")) {
            ArraySearch productsArray = new ArraySearch(getDriver());
            productsArray.click_first_product_in_grid();
        }
    }

    @When("^User navigates to product ([^\"]*) with multiple colors and multiple sizes$")
    public void search_product_from_reading_testdata(String sequenceNum) {
        HeaderWrap header = new HeaderWrap(getDriver());
        header.searchFor(testDataReader.getData("multiple.colors.multiple.sizes.item" + sequenceNum));
    }
    
    @When("This script cleans bag for current user")
    public void clean_bag_for_current_user() {
    	navigation.clearBag();
    }
    
    @When("^User navigates to backordered product$")
    public void navigate_backordered() {
        HeaderWrap header = new HeaderWrap(getDriver());
        header.searchFor(testDataReader.getData("back.order.item"));
        
        ProductDetails pdp = new ProductDetails(getDriver());
        pdp.selectColor(testDataReader.getData("back.order.color"));
        pdp.selectSize(testDataReader.getData("back.order.size"));
        
        header.stateHolder.put("backorderedItem", testDataReader.getData("back.order.item"));
    }
    
    @When("^User navigates to only few left product$")
    public void navigate_only_few_left() {
        HeaderWrap header = new HeaderWrap(getDriver());
        header.searchFor(testDataReader.getData("few.left.item"));
        
        ProductDetails pdp = new ProductDetails(getDriver());
        pdp.selectColor(testDataReader.getData("few.left.color"));
        pdp.selectSize(testDataReader.getData("few.left.size"));
        
        header.stateHolder.put("fewLeftItem", testDataReader.getData("few.left.item"));
    }
    
    @When("^User navigates to regular product$")
    public void navigate_regular_item() {
        HeaderWrap header = new HeaderWrap(getDriver());
        header.searchFor(testDataReader.getData("regular.item"));
        
        ProductDetails pdp = new ProductDetails(getDriver());
        pdp.selectColor(testDataReader.getData("regular.item.color"));
        pdp.selectSize(testDataReader.getData("regular.item.size"));
        
        header.stateHolder.put("regularItem", testDataReader.getData("regular.item"));
    }
}
