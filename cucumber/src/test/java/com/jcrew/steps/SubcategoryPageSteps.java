package com.jcrew.steps;


import com.jcrew.page.SubcategoryPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubcategoryPageSteps extends DriverFactory {

    private SubcategoryPage subcategoryPage = new SubcategoryPage(driver);

    @When("^Adds a product to shopping bag$")
    public void adds_a_product_to_shopping_bag() throws Throwable {

        subcategoryPage.adds_a_product_to_shopping_bag();

        assertFalse("Quick shop modal should no longer be present",
                subcategoryPage.quickShopModalIsPresent());

    }

    @When("^Clicks on shopping bag link$")
    public void clicks_on_shopping_bag_link() throws Throwable {

        subcategoryPage.clicks_on_shopping_bag_link();

    }

    @Then("^User is in shirts and tops for women page$")
    public void user_is_in_shirts_and_tops_for_women_page() throws Throwable {

        assertTrue("A Subcategory page should have a product grid", subcategoryPage.isProductGridPresent());

        assertTrue("User should be in shirts and top page for women",
                driver.getCurrentUrl().endsWith("/c/womens_category/shirtsandtops"));

    }

}
