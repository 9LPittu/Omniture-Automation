package com.jcrew.steps;

import com.jcrew.page.MiniBag;
import com.jcrew.page.ProductDetails;
import com.jcrew.pojo.Product;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;

import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class MiniBagSteps extends DriverFactory {
    MiniBag miniBag = new MiniBag(getDriver());
    StateHolder holder = StateHolder.getInstance();

    @Then("Verify mini bag contains (\\d+) item")
    public void verify_mini_bag_contains_x_item(int items) {
        assertEquals("Mini bag contains expected " + items + " items", items, miniBag.getItemsNumber());
    }

    @Then("Verify mini bag contains only 3 items and a message to show more")
    public void verify_mini_bag_contains_x_item_and_message() {
        assertEquals("Mini bag contains 3 items in stack", miniBag.getItemsNumber(), 3);
        assertTrue("Mini bag shows message to see more items", miniBag.showsMoreItems());
    }

    @Then("Verify subtotal in mini bag matches items")
    public void verify_subtotal_in_mini_bag_matches_items() {
        float stackSum = 0;
        Stack<Product> products = (Stack<Product>) holder.get("bag_items");

        while (!products.empty()) {
            stackSum += products.pop().getItemPrice();
        }

        assertTrue("Subtotal in mini bag matches subtotal stack " + stackSum + "/" + miniBag.getMiniCartSubtotal()
                , stackSum == miniBag.getMiniCartSubtotal());
    }

    @Then("Verify first item is the recently added product")
    public void verify_first_item_is_the_recently_added_product() {
        Stack<Product> products = (Stack<Product>) holder.get("bag_items");

        Product firstProduct = miniBag.getItem(0);
        Product topProduct = products.peek();

        assertTrue("First product in bag is the recently added product", firstProduct.equals(topProduct));
    }

    @Then("Verify each item links to product PDP")
    public void verify_each_item_links_to_product_pdp() {
        int items = miniBag.getItemsNumber();

        Stack<Product> bagStack = (Stack<Product>) holder.get("bag_items");

        for (int i = 0; i < items; i++) {
            miniBag.clickOnItem(i);
            ProductDetails pdp = new ProductDetails(getDriver());
            Product product = pdp.getProduct();
            assertEquals("Product in bag gets to PDP", product, bagStack.pop());
        }

    }

    @Then("Verify message link matches button link")
    public void verify_message_link_matches_button_link() {
        assertTrue("Show more message goes to shopping cart", miniBag.messageAndButtonToCart());
    }

}
