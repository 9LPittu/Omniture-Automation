package com.jcrew.steps;

import com.jcrew.page.Wishlist;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/31/16.
 */
public class WishlistSteps extends DriverFactory {
    Wishlist wishlist = new Wishlist(getDriver());

    @Then("Verify user is in wishlist page")
    public void user_is_in_wish_list_page(){
        assertTrue("User is in wishlist page", wishlist.isWishList());
    }
}
