package com.jcrew.steps;

import com.jcrew.page.ReviewPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

public class ReviewPageSteps extends DriverFactory {

    private final ReviewPage reviewPage = new ReviewPage(getDriver());

    @Then("^Clicks on place your order$")
    public void user_places_its_order() throws Throwable {
        reviewPage.user_places_its_order();
    }

    @And("^Validates billing section is present in review page$")
    public void Validates_billing_section_is_present_in_review_page() throws Throwable {
        assertTrue("Billing section should be displayed", reviewPage.isBillingSectionDisplayed());
    }

    @And("^Inputs credit card security code$")
    public void inputs_credit_card_security_code() throws Throwable {
       reviewPage.input_credit_card_security_code();
    }

    @And("^Validates shipping section is present in review page$")
    public void validates_shipping_section_is_present_in_review_page() throws Throwable {
        assertTrue("Shipping section should be displayed", reviewPage.isShippingSectionDisplayed());
    }
    
    @And("^items count should be ([^\"]*) on the review page$")
    public void verify_items_count_on_review_page(String itemsCount){
    	assertTrue("Items count on review page should be " + itemsCount, reviewPage.isItemsCountMatchesOnReviewPage(itemsCount));
    }
}
