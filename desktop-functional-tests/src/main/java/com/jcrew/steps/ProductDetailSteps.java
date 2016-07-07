package com.jcrew.steps;

import com.jcrew.page.ProductDetails;
import com.jcrew.utils.DriverFactory;
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
    
    @When("^Add to cart button is pressed$")
    public void add_to_cart_button_is_pressed() throws Throwable {
    	productDetails.click_add_to_cart();
    }
    
    @When("^Update Bag button is pressed$")
    public void Update_Bag_button_is_pressed() throws Throwable {
    	productDetails.click_update_cart();
    }

    @Then("^Verify sold out message is displayed on PDP$")
    public void user_should_see_pdp_page_soldout_message_which_includes_phone_number(){
        assertTrue("user should see PDP page with soldout message which includes phone number", productDetails.isSoldOutMessageDisplayed());
    }

    @Then("^Verify PDP message is displayed for the selected country$")
    public void user_should_see_pdp_messages(){
        assertTrue("User should see size related messages on the PDP page for the selected country",productDetails.isSizeMessageDisplayedOnPDP());
        assertTrue("User should see message on the PDP page for the selected country",productDetails.isPriceMessageDisplayedOnPDP());
    }

    @When("^User selects random variant on the PDP page$")
    public void user_selects_random_variant_on_PDP_Page(){
        productDetails.selectRandomVariantOnPDP();
    }

    @Then("^Verify VPS item message is displayed on PDP$")
    public void user_should_see_PDP_page_with_vps_item_message(){
        assertTrue("user should see PDP page with message for vps item",productDetails.isVPSMessageDisplayed());
    }

    @Then("Verify shipping restriction message is displayed on PDP")
    public void user_should_see_PDP_page_with_shipping_restriction_message(){
        assertTrue("user should see PDP page with shipping restriction message",productDetails.isShippingRestrictionMessageDisplayed());
    }

    @Then("^Verify proper currency symbol is displayed on PDP page$")
    public void verify_currency_on_product_PDP(){
        assertTrue("Currency on product details page",productDetails.isCorrectCurrencySymbolonPDP());
    }
    
    @Then("^Verify 'size & fit details' link is displayed above the 'Add to Bag' button$")
    public void verify_size_and_fit_details_link_displayed_above_add_to_bag_button(){
    	assertTrue("Verify 'size & fit details' link is displayed above the 'Add to Bag' button",productDetails.isSizeAndFitDetailsLinkDisplayedAboveAddToBag());
    }
    
    @Then("^Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button$")
    public void verify_size_and_fit_drawer_displayed_below_add_to_bag_button(){
    	assertTrue("Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button",productDetails.isSizeAndFitDrawerDisplayedBelowAddToBag());
    }
    
    @Then("^Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer$")
    public void verify_product_details_drawer_displayed_below_size_and_fit_drawer(){
    	assertTrue("Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer",productDetails.isProductDetailsDrawerDisplayedBelowSizeAndFitDrawer());
    }
    
    @When("^user clicks on '([^\"]*)' drawer$")
    public void user_clicks_pdp_drawer(String drawerName){
    	productDetails.clickPdpDrawer(drawerName);
    }
    
    @Then("^Verify '([^\"]*)' drawer is ([^\"]*) state$")
    public void verify_pdp_drawer_state(String drawerName, String expectedState){
    	assertTrue("Verify " + drawerName + " drawer is " + expectedState,productDetails.isPdpDrawerInExpectedState(drawerName, expectedState));
    }
    
    @Then("^Verify item details are displayed in the 'PRODUCT DETAILS' drawer$")
    public void verify_item_details_dsiplayed_in_product_details_drawer(){
    	assertTrue("Verify item details are displayed in the 'PRODUCT DETAILS' drawer",productDetails.isItemDetailsDisplayedInProductDetailsDrawer());
    }
}
