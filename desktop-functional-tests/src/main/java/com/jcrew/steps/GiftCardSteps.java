package com.jcrew.steps;

import com.jcrew.page.GiftCards;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class GiftCardSteps extends DriverFactory {
	
	GiftCards giftCards = new GiftCards(getDriver());

    @When("^User adds e-gift card worth ([^\"]*) value to bag$")
    public void add_e_gift_card_to_bag(String amount) {
    	giftCards.addEgiftCardToBag(amount);
    }
    
    @Then("^Verify J.Crew Gift Cards page is displayed")
    public void verify_gift_card_page() {
    	giftCards.isDisplayed();
    }
    
    @When("^User selects \"([^\"]*)\" as card type$")
    public void user_selects_card_type(String cardType){
    	giftCards.selectCardType(cardType);
    }  
    
    @And("^User chooses gift amount of ([^\"]*) value$")
    public void user_chooses_gift_amount(String giftAmount){
    	giftCards.selectGiftAmount(giftAmount);
    }
    
    @And("^User enters ([^\"]*) as Sender Name$")
    public void user_enters_sender_name(String senderName){
    	giftCards.enterSenderName(senderName);
    }
    
    @And("^User enters ([^\"]*) as Recipient Name$")
    public void user_enters_recipient_name(String recipientName){
    	giftCards.enterRecipientName(recipientName);
    }
    
    @And("^User enters ([^\"]*) as Recipient Email Address$")
    public void user_enters_recipient_email_address(String recipientEmailAddress){
    	giftCards.enterRecipientEmailAddress(recipientEmailAddress);
    }
    
    @And("^User enters ([^\"]*) as gift message in ([^\"]*)$")
    public void user_enters_gift_message(String message, String fieldName){
    	giftCards.enterGiftMessage(message, fieldName);
    }
    
    @And("^User clicks Add to Bag on gift cards page$")
    public void user_clicks_add_to_bag(){
    	giftCards.clickAddtoBag();
    }        
}
