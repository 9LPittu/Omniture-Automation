package com.jcrew.steps;

import com.jcrew.page.MergeBagPage;
import com.jcrew.pojo.User;
import com.jcrew.util.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MergeBagPageSteps extends DriverFactory {

	MergeBagPage mergeBagPage = new MergeBagPage(getDriver());
	
	@Then("Verify user is in Merge Cart page")
    public void is_in_merge_cart() {
        assertTrue("User is in merge cart page", mergeBagPage.isDisplayed());
    }
	
	@Then("Verify user is welcome to Merge Cart page")
    public void user_is_welcome() {
    	String userFirstName = "";
    	if(mergeBagPage.stateHolder.hasKey("userObject")){
    		User user = (User) mergeBagPage.stateHolder.get("userObject");
    		userFirstName = user.getFirstName();
    	}
    	else{        
    		userFirstName = User.getUser(User.DEFAULT).getFirstName();
    	}
        
        String userName = mergeBagPage.getUserName();       
        assertEquals("User is in welcome to merge cart page", userFirstName.toLowerCase(), userName.toLowerCase());
    }
	
    @Then("User clicks on save to wishlist button and continue checkout")
    public void check_if_user_is_in_merge_bag_page() {        
        if (mergeBagPage.isMergeBagPage()) {
            mergeBagPage.click_save_to_wishlist_and_continue_checkout();
        }
    }
    
    @And("^user should see 'SAVE TO WISHLIST & CONTINUE' button on the page$")
    public void verify_save_to_wishlist_and_continue_button_existence(){
    	assertTrue("'SAVE TO WISHLIST & CONTINUE' button should be displayed on the page",mergeBagPage.isSaveToWishlistAndContinueDisplayed());
    }
    
	@And("^user should see 'ADD ITEMS TO BAG & REVIEW ORDER' button on the page$")
    public void verify_add_items_to_bag_and_review_order_existence(){
		assertTrue("'ADD ITEMS TO BAG & REVIEW ORDER' button should be displayed on the page",mergeBagPage.isAddItemsToBagAndReviewOrderDisplayed());
    }
    
    @And("click on ADD ITEMS TO BAG & REVIEW ORDER button")
    public void click_add_items_to_bag_and_review_order(){
    	mergeBagPage.clickAddItemsToBagAndReviewOrder();
    }
    
    @Then("Verify previously added items are in Merge Cart page")
    public void previously_added_items() {
        assertTrue("Previously added items match", mergeBagPage.previouslyAdded());
    }
    
    @Then("^Verify the message as '([^\"]*)' for the few left item in Merge Cart page$")
    public void verify_few_left_out_item_message(String expectedMessage){
    	assertEquals(expectedMessage + " for the few left item in Merge Cart page is displayed", expectedMessage.toLowerCase(), mergeBagPage.getItemStatusFromMergeCart("FEW LEFT").toLowerCase());
    }
    
    @Then("^Verify the message as '([^\"]*)' for the regular item in Merge Cart page$")
    public void verify_regular_item_message(String expectedMessage){
    	assertEquals(expectedMessage + " for the regular item in Merge Cart page is displayed", expectedMessage.toLowerCase(), mergeBagPage.getItemStatusFromMergeCart("REGULAR").toLowerCase());
    }
    
    @Then("^Verify the message for the backordered item in Merge Cart page$")
    public void verify_backordered_item_message(){
    	String itemStatus = mergeBagPage.getItemStatusFromMergeCart("BACKORDERED").toLowerCase();    	
   		boolean result = itemStatus.matches("backordered - expected to ship on [A-Za-z]{3} \\d{1,2}, \\d{4}");
    	assertTrue("Status for the backordered item in Merge Cart page is displayed as " + itemStatus, result);
    }
    
    @Then("Verify e-gift card details in Merge Cart page")
    public void verify_egift_card_details(){
        
        String expectedSenderName = (String) mergeBagPage.stateHolder.get("giftCardSenderName");
    	String expectedRecipientName = (String) mergeBagPage.stateHolder.get("giftCardRecipientName");
    	String expectedRecipientEmail = (String) mergeBagPage.stateHolder.get("giftCardRecipientEmail");
    	String expectedDate = (String) mergeBagPage.stateHolder.get("giftCardDateSent");
    			
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
    	
    	assertEquals("Same e-Gift card sender name", expectedSenderName.toLowerCase(), mergeBagPage.getEgiftCardSenderName().toLowerCase());
    	assertEquals("Same e-Gift card recipient name", expectedRecipientName.toLowerCase(), mergeBagPage.getEgiftCardRecipientName().toLowerCase());
    	assertEquals("Same e-Gift card recipient email", expectedRecipientEmail.toLowerCase(), mergeBagPage.getEgiftCardRecipientEmailAddress().toLowerCase());
    	assertEquals("Same e-Gift card date sent", expectedDateSent.toLowerCase(), mergeBagPage.getEgiftCardDateSent().toLowerCase());
    }
}