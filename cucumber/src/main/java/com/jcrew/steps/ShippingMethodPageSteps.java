package com.jcrew.steps;

import com.jcrew.page.ShippingMethodPage;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.ShippingMethod;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.ShippingMethodCalculator;
import com.jcrew.util.Util;
import java.text.SimpleDateFormat;

import cucumber.api.java.en.And;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

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

        if (countryCode.equalsIgnoreCase("us")) {

            String addressType = (String) stateHolder.get("atpAddressType");
            String expectedDefaultShipMethod = testDataReader.getData(addressType + ".default.shipping.method");

            String actualShippingMethodSelected = shippingMethodPage.getSelectedShippingMethod().toLowerCase();
            actualShippingMethodSelected = actualShippingMethodSelected.split("\\(")[0].trim();


            List<ShippingMethod> expectedMethods = methodCalculator.getExpectedList();
            for (int i = 0; i < expectedMethods.size(); i++) {
                ShippingMethod method = expectedMethods.get(i);
                if (method.getPrice().equalsIgnoreCase("free")) {
                    expectedDefaultShipMethod = method.getMethod();
                    expectedDefaultShipMethod = expectedDefaultShipMethod.split("\\(")[0].trim();
                    break;
                }
            }

            assertEquals("Default shipping method selected should be ", expectedDefaultShipMethod, actualShippingMethodSelected);

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
    public void select_shipping_method_at_random() {
        shippingMethodPage.selectShippingMethod();
    }

    @And("^validate correct shipping methods displayed on the page$")
    public void validate_shipping_methods() {
        Country country = (Country) stateHolder.get("context");
        String countryName = country.getCountryName().toLowerCase().trim();
        String countryCode = country.getCountry();

        List<ShippingMethod> pageMethods = shippingMethodPage.getShippingMethods();
        stateHolder.put("actualShippingMethods", pageMethods);

        if (countryCode.equalsIgnoreCase("us")) {
            List<ShippingMethod> expectedMethods = methodCalculator.getExpectedList();
            stateHolder.put("expectedShippingMethods", expectedMethods);

            for (int i = 0; i < expectedMethods.size(); i++) {
                ShippingMethod actual = pageMethods.get(i);
                ShippingMethod expected = expectedMethods.get(i);

                assertEquals("Expected: " + expected.toString() + " actual: " + actual.toString() + " should be same", expected, actual);
                verify_ATP_date(actual, expected);
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


    public void verify_ATP_date(ShippingMethod actual, ShippingMethod expected) {
        //Verifies if ATP date is falling in between expected date range
        String actualName = actual.getMethod();
        String expectedName = expected.getMethod().split("\\|")[0];

        String actualDate = actualName.replaceFirst(expectedName, "").trim();
        actualDate = actualDate.replace("–", "").trim();

        if (!actualDate.isEmpty()) {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEEE, MMMM dd");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat1.parse(actualDate);
                Calendar actualShipDay = Calendar.getInstance();
                actualShipDay.setTime(date);

                Calendar today = Calendar.getInstance();

                int actualMonth = actualShipDay.get(Calendar.MONTH);
                int currentMonth = today.get(Calendar.MONTH);
                int currentYear = today.get(Calendar.YEAR);

                if (actualMonth < currentMonth) {
                    actualShipDay.set(Calendar.YEAR, currentYear + 1);
                } else {
                    actualShipDay.set(Calendar.YEAR, currentYear);
                }
                Date actualShipDate = actualShipDay.getTime();

                Date startDate = dateFormat2.parse(expected.getMethod().split("\\|")[1]);
                Date endDate = dateFormat2.parse(expected.getMethod().split("\\|")[2]);

                assertTrue("ATP shipping date for the method " + expectedName + " should be between " + startDate.toString() + " and " + endDate.toString() + ". But, the actual ship date is " + actualShipDate.toString(),(!actualShipDate.before(startDate)) && (!actualShipDate.before(endDate)));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}