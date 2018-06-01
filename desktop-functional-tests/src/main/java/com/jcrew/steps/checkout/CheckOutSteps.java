package com.jcrew.steps.checkout;

import com.jcrew.page.checkout.CheckoutShoppingBag;
import com.jcrew.pojo.Country;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import java.util.List;

import static org.junit.Assert.assertTrue;
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
}
