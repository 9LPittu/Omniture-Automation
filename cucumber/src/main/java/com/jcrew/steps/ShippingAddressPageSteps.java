package com.jcrew.steps;

import com.jcrew.page.ShippingAddressPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShippingAddressPageSteps extends DriverFactory {

    private ShippingAddressPage shippingAddressPage = new ShippingAddressPage(getDriver());

    @When("^Fills shipping address")
    public void fills_shipping_address() throws Throwable {

        shippingAddressPage.fills_shipping_address();

        assertEquals("New York, NY", shippingAddressPage.getSelectedCityAndState());

        assertTrue("Billing checkbox should be selected", shippingAddressPage.isBillingAndShippingSameAddress());

    }

    @And("^Presses continue button on shipping address$")
    public void presses_continue_button_on_shipping_address() throws Throwable {
        shippingAddressPage.presses_continue_button_on_shipping_address();
    }
}
