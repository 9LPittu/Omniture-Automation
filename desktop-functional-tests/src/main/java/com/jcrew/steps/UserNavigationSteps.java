package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.page.header.*;
import com.jcrew.page.product.ProductDetailColors;
import com.jcrew.page.product.ProductDetails;
import com.jcrew.page.product.ProductDetailsSizes;
import com.jcrew.pojo.Country;
import com.jcrew.steps.header.HeaderSearchSteps;
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

        TopNav header = new TopNav(driver);
        header.hoverCategory(category);
        SubCategory subCategory = new SubCategory(driver);
        subCategory.selectSubCategory();
        Util.waitLoadingBar(driver);
    }


    @When("User navigates to a pdp")
    public void user_navigates_to_a_pdp () {
        user_navigates_to_subcategory_from_main_category();

        ArrayCategory productsArray = new ArrayCategory(driver);
        productsArray.selectRandomProduct("any");

    }

    @When("User adds to bag a random product from a search")
    public void users_add_random_product_from_search() {
        HeaderSearchSteps searchSteps = new HeaderSearchSteps();
        searchSteps.user_searches_for_a_search_term("random");

        ArraySearch searchArray = new ArraySearch(driver);
        searchArray.selectRandomProduct();

        select_product_and_add_to_bag();
    }

    @When("User navigates to a random sale page")
    public void user_navigates_to_a_random_sale_page() {
        TopNav header = new TopNav(driver);
        header.hoverCategory("sale");
        SubCategory subCategory = new SubCategory(driver);
        subCategory.selectASaleSubCategory();
    }
    
    @When("User navigates to random ([^\"]*) page")
    public void user_navigates_to_a_random_sale_clearance_page(String link) {
        TopNav header = new TopNav(driver);
        header.hoverCategory(link);
        SubCategory subCategory = new SubCategory(driver);
        subCategory.selectASaleSubCategory();
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
        ProductDetailColors colors = new ProductDetailColors(driver);
        colors.selectRandomColor();

        ProductDetailsSizes sizes = new ProductDetailsSizes(driver);
        sizes.selectRandomSize();
        
        //Commenting the below step as higher quantities are getting selected and causing problem during checkout 
        //productDetails.selectRandomQty();

        ProductDetails productDetails = new ProductDetails(driver);
        productDetails.addToBag();

        HeaderBag header = new HeaderBag(driver);
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
        String title = driver.getTitle();
        driver.navigate().back();
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        
        if (title.toLowerCase().contains("wishlist") && driver.getTitle().toLowerCase().contains("wishlist")) {
        	String newUrl = driver.getCurrentUrl();
            driver.navigate().back();
            Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(newUrl)));
        }
        Util.waitForPageFullyLoaded(driver);
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
        HeaderSearch header = new HeaderSearch(driver);
        header.searchFor(testDataReader.getData("multiple.colors.multiple.sizes.item" + sequenceNum));

        String url = getDriver().getCurrentUrl();
        if (url.contains("/r/search")) {
            ArraySearch search = new ArraySearch(getDriver());
            search.selectRandomProduct();
        }
    }
    
    @When("This script cleans bag for current user")
    public void clean_bag_for_current_user() {
    	navigation.clearBag();
    }
    
    @When("^User navigates to backordered product$")
    public void navigate_backordered() {
        HeaderSearch header = new HeaderSearch(driver);
        header.searchFor(testDataReader.getData("back.order.item"));
        
        select_item_from_search_results();

        ProductDetailColors colors = new ProductDetailColors(getDriver());
        colors.selectColor(testDataReader.getData("back.order.color"));

        ProductDetailsSizes sizes = new ProductDetailsSizes(driver);
        sizes.selectSize(testDataReader.getData("back.order.size"));
        
        header.stateHolder.put("backorderedItem", testDataReader.getData("back.order.item"));
    }
    
    @When("^User navigates to only few left product$")
    public void navigate_only_few_left() {
        HeaderSearch header = new HeaderSearch(driver);
        header.searchFor(testDataReader.getData("few.left.item"));
        
        select_item_from_search_results();

        ProductDetailColors colors = new ProductDetailColors(getDriver());
        colors.selectColor(testDataReader.getData("few.left.color"));

        ProductDetailsSizes sizes = new ProductDetailsSizes(driver);
        sizes.selectSize(testDataReader.getData("few.left.size"));
        
        header.stateHolder.put("fewLeftItem", testDataReader.getData("few.left.item"));
    }
    
    @When("^User navigates to regular product$")
    public void navigate_regular_item() {
        HeaderSearch header = new HeaderSearch(driver);
        header.searchFor(testDataReader.getData("regular.item"));
        
        select_item_from_search_results();

        ProductDetailColors colors = new ProductDetailColors(getDriver());
        colors.selectColor(testDataReader.getData("regular.item.color"));

        ProductDetailsSizes sizes = new ProductDetailsSizes(driver);
        sizes.selectSize(testDataReader.getData("regular.item.size"));
        
        header.stateHolder.put("regularItem", testDataReader.getData("regular.item"));
    }
    
	@When("^User navigates to promo applicable product$")
	public void navigate_promoapplicable_item() {
        HeaderSearch header = new HeaderSearch(driver);
		header.searchFor(testDataReader.getData("promoaplicable.item"));

		select_item_from_search_results();

        ProductDetailColors colors = new ProductDetailColors(getDriver());
		colors.selectColor(testDataReader.getData("promoaplicable.item.color"));

        ProductDetailsSizes sizes = new ProductDetailsSizes(driver);
		sizes.selectSize(testDataReader.getData("promoaplicable.item.size"));

		header.stateHolder.put("promoaplicableItem",
				testDataReader.getData("promoaplicable.item"));
	}
    
    public void select_item_from_search_results(){
    	String currentUrl = driver.getCurrentUrl();
		if(currentUrl.contains("/r/search")){
			ArraySearch arraySearch = new ArraySearch(driver);
			arraySearch.click_first_product_in_grid();
		}
    }
    

}
