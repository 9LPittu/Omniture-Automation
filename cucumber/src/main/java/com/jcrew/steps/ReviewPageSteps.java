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
    
    @And("^select \"([^\"]*)\" breadcrumb item$")
    public void select_breadcrumb_item(String breadcrumbItemName){
    	reviewPage.selectBreadcrumbItem(breadcrumbItemName);
    }
    
    @And("^click on 'CHANGE' button of 'SHIPPING DETAILS' section on 'Review' page$")
    public void click_changes_button_shipping_details_section_review_page(){
    	reviewPage.clickChangeButtonOfShippingDetailsOnReviewPage();
    }
    
    @And("^click on 'CHANGE' button of 'BILLING DETAILS' section on 'Review' page$")
    public void click_changes_button_billing_details_section_review_page(){
    	reviewPage.clickChangeButtonOfBillingDetailsOnReviewPage();
    }
    
    @And("^product name and price on review page should be displayed correctly$")
    public void product_name_price_on_review_page_should_be_displayed_correctly(){
    	assertTrue("Product name and price on review page should be displayed correctly",reviewPage.isProductNamePriceMatchesOnReviewPage());
    }
}