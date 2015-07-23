package com.jcrew.steps;


import com.jcrew.page.SubcategoryPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;

public class SubcategoryPageSteps extends DriverFactory {

    private Logger logger = LoggerFactory.getLogger(SubcategoryPageSteps.class);

    @When("^Adds a product to shopping bag$")
    public void adds_a_product_to_shopping_bag() throws Throwable {
        SubcategoryPage subcategoryPage = new SubcategoryPage(driver);

        subcategoryPage.adds_a_product_to_shopping_bag();

        assertFalse("Quick shop modal should no longer be present",
                subcategoryPage.quickShopModalIsPresent());

    }

    @When("^Clicks on shopping bag link$")
    public void clicks_on_shopping_bag_link() throws Throwable {

        new SubcategoryPage(driver).clicks_on_shopping_bag_link();

    }
}
