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

    @When("^User fills payment method as guest with ([^\"]*) and continues$")
    public void fill_payment_method_card_continue(String card) {
        billing.fillPaymentMethod(card);
        billing.continueCheckout();
    }
    
    @When("User fills payment method as registered user and continues")
    public void fill_payment_method_registered() {
        billing.fillPaymentMethod(false);
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
    
   
    
    @Then("^Verify no additional charges are applied for gift receipt$")
    public void verify_no_additional_charges_applied_for_gift_receipt(){
    	String orderSubtotalBeforeGiftReceipt = (String) billing.stateHolder.get("subtotal");
    	
    	String orderSubtotalOnBilling = billing.getSubTotal().replaceAll("[^0-9\\.]", "");
    	
    	assertEquals("No additional charges should be applied for gift receipt on billing page", orderSubtotalBeforeGiftReceipt, orderSubtotalOnBilling);
    }
}
