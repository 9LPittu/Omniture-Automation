package com.jcrew.steps;

import com.jcrew.page.ProductDetailPage;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.Reporting;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductDetailPageSteps extends DriverFactory {

    private ProductDetailPage productDetailPage = new ProductDetailPage(getDriver());
    
    private Scenario scenario;
    private Reporting reporting = new Reporting();
    
    @Before
    public void getScenarioObject(Scenario s){
      this.scenario = s;
    }


    @Given("User is in product detail page")
    public void user_is_on_a_product_detail_page() throws InterruptedException {
        assertTrue("User should be in detail page",
                productDetailPage.isProductDetailPage());
    }

    @And("^A variation is selected$")
    public void a_variation_is_selected() throws Throwable {
        productDetailPage.select_variation();
    }

    @And("^A color is selected$")
    public void a_color_is_selected() throws Throwable {
        productDetailPage.select_color();
    }

    @And("^A size is selected$")
    public void a_size_is_selected() throws Throwable {
        productDetailPage.select_size();
    }

    @And("^Quantity is selected$")
    public void quantity_is_selected() throws Throwable {
        productDetailPage.select_quantity("2");
    }
    
    @Then("select a product from array page and select variation, color, size")
    public void select_a_product_from_array_page_and_select_variation_color_size() throws InterruptedException{
    	productDetailPage.selectProductWithVariationColorSize();
    	
    	reporting.takeScreenshot(scenario);
    }

    @And("^A wishlist button is present$")
    public void a_wishlist_button_is_present() throws Throwable {
        assertTrue("A wishlist button should be displayed",
                productDetailPage.isWishlistButtonPresent());
        
        reporting.takeScreenshot(scenario);
    }

    @When("^Add to cart button is pressed$")
    public void add_to_cart_button_is_pressed() throws Throwable {
        productDetailPage.click_add_to_cart();
        
        reporting.takeScreenshot(scenario);
    }

    @Then("^Bag should have (\\d+) item\\(s\\) added$")
    public void bag_should_have_item_s_added(int numberOfItems) throws Throwable {
        assertEquals("Number of Items should be the same",
                numberOfItems, productDetailPage.getNumberOfItemsInBag());

    }

    @Then("^A minicart modal should appear with message '([^\"]*)'$")
    public void a_minicart_modal_should_appear_with_message(String message) throws Throwable {
        assertEquals(message, productDetailPage.getMinicartMessage());
        
        reporting.takeScreenshot(scenario);
    }

    @Given("^Bag should have item\\(s\\) added$")
    public void bag_should_have_item_s_added() throws Throwable {
        assertTrue("It should contain at least one item", productDetailPage.getNumberOfItemsInBag() > 0);
        
        reporting.takeScreenshot(scenario);
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
        assertEquals("UPDATE BAG", productDetailPage.getBagButtonText());
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
        assertEquals("Expected message was not received", "ADDED TO WISHLIST", productDetailPage.getWishlistConfirmationMessage());
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
        assertEquals("Expected price is not the same", listPrice, productDetailPage.getProductPrice());
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

    @Given("^item bag is clicked$")
    public void item_bag_is_Clicked() throws Throwable {
        productDetailPage.click_item_bag();
        
        reporting.takeScreenshot(scenario);
    }
}
