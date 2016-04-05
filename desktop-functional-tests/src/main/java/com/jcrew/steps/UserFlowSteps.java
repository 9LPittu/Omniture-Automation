package com.jcrew.steps;

import com.jcrew.page.MenuDrawer;
import com.jcrew.page.ProductDetails;
import com.jcrew.page.ProductsArray;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;
import java.util.Arrays;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class UserFlowSteps extends DriverFactory {

    @When("User adds to bag a random product using a main category")
    public void users_add_random_product(){
        List<String> categories = Arrays.asList("Men", "Women","Girls", "Boys");

        MenuDrawer menuDrawer = new MenuDrawer(getDriver());
        menuDrawer.selectCategoryFromList(categories);
        menuDrawer.selectSubCategory();

        ProductsArray productsArray = new ProductsArray(getDriver());
        productsArray.selectRandomProduct();

        ProductDetails productDetails = new ProductDetails(getDriver());
        productDetails.selectRandomColor();
        productDetails.selectRandomSize();
        productDetails.selectRandomQty();
        productDetails.addToBag();

    }
}
