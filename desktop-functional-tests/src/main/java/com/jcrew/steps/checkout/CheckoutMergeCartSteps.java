package com.jcrew.steps.checkout;

import com.jcrew.page.checkout.CheckoutMergeCart;
import com.jcrew.pojo.User;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.fail;

/**
 * Created by nadiapaolagarcia on 5/17/16.
 */
@SuppressWarnings("static-access")
public class CheckoutMergeCartSteps extends DriverFactory {
    private CheckoutMergeCart mergeCart = new CheckoutMergeCart(getDriver());

    @Then("Verify user is welcome to Merge Cart page")
    public void user_is_welcome() {
    	String userFirstName = "";
    	if(mergeCart.stateHolder.hasKey("userObject")){
    		User user = mergeCart.stateHolder.get("userObject");
    		userFirstName = user.getFirstName();
    	}
    	else{        
    		userFirstName = User.getUser(User.DEFAULT).getFirstName();
    	}
        
        String userName = mergeCart.getUserName();

        assertEquals("User is in welcome to merge cart page", userFirstName.toLowerCase(), userName.toLowerCase());
    }

    @Then("Verify user is in Merge Cart page")
    public void is_in_merge_cart() {
        assertTrue("User is in merge cart page", mergeCart.isDisplayed());
    }

    @When("User clicks Save to Wishlist & Continue")
    public void save_to_wishlist() {
        mergeCart.saveWishlist();
    }

    @Then("Verify previously added items are in Merge Cart page")
    public void previously_added_items() {
        assertTrue("Previously added items match", mergeCart.previouslyAdded());
    }

    @When("User clicks Add items to bag & Review Order")
    public void merge_cart_and_review() {
        mergeCart.addItems();
    }
    
    @And("^Verify '([^\"]*)' button is available in Merge Cart Page$")
    public void verify_button_availability_in_merge_cart_page(String mergeCartElementName){
    	assertTrue("Verify '" + mergeCartElementName + "' is available in Merge Cart page", mergeCart.getButtonElementInMergeCartPage(mergeCartElementName).isDisplayed());
    }
    
    @Then("^Verify the message as '([^\"]*)' for the few left item in Merge Cart page$")
    public void verify_few_left_out_item_message(String expectedMessage){
    	assertEquals(expectedMessage + " for the few left item in Merge Cart page is displayed", expectedMessage.toLowerCase(), mergeCart.getItemStatusFromMergeCart("FEW LEFT").toLowerCase());
    }
    
    @Then("^Verify the message as '([^\"]*)' for the regular item in Merge Cart page$")
    public void verify_regular_item_message(String expectedMessage){
    	assertEquals(expectedMessage + " for the regular item in Merge Cart page is displayed", expectedMessage.toLowerCase(), mergeCart.getItemStatusFromMergeCart("REGULAR").toLowerCase());
    }
    
    @Then("^Verify the message for the backordered item in Merge Cart page$")
    public void verify_backordered_item_message(){
    	String itemStatus = mergeCart.getItemStatusFromMergeCart("BACKORDERED").toLowerCase();    	
   		boolean result = itemStatus.matches("backordered - expected to ship on [A-Za-z]{3} \\d{1,2}, \\d{4}");
    	assertTrue("Status for the backordered item in Merge Cart page is displayed as " + itemStatus, result);
    }
    
    @Then("Verify e-gift card details in Merge Cart page")
    public void verify_egift_card_details(){
        
        String expectedSenderName = mergeCart.stateHolder.get("giftCardSenderName");
    	String expectedRecipientName = mergeCart.stateHolder.get("giftCardRecipientName");
    	String expectedRecipientEmail = mergeCart.stateHolder.get("giftCardRecipientEmail");
    	String expectedDate = mergeCart.stateHolder.get("giftCardDateSent");
    			
    	SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");		
    	SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMM d, yyyy");

    	Date date = null;
    	try {
    		  date = dateFormat1.parse(expectedDate);
    	} catch (ParseException e) {
    		e.printStackTrace();
    		fail("Failed to parse date " + expectedDate);
    	}	
    	
    	String expectedDateSent = dateFormat2.format(date);
    	
    	assertEquals("Same e-Gift card sender name", expectedSenderName.toLowerCase(), mergeCart.getEgiftCardSenderName().toLowerCase());
    	assertEquals("Same e-Gift card recipient name", expectedRecipientName.toLowerCase(), mergeCart.getEgiftCardRecipientName().toLowerCase());
    	assertEquals("Same e-Gift card recipient email", expectedRecipientEmail.toLowerCase(), mergeCart.getEgiftCardRecipientEmailAddress().toLowerCase());
    	assertEquals("Same e-Gift card date sent", expectedDateSent.toLowerCase(), mergeCart.getEgiftCardDateSent().toLowerCase());
    }
}
