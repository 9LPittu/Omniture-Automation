package com.jcrew.steps;

import com.jcrew.page.ReviewPage;
import com.jcrew.pojo.Address;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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
    
    @Then("Verify user is in review page")
    public void is_review_page() {
        assertTrue("User is in review page", reviewPage.isDisplayed());
    }
    
    @Then("Verify added billing address matches review page")
    public void verify_added_billing_address() {
        Address address = new Address("billing");
        verify_address(address);
    }
    
    private void verify_address(Address address) {

        String billingAddress = reviewPage.getBillingAddress();

        assertTrue("Review billing address contains " + address.getLine1(), billingAddress.contains(address.getLine1()));
        assertTrue("Review billing address contains " + address.getLine2(), billingAddress.contains(address.getLine2()));
        assertTrue("Review billing address contains " + address.getZipcode(), billingAddress.contains(address.getZipcode()));
        assertTrue("Review billing address contains " + address.getCity(), billingAddress.contains(address.getCity()));
        assertTrue("Review billing address contains " + address.getState(), billingAddress.contains(address.getState()));
        assertTrue("Review billing address contains " + address.getPhone(), billingAddress.contains(address.getPhone()));
    }
    
    @When("User edits details for ([^\"]*)")
    public void edit_details(String details) {
    	reviewPage.editDetails(details);
    }
}