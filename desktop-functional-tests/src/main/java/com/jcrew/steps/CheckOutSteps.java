package com.jcrew.steps;

import com.jcrew.page.*;
import com.jcrew.utils.DriverFactory;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class CheckOutSteps extends DriverFactory {

    @When("User clicks check out button")
    public void user_clicks_check_out_button() {
        ShoppingBag shoppingBag = new ShoppingBag(getDriver());
        shoppingBag.clickCheckoutButton();
    }

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

    @When("Guest user fills shipping address and continue")
    public void guest_user_fills_shipping_address() {
        Shipping shipping = new Shipping(getDriver());
        shipping.fillGuestData();
        shipping.saveShippingAddress();
    }

    @When("User selects random shipping method and continue")
    public void user_selects_random_shipping_method() {
        ShippingMethod method = new ShippingMethod(getDriver());
        method.selectRandomShippingMethod();
        method.clickContinue();
    }

    @When("User fills payment method and continue")
    public void user_fills_payment_method_and_continue() {
        Payment payment = new Payment(getDriver());
        payment.fillPayment();
        payment.submitPayment();
    }

    @When("User reviews and places order")
    public void user_reviews_and_places_order() {
        OrderReview review = new OrderReview(getDriver());
        review.placeOrder();
    }

    @Then("User gets an order confirmation number")
    public void user_gets_an_order_confirmation_number() {
        OrderConfirmation confirmation = new OrderConfirmation(getDriver());
        assertTrue("Confirmation number in page", confirmation.orderNumberIsVisible());
    }

}