package com.jcrew.steps;


import com.jcrew.page.WishlistPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class WishlistPageSteps extends DriverFactory {

    @And("^User should be in wishlist page$")
    public void User_should_be_in_wishlist_page() throws Throwable {
        WishlistPage wishlistPage = new WishlistPage(getDriver());
        assertTrue("User should have been in wishlist page", wishlistPage.isWishlistPage());
    }
}
