package com.jcrew.steps;


import com.jcrew.page.WishlistPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WishlistPageSteps extends DriverFactory {

    private final WishlistPage wishlistPage = new WishlistPage(getDriver());


    @And("^User should be in wishlist page$")
    public void user_should_be_in_wishlist_page() throws Throwable {
        assertTrue("User should have been in wishlist page", wishlistPage.isWishlistPage());
    }

    @Then("^Click on home icon menu$")
    public void click_on_home_icon_menu() throws Throwable {
        wishlistPage.click_home_icon();
    }

    @And("^Click on edit wishlist product$")
    public void click_on_edit_wishlist_product() throws Throwable {
        wishlistPage.click_edit_product();
    }

    @And("^Verify product ([^\"]*) color is ([^\"]*) size is ([^\"]*) and quantity is ([^\"]*) in wishlist page$")
    public void verify_product_attributes(String productName, String productColor, String productSize, String productQuantity) throws Throwable {
        assertEquals("Color should have been equal", productColor, wishlistPage.getColorForProduct(productName));
        assertEquals("Size should have been equal", productSize, wishlistPage.getSizeForProduct(productName));
        assertEquals("Quantity should have been equal", productQuantity, wishlistPage.getQuantityForProduct(productName));
    }

    @Then("^Edit wishlist for product ([^\"]*)$")
    public void edit_wishlist_for_product_Rustic_cotton_fisherman_sweater(String productName) throws Throwable {
        wishlistPage.click_product(productName);
    }


    @Then("^Verify update message for button wishlist is displayed$")
    public void verify_update_message_for_button_wishlist_is_displayed() throws Throwable {
        assertEquals("Button message after updating was not present", "ADDED TO WISHLIST",
                wishlistPage.getUpdateWishlistMessage());
    }

    @And("^Deletes all previous wishlist items from the list$")
    public void deletes_all_previous_wishlist_items_from_the_list() throws Throwable {
        wishlistPage.delete_current_products();
    }
}
