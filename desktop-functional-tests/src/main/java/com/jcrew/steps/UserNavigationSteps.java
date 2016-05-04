package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.pojo.Country;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import cucumber.api.java.en.And;
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
    private DriverFactory driverFactory;
    private WebDriver driver;

    @When("User adds to bag a random product using a main category")
    public void users_add_random_product() {
        user_navigates_to_a_pdp();

        select_product_and_add_to_bag();

    }

    @When("User navigates to a subcategory from main category")
    public void user_navigates_to_subcategory_from_main_category() {
        WebDriver driver = getDriver();
        String category = testDataReader.getCategory();
        String subCategory = testDataReader.getSubCategory(category);

        HeaderWrap header = new HeaderWrap(driver);
        header.openMenu();

        MenuDrawer menuDrawer = new MenuDrawer(driver);
        menuDrawer.selectCategory(category);

        menuDrawer.selectSubCategory(subCategory);
    }



    @When("User navigates to a pdp")
    public void user_navigates_to_a_pdp () {
        user_navigates_to_subcategory_from_main_category();

        ProductsArray productsArray = new ProductsArray(getDriver());
        productsArray.selectRandomProduct();

    }

    @When("User searches for a random search term")
    public void user_searches_for_a_random_search_term() {
        WebDriver driver = getDriver();
        String term = testDataReader.getSearchWord();

        HeaderWrap header = new HeaderWrap(driver);
        header.searchFor(term);

    }

    @When("User adds to bag a random product from a search")
    public void users_add_random_product_from_search() {
        user_searches_for_a_random_search_term();

        SearchArray searchArray = new SearchArray(getDriver());
        searchArray.selectRandomProduct();

        select_product_and_add_to_bag();
    }

    @When("User navigates to a random sale page")
    public void user_navigates_to_a_random_sale_page() {
        WebDriver driver = getDriver();

        HeaderWrap header = new HeaderWrap(driver);
        header.openMenu();

        MenuDrawer menuDrawer = new MenuDrawer(driver);
        menuDrawer.openSaleLandingPage();

        SaleLanding saleLandingPage = new SaleLanding(driver);
        saleLandingPage.selectRandomSaleCategory();

    }

    @When("User adds to bag a random product from sale")
    public void users_add_random_product_from_sale() {
        user_navigates_to_a_random_sale_page();

        SearchArray searchArray = new SearchArray(getDriver());
        searchArray.selectRandomProduct();

        select_product_and_add_to_bag();
    }

    @When("User adds selected product to bag")
    public void select_product_and_add_to_bag() {
        ProductDetails productDetails = new ProductDetails(getDriver());
        productDetails.selectRandomColor();
        productDetails.selectRandomSize();
        productDetails.selectRandomQty();
        productDetails.addToBag();
    }

    @Then("Verify international page url")
    public void verify_pdp_url() {
        StateHolder stateHolder = StateHolder.getInstance();
        Country country = (Country) stateHolder.get("context");

        assertTrue("Is an array url", Util.countryContextURLCompliance(getDriver(), country));
    }

    @Then("^User is on internal ([^\"]*) page$")
    public void user_is_on_page(String page) {
        Util.createWebDriverWait(getDriver()).until(ExpectedConditions.urlContains(page));
        assertTrue("Browser was expected to be at " + page + " and current page is "+getDriver().getCurrentUrl(),
                getDriver().getCurrentUrl().endsWith(page));
    }

    @Then("^External ([^\"]*) page is opened in a different tab$")
    public void user_is_on_external_page(String page) {
        assertTrue("User is not in an expected page in a different tab " + page, navigation.isCurrentUrl(page));
    }

    @Then("^User presses browser back button$")
    public void user_presses_back_button() throws Throwable {
        getDriver().navigate().back();
    }

    @And("User should see country code in the url for international countries")
    public void user_should_see_country_code_in_url_for_international_countries(){
    	
        Country c = (Country)stateHolder.get("context");
        WebDriver driver = getDriver();
        assertTrue("Country code '" + c.getCountry() + "' should be displayed in the url except United States",
                Util.createWebDriverWait(driver).until(ExpectedConditions.urlMatches(c.getHomeurl())));
    }


}
