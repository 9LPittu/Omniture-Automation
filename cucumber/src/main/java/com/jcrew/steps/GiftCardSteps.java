package com.jcrew.steps;

import com.jcrew.page.GiftCards;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.When;

public class GiftCardSteps extends DriverFactory {
	
	GiftCards giftCards = new GiftCards(getDriver());

    @When("^User adds e-gift card worth ([^\"]*) value to bag$")
    public void add_e_gift_card_to_bag(String amount) {
    	giftCards.addEgiftCardToBag(amount);
    }
}
