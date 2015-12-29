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
    
    @And("^enter first name as \"([^\"]*)\" on shipping address page$")
    public void enter_first_name_on_shipping_address_page(String firstName){
    	shippingAddressPage.enterFirstNameOnShippingAddressPage(firstName);
    }
    
    @And("^enter last name as \"([^\"]*)\" on shipping address page$")
    public void enter_last_name_on_shipping_address_page(String lastName){
    	shippingAddressPage.enterLastNameOnShippingAddressPage(lastName);
    }
    
    @And("^enter address line1 as \"([^\"]*)\"$")
    public void enter_address_line1_on_shipping_address_page(String addressLine1){    	
    	shippingAddressPage.enterAddressLine1OnShippingAddressPage(addressLine1);    	
    }
    
    @And("^enter address line2 as \"([^\"]*)\"$")
    public void enter_address_line2_on_shipping_address_page(String addressLine2){    	
    	shippingAddressPage.enterAddressLine2OnShippingAddressPage(addressLine2);    	    	
    }
    
    @And("^enter zip code as \"([^\"]*)\"$")
    public void enter_zip_code_on_shipping_address_page(String zipCode){
    	shippingAddressPage.enterZipCodeOnShippingAddressPage(zipCode);
    }
    
    @And("^enter phone number as \"([^\"]*)\"$")
    public void enter_phone_number_on_shipping_address_page(String phoneNumber){
    	shippingAddressPage.enterPhoneNumberOnShippingAddressPage(phoneNumber);
    }  
    
}