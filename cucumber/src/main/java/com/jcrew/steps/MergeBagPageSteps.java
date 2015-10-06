package com.jcrew.steps;

import com.jcrew.page.MergeBagPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.Then;

public class MergeBagPageSteps extends DriverFactory {

    @Then("Click save to wishlist and continue checkout if user is in merge bag page")
    public void check_if_user_is_in_merge_bag_page() {
        MergeBagPage mergeBagPage = new MergeBagPage(getDriver());
        if (mergeBagPage.isMergeBagPage()) {
            mergeBagPage.click_save_to_wishlist_and_continue_checkout();
        }
    }

}
