package com.jcrew.steps;

import com.jcrew.pojo.Product;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 3/31/16.
 */
public class WishlistSteps extends DriverFactory {
    private StateHolder stateHolder = StateHolder.getInstance();
    Wishlist wishlist = new Wishlist(getDriver());


    @Then("Verify user is in wishlist page")
    public void user_is_in_wish_list_page() {
        assertTrue("User is in wishlist page", wishlist.isWishList());
    }
    @Then("^Deletes all previous wishlist items from the list$")
    public void deletes_all_previous_wishlist_items_from_the_list(){
        wishlist.delete_current_products();
    }

    @Then("Verify product name on wishlist matches with QS")
    public void product_name_matches_quick_shop() {
        Product product = (Product) stateHolder.get("fromQuickShop");
        assertTrue("Product "+product.getName()+" is available in wishlist page", wishlist.verifyProductInWishlist(product.getName()));
    }


}

