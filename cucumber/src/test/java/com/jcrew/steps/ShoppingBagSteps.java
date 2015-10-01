package com.jcrew.steps;

import com.jcrew.page.ShoppingBagPage;
import com.jcrew.pojo.Product;
import com.jcrew.util.DriverFactory;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingBagSteps extends DriverFactory {


    private final ShoppingBagPage shoppingBagPage = new ShoppingBagPage(getDriver());
    private StateHolder stateHolder = StateHolder.getInstance();


    @When("^Clicks on checkout$")
    public void clicks_on_checkout() throws Throwable {
        shoppingBagPage.click_checkout_button();
    }

    @Then("^User should be in shopping bag page$")
    public void user_should_be_in_shopping_bag_page() throws Throwable {
        assertTrue("Article checkout should have been present",
                shoppingBagPage.isArticleCheckoutPresent());
    }

    @And("^Verifies edit button is present$")
    public void verifies_edit_button_is_present() throws Throwable {
        assertTrue(shoppingBagPage.isEditButtonPresent());
    }

    @And("^Verifies remove button is present$")
    public void verifies_remove_button_is_present() throws Throwable {
        assertTrue(shoppingBagPage.isRemoveButtonPresent());
    }

    @And("^Verifies that total amount and subtotal values are similar$")
    public void verifies_that_total_amount_and_subtotal_values_are_similar() throws Throwable {
        String totalAmount = shoppingBagPage.getTotalAmountPage();
        String subtotalValue = shoppingBagPage.getSubtotalValue();
        assertEquals("Total Amount and subtotal should be the same", totalAmount, subtotalValue);
    }

    @Given("^Verifies that total amount and subtotal values are numbers$")
    public void Verifies_that_total_amount_and_subtotal_values_are_numbers() throws Throwable {
        String totalAmount = shoppingBagPage.getTotalAmountPage();
        String subtotalValue = shoppingBagPage.getSubtotalValue();
        Pattern totalAmountPattern = Pattern.compile("^\\$\\d(\\d)?\\.\\d\\d$");
        Pattern subtotalAmountPattern = Pattern.compile("^\\$\\d{1,3}(,\\d{3}|\\d)?\\.\\d\\d$");

        Matcher totalAmountMatch = totalAmountPattern.matcher(totalAmount);
        Matcher subtotalAmountMatch = subtotalAmountPattern.matcher(subtotalValue);

        assertTrue("Total amount should be a price value", totalAmountMatch.matches());
        assertTrue("Subtotal amount should be a price value", subtotalAmountMatch.matches());

    }

    @And("^Clicks edit button on item bag page$")
    public void clicks_edit_button_on_item_bag_page() throws Throwable {
        shoppingBagPage.click_edit_button();
    }

    @Then("^Verify color ([^\"]*) is displayed in shopping bag$")
    public void verify_color_is_displayed_in_shopping_bag(String productColor) throws Throwable {
        assertTrue(productColor + " color should have been displayed",
                shoppingBagPage.isProductColorDisplayed(productColor));
    }

    @And("^Verify size ([^\"]*) is displayed in shopping bag$")
    public void verify_size_is_displayed_in_shopping_bag(String productSize) throws Throwable {
        assertTrue(productSize + " color should have been displayed",
                shoppingBagPage.isProductSizeDisplayed(productSize));
    }

    @And("^Verify ([^\"]*) items are specified as quantity in shopping bag$")
    public void verify_items_are_specified_as_quantity(String productQuantity) throws Throwable {
        assertEquals("Product Quantity should be the same", productQuantity,
                shoppingBagPage.getItemQuantity());
    }

    @And("^Verify product name is the same as the one displayed in pdp page$")
    public void verify_product_name_is_the_same_as_the_one_displayed_in_pdp_page() throws Throwable {
        Product product = Util.getCurrentProduct();
        String productName = product.getProductName().toUpperCase();
        assertTrue(productName + " product is not the one displayed " + shoppingBagPage.getProductName(),
                shoppingBagPage.getProductName().endsWith(productName));

    }

    @And("^Verify color is the one selected from pdp page$")
    public void verify_color_is_the_one_selected_from_pdp_page() throws Throwable {
        Product product = Util.getCurrentProduct();
        String expectedColor = product.getSelectedColor();
        assertTrue(expectedColor + " color is not displayed", shoppingBagPage.isProductColorDisplayed(expectedColor));
    }

    @And("^Verify size is the one selected from pdp page$")
    public void verify_size_is_the_one_selected_from_pdp_page() throws Throwable {
        Product product = Util.getCurrentProduct();
        String expectedSize = product.getSelectedSize();
        assertTrue(expectedSize + " size is not displayed", shoppingBagPage.isProductSizeDisplayed(expectedSize));
    }

    @And("^Verify price is the same as the one displayed in pdp page$")
    public void verify_price_is_the_same_as_the_one_displayed_in_pdp_page() throws Throwable {
        Product product = Util.getCurrentProduct();
        String price = product.getPriceList() == null ? product.getPriceSale() : product.getPriceList();
        assertEquals("Price should be the same", price, shoppingBagPage.getSubtotalValue());
    }

    @And("^Verify variation is the one selected from pdp page$")
    public void verify_variation_is_the_one_selected_from_pdp_page() throws Throwable {
        Product product = Util.getCurrentProduct();
        String variation = product.getSelectedVariation();
        if (variation != null) {
            String prefix = variation.toUpperCase();
            if (!"REGULAR".equalsIgnoreCase(prefix)) {
                assertTrue(prefix + " variation should have been displayed as part of the product name:",
                        shoppingBagPage.getProductName().startsWith(prefix));
            }
        }
    }

    @Then("^Verify all selected products are displayed$")
    public void verify_all_selected_products_are_displayed() throws Throwable {
        final List<Product> productList = (List<Product>) stateHolder.get("productList");
        for (Product product : productList) {
            assertTrue(product.getSelectedColor() + " color is not displayed for product " + product.getProductName(),
                    shoppingBagPage.isColorDisplayedForProduct(product.getProductName(), product.getSelectedColor()));
            assertTrue(product.getSelectedSize() + " size is not displayed for product " + product.getProductName(),
                    shoppingBagPage.isSizeDisplayedForProduct(product.getProductName(), product.getSelectedSize()));
        }
    }
}
