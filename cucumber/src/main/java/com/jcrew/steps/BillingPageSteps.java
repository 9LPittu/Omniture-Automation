package com.jcrew.steps;

import com.jcrew.page.BillingPage;
import com.jcrew.util.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;

public class BillingPageSteps extends DriverFactory {

    private final BillingPage billingPage = new BillingPage(getDriver());

    @When("^Fills required payment data in billing page$")
    public void fills_required_payment_data() throws Throwable {

        billingPage.fill_required_payment_data();

    }

    @And("^Verify user is in billing page$")
    public void verify_user_is_in_billing_page() throws Throwable {
        assertTrue("User should be in billing page", billingPage.isBillingPage());
    }

    @And("^Submits payment data in billing page$")
    public void Submits_payment_data_in_billing_page() throws Throwable {
        billingPage.submit_form();
    }
    
    @And("^enter \"([^\"]*)\" details on billing page$")    
    public void enter_credit_card_details(String cardName){
    	billingPage.enterCreditCardDetails(cardName);
    }
    
    @And("^enter email address as \"([^\"]*)\"$")
    public void enter_email_address_on_billing_page(String emailAddress){
    	billingPage.enterEmailAddressOnBillingPage(emailAddress);
    }
    
    @And("^click 'Add New Card' on billing page$")
    public void click_add_new_card_on_billing_page(){
    	billingPage.clickAddNewCardOnBillingPage();
    }
    
    @And("^click on 'SAVE & CONTINUE' button$")
    public void click_save_continue_button(){
    	billingPage.clickSaveAndContinueButton();
    }
    
    @And("^click on 'ADD NEW BILLING ADDRESS' on Billing page$")
    public void click_add_new_shipping_address(){
    	billingPage.clickAddNewBillingAddress();
    }
    
    @And("^select country as \"([^\"]*)\" in the Add New Billing Address form$")
    public void select_country_in_new_billing_address_form(String country){
    	billingPage.selectCountryOnNewBillingAddressForm(country);
    }
    
    @And("^enter first name in the Add New Billing Address form$")    
    public void enter_first_name_in_new_billing_address_form(){
    	billingPage.enterFirstNameOnNewBillingAddressForm();
    }
    
    @And("^enter last name in the Add New Billing Address form$")    
    public void enter_last_name_in_new_billing_address_form(){
    	billingPage.enterLastNameOnNewBillingAddressForm();
    }
    
    @And("^enter \"([^\"]*)\" as address line1 in the Add New Billing Address form$")
    public void enter_address1_in_add_new_billing_address_form(String addressLine1){
    	billingPage.enterAddressLine1OnAddNewBillingAddressForm(addressLine1);
    }
    
    @And("^enter \"([^\"]*)\" as address line2 in the Add New Billing Address form$")
    public void enter_address2_in_add_new_billing_address_form(String addressLine2){
    	billingPage.enterAddressLine2OnAddNewBillingAddressForm(addressLine2);
    }
    
    @And("^enter \"([^\"]*)\" as zipcode in the Add New Billing Address form$")
    public void enter_zipcode_in_add_new_billing_address_form(String zipCode){
    	billingPage.enterZipCodeOnAddNewBillingAddressForm(zipCode);
    }
    
    @And("^enter phone number in the Add New Billing Address form$")
    public void enter_phone_number_in_add_new_billing_address_Form(){
    	billingPage.enterPhoneNumberOnAddNewBillingAddressForm();
    }
    
    @Then("^click on 'SAVE' button in the Add New Billing Address form$")
    public void click_save_and_continue_button_in_add_new_billing_address_form(){
    	billingPage.clickSaveInAddNewBillingAddressForm();
    }
    
    @Then("^user should see QAS verification in the Billing page$")
    public void user_should_see_qas_verification_window_in_billing_page(){
    	assertTrue("User should see QAS Verification window",billingPage.isQASVerificationDisplayed());
    }
    
    @And("^click on 'USE ADDRESS AS ENTERED' button in the Billing page$")
    public void click_use_address_as_entered_button_in_billing_page(){
    	billingPage.clickUseAddressAsEnteredButton();
    }
}