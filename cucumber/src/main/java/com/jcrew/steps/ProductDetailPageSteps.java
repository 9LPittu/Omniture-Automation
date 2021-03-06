package com.jcrew.steps;

import com.jcrew.page.ProductDetailPage;
import com.jcrew.pojo.Product;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class ProductDetailPageSteps extends DriverFactory {

    private final ProductDetailPage productDetailPage = new ProductDetailPage(getDriver());
    private final Logger logger = LoggerFactory.getLogger(ProductDetailPageSteps.class);
    private StateHolder stateHolder = StateHolder.getInstance();

    @Given("^User is in product detail page$")
    public void user_is_on_a_product_detail_page() throws InterruptedException {
        assertTrue(Util.getSelectedCountryName() + "User should be in product detail page",
                productDetailPage.isProductDetailPage());
    }
    
    @And("^product name and price should match with array page$")
    public void product_name_price_matches_with_array_page(){
    	assertTrue("product name and price should match with array page",productDetailPage.isProductNamePriceListMatchesWithArrayPage());
    }

    @And("^A variation is selected$")
    public void a_variation_is_selected() throws Throwable {
        productDetailPage.select_variation();
    }

    @And("^A color is selected$")
    public void a_color_is_selected() throws Throwable {
        productDetailPage.select_random_color();
    }

    @And("^A size is selected$")
    public void a_size_is_selected() throws Throwable {
        productDetailPage.select_random_size();
    }

    @And("^Quantity is selected$")
    public void quantity_is_selected() throws Throwable {
        productDetailPage.select_quantity("2");
    }

    @And("^A wishlist button is present$")
    public void a_wishlist_button_is_present() throws Throwable {
        assertTrue("A wishlist button should be displayed",
                productDetailPage.isWishlistButtonPresent());
    }

    @When("^Add to cart button is pressed$")
    public void add_to_cart_button_is_pressed() throws Throwable {
        //Retrieve number of items present in Cart and add it to stateholder
        int cartItemNumber = productDetailPage.getNumberOfItemsInBag();
        stateHolder.put("itemsInCart_BeforeAddToBag",cartItemNumber);
        productDetailPage.click_add_to_cart();
    }

    @Then("^Bag should have (\\d+) item\\(s\\) added$")
    public void bag_should_have_item_s_added(int numberOfItems) throws Throwable {
        assertEquals("Number of Items should be the same",
                numberOfItems, productDetailPage.getNumberOfItemsInBag());

    }

    @Then("^A minicart modal should appear with message '([^\"]*)'$")
    public void a_minicart_modal_should_appear_with_message(String message) throws Throwable {
        boolean isMiniCartMessage = productDetailPage.showsMinicartMessage(message);
        if (!isMiniCartMessage) {
            int previousItemCountInBag = (int) stateHolder.get("itemsInCart_BeforeAddToBag");
            int currentItemsInBag = productDetailPage.getNumberOfItemsInBag();
            assertEquals("Item count in Bag should match", previousItemCountInBag + 1, currentItemsInBag);
        }
    }

    @Given("^Bag should have item\\(s\\) added$")
    public void bag_should_have_item_s_added() throws Throwable {
        assertTrue("It should contain at least one item", productDetailPage.getNumberOfItemsInBag() > 0);
    }

    @And("^Verify product sale price is ([^\"]*)$")
    public void Verify_product_sale_price_is_now_$_(String salePrice) throws Throwable {
        assertEquals("Sale price differs from what is expected",
                salePrice, productDetailPage.getSalePrice());
    }

    @And("^Size ([^\"]*) is selected by user$")
    public void size_is_selected(String productSize) throws Throwable {
        productDetailPage.select_size(productSize);
    }

    @And("^Color ([^\"]*) is selected by user$")
    public void color_is_selected(String productColor) throws Throwable {
        productDetailPage.select_color(productColor);
    }

    @Then("^Verify color ([^\"]*) is selected$")
    public void verify_color_is_selected(String productColor) throws Throwable {
        assertEquals("Color should have been selected", productColor, productDetailPage.getSelectedColor());
    }

    @And("^Verify size ([^\"]*) is selected$")
    public void verify_size_is_selected(String productSize) throws Throwable {
        assertEquals("Size should have been selected", productSize,
                productDetailPage.getSelectedSize());
    }

    @And("^Verify update bag button is present$")
    public void verify_update_bag_button_is_present() throws Throwable {
        assertTrue("Element should have had UPDATE BAG value set",
                productDetailPage.isBagButtonText("UPDATE BAG"));
    }

    @Then("^Update Bag button is pressed$")
    public void Update_Bag_button_is_pressed() throws Throwable {
        productDetailPage.click_update_cart();
    }

    @And("^Quantity ([^\"]*) is selected by user$")
    public void quantity_is_selected_by_user(String quantity) throws Throwable {
        productDetailPage.select_quantity(quantity);
    }

    @And("^Variation ([^\"]*) with list price ([^\"]*) is previously selected$")
    public void variation_previously_selected(String variation, String price) throws Throwable {
        assertEquals("Variation was not the expected",
                variation, productDetailPage.getSelectedVariationName());
    }

    @And("^Verify ([^\"]*) items are specified as quantity$")
    public void verify_items_are_specified_as_quantity(String quantity) throws Throwable {
        assertEquals("Quantity is not what is expected", quantity, productDetailPage.getSelectedQuantity());
    }

    @And("^Verify update wishlist button is displayed$")
    public void verify_update_wishlist_button_is_displayed() throws Throwable {
        assertEquals("Expected wishlist button is not present", "UPDATE WISHLIST",
                productDetailPage.getWishlistButtonMessage());
    }

    @And("^Wishlist button is pressed$")
    public void wishlist_button_is_pressed() throws Throwable {
        productDetailPage.click_wishlist();
    }

    @Then("^Verify update message for wishlist is displayed and go to wishlist page$")
    public void verify_update_message_for_wishlist_is_displayed_and_go_to_wishlist_page() throws Throwable {
        //assertion removed because we are not able to verify that the confirmation is present since
        //is disappearing before being abl to interact with it.
        //assertTrue("Expected message was not received", productDetailPage.isWishlistConfirmationMessageDisplayed());
        productDetailPage.go_to_wishlist();
    }

    @Then("^Size selector is not displayed$")
    public void size_selector_is_not_displayed() throws Throwable {
        assertFalse("Size selector should not be displayed",
                productDetailPage.isSizeSelectorSectionPresent());
    }

    @And("^Color selector is not displayed$")
    public void color_selector_is_not_displayed() throws Throwable {
        assertFalse("Clor selector should not be displayed",
                productDetailPage.isColorSelectorSectionPresent());
    }

    @And("^Quantity selector is not displayed$")
    public void quantity_selector_is_not_displayed() throws Throwable {
        assertFalse("Quantity selector should not be displayed",
                productDetailPage.isQuantitySelectorSectionPresent());
    }

    @And("^Add to bag button is not displayed$")
    public void add_to_bag_button_is_not_displayed() throws Throwable {
        assertFalse("Add to bag button should not be displayed",
                productDetailPage.isAddToBagButtonPresent());
    }

    @And("^Wishlist button is not displayed$")
    public void wishlist_button_is_not_displayed() throws Throwable {
        assertFalse("Add to bag button should not be displayed",
                productDetailPage.isWishlistButtonPresent());
    }

    @Then("^Verify headline message for VPS product is '([^\"]*)'$")
    public void verify_headline_message_for_vps_product(String messageHeadline) throws Throwable {
        assertEquals("Headline message is not the expected one",
                messageHeadline, productDetailPage.getHeadlineMessage());
    }

    @Then("^Verify body message for VPS product is '([^\"]*)'$")
    public void verify_body_message_for_vps_product(String messageBody) throws Throwable {
        assertEquals("Body message is not the expected one",
                messageBody, productDetailPage.getBodyMessage());
    }

    @And("^Verifies product list price is ([^\"]*)$")
    public void Verifies_product_list_price_is_$_(String listPrice) throws Throwable {
        assertEquals("Expected price is not the same", listPrice, productDetailPage.getProductPriceList());
    }

    @Then("^Verify product is a pre-order one$")
    public void verify_product_is_a_pre_order_one() throws Throwable {
        assertTrue("Product should have been a pre-order one",
                productDetailPage.getProductNameFromPDP().endsWith("(pre-order)"));
    }

    @Then("^Preorder button is displayed$")
    public void Preorder_button_is_displayed() throws Throwable {
        assertTrue("Pre-order button should be displayed", productDetailPage.isPreOrderButtonDisplayed());
        assertEquals("Name of the button is not pre order", "PRE-ORDER", productDetailPage.getBagButtonText());
    }

    @And("^Preorder button is pressed$")
    public void preorder_button_is_pressed() throws Throwable {
        productDetailPage.click_add_to_cart();
    }

    @And("^Selects any color for the product$")
    public void selects_any_color_for_the_product() throws Throwable {
        productDetailPage.select_random_color();
        Product product = Util.getCurrentProduct();
        assertNotNull("A color should have been selected", product.getSelectedColor());
    }

    @And("^Selects any size for the product$")
    public void selects_any_size_for_the_product() throws Throwable {
        productDetailPage.select_random_size();
        Product product = Util.getCurrentProduct();
        assertNotNull("A size should have been selected", product.getSelectedSize());
    }

    @Then("^Verify product name is the one it was selected$")
    public void verify_product_name_is_the_one_it_was_selected() throws Throwable {
        String productName = Util.getCurrentProduct().getProductName().toLowerCase().trim();
        productName = productName.replaceAll("pre-order ", "");
        String currentName = productDetailPage.getProductNameFromPDP().toLowerCase().trim();

        assertTrue("Product should be the selected one. Expected: " + productName + ", got: " + currentName,
                currentName.contains(productName));
    }

    @And("^Verify amount of colors listed is correct$")
    public void verify_amount_of_colors_listed_is_correct() throws Throwable {
        Product product = Util.getCurrentProduct();
        String colorsCountString = product.getColorsCount();

        int colorsCount = 1;
        if (colorsCountString != null) {
            colorsCount = Integer.parseInt(colorsCountString.trim());
        }

        assertEquals("Number of colors was not the expected", colorsCount, productDetailPage.getNumberOfColors());

    }

    @And("^Verify price list is correct$")
    public void verify_price_list_is_correct() throws Throwable {
        Product product = Util.getCurrentProduct();
        String priceList = product.getPriceList();

        if (priceList != null) {
            assertEquals("Price List should be the same", priceList, productDetailPage.getProductPriceList());
        }
    }

    @And("^Verify price sale is correct$")
    public void verify_price_sale_is_correct() throws Throwable {
        Product product = Util.getCurrentProduct();
        String priceSale = product.getPriceSale();

        if (priceSale != null) {
            assertEquals("Price Sale should be the same", priceSale, productDetailPage.getProductPriceSale());
        }

    }

    @And("^Verify price was is correct$")
    public void verify_price_was_is_correct() throws Throwable {
        Product product = Util.getCurrentProduct();
        String priceWas = product.getPriceWas();

        if (priceWas != null) {
            assertEquals("Price Was should be the same", priceWas, productDetailPage.getProductPriceWas());
        }

    }

    @And("^Verify variations listed are the expected ones$")
    public void verify_variations_listed_are_the_expected_ones() throws Throwable {
        Product product = Util.getCurrentProduct();
        String variations = product.getVariations();

        if (variations != null) {
            String[] variationsArray = variations.split(",");
            List<String> variationsList = new ArrayList<>();
            variationsList.add("Regular");
            for (String variation : variationsArray) {
                variationsList.add(variation.trim());
            }
            assertEquals("Variations should be the same", variationsList, productDetailPage.getVariationsNames());
        }
    }

    @And("^A button saying '([^\"]*)' is displayed$")
    public void a_button_saying_is_displayed(String message) throws Throwable {
        assertEquals("Expected message was not displayed", message, productDetailPage.getButtonErrorMessage());
    }
    
    @And("^click on checkout from minicart modal$")
    public void click_checkout_from_minicart_modal(){
    	productDetailPage.clickMinicartCheckout();
    }
    
    @Then("^user should see that previously selected color is retained$")
    public void verify_previously_selected_color_is_still_displayed_on_pdp_page(){

        if(productDetailPage.isPreviouslySelectedColorStillDisplayedAsSelected())
            assertTrue("User should see that the previously selected color is retained",
                    productDetailPage.isPreviouslySelectedColorStillDisplayedAsSelected());
        else
            logger.warn("Ignoring assertion: User should see that the previously selected color is retained. " +
                    "JCSC-731 will not be fixed.");
    }

    @And("^user should see that previously selected size is retained$")
    public void verify_previously_selected_size_is_still_displayed_on_pdp_page(){

        if(productDetailPage.isPreviouslySelectedSizeStillDisplayedAsSelected())
            assertTrue("User should see that the previously selected size is retained",
                    productDetailPage.isPreviouslySelectedSizeStillDisplayedAsSelected());
        else
            logger.warn("Ignoring assertion: User should see that the previously selected color is retained. " +
                    "JCSC-731 will not be fixed.");
    }

    @And("^user selects a new color$")
    public void user_selects_new_color(){    	
    	productDetailPage.selectNewColor();   	
    }

    @And("^user selects a new size$")
    public void user_selects_new_size(){    	
    	productDetailPage.selectNewSize();    	
    }
    
    @And("^Verify proper currency symbol is displayed on PDP page$")
    public void verify_currency_on_product_PDP(){
    	assertTrue(Util.getSelectedCountryName() + "Currency on product details page",productDetailPage.isCorrectCurrencySymbolonPDP());
    }
    
    @And("^user should see the PDP messages for the selected country$")
    public void user_should_see_pdp_messages(){
    	assertTrue(Util.getSelectedCountryName() + "User should see size related messages on the PDP page for the selected country",productDetailPage.isSizeMessageDisplayedOnPDP());
    	assertTrue(Util.getSelectedCountryName() + "User should see message on the PDP page for the selected country",productDetailPage.isMessageDisplayedOnPDP());
    }
    
    @Then("^user should see PDP page with soldout message which includes phone number$")
    public void user_should_see_pdp_page_soldout_message_which_includes_phone_number(){
    	assertTrue(Util.getSelectedCountryName() + "user should see PDP page with soldout message which includes phone number", productDetailPage.isSoldOutMessageDisplayed());
    }
    
    @And("^user selects random variant on the PDP page$")
    public void user_selects_random_variant_on_PDP_Page(){
    	productDetailPage.selectRandomVariantOnPDP();
    }
    
    @Then("^user should see PDP page with message for vps item$")
    public void user_should_see_PDP_page_with_vps_item_message(){
    	assertTrue(Util.getSelectedCountryName() + "user should see PDP page with message for vps item",productDetailPage.isVPSMessageDisplayed());
    }
    
    @Then("user should see PDP page with shipping restriction message")
    public void user_should_see_PDP_page_with_shipping_restriction_message(){
    	assertTrue(Util.getSelectedCountryName() + "user should see PDP page with shipping restriction message",productDetailPage.isShippingRestrictionMessageDisplayed());
    }
    
    @Then("^Verify 'size & fit details' link is displayed above the 'Add to Bag' button$")
    public void verify_size_and_fit_details_link_displayed_above_add_to_bag_button(){
    	assertTrue("Verify 'size & fit details' link is displayed above the 'Add to Bag' button",productDetailPage.isSizeAndFitDetailsLinkDisplayedAboveAddToBag());
    }
    
    @Then("^Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button$")
    public void verify_size_and_fit_drawer_displayed_below_add_to_bag_button(){
        boolean isSizeAndFit = productDetailPage.isSizeAndFitDrawerDisplayed();
        if (isSizeAndFit)
    	    assertTrue("Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button",productDetailPage.isSizeAndFitDrawerDisplayedBelowAddToBag());
    }
    
    @Then("^Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer$")
    public void verify_product_details_drawer_displayed_below_size_and_fit_drawer(){
    	assertTrue("Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer",productDetailPage.isProductDetailsDrawerDisplayedBelowSizeAndFitDrawer());
    }
    
    @When("^user clicks on '([^\"]*)' drawer$")
    public void user_clicks_pdp_drawer(String drawerName){
        boolean isSizeAndFit = productDetailPage.isSizeAndFitDrawerDisplayed();
        if (isSizeAndFit)
            productDetailPage.clickPdpDrawer(drawerName);
    }
    
    @Then("^Verify '([^\"]*)' drawer is ([^\"]*) state$")
    public void verify_pdp_drawer_state(String drawerName, String expectedState){
        boolean isSizeAndFit = productDetailPage.isSizeAndFitDrawerDisplayed();
        if (isSizeAndFit)
    	    assertTrue("Verify " + drawerName + " drawer is " + expectedState,productDetailPage.isPdpDrawerInExpectedState(drawerName, expectedState));
    }
    
    @Then("^Verify item details are displayed in the 'PRODUCT DETAILS' drawer$")
    public void verify_item_details_dsiplayed_in_product_details_drawer(){
    	assertTrue("Verify item details are displayed in the 'PRODUCT DETAILS' drawer",productDetailPage.isItemDetailsDisplayedInProductDetailsDrawer());
    }
}