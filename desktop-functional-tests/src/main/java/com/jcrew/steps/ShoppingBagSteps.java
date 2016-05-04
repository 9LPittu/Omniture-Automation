package com.jcrew.steps;

import com.jcrew.page.ShoppingBag;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class ShoppingBagSteps extends DriverFactory{
	
	private final ShoppingBag shoppingBagPage = new ShoppingBag(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();
    
	@Then("^User should be in shopping bag page$")
    public void user_should_be_in_shopping_bag_page() throws Throwable {
        assertTrue("Article checkout should have been present",
                shoppingBagPage.isArticleCheckoutPresent());
    }
	
	@And("^Clicks edit button on item bag page$")
    public void clicks_edit_button_on_item_bag_page() throws Throwable {
        shoppingBagPage.click_edit_button();
    }
	
	 @When("^Clicks on checkout$")
	    public void clicks_on_checkout() throws Throwable {
	        shoppingBagPage.click_checkout_button();
    }
}
