package com.jcrew.steps;

import com.jcrew.page.BillingPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Util;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BillingPageSteps extends DriverFactory {

    private final BillingPage billingPage = new BillingPage(getDriver());

    @When("^Fills required payment data in billing page$")
    public void fills_required_payment_data() throws Throwable {

        billingPage.fill_required_payment_data();

    }

    @And("^Verify user is in billing page$")
    public void verify_user_is_in_billing_page() throws Throwable {
        assertTrue(Util.getSelectedCountryName() + "User should be in billing page", billingPage.isBillingPage());
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
    @And("^Presses continue button on Billing page$")
    public void presses_continue_button_on_shipping_address() throws Throwable {
    	billingPage.presses_continue_button_on_Billingpage();
    }
    
    @When("^User adds new billing address$")
    public void add_billing_address() {
    	billingPage.addNewBillingAddress();
    }
    
    @Then("Verify Billing Address page is displayed")
    public void is_billing_address_page_displayed() {
        assertTrue("Is billing address page", billingPage.isDisplayed());
    }
    
    @Then("Verify Billing page is displayed")
    public void is_billing_page_displayed() {
        assertTrue("Is billing page", billingPage.isBillingPageDisplayed());
    }
    
    @When("User fills billing address and continues")
    public void save_billing_address() {
    	billingPage.fillFormData();
    	billingPage.continueCheckout();
    }
    
    @When("User fills payment method as guest and continues")
    public void fill_payment_method() {
    	billingPage.fillPaymentMethod(true);
    	billingPage.continueCheckout();
    }
    
    @Then("^Verify no additional charges are applied for gift receipt$")
    public void verify_no_additional_charges_applied_for_gift_receipt(){
    	String orderSubtotalBeforeGiftReceipt = (String) billingPage.stateHolder.get("ordersubtotal");
    	
    	String orderSubtotalOnBilling = billingPage.getSubTotal();
    	
    	assertEquals("No additional charges should be applied for gift receipt on billing page", orderSubtotalBeforeGiftReceipt, orderSubtotalOnBilling);
    }
    
    @Then("Verify accepted cards from list")
    public void accepted_cards(List<String> expectedCards) {
        List<String> actualCards = billingPage.getAcceptedCards();

        for(String card : actualCards)
            billingPage.logger.debug("accepted card: " + card);

        assertEquals("Same number of accepted cards", expectedCards.size(), actualCards.size());

        for (int i = 0; i < expectedCards.size(); i++) {
            String expected = expectedCards.get(i);
            String actual = actualCards.get(i);

            assertEquals("Expected accepted card", expected, actual);
        }
    }
    
    @Then("Verify available payment methods from list")
    public void available_payment_methods(List<String> expectedMethods) {
        List<String> actualMethods = billingPage.getPaymentMethods();

        assertEquals("Same number of payment methods", expectedMethods.size(), actualMethods.size());

        for (int i = 0; i < expectedMethods.size(); i++) {
            String expected = expectedMethods.get(i);
            String actual = actualMethods.get(i);

            assertEquals("Expected payment method", expected, actual);

        }
    }
    
    @When("User adds a promo code ([^\"]*) in Payment Method page")
    public void add_payment_method(String code) {
    	billingPage.addPromoCode(code);
    }

    @Then("Verify promo message says: ([^\"]*)")
    public void promo_message(String message) {
        String actual = billingPage.getPromoCodeMessage();

        assertEquals("Expected promo message", message, actual);
    }

    @Then("Verify promo name contains: ([^\"]*)")
    public void promo_name(String message) {
        message = message.toLowerCase();
        String actual = billingPage.getPromoName().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
    }

    @Then("Verify promo details contains: ([^\"]*)")
    public void promo_details(String message) {
        message = message.toLowerCase();
        String actual = billingPage.getPromoDetails().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
        billingPage.stateHolder.put("promoMessage", message);
    }

    @Then("Verify promo code applied 10 percent from subtotal")
    public void applied_promo() {
        String subtotal = billingPage.getSubTotal();
        subtotal = subtotal.replaceAll("[^0-9]", "");
        String promo = billingPage.getPromoDiscount();
        promo = promo.replaceAll("[^0-9]", "");

        int subtotalInt = Integer.parseInt(subtotal);
        int promoInt = Integer.parseInt(promo) * 10;
        
        boolean result = false;
        if(subtotalInt==promoInt || (subtotalInt + 1)==promoInt){
        	result = true;
        }

        assertTrue("Promo was applied correctly", result);
    }
    
    @And("^Verify remove button is displayed in promo section$")
    public void remove_button_displayed_in_promo_section(){
    	assertTrue("remove button is displayed in promo section after promo code is applied", billingPage.getPromoRemoveElement().isDisplayed()); 
    }
    
    @And("^Verify promo message is updated in the summary section$")
    public void promo_message_updated_in_summary_section(){
    	assertTrue("Promo message is updated in the order summary section after promo code is applied", billingPage.getPromoMessageElementFromOrderSummary().isDisplayed());
    }

}