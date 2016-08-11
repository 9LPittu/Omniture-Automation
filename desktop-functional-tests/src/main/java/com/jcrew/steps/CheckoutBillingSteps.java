package com.jcrew.steps;

import com.jcrew.page.CheckoutBilling;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 5/9/16.
 */
public class CheckoutBillingSteps extends DriverFactory {

    private CheckoutBilling billing = new CheckoutBilling(getDriver());

    @When("User fills payment method as guest and continues")
    public void fill_payment_method() {
        billing.fillPaymentMethod(true);
        billing.continueCheckout();
    }

    @When("User continues to review page")
    public void continue_to_review() {
        billing.continueCheckout();
    }

    @Then("Verify Billing Page url is ([^\"]*)")
    public void review_page_url(String url) {
        String current_url = getDriver().getCurrentUrl();
        assertTrue("Payment Method page url is " + url, current_url.contains(url));
    }

    @Then("Verify checkout breadcrumb is BILLING")
    public void verify_progress() {
        assertEquals("Breadcrumb is BILLING", "BILLING", billing.getBreadCrumb());
    }

    @Then("Verify Billing page is displayed")
    public void is_shipping_options() {
        assertTrue("Is billing page", billing.isDisplayed());
    }

    @Then("Verify available payment methods from list")
    public void available_payment_methods(List<String> expectedMethods) {
        List<String> actualMethods = billing.getPaymentMethods();

        assertEquals("Same number of payment methods", expectedMethods.size(), actualMethods.size());

        for (int i = 0; i < expectedMethods.size(); i++) {
            String expected = expectedMethods.get(i);
            String actual = actualMethods.get(i);

            assertEquals("Expected payment method", expected, actual);

        }

    }

    @Then("Verify accepted cards from list")
    public void accepted_cards(List<String> expectedCards) {
        List<String> actualCards = billing.getAcceptedCards();

        for(String card : actualCards)
            System.out.println("accepted card: " + card);

        assertEquals("Same number of accepted cards", expectedCards.size(), actualCards.size());

        for (int i = 0; i < expectedCards.size(); i++) {
            String expected = expectedCards.get(i);
            String actual = actualCards.get(i);

            assertEquals("Expected accepted card", expected, actual);

        }

    }

    @When("User adds a promo code ([^\"]*) in Payment Method page")
    public void add_payment_method(String code) {
        billing.addPromoCode(code);
    }

    @Then("Verify promo message says: ([^\"]*)")
    public void promo_message(String message) {
        String actual = billing.getPromoCodeMessage();

        assertEquals("Expected promo message", message, actual);
    }

    @Then("Verify promo name contains: ([^\"]*)")
    public void promo_name(String message) {
        message = message.toLowerCase();
        String actual = billing.getPromoName().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
    }

    @Then("Verify promo details contains: ([^\"]*)")
    public void promo_details(String message) {
        message = message.toLowerCase();
        String actual = billing.getPromoDetails().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
        billing.stateHolder.put("promoMessage", message);
    }

    @Then("Verify promo code applied 10 percent from subtotal")
    public void applied_promo() {
        String subtotal = billing.getSubTotal();
        subtotal = subtotal.replaceAll("[^0-9]", "");
        String promo = billing.getPromoDiscount();
        promo = promo.replaceAll("[^0-9]", "");

        int subtotalInt = Integer.parseInt(subtotal);
        int promoInt = Integer.parseInt(promo) * 10;

        assertEquals("Promo was applied correctly", subtotalInt, promoInt);
    }

    @When("^User adds new billing address$")
    public void add_billing_address() {
        billing.addNewBillingAddress();
    }

    @When("^User edits recently added card$")
    public void edit_card() {
        billing.editCard();
    }

    @When("^User adds new card$")
    public void add_new_card() {
        billing.addNewCard();
    }

    @When("^User removes ([^\"]*) card$")
    public void remove_card(String type) {
        billing.removeCard(type);
    }

    @Then("^Verify card has been removed$")
    public void card_has_been_removed() {
        String removedCard = (String) billing.stateHolder.get("removedCard");
        List<String> cards = billing.getCards();

        assertFalse("Removed card is not part of user cards", cards.contains(removedCard));
    }

    @Then("^Verify card has been added$")
    public void card_has_been_added() {
        String addedCard = (String) billing.stateHolder.get("addedCard");
        List<String> cards = billing.getCards();
        Iterator<String> cardsI = cards.iterator();

        boolean found = false;

        while(!found & cardsI.hasNext()) {
            String info = cardsI.next();
            if(info.contains(addedCard))
                found = true;
        }

        assertFalse("Removed card is not part of user cards", found);
    }

    @Then("^Verify card has been edited")
    public void card_has_been_edited() {
        List<String> cards = billing.getCards();
        String card = cards.get(cards.size() - 1);

        assertTrue("Edited card has new info", card.contains("Edited Card Name"));
    }
    
    @Then("^Select different card from the card list$")
    public void select_card_nodefault(){
    	
    	billing.SelectPaymentMethodNoDefault();
    }
    
    @And("^Verify remove button is displayed in promo section$")
    public void remove_button_displayed_in_promo_section(){
    	assertTrue("remove button is displayed in promo section after promo code is applied", billing.getPromoRemoveElement().isDisplayed()); 
    }
    
    @And("^Verify promo message is updated in the summary section$")
    public void promo_message_updated_in_summary_section(){
    	assertTrue("Promo message is updated in the order summary section after promo code is applied", billing.getPromoMessageElementFromOrderSummary().isDisplayed());
    }
    
    @Then("^Verify no additional charges are applied for gift receipt$")
    public void verify_no_additional_charges_applied_for_gift_receipt(){
    	String orderSubtotalBeforeGiftReceipt = (String) billing.stateHolder.get("ordersubtotal");
    	
    	String orderSubtotalOnBilling = billing.getSubTotal();
    	
    	assertEquals("No additional charges should be applied for gift receipt on billing page", orderSubtotalBeforeGiftReceipt, orderSubtotalOnBilling);
    }
}
