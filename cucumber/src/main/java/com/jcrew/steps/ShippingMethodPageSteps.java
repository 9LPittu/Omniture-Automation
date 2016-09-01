package com.jcrew.steps;

import com.jcrew.page.ShippingMethodPage;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.ShippingMethod;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.ShippingMethodCalculator;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShippingMethodPageSteps extends DriverFactory {

    private final ShippingMethodPage shippingMethodPage = new ShippingMethodPage(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();
    private ShippingMethodCalculator methodCalculator = new ShippingMethodCalculator();
    private TestDataReader testDataReader = TestDataReader.getTestDataReader();

    @And("^Verifies is in shipping method page$")
    public void verifies_is_in_shipping_method_page() throws Throwable {

        assertTrue(Util.getSelectedCountryName() + "User should be in shipping method page", shippingMethodPage.isShippingMethodPage());
    }

    @And("^Uses default value for shipping method$")
    public void uses_default_value_for_shipping_method() throws Throwable {
        Country country = (Country) stateHolder.get("context");
        String countryName = country.getCountryName().toLowerCase().trim();
        String countryCode = country.getCountry();

        if(countryCode.equalsIgnoreCase("us")) {

            String addressType = (String) stateHolder.get("atpAddressType");
            String expectedDefaultShipMethod = testDataReader.getData(addressType + ".default.shipping.method");

            String actualShippingMethodSelected = shippingMethodPage.getSelectedShippingMethod().toLowerCase();
            actualShippingMethodSelected = actualShippingMethodSelected.split("\\(")[0].trim();


            List<ShippingMethod> expectedMethods = methodCalculator.getExpectedList();
            for (int i = 0; i < expectedMethods.size(); i++) {
                ShippingMethod method = expectedMethods.get(i);
                if (method.getPrice().equalsIgnoreCase("free")) {
                    expectedDefaultShipMethod =  method.getMethod();
                    expectedDefaultShipMethod = expectedDefaultShipMethod.split("\\(")[0].trim();
                    break;
                }
            }

            assertEquals("Default shipping method selected should be ",expectedDefaultShipMethod,actualShippingMethodSelected);

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
    
    @And("^validate correct shipping methods displayed on the page$")
    public void validate_shipping_methods(){
        Country country = (Country) stateHolder.get("context");
        String countryName = country.getCountryName().toLowerCase().trim();
        String countryCode = country.getCountry();

        List<ShippingMethod> pageMethods = shippingMethodPage.getShippingMethods();

        if(countryCode.equalsIgnoreCase("us")) {
            List<ShippingMethod> expectedMethods = methodCalculator.getExpectedList();

            for (int i = 0; i < expectedMethods.size(); i++) {
                ShippingMethod actual = pageMethods.get(i);
                ShippingMethod expected = expectedMethods.get(i);

                assertEquals("Expected shipping method", expected, actual);
            }
        } else {
            String shipMethods[] = testDataReader.getDataArray(countryCode + ".shippingMethods");
            List<String> expectedMethods = Arrays.asList(shipMethods);

            for (int i = 0; i < expectedMethods.size(); i++) {
                String actualShipMethod = pageMethods.get(i).getMethod().toLowerCase();
                String expectedShipMethod = expectedMethods.get(i).toLowerCase();
                assertEquals("Expected shipping method", expectedShipMethod, actualShipMethod);
            }

        }
    }
}
