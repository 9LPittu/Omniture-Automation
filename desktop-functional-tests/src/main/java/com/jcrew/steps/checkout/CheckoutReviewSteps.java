package com.jcrew.steps.checkout;

import com.jcrew.page.checkout.CheckoutReview;
import com.jcrew.pojo.Address;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutReviewSteps extends DriverFactory {
    private CheckoutReview review = new CheckoutReview(getDriver());
    private boolean isProduction = false;
    private StateHolder stateHolder = StateHolder.getInstance();

    public CheckoutReviewSteps() {
        PropertyReader properties = PropertyReader.getPropertyReader();
        String environment = properties.getProperty("environment");

        if("production".equalsIgnoreCase(environment)) {
            isProduction = true;
        }
    }

    @Then("Verify user is in review page")
    public void is_review_page() {
        assertTrue("User is in review page", review.isDisplayed());
    }

    @When("User fills security code")
    public void fill_security_code() {
        review.fillSecurityCode();
    }

    @When("User clicks on PLACE MY ORDER")
    public void place_order() {

        if(!isProduction) {
            review.placeOrder();
        }
    }
    
    @Then("Verify Review Page url is ([^\"]*)")
    public void review_page_url(String url) {
        String current_url = getDriver().getCurrentUrl();
        assertTrue("Review page url is " + url, current_url.contains(url));
    }

    @Then("Verify that Review title is Checkout")
    public void verify_title() {
        String title = review.getTitle();

        assertTrue("Title is Checkout", "Checkout".equalsIgnoreCase(title));
    }

    @Then("Verify checkout breadcrumb is REVIEW")
    public void verify_progress() {
        assertEquals("Breadcrumb is REVEW", "REVIEW", review.getBreadCrumb());
    }

    private void verify_address(Address address) {

        String billingAddress = review.getBillingAddress();

        assertTrue("Review billing address contains " + address.getLine1(), billingAddress.contains(address.getLine1()));
        assertTrue("Review billing address contains " + address.getLine2(), billingAddress.contains(address.getLine2()));
        assertTrue("Review billing address contains " + address.getZipcode(), billingAddress.contains(address.getZipcode()));
        assertTrue("Review billing address contains " + address.getCity(), billingAddress.contains(address.getCity()));
        assertTrue("Review billing address contains " + address.getState(), billingAddress.contains(address.getState()));
        assertTrue("Review billing address contains " + address.getPhone(), billingAddress.contains(address.getPhone()));
    }
    
    @Then("Verify billing address display on review page")
    public void verify_billing_address_display() {
    	String billingAddress = review.getBillingAddress();
    	assertTrue("Billing address is not displayed", !billingAddress.isEmpty());
    }
    
    @Then("Verify shipping address display on review page")
    public void verify_shipping_address_display() {
    	String shippingingAddress = review.getShippingAddress();
    	assertTrue("Shipping address is not displayed", !shippingingAddress.isEmpty());
    }
    
    @Then("Verify guest billing address matches review page")
    public void verify_billing_address() {
        Address address = new Address();
        verify_address(address);
    }

    @Then("Verify added billing address matches review page")
    public void verify_added_billing_address() {
        Address address = new Address("billing");
        verify_address(address);
    }

    @Then("Verify guest shipping address matches review page")
    public void verify_shipping_address() {
        Address address = new Address();

        String billingAddress = review.getShippingAddress();

        assertTrue("Review shipping address contains " + address.getLine1(), billingAddress.contains(address.getLine1()));
        assertTrue("Review shipping address contains " + address.getLine2(), billingAddress.contains(address.getLine2()));
        assertTrue("Review shipping address contains " + address.getZipcode(), billingAddress.contains(address.getZipcode()));
        assertTrue("Review shipping address contains " + address.getCity(), billingAddress.contains(address.getCity()));
        assertTrue("Review shipping address contains " + address.getState(), billingAddress.contains(address.getState()));
        assertTrue("Review shipping address contains " + address.getPhone(), billingAddress.contains(address.getPhone()));
    }

    @Then("Verify selected shipping method matches review page")
    public void verify_shipping_method() {
        String selectedMethod = stateHolder.get("selectedShippingMethod");
        String reviewMethod = review.getShippingMethod();

        assertTrue("Review shipping method contains " + selectedMethod, reviewMethod.contains(selectedMethod));
    }

    @Then("Verify ([^\"]*) is the selected shipping method")
    public void verify_shipping_method(String method) {
        String reviewMethod = review.getShippingMethod();

        assertTrue("Review shipping method contains " + method,
                reviewMethod.toLowerCase().contains(method.toLowerCase()));
    }

    @Then("Verify selected shipping address matches review page")
    public void verify_shipping_adress() {
        String selectedAddress = stateHolder.get("selectedshippingAddress");
        String reviewAddress = review.getShippingAddress();

        assertTrue("Review shipping address contains " + reviewAddress, selectedAddress.contains(reviewAddress));
    }
    
    @Then("Select different shipping method on review page")
    public void select_shipping_method() {
        review.selectRandomShippingMethodOnReviewPage();       
    }
    
    @When("User edits details for ([^\"]*)")
    public void edit_details(String details) {
        review.editDetails(details);
    }
    
    @Then("Verify selected billing address matches review page")
    public void verify_selected_billing_address() {
    	 String selectedPayment = stateHolder.get("selectedPaymentMethod");
    	 String payMentMethod = review.getPaymentMethod();
    	 assertTrue("Review payment method contains " + selectedPayment, payMentMethod.contains(selectedPayment));
       
    }
}