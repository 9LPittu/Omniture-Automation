package com.jcrew.steps;

import com.jcrew.page.ShippingAddressPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.TimeoutException;

import static org.junit.Assert.*;

public class ShippingAddressPageSteps extends DriverFactory {

    private final ShippingAddressPage shippingAddressPage = new ShippingAddressPage(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();

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
        stateHolder.put("atpAddressType","regular");
    }

    @And("^Presses continue button on shipping address$")
    public void presses_continue_button_on_shipping_address() throws Throwable {
        shippingAddressPage.presses_continue_button_on_shipping_address();
    }
    
    @And("^enter first name on shipping address page$")
    public void enter_first_name_on_shipping_address_page(){
    	shippingAddressPage.enterFirstNameOnShippingAddressPage();
        stateHolder.put("atpAddressType","regular");
    }
    
    @And("^enter last name on shipping address page$")
    public void enter_last_name_on_shipping_address_page(){
    	shippingAddressPage.enterLastNameOnShippingAddressPage();
    }
    
    @And("^enter address line1 as \"([^\"]*)\" on shipping address page$")
    public void enter_address_line1_on_shipping_address_page(String addressLine1){    	
    	shippingAddressPage.enterAddressLine1OnShippingAddressPage(addressLine1);    	
    }
    
    @And("^enter address line2 as \"([^\"]*)\" on shipping address page$")
    public void enter_address_line2_on_shipping_address_page(String addressLine2){    	
    	shippingAddressPage.enterAddressLine2OnShippingAddressPage(addressLine2);    	    	
    }
    
    @And("^enter zip code as \"([^\"]*)\" on shipping address page$")
    public void enter_zip_code_on_shipping_address_page(String zipCode){
    	shippingAddressPage.enterZipCodeOnShippingAddressPage(zipCode);
    }
    
    @And("^enter phone number on shipping address page$")
    public void enter_phone_number_on_shipping_address_page(){
    	shippingAddressPage.enterPhoneNumberOnShippingAddressPage();
    }
    
    @And("^click on 'ADD NEW SHIPPING ADDRESS' on Shipping Address page$")
    public void click_add_new_shipping_address(){
    	shippingAddressPage.clickAddNewShippingAddress();
    }
    
    @And("^enter first name in the Add New Shipping Address form$")    
    public void enter_first_name_in_new_shipping_address_form(){
    	shippingAddressPage.enterFirstNameOnNewShippingAddressForm();
    }
    
    @And("^enter last name in the Add New Shipping Address form$")    
    public void enter_last_name_in_new_shipping_address_form(){
    	shippingAddressPage.enterLastNameOnNewShippingAddressForm();
    }
    
    @And("^enter \"([^\"]*)\" as address line1 in the Add New Shipping Address form$")
    public void enter_address1_in_add_new_shipping_address_form(String addressLine1){
    	shippingAddressPage.enterAddressLine1OnAddNewShippingAddressForm(addressLine1);
        stateHolder.put("atpAddressType","regular");
    }
    
    @And("^enter \"([^\"]*)\" as address line2 in the Add New Shipping Address form$")
    public void enter_address2_in_add_new_shipping_address_form(String addressLine2){
    	shippingAddressPage.enterAddressLine2OnAddNewShippingAddressForm(addressLine2);
    }
    
    @And("^enter \"([^\"]*)\" as zipcode in the Add New Shipping Address form$")
    public void enter_zipcode_in_add_new_shipping_address_form(String zipCode){
    	shippingAddressPage.enterZipCodeOnAddNewShippingAddressForm(zipCode);
    }
    
    @And("^enter phone number in the Add New Shipping Address form$")
    public void enter_phone_number_in_add_new_shipping_address_Form(){
    	shippingAddressPage.enterPhoneNumberOnAddNewShippingAddressForm();
    }
    
    @Then("^click on 'SAVE & CONTINUE' button in the Add New Shipping Address form$")
    public void click_save_and_continue_button_in_add_new_shipping_address_form(){
    	shippingAddressPage.clickSaveAndContinueInAddNewShippingAddressForm();
    }
    
    @Then("^user should see QAS verification in the shipping address page$")
    public void user_should_see_qas_verification(){
    	assertTrue("User should see QAS verification",shippingAddressPage.isQASVerificationDisplayed());
    }
    
    @And("^click on 'USE ADDRESS AS ENTERED' button in the shipping address page$")
    public void click_use_address_as_entered_button(){
    	shippingAddressPage.clickUseAddressAsEnteredButton();
    }
    
    @When("^user fills shipping address$")
    public void user_fills_shipping_address() throws Throwable {

        shippingAddressPage.fills_shipping_address();
        
        shippingAddressPage.selectCityAndState();

        assertTrue("Billing checkbox should be selected", shippingAddressPage.isBillingAndShippingSameAddress());
    }
    
    @When("^user fills selected country shipping address$")
    public void user_fills_country_shipping_address() throws Throwable {

        shippingAddressPage.fills_shipping_address_testdata();
        
       // shippingAddressPage.selectCityAndState();

        assertTrue(Util.getSelectedCountryName() + "Billing checkbox should be selected", shippingAddressPage.isBillingAndShippingSameAddress());

    }
    
    @Then("Verify Shipping Page is displayed")
    public void shipping_page_displayed() {
        assertTrue("Is shipping address", shippingAddressPage.isDisplayed());
    }
}