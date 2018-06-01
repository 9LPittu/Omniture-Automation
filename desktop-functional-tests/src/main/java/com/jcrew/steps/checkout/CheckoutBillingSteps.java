package com.jcrew.steps.checkout;

import com.jcrew.page.checkout.CheckoutBilling;
import com.jcrew.steps.E2ECommon;
import com.jcrew.utils.DriverFactory;

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
@SuppressWarnings("static-access")
public class CheckoutBillingSteps extends DriverFactory {

    //private CheckoutBilling billing = new CheckoutBilling(getDriver());
    E2ECommon e2e = new E2ECommon();
    @When("User fills payment method as guest and continues")
    public void fill_payment_method() {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.fillPaymentMethod(true);
        billing.continueCheckout();
    }

    @When("^User fills payment method as guest with ([^\"]*) and continues$")
    public void fill_payment_method_card_continue(String card) {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.fillPaymentMethod(card);
        billing.continueCheckout();
    }
    
    @When("User fills payment method as registered user and continues")
    public void fill_payment_method_registered() {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.fillPaymentMethod(false);
        billing.continueCheckout();
    }

    @When("User continues to review page")
    public void continue_to_review() {
    	if(e2e.getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			return;
		}
    	if(e2e.getDataFromTestDataRowMap("Split Payments Required?").contains("No")) {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.continueCheckout();
    	}
    }

    @Then("Verify Billing Page url is ([^\"]*)")
    public void review_page_url(String url) {
        String current_url = getDriver().getCurrentUrl();
        assertTrue("Payment Method page url is " + url, current_url.contains(url));
    }

    @Then("Verify checkout breadcrumb is BILLING")
    public void verify_progress() {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	assertEquals("Breadcrumb is BILLING", "BILLING", billing.getBreadCrumb());
    }

    @Then("Verify Billing page is displayed")
    public void is_shipping_options() {
    	if(e2e.getDataFromTestDataRowMap("E2E Scenario Description").contains("Express paypal")) {
			return;
		}
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	assertTrue("Is billing page", billing.isDisplayed());
    }

    @Then("Verify available payment methods from list")
    public void available_payment_methods(List<String> expectedMethods) {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	List<String> actualMethods = billing.getPaymentMethods();
        actualMethods.remove(2);
        assertEquals("Same number of payment methods", expectedMethods.size(), actualMethods.size());

        for (int i = 0; i < expectedMethods.size(); i++) {
            String expected = expectedMethods.get(i);
            String actual = actualMethods.get(i);

            assertEquals("Expected payment method", expected, actual);

        }

    }

    @Then("Verify accepted cards from list")
    public void accepted_cards(List<String> expectedCards) {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
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
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.addNewBillingAddress();
    }

    @When("^User edits recently added card$")
    public void edit_card() {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.editCard();
    }

    @When("^User adds new card$")
    public void add_new_card() {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.addNewCard();
    }

    @When("^User removes ([^\"]*) card$")
    public void remove_card(String type) {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.removeCard(type);
    }

    
	@Then("^Verify card has been removed$")
    public void card_has_been_removed() {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	String removedCard = billing.stateHolder.get("removedCard");
        List<String> cards = billing.getCards();

        assertFalse("Removed card is not part of user cards", cards.contains(removedCard));
    }

    @Then("^Verify card has been added$")
    public void card_has_been_added() {
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	String addedCard = billing.stateHolder.get("addedCard");
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
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	List<String> cards = billing.getCards();
        String card = cards.get(cards.size() - 1);

        assertTrue("Edited card has new info", card.contains("Edited Card Name"));
    }
    
    @Then("^Select different card from the card list$")
    public void select_card_nodefault(){
    	CheckoutBilling billing = new CheckoutBilling(getDriver());
    	billing.SelectPaymentMethodNoDefault();
    }
}
