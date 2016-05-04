package com.jcrew.steps;

import com.jcrew.page.ShippingAddress;
import com.jcrew.page.ShippingMethod;
import com.jcrew.page.ShoppingBag;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class ShippingMethodSteps extends DriverFactory{
	
	private final ShippingMethod shippingMethodPage = new ShippingMethod(getDriver());
	
	@And("^Verifies is in shipping method page$")
	public void verifies_is_in_shipping_method_page() throws Throwable {
	   assertTrue("User should be in shipping method page", shippingMethodPage.isShippingMethodPage());
	}
	
	@And("^Uses default value for shipping method$")
    public void uses_default_value_for_shipping_method() throws Throwable {

        assertTrue("Economy UPS checkbox should be selected", shippingMethodPage.isEconomyUps());

    }
	
	@And("^Clicks continue button on shipping method page$")
    public void clicks_continue_button_on_shipping_method_page() throws Throwable {
        shippingMethodPage.click_continue_button();
    }
}
