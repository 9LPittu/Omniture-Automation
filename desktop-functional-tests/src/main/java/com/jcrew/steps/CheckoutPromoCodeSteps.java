package com.jcrew.steps;

import com.jcrew.page.CheckoutPromoCode;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ravi kumar on 4/5/16.
 */
public class CheckoutPromoCodeSteps extends DriverFactory {
    private CheckoutPromoCode promocode = new CheckoutPromoCode(getDriver());

    @Then("Verify bag has a promo code section")
    public void promo_code_section() {
        assertTrue("Bag has a promo code section", promocode.isDisplayed());
    }

    @When("User adds a promo code ([^\"]*)")
    public void add_promo_code(String code) {
    	promocode.addPromoCode(code);
    }

    @Then("Verify promo message says: ([^\"]*)")
    public void promo_message(String message) {
        String actual = promocode.getPromoCodeMessage();

        assertEquals("Expected promo message", message, actual);
    }

    @Then("Verify promo name contains: ([^\"]*)")
    public void promo_name(String message) {
        message = message.toLowerCase();
        String actual = promocode.getPromoName().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
    }

    @Then("Verify promo details contains: ([^\"]*)")
    public void promo_details(String message) {
        message = message.toLowerCase();
        String actual = promocode.getPromoDetails().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
        promocode.stateHolder.put("promoMessage", message);
    }

    @Then("Verify promo code applied (\\d+) percent from subtotal")
    public void applied_promo(int discount) {
        String subtotal = promocode.getSubTotal();
        subtotal = subtotal.replaceAll("[^0-9]", "");

        String promo = promocode.getPromoDiscount();
        promo = promo.replaceAll("[^0-9]", "");
        int promoInt = Integer.parseInt(promo) * 10;

        double subtotalFloat = Integer.parseInt(subtotal) * (discount / 100.00);
        subtotalFloat = round(subtotalFloat);
        double promoFloat = promoInt / 10.00;

        assertEquals("Promo was applied correctly", (int) subtotalFloat, (int) promoFloat);
    }

    private double round(double doubleNumber) {
        double decimals = doubleNumber - (int) doubleNumber;
        double integer = doubleNumber - decimals;

        return decimals >= .60? integer + 1 : integer;
    }
        
    @And("^Verify remove button is displayed in promo section$")
    public void remove_button_displayed_in_promo_section(){
    	assertTrue("remove button is displayed in promo section after promo code is applied",
                promocode.getPromoRemoveElement().isDisplayed());
    }
    
    @And("^Verify promo message is updated in the summary section$")
    public void promo_message_updated_in_summary_section(){
    	assertTrue("Promo message is updated in the order summary section after promo code is applied",
                promocode.getPromoMessageElementFromOrderSummary().isDisplayed());
    }
    
    @Then("^Verify second promo text box is displayed in promo section$")
    public void secondpromo_textBox_displayed_promo_section(){
        assertTrue("second promo text box  is displayed in promo section after firtst promo code is applied",
        		promocode.isPromoTextBoxDisplayed());
    }
    
    @Then("^Verify user is not allowed to add thrid promo$")
    public void thirdpromo_textBox_not_displayed_promo_section(){
    	assertFalse("Promo text box  is not displayed in promo section after second promo code is applied",
    			promocode.isPromoTextBoxDisplayed());
    }
    
    @Then("^Verify the applied promo code is (active|inactive)$")
    public void verify_promo_code_state(String expectedState){
    	String promoCode = promocode.stateHolder.get("promocode");
    	String actualState = promocode.getPromoCodeAppliedState(promoCode);
    	
   		assertEquals("Promo code '" + promoCode + "' should be '" + expectedState + "' state", expectedState, actualState);
    }
}