package com.jcrew.steps;

import com.jcrew.page.ShippingMethodPage;
import com.jcrew.pojo.Country;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShippingMethodPageSteps extends DriverFactory {

    private final ShippingMethodPage shippingMethodPage = new ShippingMethodPage(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();

    @And("^Verifies is in shipping method page$")
    public void verifies_is_in_shipping_method_page() throws Throwable {

        assertTrue(Util.getSelectedCountryName() + "User should be in shipping method page", shippingMethodPage.isShippingMethodPage());
    }

    @And("^Uses default value for shipping method$")
    public void uses_default_value_for_shipping_method() throws Throwable {
        TestDataReader testDataReader = TestDataReader.getTestDataReader();

        Country country = (Country) stateHolder.get("context");
        String countryName = country.getCountryName().toLowerCase().trim();
        String countryCode = country.getCountry();
        if(countryCode.equalsIgnoreCase("us")) {
            String actualShippingMethod = shippingMethodPage.getSelectedShippingMethod().toLowerCase();

            // retrieve expected shipping method to be selected by default
            double subtotal = Double.parseDouble((String) stateHolder.get("subtotal"));
            double  standardShippingThreshold = Double.parseDouble(testDataReader.getData(countryCode + ".standard.FreeShippingThreshold"));

            String expectedShippingMethod = "economy";

            if (subtotal >= standardShippingThreshold)
                expectedShippingMethod = "standard";

            assertEquals("Default shipping method selected should be ",expectedShippingMethod,actualShippingMethod);
        } else {
            assertTrue("First shipping method should be selected by default for the country " + countryName, shippingMethodPage.isFirstShippingMethod());
        }

    }

    @And("^Uses default value for gifts option$")
    public void uses_default_value_for_gifts_option() throws Throwable {

        assertTrue("No gifts checkbox should be selected", shippingMethodPage.isNoGifts());
    }

    @And("^Clicks continue button on shipping method page$")
    public void clicks_continue_button_on_shipping_method_page() throws Throwable {
        shippingMethodPage.click_continue_button();
    }
    
    @And("^select shipping method on shipping & gift options page$")
    public void select_shipping_method_at_random(){
    	shippingMethodPage.selectShippingMethod();
    }
    
    @And("^validate shipping methods displayed on the page$")
    public void validate_shipping_methods(){
    	assertTrue("Validate the shipping methods displayed", shippingMethodPage.isShippingMethodsDisplayedCorrectly());
    }
}
