package com.jcrew.steps;

import com.jcrew.page.ShippingMethodPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class ShippingMethodPageSteps extends DriverFactory {

    @When("^Selects a shipping method$")
    public void selects_a_shipping_method() throws Throwable {

        ShippingMethodPage shippingMethodPage = new ShippingMethodPage(driver);

        assertTrue("Economy UPS checkbox should be selected", shippingMethodPage.isEconomyUps());

        assertTrue("No gifts checkbox should be selected", shippingMethodPage.isNoGifts());

        shippingMethodPage.continue_to_billing();

    }

}
