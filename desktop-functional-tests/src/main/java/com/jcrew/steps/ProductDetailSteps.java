package com.jcrew.steps;

import com.jcrew.page.ProductDetails;
import com.jcrew.pojo.Product;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;

import com.jcrew.utils.StateHolder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */

public class ProductDetailSteps extends DriverFactory {

    private StateHolder stateHolder = StateHolder.getInstance();
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

    @Then("Verify product detail page is displayed")
    public void user_is_on_a_product_detail_page() throws InterruptedException {
        assertTrue("User should be in detail page",
        		productDetails.isProductDetailPage());
    }



    @Then("Verify price matches with category array")
    public void price_matches_category_array() {
        Product product = (Product) stateHolder.get("fromArray");
        assertEquals("Product price matches category array", product.getPrice(), productDetails.getProductPrice());
    }


    @Then("Verify product name on PDP matches with category array")
    public void product_name_matches_category_array() {
        Product product = (Product) stateHolder.get("fromArray");

        assertTrue("Product name matches category array", productDetails.compare_PDP_name(product));
    }

    @When("User clicks on write a review button")
    public void write_review_button_pressed(){
        productDetails.click_write_review();
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
        List<String> listPrice = productDetails.getAllPrices();
        String countryName = productDetails.country.getName();
        for(String price : listPrice) {
            assertTrue("Price " + price + " matches country context " + countryName,
                    CurrencyChecker.isValid(price, productDetails.country));
        }
    }
    


    @Then("^Verify ([^\"]*) is displayed between ([^\"]*) and ([^\"]*)$")
    public void verify_elements_layout_PDP(String elementtoFind,String elementAbove,String elementBelow){
        int Find_Y = productDetails.getYCoordinate(elementtoFind);
        int below_Y = productDetails.getYCoordinate(elementBelow);
        int Above_Y = productDetails.getYCoordinate(elementAbove);
        assertTrue("Verify '"+elementtoFind+"' is displayed below the '"+elementAbove+"'",((below_Y > Find_Y) &&(Above_Y < Find_Y)));
    }



    @When("^user clicks on '([^\"]*)' drawer$")
    public void user_clicks_pdp_drawer(String drawerName){
    	productDetails.clickPdpDrawer(drawerName);
    }
    
    @Then("^Verify ([^\"]*) drawer is ([^\"]*) state$")
    public void verify_pdp_drawer_state(String drawerName, String expectedState){
    	assertTrue("Verify " + drawerName + " drawer is " + expectedState,productDetails.isPdpDrawerInExpectedState(drawerName, expectedState));
    }
    
    @Then("^Verify item details are displayed in the 'PRODUCT DETAILS' drawer$")
    public void verify_item_details_dsiplayed_in_product_details_drawer(){
    	assertTrue("Verify item details are displayed in the 'PRODUCT DETAILS' drawer",productDetails.isItemDetailsDisplayedInProductDetailsDrawer());
    }

    @Then("Verify ([^\"]*) displayed in PDP")
    public void verify_pdp_has(String element){
        assertEquals("Verify "+element+" is displayed in PDP",true,productDetails.isDisplayedInPDP(element));
    }

    @Then("Verify that page contains a selected color")
    public void has_selected_color() {
        assertFalse("Color field is not empty", productDetails.getSelectedColor().isEmpty());
    }

    @Then("Verify that page contains a selected size")
    public void has_selected_size() {
        assertFalse("Size field is not empty", productDetails.getSelectedSize().isEmpty());
    }
}
