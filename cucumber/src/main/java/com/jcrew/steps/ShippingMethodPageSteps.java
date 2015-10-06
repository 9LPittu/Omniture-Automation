package com.jcrew.steps;

import com.jcrew.page.ShippingMethodPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertTrue;

public class ShippingMethodPageSteps extends DriverFactory {

    private final ShippingMethodPage shippingMethodPage = new ShippingMethodPage(getDriver());

    @And("^Verifies is in shipping method page$")
    public void verifies_is_in_shipping_method_page() throws Throwable {

        assertTrue("User should be in shipping method page", shippingMethodPage.isShippingMethodPage());
    }

    @And("^Uses default value for shipping method$")
    public void uses_default_value_for_shipping_method() throws Throwable {

        assertTrue("Economy UPS checkbox should be selected", shippingMethodPage.isEconomyUps());

    }

    @And("^Uses default value for gifts option$")
    public void uses_default_value_for_gifts_option() throws Throwable {

        assertTrue("No gifts checkbox should be selected", shippingMethodPage.isNoGifts());
    }

    @And("^Clicks continue button on shipping method page$")
    public void clicks_continue_button_on_shipping_method_page() throws Throwable {

        if (shippingMethodPage.isPageLoaded()) {
            shippingMethodPage.click_continue_button();
        }

    }
}
