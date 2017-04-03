package com.jcrew.steps.product;

import com.jcrew.page.product.ProductDetails;
import com.jcrew.page.product.ProductDetailsActions;
import com.jcrew.page.product.ProductDetailsSizes;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.TestDataReader;

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
    private ProductDetails productDetails = new ProductDetails(getDriver());


    @Then("Verify context in the product detail page")
    public void verify_context_in_the_product_detail_page() {
        assertTrue("Currency and URL are expected for country", productDetails.verifyContext());
    }

    @Then("Verify product detail page is displayed")
    public void user_is_on_a_product_detail_page() throws InterruptedException {
        assertTrue("User should be in detail page",
        		productDetails.isProductDetailPage());
    }

    @Then("Verify product detail page from recommendation is displayed")
    public void baynote_pdp_is_displayed() {
        String expected = stateHolder.get("baynote");

        if (expected != null) {
            assertEquals("Same item from baynote", expected.toLowerCase(), productDetails.getProductCode().toLowerCase());
        }
    }

    @Then("Verify price matches with category array")
    public void price_matches_category_array() {
        Product product = (Product) stateHolder.get("fromArray");
        assertEquals("Product price matches category array", product.getPrice(), productDetails.getProductPrice());
    }

    @Then("Verify product name on PDP matches with QS")
    public void product_name_matches_quick_shop() {
        Product product = (Product) stateHolder.get("fromQuickShop");
        assertEquals("Product name matches on QS matches with PDP", product.getName(), productDetails.getProductName());

    }
    @Then("Verify product name on PDP matches with category array")
    public void product_name_matches_category_array() {
        Product product = (Product) stateHolder.get("fromArray");
        assertEquals("Product name matches category array", product.getName(), productDetails.getProductName());
    }

    @When("User clicks on write a review button")
    public void write_review_button_pressed(){
        productDetails.click_write_review();
    }


    @Then("^Verify PDP message is displayed for the selected country$")
    public void user_should_see_pdp_messages(){
        ProductDetailsSizes sizes = new ProductDetailsSizes(getDriver());

        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        String expected = testDataReader.getData("pdp.size.message");
        String actual = sizes.getMessage();

        assertEquals("User should see size related messages on the PDP page for the selected country",
                expected.toLowerCase(), actual.toLowerCase());

        assertTrue("User should see message on the PDP page for the selected country",
                productDetails.isPriceMessageDisplayedOnPDP());

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
        Country country = stateHolder.get("context");
        String countryName = country.getName();

        for(String price : listPrice) {
            assertTrue("Price " + price + " matches country context " + countryName,
                    CurrencyChecker.isValid(price));
        }
    }
    


    @Then("^Verify (SIZE & FIT|PRODUCT DETAILS) is displayed between (Add to Bag|SIZE & FIT) and ([^\"]*)$")
    public void verify_elements_layout_PDP(String elementtoFind, String elementAbove, String elementBelow){
        boolean isSizeAndFit  = productDetails.isSizeAndFitDrawerDisplayed();
        boolean result=true;

        if ((!elementtoFind.equalsIgnoreCase("size & fit")) || isSizeAndFit) {
            int Find_Y = productDetails.getYCoordinate(elementtoFind);

            if ((!elementBelow.equalsIgnoreCase("size & fit")) || isSizeAndFit) {
                int below_Y = productDetails.getYCoordinate(elementBelow);
                result = result && (below_Y > Find_Y);
            }

            if (elementAbove.equalsIgnoreCase("add to bag")) {
                ProductDetailsActions productDetailsActions = new ProductDetailsActions(getDriver());
                result &= productDetailsActions.getYCoordinate() < Find_Y;
            } else if (isSizeAndFit) {
                int Above_Y = productDetails.getYCoordinate(elementAbove);
                result &= result && (Above_Y < Find_Y);
            }

            assertTrue("Verify '"+elementtoFind+"' is displayed below the '"+elementAbove+"'",result);
        }
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
    	if (!element.equalsIgnoreCase("endcaps")) {
    		assertEquals("Verify "+element+" is displayed in PDP",true,productDetails.isDisplayedInPDP(element));
    	} else {
    		TestDataReader testDataReader = TestDataReader.getTestDataReader();
    		boolean isEndNavDiaplay = testDataReader.getBoolean("pdp.endnav.toggle");
    		if (isEndNavDiaplay)
    			assertEquals("Verify "+element+" is displayed in PDP",true,productDetails.isDisplayedInPDP(element));
    	}
    }
}
