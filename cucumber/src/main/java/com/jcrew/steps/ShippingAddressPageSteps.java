package com.jcrew.steps;

import com.jcrew.page.ShippingAddressPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.TimeoutException;

import static org.junit.Assert.*;

public class ShippingAddressPageSteps extends DriverFactory {

    private final ShippingAddressPage shippingAddressPage = new ShippingAddressPage(getDriver());

    @When("^Fills shipping address")
    public void fills_shipping_address() throws Throwable {

        shippingAddressPage.fills_shipping_address();
        try {

            assertEquals("New York, NY", shippingAddressPage.getSelectedCityAndState());

        } catch (TimeoutException te) {
            fail("Unable to retrieve Dropdown menu containing City/State for the corresponding zip code 10003." +
                    " Service unavailable");
        }

        assertTrue("Billing checkbox should be selected", shippingAddressPage.isBillingAndShippingSameAddress());

    }

    @And("^Presses continue button on shipping address$")
    public void presses_continue_button_on_shipping_address() throws Throwable {
        shippingAddressPage.presses_continue_button_on_shipping_address();
    }
}
