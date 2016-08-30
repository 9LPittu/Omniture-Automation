package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class CheckOutSteps extends DriverFactory {
	
	CheckoutShoppingBag checkout = new CheckoutShoppingBag(getDriver());

    @Then("^Verify proper currency symbol for the items is displayed on bag page$")
    public void verify_items_currency_sign_matches_context_on_bag_page() {
    	
        String countryName = checkout.country.getName();
        List<String> itemsPrice = checkout.getItemsPrice();
        for (String price : itemsPrice) {
            assertTrue("Item price " + price + " matches country context " + countryName,
                    CurrencyChecker.isValid(price, checkout.country));
        }
    }

    @Then("^Verify proper currency symbol for subtotal is displayed on bag page$")
    public void verify_subtotal_currency_sign_matches_context_on_bag_page() {
        String countryName = checkout.country.getName();
        String subtotal = checkout.getSubtotal();
        assertTrue("Subtotal " + subtotal + " matches country context " + countryName,
                    CurrencyChecker.isValid(subtotal, checkout.country));
    }

    @Then("^Verify proper currency symbol for shipping is displayed on bag page$")
    public void verify_shipping_currency_sign_matches_context() {
        String shipping = checkout.getEstimatedShipping();
        String countryName = checkout.country.getName();
        assertTrue("Shipping " + shipping + " matches country context " + countryName,
                CurrencyChecker.isValid(shipping, checkout.country));

    }

    @Then("^Verify proper currency symbol for total is displayed on bag page$")
    public void verify_total_currency_sign_matches_context() {

        String total = checkout.getEstimatedTotal();
        String countryName = checkout.country.getName();
        assertTrue("Subtotal " + total + " matches country context " + countryName,
                CurrencyChecker.isValid(total, checkout.country));
    }
}
