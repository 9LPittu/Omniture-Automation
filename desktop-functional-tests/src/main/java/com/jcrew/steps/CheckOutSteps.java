package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class CheckOutSteps extends DriverFactory {

    @When("User selects guest check out")
    public void user_selects_guest_check_out() {
        ShoppingBagSignIn signIn = new ShoppingBagSignIn(getDriver());
        signIn.startGuestCheckout();
    }

    @When("User signs in and check out")
    public void user_signs_in_and_check_out() {
        ShoppingBagSignIn signIn = new ShoppingBagSignIn(getDriver());
        signIn.startRegisteredCheckout();
    }
    @Then("Verify user is in shipping address page")
    public void verifies_user_is_in_shipping_address_page(){
        Shipping shipping = new Shipping(getDriver());
        assertTrue("User should be in shopping bag page", shipping.isShippingAddressPage());
    }
    @When("Guest user fills shipping address and continue")
    public void guest_user_fills_shipping_address() {
        Shipping shipping = new Shipping(getDriver());
        shipping.fillGuestData();
        shipping.saveShippingAddress();
    }

    @Then("^Verify user is in shipping method page$")
    public void verifies_is_in_shipping_method_page() throws Throwable {
        ShippingMethod shippingMethod = new ShippingMethod(getDriver());

        assertTrue("User should be in shipping method page", shippingMethod.isShippingMethodPage());
    }

    @When("User selects random shipping method and continue")
    public void user_selects_random_shipping_method() {
        ShippingMethod method = new ShippingMethod(getDriver());
        method.selectRandomShippingMethod();
        method.clickContinue();
    }

    @Then("^Verify user is in billing page$")
    public void verify_user_is_in_billing_page() throws Throwable {
        Payment billingPage = new Payment(getDriver());
        assertTrue("User should be in billing page", billingPage.isBillingPage());
    }

    @When("User fills payment method and continue")
    public void user_fills_payment_method_and_continue() {
        Payment payment = new Payment(getDriver());
        payment.fillPayment();
        payment.submitPayment();
    }

    @When("User reviews and places order")
    public void user_reviews_and_places_order() {
        CheckoutReview review = new CheckoutReview(getDriver());
        review.placeOrder();
    }

    @Then("User gets an order confirmation number")
    public void user_gets_an_order_confirmation_number() {
        Checkout checkoutPage = new Checkout(getDriver());

        if (checkoutPage.isOrderConfirmationPage()) {
            assertTrue("Confirmation number in page", checkoutPage.orderNumberIsVisible());
        } else {
            float total = checkoutPage.getOrderTotal();
            assertTrue("Order total is " + total + " than threshold and there is no error", checkoutPage.hasErrors());
        }
    }

    @When("In Shipping Address Page, user clicks continue")
    public void shipping_address_continue() {
        Shipping shipping = new Shipping(getDriver());
        shipping.continueWithDefaultAddress();
    }

    @When("In Payment page, user clicks continue")
    public void payment_continue() {
        Payment payment = new Payment(getDriver());
        payment.submitPayment();
    }

    @When("In Review page, user fills cvv and places order")
    public void review_fill_cvv_and_continue() {
        CheckoutReview review = new CheckoutReview(getDriver());
        review.fillSecurityCode();
        review.placeOrder();
    }

    @Then("Verify user is in review page$")
    public void verify_user_is_in_review_page() {
        CheckoutReview review = new CheckoutReview(getDriver());
        assertTrue("User should be in review page", review.isReviewPage());
    }

    @Then("Verify user is in order confirmation page")
    public void verify_user_is_in_order_confirmation_page() {
        Checkout confirmation = new Checkout(getDriver());
        assertTrue("User should be in order confirmation page", confirmation.isOrderConfirmationPage());
    }

    @Then("^Verify proper currency symbol for the items is displayed on bag page$")
    public void verify_items_currency_sign_matches_context_on_bag_page() {
        Checkout checkout = new Checkout(getDriver());
        String countryName = checkout.country.getName();
        List<String> itemsPrice = checkout.getItemsPrice();
        for (String price : itemsPrice) {
            assertTrue("Item price " + price + " matches country context " + countryName,
                    CurrencyChecker.isValid(price, checkout.country));
        }
    }

    @Then("^Verify proper currency symbol for subtotal is displayed on bag page$")
    public void verify_subtotal_currency_sign_matches_context_on_bag_page() {
        Checkout checkout = new Checkout(getDriver());
        String countryName = checkout.country.getName();
        String subtotal = checkout.getSubtotal();
        assertTrue("Subtotal " + subtotal + " matches country context " + countryName,
                    CurrencyChecker.isValid(subtotal, checkout.country));
    }

    @Then("^Verify proper currency symbol for shipping is displayed on bag page$")
    public void verify_shipping_currency_sign_matches_context() {
        Checkout checkout = new Checkout(getDriver());
        String shipping = checkout.getEstimatedShipping();
        String countryName = checkout.country.getName();
        assertTrue("Shipping " + shipping + " matches country context " + countryName,
                CurrencyChecker.isValid(shipping, checkout.country));

    }

    @Then("^Verify proper currency symbol for total is displayed on bag page$")
    public void verify_total_currency_sign_matches_context() {
        Checkout checkout = new Checkout(getDriver());

        String total = checkout.getEstimatedTotal();
        String countryName = checkout.country.getName();
        assertTrue("Subtotal " + total + " matches country context " + countryName,
                CurrencyChecker.isValid(total, checkout.country));
    }
}
