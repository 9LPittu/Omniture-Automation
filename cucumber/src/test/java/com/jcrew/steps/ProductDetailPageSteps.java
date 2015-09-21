package com.jcrew.steps;

import com.jcrew.page.ProductDetailPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductDetailPageSteps extends DriverFactory {

    private ProductDetailPage productDetailPage = new ProductDetailPage(getDriver());


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

    @And("^A wishlist button is present$")
    public void a_wishlist_button_is_present() throws Throwable {
        assertTrue("A wishlist button should be displayed",
                productDetailPage.isWishlistButtonPresent());
    }

    @When("^Add to cart button is pressed$")
    public void add_to_cart_button_is_pressed() throws Throwable {
        productDetailPage.click_add_to_cart();
    }

    @Then("^Bag should have (\\d+) item\\(s\\) added$")
    public void bag_should_have_item_s_added(int numberOfItems) throws Throwable {
        assertEquals("Number of Items should be the same",
                numberOfItems, productDetailPage.getNumberOfItemsInBag());

    }

    @Then("^A minicart modal should appear with message '([^\"]*)'$")
    public void a_minicart_modal_should_appear_with_message(String message) throws Throwable {
        assertEquals(message, productDetailPage.getMinicartMessage());
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
        assertEquals("UPDATE BAG", productDetailPage.getAddToOrUpdateBagButtonText());
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
}
