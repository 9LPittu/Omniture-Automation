package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.Util;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class UserFlowSteps extends DriverFactory {

    @When("User adds to bag a random product using a main category")
    public void users_add_random_product() {
        WebDriver driver = getDriver();
        List<String> categories = Arrays.asList("Men", "Women", "Girls", "Boys");

        HeaderWrap header = new HeaderWrap(driver);
        header.openMenu();

        MenuDrawer menuDrawer = new MenuDrawer(driver);
        menuDrawer.selectCategoryFromList(categories);
        menuDrawer.selectSubCategory();

        ProductsArray productsArray = new ProductsArray(driver);
        productsArray.selectRandomProduct();

        select_product_and_add_to_bag();

    }

    @When("User adds to bag a random product from a search")
    public void users_add_random_product_from_search() {
        WebDriver driver = getDriver();
        List<String> terms = Arrays.asList("SWEATERS", "SKIRTS", "JEANS", "RINGS", "BLOUSE");
        int index = Util.randomIndex(terms.size());
        String term = terms.get(index);

        HeaderWrap header = new HeaderWrap(driver);
        header.searchFor(term);

        SearchArray searchArray = new SearchArray(driver);
        searchArray.selectRandomProduct();

        select_product_and_add_to_bag();
    }

    @When("User adds to bag a random product from sale")
    public void users_add_random_product_from_sale() {
        WebDriver driver = getDriver();

        HeaderWrap header = new HeaderWrap(driver);
        header.openMenu();

        MenuDrawer menuDrawer = new MenuDrawer(driver);
        menuDrawer.openSaleLandingPage();

        SaleLandingPage saleLandingPage = new SaleLandingPage(driver);
        saleLandingPage.selectRandomSaleCategory();

        SearchArray searchArray = new SearchArray(driver);
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
