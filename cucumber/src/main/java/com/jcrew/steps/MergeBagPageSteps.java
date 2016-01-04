package com.jcrew.steps;

import com.jcrew.page.MergeBagPage;
import com.jcrew.util.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class MergeBagPageSteps extends DriverFactory {

	MergeBagPage mergeBagPage = new MergeBagPage(getDriver());
	
    @Then("Click save to wishlist and continue checkout if user is in merge bag page")
    public void check_if_user_is_in_merge_bag_page() {        
        if (mergeBagPage.isMergeBagPage()) {
            mergeBagPage.click_save_to_wishlist_and_continue_checkout();
        }
    }
    
    @And("click on ADD ITEMS TO BAG & REVIEW ORDER button")
    public void click_add_items_to_bag_and_review_order(){
    	mergeBagPage.clickAddItemsToBagAndReviewOrder();
    }

}
