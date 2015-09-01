package com.jcrew.steps;

import com.jcrew.page.ShippingAndHandlingPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

/**
 * Created by 9hvenaga on 9/1/2015.
 */
public class ShippingAndHandlingPageSteps extends DriverFactory {

    @And("^Verify user is on shipping & handling page$")
    public void verify_user_is_on_shipping_handling_page() throws Throwable {
        ShippingAndHandlingPage shippingAndHandlingPage = new ShippingAndHandlingPage(getDriver());
        assertTrue("User should be on shipping and handling page", shippingAndHandlingPage.isShippingAndHandlingPage());


    }
}