package com.jcrew.steps;

import com.jcrew.page.ReviewPage;
import com.jcrew.pojo.Address;
import com.jcrew.pojo.ShippingMethod;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.ShippingMethodCalculator;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReviewPageSteps extends DriverFactory {

    private final ReviewPage reviewPage = new ReviewPage(getDriver());
    private ShippingMethodCalculator methodCalculator = new ShippingMethodCalculator();
    
    @Then("^User clicks on place your order button$")
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
    
    @Then("Verify checkout breadcrumb is REVIEW")
    public void verify_progress() {
        assertEquals("Breadcrumb is REVEW", "REVIEW", reviewPage.getBreadCrumb());
    }
    
    @Then("Verify that Review title is Checkout")
    public void verify_title() {
        String title = reviewPage.getTitle();

        assertTrue("Title is Checkout", "Checkout".equalsIgnoreCase(title));
    }
    
    @When("^User adds a promo code ([^\"]*) in review page$")
    public void add_promo_in_review_page(String promoCode) {
    	reviewPage.addPromoCode(promoCode);
    }
    
    @Then("Verify selected shipping address matches review page")
    public void verify_shipping_adress() {
        String selectedAddress = (String) reviewPage.stateHolder.get("selectedshippingAddress");
        String reviewAddress = reviewPage.getShippingAddress();

        assertTrue("Review shipping address contains " + reviewAddress, selectedAddress.contains(reviewAddress));
    }
    
    @Then("Verify selected shipping method matches review page")
    public void verify_shipping_method() {
        String selectedMethod = (String) reviewPage.stateHolder.get("selectedShippingMethod");
        String reviewMethod = reviewPage.getShippingMethod();

        assertTrue("Review shipping method contains " + selectedMethod, reviewMethod.contains(selectedMethod));
    }
    
    @Then("Verify selected billing address matches review page")
    public void verify_selected_billing_address() {
    	 String selectedPayment = (String) reviewPage.stateHolder.get("selectedPaymentMethod");
    	 String payMentMethod = reviewPage.getPaymentMethod();
    	 assertTrue("Review payment method contains " + selectedPayment, payMentMethod.contains(selectedPayment));
       
    }
    
    @Then("Select different shipping method on review page")
    public void select_shipping_method() {
    	reviewPage.selectRandomShippingMethodOnReviewPage();       
    }
    
    @Then("^Verify all shipping methods are available in review page$")
    public void shipping_methods() {
        List<ShippingMethod> pageMethods = reviewPage.getShippingMethods();
        List<ShippingMethod> expectedMethods = methodCalculator.getExpectedList();

        for (int i = 0; i < pageMethods.size(); i++) {
            ShippingMethod actual = pageMethods.get(i);
            ShippingMethod expected = expectedMethods.get(i);

            assertEquals("Expected shipping method", expected, actual);
        }
    }
    
    @Then("^Verify all shipping methods show estimated shipping date in review page$")
    public void shipping_method_with_estimated_shipping_date() {
        List<ShippingMethod> pageMethods = reviewPage.getShippingMethods();
        List<ShippingMethod> expectedMethods = methodCalculator.getExpectedList();

        for (int i = 0; i < pageMethods.size(); i++) {
            ShippingMethod actual = pageMethods.get(i);
            ShippingMethod expected = expectedMethods.get(i);

            String actualName = actual.getMethod();
            String expectedName = expected.getMethod();

            String date = actualName.replace(expectedName + " – ", "");
            reviewPage.logger.debug("expected {} actual {} dat {}", expectedName, actualName, date);
            assertFalse("Shipping method " + actualName + " contains a date", date.isEmpty());

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd");

            try {

                dateFormat.parse(date);
                review.logger.info("Successfully parsed " + date);

            } catch (ParseException e) {
                e.printStackTrace();
                fail("Failed to parse date " + date);
            }

        }
    }
}