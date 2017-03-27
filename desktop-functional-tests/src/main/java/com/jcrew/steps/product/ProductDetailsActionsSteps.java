package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetailsActions;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 3/23/17.
 */
public class ProductDetailsActionsSteps extends DriverFactory {
    private ProductDetailsActions actions = new ProductDetailsActions(getDriver());

    @When("User adds product to bag")
    public void user_adds_product_to_bag() {
        actions.addToBag();
    }

    @Then("Verify Add To Bag button is displayed")
    public void add_to_bag_is_displayed() {
        assertTrue("Add to bag button is displayed", actions.isAddToBagDisplayed());
    }

    @When("^Update Bag button is pressed$")
    public void Update_Bag_button_is_pressed() {
        actions.click_update_cart();
    }

    @Then("Verify Update Bag button is displayed")
    public void edit_bag_is_displayed() {
        assertTrue("Update bag button is displayed", actions.isUpdateBagDisplayed());
    }

    @Then("Verify Wishlist button is displayed")
    public void wishlist_is_displayed() {
        assertTrue("Wishlist button is displayed", actions.isWishlistDisplayed());
    }
}
