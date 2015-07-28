package com.jcrew.steps;

import com.jcrew.page.ProductDetailPage;
import com.jcrew.util.DriverFactory;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductDetailPageSteps extends DriverFactory {

    private ProductDetailPage productDetailPage = new ProductDetailPage(driver);


    @Given("User is on a product detail page")
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

    @Then("^A minicart modal should appear$")
    public void a_minicart_modal_should_appear() throws Throwable {
        assertTrue("Minicart should appear", productDetailPage.isMinicartDisplayed());
    }
}
