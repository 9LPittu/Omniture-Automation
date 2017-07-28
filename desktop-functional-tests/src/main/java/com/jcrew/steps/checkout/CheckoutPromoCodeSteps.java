package com.jcrew.steps.checkout;

import com.jcrew.page.checkout.CheckoutPromoCode;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.openqa.selenium.WebDriverException;

/**
 * Created by ravi kumar on 4/5/16.
 */
public class CheckoutPromoCodeSteps extends DriverFactory {
    private CheckoutPromoCode promocode = new CheckoutPromoCode(getDriver());
    private StateHolder stateHolder = StateHolder.getInstance();  

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

    @Then("Verify second promo name contains: ([^\"]*)")
    public void second_promo_name(String message) {
        message = message.toLowerCase();
        String actual = promocode.getSecondPromoName().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
    }

    @Then("Verify promo details contains: ([^\"]*)")
    public void promo_details(String message) {
        message = message.toLowerCase();
        String actual = promocode.getPromoDetails().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
        stateHolder.put("promoMessage", message);
    }

    @Then("Verify second promo details contains: ([^\"]*)")
    public void second_promo_details(String message) {
        message = message.toLowerCase();
        String actual = promocode.getSecondPromoDetails().toLowerCase();

        assertTrue("Expected promo name contains " +  message, actual.contains(message));
        stateHolder.put("promoMessage", message);
    }
        
    @And("^Verify remove button is displayed in promo section$")
    public void remove_button_displayed_in_promo_section(){
    	assertTrue("remove button is displayed in promo section after promo code is applied",
                promocode.getPromoRemoveElement().isDisplayed());
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
    	String promoCode = stateHolder.get("promocode");
    	String actualState = promocode.getPromoCodeAppliedState(promoCode);
    	
   		assertEquals("Promo code '" + promoCode + "' should be '" + expectedState + "' state", expectedState, actualState);
    }

    @And("^User removes the already applied promo$")
    public void remove_promo(){
    	promocode.removePromo();
    }
    
    @Then("^Verify the applied promo code ([^\"]*) is (active|inactive)$")
    public void verify_specific_promo_code_state(String promotionCode, String expectedState){
    	String actualState = promocode.getPromoCodeAppliedState(promotionCode);
    	assertEquals("Promo code '" + promotionCode + "' should be '" + expectedState + "' state", expectedState, actualState);
	}
}