package com.jcrew.steps;

import com.jcrew.page.ProductDetails;
import com.jcrew.utils.DriverFactory;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ProductDetailSteps extends DriverFactory {
    ProductDetails productDetails = new ProductDetails(getDriver());

    @When("User selects random color")
    public void user_selects_random_color() {
        productDetails.selectRandomColor();
    }

    @When("User selects random size")
    public void user_selects_random_size() {
        productDetails.selectRandomSize();
    }

    @When("User selects random quantity")
    public void user_selects_random_quantity() {
        productDetails.selectRandomQty();
    }

    @When("User adds product to bag")
    public void user_adds_product_to_bag() {
        productDetails.addToBag();
    }

    @Then("Verify context in the product detail page")
    public void verify_context_in_the_product_detail_page() {
        assertTrue("Currency and URL are expected for country", productDetails.verifyContext());
    }
    
    @Then("User is in product detail page")
    public void user_is_on_a_product_detail_page() throws InterruptedException {
        assertTrue("User should be in detail page",
        		productDetails.isProductDetailPage());
    }
    
    @Then("^Add to cart button is pressed$")
    public void add_to_cart_button_is_pressed() throws Throwable {
    	productDetails.click_add_to_cart();
    }
    
    @Then("^Update Bag button is pressed$")
    public void Update_Bag_button_is_pressed() throws Throwable {
    	productDetails.click_update_cart();
    }

    @Then("^User should see PDP page with soldout message which includes phone number$")
    public void user_should_see_pdp_page_soldout_message_which_includes_phone_number(){
        assertTrue("user should see PDP page with soldout message which includes phone number", productDetails.isSoldOutMessageDisplayed());
    }

    @And("^user should see the PDP messages for the selected country$")
    public void user_should_see_pdp_messages(){
        assertTrue("User should see size related messages on the PDP page for the selected country",productDetails.isSizeMessageDisplayedOnPDP());
        assertTrue("User should see message on the PDP page for the selected country",productDetails.isMessageDisplayedOnPDP());
    }

    @And("^user selects random variant on the PDP page$")
    public void user_selects_random_variant_on_PDP_Page(){
        productDetails.selectRandomVariantOnPDP();
    }

    @Then("^user should see PDP page with message for vps item$")
    public void user_should_see_PDP_page_with_vps_item_message(){
        assertTrue("user should see PDP page with message for vps item",productDetails.isVPSMessageDisplayed());
    }

    @Then("user should see PDP page with shipping restriction message")
    public void user_should_see_PDP_page_with_shipping_restriction_message(){
        assertTrue("user should see PDP page with shipping restriction message",productDetails.isShippingRestrictionMessageDisplayed());
    }
}
