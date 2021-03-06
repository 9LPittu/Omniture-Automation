package com.jcrew.steps;

import com.jcrew.page.header.HeaderBag;
import com.jcrew.page.header.HeaderLogo;
import com.jcrew.page.MiniBag;
import com.jcrew.page.product.ProductDetails;
import com.jcrew.pojo.Product;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class MiniBagSteps extends DriverFactory {
    private final Logger logger = LoggerFactory.getLogger(MiniBagSteps.class);
    private MiniBag miniBag = new MiniBag(getDriver());
    private StateHolder holder = StateHolder.getInstance();
    private final boolean isBrowser;

    public MiniBagSteps() {
        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String browser = propertyReader.getProperty("browser");

        isBrowser = "chrome".equalsIgnoreCase(browser) | "firefox".equalsIgnoreCase(browser);
    }

    @Then("Verify mini bag contains (\\d+) item")
    public void verify_mini_bag_contains_x_item(int items) {
        if(isBrowser) {
            assertEquals("Mini bag contains expected " + items + " items", items, miniBag.getItemsNumber());
        } else {
            logger.warn("Not able to verify mini bag in phantomJS, skipping assert");
        }
    }

    @Then("Verify mini bag contains a message to show more and 3 items")
    public void verify_mini_bag_contains_x_item_and_message() {
        if(isBrowser) {
            assertEquals("Mini bag contains 3 items in stack", miniBag.getItemsNumber(), 3);
            assertTrue("Mini bag shows message to see more items", miniBag.showsMoreItems());
        } else {
            logger.warn("Not able to verify mini bag in phantomJS, skipping assert");
        }
    }

    @Then("Verify subtotal in mini bag matches items")
    public void verify_subtotal_in_mini_bag_matches_items() {
        float stackSum = 0;
        Stack<Product> productsStack = holder.get("bag_items");
        Stack<Product> original = (Stack<Product>) productsStack.clone();

        while (!productsStack.empty()) {
            Product p = productsStack.pop();
            stackSum += p.getItemPrice();
        }

        holder.put("bag_items", original);

        assertEquals("Subtotal in mini bag matches subtotal stack " + stackSum + "/" + miniBag.getMiniCartSubtotal(),
                String.format("%.2f", stackSum), miniBag.getMiniCartSubtotal());
    }

    @Then("Verify first item is the recently added product")
    public void verify_first_item_is_the_recently_added_product() {
        Stack<Product> products = holder.get("bag_items");

        Product firstProduct = miniBag.getItem(0);
        Product topProduct = products.peek();

        assertTrue("First product in bag is the recently added product", firstProduct.equals(topProduct));

        HeaderLogo headerLogo = new HeaderLogo(getDriver());
        headerLogo.hoverLogo();

        HeaderBag bag = new HeaderBag(getDriver());
        bag.waitUntilNoCheckOutDropdown();
    }

    @Then("Verify each item links to product PDP")
    public void verify_each_item_links_to_product_pdp() {
        HeaderBag bag = new HeaderBag(getDriver());
        if(isBrowser) {
            int items = miniBag.getItemsNumber();

            Stack<Product> bagStack = holder.get("bag_items");
            assertTrue("Number of items in mini bag (" + items + ") are less or equal " +
                    "than expected (" + bagStack.size() + ")", items <= bagStack.size());

            Stack<Product> original = (Stack<Product>) bagStack.clone();

            logger.debug("Checking {} items in bag", items);

            for (int i = 0; i < items; i++) {
                bag.hoverBag();
                miniBag.clickOnItem(i);
                ProductDetails pdp = new ProductDetails(getDriver());
                Product product;
                try {

                    product = pdp.getProduct();
                    assertTrue("Product " + i + " in bag gets to PDP", product.equals(bagStack.pop(), true));

                } catch (NoSuchElementException notExpectedPDP) {
                    if (notExpectedPDP.getMessage().contains("js-product__size")) {
                        logger.error("PDP does not pre-selected the expected size displayed in minibag. This error is reported in JCSC-1003.");
                        bagStack.pop();
                    } else {
                        throw notExpectedPDP;
                    }
                }
            }

            holder.put("bag_items", original);
        } else {
            logger.warn("Not able to verify mini bag in phantomJS, skipping assert");
        }
    }

    @Then("Verify message link matches button link")
    public void verify_message_link_matches_button_link() {
        assertTrue("Show more message goes to shopping cart", miniBag.messageAndButtonToCart());
    }

    @Then("Verify mini bag is hidden")
    public void verify_mini_bag_is_hidden() {
        assertFalse("Mini bag is hidden", miniBag.isMiniBagVisible());
    }


    @Then("Verify proper currency symbol is displayed on minibag")
    public void verify_currency_on_mini_bag() {
        if(isBrowser) {
            assertTrue("mini bag currency should be displayed correctly",
                    miniBag.isCorrectCurrencyDisplayedOnMinibag());
        } else {
            logger.warn("Not able to verify mini bag in phantomJS, skipping assert");
        }
    }

}
