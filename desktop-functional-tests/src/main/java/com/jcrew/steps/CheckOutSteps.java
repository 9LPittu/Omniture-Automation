package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.pojo.Country;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class CheckOutSteps extends DriverFactory {
	private CheckoutShoppingBag checkout = new CheckoutShoppingBag(getDriver());
	private StateHolder holder = StateHolder.getInstance();

    @Then("^Verify proper currency symbol for the items is displayed on bag page$")
    public void verify_items_currency_sign_matches_context_on_bag_page() {
    	Country country = holder.get("context");
        String countryName = country.getName();

        List<String> itemsPrice = checkout.getItemsPrice();
        for (String price : itemsPrice) {
            assertTrue("Item price " + price + " matches country context " + countryName,
                    CurrencyChecker.isValid(price));
        }
    }

    @Then("^Verify proper currency symbol for subtotal is displayed on bag page$")
    public void verify_subtotal_currency_sign_matches_context_on_bag_page() {
        Country country = holder.get("context");
        String countryName = country.getName();

        String subtotal = checkout.getSubtotal();
        assertTrue("Subtotal " + subtotal + " matches country context " + countryName,
                    CurrencyChecker.isValid(subtotal));
    }

    @Then("^Verify proper currency symbol for shipping is displayed on bag page$")
    public void verify_shipping_currency_sign_matches_context() {
        String shipping = checkout.getEstimatedShipping();
        Country country = holder.get("context");
        String countryName = country.getName();
        assertTrue("Shipping " + shipping + " matches country context " + countryName,
                CurrencyChecker.isValid(shipping));

    }

    @Then("^Verify proper currency symbol for total is displayed on bag page$")
    public void verify_total_currency_sign_matches_context() {

        String total = checkout.getEstimatedTotal();
        Country country = holder.get("context");
        String countryName = country.getName();
        assertTrue("Subtotal " + total + " matches country context " + countryName,
                CurrencyChecker.isValid(total));
    }
    
    @When("User adds a zip code ([^\"]*)")
    public void add_zip_code(String code) {
    	checkout.addZipCode(code);
    }

@Then("Verify zipcode message says: ([^\"]*)")
    public void zip_message(String message) {
        String actual = checkout.getZipCodeMessage();

        assertEquals("Expected zip code message", message, actual);
    }
}
