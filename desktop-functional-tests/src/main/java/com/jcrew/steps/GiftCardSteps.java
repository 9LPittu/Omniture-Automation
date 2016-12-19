package com.jcrew.steps;

import com.jcrew.page.GiftCards;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.When;
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
}
