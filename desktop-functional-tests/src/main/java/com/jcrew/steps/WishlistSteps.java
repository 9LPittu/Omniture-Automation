package com.jcrew.steps;

import com.jcrew.page.Wishlist;
import com.jcrew.pojo.Product;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

import java.util.List;

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
    @Then("Verify all items were added to wishlist")
    public void all_items_added_to_wishlist() {
        List<Product> addedList = stateHolder.getList("toWishList");

        assertEquals("All items were added", addedList.size(), wishlist.getItemNumber());

        for(Product added : addedList) {
            Product inWishList = wishlist.getWishlistItem(added.getItemNumber());

            assertNotNull("Added item " + added.getItemNumber() + " is in wishlist", inWishList);
            assertEquals("Added item has same name", added.getName().toLowerCase(), inWishList.getName().toLowerCase());
            assertEquals("Added item has same color", added.getColor().toLowerCase(), inWishList.getColor().toLowerCase());
            assertEquals("Added item has same size", added.getSize().toLowerCase(), inWishList.getSize().toLowerCase());
        }
    }
    @Then("Verify items count matches in wishlist")
    public void isWishlistItemsCountMatches() {
    	List<Product> itemsCount = stateHolder.get("itemsInTray");
    	
    	int expected = itemsCount.size();
            int actual = wishlist.getItemNumber();

            assertEquals("Expected number of items in wishlist", expected, actual);
        }
}

