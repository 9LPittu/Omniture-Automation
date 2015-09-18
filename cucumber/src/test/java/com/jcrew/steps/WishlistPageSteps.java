package com.jcrew.steps;


import com.jcrew.page.WishlistPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WishlistPageSteps extends DriverFactory {

    private WishlistPage wishlistPage = new WishlistPage(getDriver());


    @And("^User should be in wishlist page$")
    public void User_should_be_in_wishlist_page() throws Throwable {
        assertTrue("User should have been in wishlist page", wishlistPage.isWishlistPage());
    }

    @Then("^Verify color is ([^\"]*) in wishlist page$")
    public void verify_color_is_in_wishlist_page(String productColor) throws Throwable {
        assertEquals("Color should have been equal", productColor, wishlistPage.getSelectedItemProductColor());
    }

    @Then("^Verify size is ([^\"]*) in wishlist page$")
    public void Verify_size_is_in_wishlist_page(String productSize) throws Throwable {
        assertEquals("Size should have been equal", productSize, wishlistPage.getSelectedItemProductSize());
    }

    @Then("^Verify quantity is ([^\"]*) in wishlist page$")
    public void Verify_quantity_is_in_wishlist_page(String productQuantity) throws Throwable {
        assertEquals("Quantity should have been equal", productQuantity, wishlistPage.getSelectedItemProductQuantity());
    }

    @Then("^Click on product ([^\"]*) to display properties$")
    public void Click_on_product_item_to_display_properties(String itemId) throws Throwable {
        wishlistPage.click_product(itemId);
    }

    @Then("^Click on home icon menu$")
    public void Click_on_home_icon_menu() throws Throwable {
        wishlistPage.click_home_icon();
    }

    @And("^Click on edit wishlist product$")
    public void Click_on_edit_wishlist_product() throws Throwable {
        wishlistPage.click_edit_product();
    }
}
