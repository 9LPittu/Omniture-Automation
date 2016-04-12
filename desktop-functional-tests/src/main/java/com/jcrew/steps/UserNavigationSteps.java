package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class UserNavigationSteps extends DriverFactory {
    TestDataReader testDataReader = TestDataReader.getTestDataReader();

    @When("User adds to bag a random product using a main category")
    public void users_add_random_product() {
        user_navigates_to_a_pdp();

        select_product_and_add_to_bag();

    }

    @When("User navigates to a subcategory from main category")
    public void user_navigates_to_subcategory_from_main_category() {
        WebDriver driver = getDriver();
        List<String> categories = testDataReader.getMainCategories();

        HeaderWrap header = new HeaderWrap(driver);
        header.openMenu();

        MenuDrawer menuDrawer = new MenuDrawer(driver);
        menuDrawer.selectCategoryFromList(categories);
        menuDrawer.selectSubCategory();
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

        SaleLandingPage saleLandingPage = new SaleLandingPage(driver);
        saleLandingPage.selectRandomSaleCategory();

    }

    @When("User adds to bag a random product from sale")
    public void users_add_random_product_from_sale() {
        user_navigates_to_a_random_sale_page();

        SearchArray searchArray = new SearchArray(getDriver());
        searchArray.selectRandomProduct();

        select_product_and_add_to_bag();
    }

    private void select_product_and_add_to_bag() {
        ProductDetails productDetails = new ProductDetails(getDriver());
        productDetails.selectRandomColor();
        productDetails.selectRandomSize();
        productDetails.selectRandomQty();
        productDetails.addToBag();
    }
}
