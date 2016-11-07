package com.jcrew.steps;

import com.google.common.collect.Lists;
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

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class ShoppingBagSteps extends DriverFactory {


    private final ShoppingBagPage shoppingBagPage = new ShoppingBagPage(getDriver());
    private final StateHolder stateHolder = StateHolder.getInstance();


    @When("^Clicks on checkout$")
    public void clicks_on_checkout() throws Throwable {
        //add sub total value to stateHolder before starting checkout
        String subTotal = shoppingBagPage.getSubtotalValue().trim();
        subTotal=subTotal.replaceAll("[^0-9\\.]", "");
        stateHolder.put("subtotal",subTotal);

        shoppingBagPage.click_checkout_button();
    }

    @Then("^User should be in shopping bag page$")
    public void user_should_be_in_shopping_bag_page() throws Throwable {
        assertTrue(Util.getSelectedCountryName() + "Article checkout should have been present",
                shoppingBagPage.isArticleCheckoutPresent());
        
        try{
        	String subTotal = shoppingBagPage.getSubtotalValue().trim();
        	subTotal=subTotal.replaceAll("[^0-9\\.]", "");
        	stateHolder.put("subtotal",subTotal);
        }
        catch(NoSuchElementException nsee){
        	shoppingBagPage.logger.error("Exception is thrown in capturing subtotal value");
        }

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
        Pattern pricePattern = Pattern.compile("^\\$\\d{1,3}(,\\d{3}|\\d)?\\.\\d\\d$");

        Matcher totalAmountMatch = pricePattern.matcher(totalAmount);
        Matcher subtotalAmountMatch = pricePattern.matcher(subtotalValue);

        assertTrue("Total amount should be a price value: " + totalAmount, totalAmountMatch.matches());
        assertTrue("Subtotal amount should be a price value: " + subtotalValue, subtotalAmountMatch.matches());
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
        assertVariation(variation);
    }

    @Then("^Verify all selected products are displayed$")
    public void verify_all_selected_products_are_displayed() throws Throwable {
        final List<Product> productList = (List<Product>) stateHolder.get("productList");
        for (Product product : productList) {
            assertTrue(product.getSelectedColor() + " color is not displayed for product " + product.getProductName(),
                    shoppingBagPage.isColorDisplayedForProduct(product.getProductName(), product.getSelectedColor()));
            assertTrue(product.getSelectedSize() + " size is not displayed for product " + product.getProductName(),
                    shoppingBagPage.isSizeDisplayedForProduct(product.getProductName(), product.getSelectedSize()));

            String price = product.getPriceList() == null ? product.getPriceSale() : product.getPriceList();
            price = price.replaceAll("now", "").trim();
            assertEquals("Price should be the same", price, shoppingBagPage.getPriceDisplayedForProduct(product.getProductName()));
            String variation = product.getSelectedVariation();
            assertVariation(variation);
        }
    }

    private void assertVariation(String variation) {
        if (variation != null) {
            String prefix = variation.toUpperCase();
            if (!"REGULAR".equalsIgnoreCase(prefix)) {
                assertTrue(prefix + " variation should have been displayed as part of the product name:",
                        shoppingBagPage.getProductName().startsWith(prefix));
            }
        }
    }
    
    @Then("^page title should contain \"([^\"]*)\"$")
    public void verify_page_title(String pageTitle){
    	assertTrue("Page title should contain " + pageTitle, shoppingBagPage.isPageTitleContains(pageTitle));
    }
    
    @And("^items count should be displayed as (\\d+) in the bag$")
    public void verify_bag_items_count(int itemsCount){
    	assertTrue("Items count in the bag should be displayed as " + itemsCount,shoppingBagPage.isBagItemsCountMatches(itemsCount));
    }
    
    @And("^Breadcrumb should display ([^\"]*)$")
    public void verify_breadcrumb_text(String text){
    	assertTrue("Breadcrumb should display the text as " + text, shoppingBagPage.isBreadcrumbDisplayed(text));
    }
    
    @And("^in shopping bag page, user should see the color selected on the PDP page$")
    public void user_should_see_color_selected_on_pdp_in_shopping_bag(){
    	assertTrue("User should see the color selected on the PDP page in the shopping bag",shoppingBagPage.isPDPPageColorDisplayedInShoppingBag());
    }

    @And("^in shopping bag page, user should see the size selected on the PDP page$")
    public void user_should_see_size_selected_on_pdp_in_shopping_bag(){
    	assertTrue("User should see the size selected on the PDP page in the shopping bag",shoppingBagPage.isPDPPageSizeDisplayedInShoppingBag());
    }
    
    @And("^Verify proper currency symbol is displayed on ([^\"]*) section on Checkout page$")
    public void verify_currency_on_checkout_pages_section(String sectionName) {
        assertTrue(Util.getSelectedCountryName() + "Currency on product details page", shoppingBagPage.isCorrectCurrencySymbol(sectionName.toLowerCase()));
    }


    @Then("^make sure that subtotal is less than creditcard threshold$")
        public void make_sure_that_subtotal_is_less_than_creditcard_threshold() {
        shoppingBagPage.applyCreditCardThreshold();
    }
    
    @Then("Verify products added matches with products in bag")
    public void product_matches_in_bag(){
        assertTrue("Products in the bag don't match with the ones which are added", shoppingBagPage.itemsInBag());
    }
    
    @When("User edits last item from bag")
    public void edit_last_item_from_bag() {
    	shoppingBagPage.editItem(-1);
    }
    
    @When("User edits quantity of first item from bag")
    public void edit_quantity_first_item() {
    	shoppingBagPage.editQuantity(0);
    }
    
    @Then("^Verify edited item is displayed first in shopping bag$")
    public void verify_edited_item_displayed_first_in_shopping_bag(){
    	assertTrue("Edited item should be displayed first in the shopping bag", shoppingBagPage.isEditedItemDisplayedFirst());
    }
    
    @Then("^Verify Order Subtotal is updated when item is removed$")
    public void verify_order_subtotal_when_item_removed(){
    	String orderSubTotalBeforeDeletion = (String) shoppingBagPage.stateHolder.get("subtotal");
    	orderSubTotalBeforeDeletion = orderSubTotalBeforeDeletion.replaceAll("[^0-9]", "");
    	int subTotalBeforeDeletion = Integer.parseInt(orderSubTotalBeforeDeletion);
    	
    	String deletedItemPrice = (String) shoppingBagPage.stateHolder.get("deleteditemprice");
    	deletedItemPrice = deletedItemPrice.replaceAll("[^0-9]", "");
    	int itemPrice = Integer.parseInt(deletedItemPrice);
    	
    	String deletedItemQty = (String) shoppingBagPage.stateHolder.get("deleteditemqty");
    	int qty = Integer.parseInt(deletedItemQty);
    	
    	String orderSubTotalAfterDeletion = shoppingBagPage.getSubTotal();
    	orderSubTotalAfterDeletion = orderSubTotalAfterDeletion.replaceAll("[^0-9]", "");
    	int subTotalAfterDeletion = Integer.parseInt(orderSubTotalAfterDeletion);
    	
    	assertEquals("Order subtotal is updated correctly when item is removed", (subTotalBeforeDeletion - (itemPrice * qty)), subTotalAfterDeletion);
    }
    
    @Then("^Verify Order Subtotal is updated when item quantity is changed$")
    public void verify_order_subtotal_when_item_changed(){

    	int expectedOrderSubtotal = (int) shoppingBagPage.stateHolder.get("expectedOrderSubTotal");
    	shoppingBagPage.logger.debug("Expected Order subtotal: {}", expectedOrderSubtotal);
    	
    	String currentOrderSubTotal = shoppingBagPage.getSubTotal();
    	currentOrderSubTotal = currentOrderSubTotal.replaceAll("[^0-9]", "");
    	int intCurrentOrderSubTotal = Integer.parseInt(currentOrderSubTotal);
    	shoppingBagPage.logger.debug("Actual Order subtotal: {}", intCurrentOrderSubTotal);
    	
    	assertEquals("Order Subtotal is updated when item quantity is changed", expectedOrderSubtotal, intCurrentOrderSubTotal);
    }
    
    @Then("Verify all products have edit and remove buttons")
    public void edit_and_remove_buttons() {
        assertTrue("All productus have edit and remove buttons", shoppingBagPage.itemsButtons());
    }

    @Then("Verify bag has a promo code section")
    public void promo_code_section() {
        assertTrue("Bag has a promo code section", shoppingBagPage.promoSection());
    }

    @Then("Verify bag has a gift card section")
    public void gift_card_section() {
        assertTrue("Bag has a gift card section", shoppingBagPage.giftCard());
    }

    @Then("Verify bag has a order summary section")
    public void order_summary_section() {
        assertTrue("Bag has a order summary section", shoppingBagPage.summary());
    }

    @Then("Verify bag has a paypal button")
    public void paypal_button() {
        assertTrue("Bag has a paypal button", shoppingBagPage.payPalButton());
    }

    @Then("Verify bag has a help section with phone ([^\"]*) for questions")
    public void help_section(String phone) {
        assertTrue("Bag has a help section", shoppingBagPage.help());

        String phonePage = shoppingBagPage.getQuestionsPhone();
        assertEquals("Phone number for questions matches", phone, phonePage);
    }

    @When("User fills zip code field with ([^\"]*)")
    public void zipcode_field(String zipcode) {
    	shoppingBagPage.estimateTax(zipcode);
    }

    @Then("Verify estimated tax is populated")
    public void estimated_tax() {
        String estimatedTax = shoppingBagPage.getEstimatedTax();
        assertNotEquals("Estimated tax is populated", "- - - -", estimatedTax);
    }

    @Then("Verify estimated total sum")
    public void estimated_total_sum() {
        String estimatedTax = shoppingBagPage.getEstimatedTax();
        String estimatedShipping = shoppingBagPage.getEstimatedShipping();
        String subTotal = shoppingBagPage.getSubTotal();
        String estimatedTotal = shoppingBagPage.getEstimatedTotal();

        estimatedTax = estimatedTax.replaceAll("[^0-9]", "");
        estimatedShipping = estimatedShipping.replaceAll("[^0-9]", "");
        subTotal = subTotal.replaceAll("[^0-9]", "");
        estimatedTotal = estimatedTotal.replaceAll("[^0-9]", "");

        int estimatedTaxInt = Integer.parseInt(estimatedTax);
        
        int estimatedShippingInt = 0;
        if(!estimatedShipping.isEmpty()){
        	estimatedShippingInt = Integer.parseInt(estimatedShipping);
        }
        
        int subTotalInt = Integer.parseInt(subTotal);
        int estimatedTotalInt = Integer.parseInt(estimatedTotal);

        assertEquals("Estimated Total sum matches", estimatedTaxInt + estimatedShippingInt + subTotalInt, estimatedTotalInt);
    }
    
    @When("User removes first item from bag")
    public void remove_first_item_from_bag() {
    	shoppingBagPage.removeItem(0);
    }
    
    @Then("Verify items quantity and prices")
    public void items_times_quantity() {
		List<Product> products = shoppingBagPage.stateHolder.getList("toBag");
        products = Lists.reverse(products);
        
        int expectedOrderSubTotal = 0;
        for(int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            String itemTotal = shoppingBagPage.getItemTotal(i);
            int total = Integer.parseInt(itemTotal);
            int qty = Integer.parseInt(p.getQuantity());
            String itemPrice = p.getPriceList();
            itemPrice = itemPrice.replaceAll("[^0-9]", "");
            int price = Integer.parseInt(itemPrice);

            assertEquals("Item price of item " + i + " times quantity matches total", qty * price, total);
            expectedOrderSubTotal += qty * price;
        }
        
        shoppingBagPage.stateHolder.put("expectedOrderSubTotal", expectedOrderSubTotal);
    }
    
    @When("^User edits first added item from bag$")
    public void user_edit_first_item_from_bag(){
    	
    	List<WebElement> editButtons = shoppingBagPage.getItemEditElements();
    	
    	WebElement lastEditButton = editButtons.get(editButtons.size()-1);    	
    	String editedItemCode = shoppingBagPage.getItemCode(lastEditButton);
    	
    	List<Product> bagProducts = deleteProductFromStateHolder(editedItemCode);    	
    	shoppingBagPage.stateHolder.put("toBag",bagProducts);
    	
    	lastEditButton.click();
    	Util.waitLoadingBar(getDriver());
    }
    
    private List<Product> deleteProductFromStateHolder(String expectedItemCode){
    	@SuppressWarnings("unchecked")
    	List<Product> bagProducts = (List<Product>) shoppingBagPage.stateHolder.get("toBag");
    	for(int i=0;i<bagProducts.size();i++){
    		Product bagProduct = bagProducts.get(i);
    		
    		String itemCode = bagProduct.getProductCode();
    		
    		if(expectedItemCode.equalsIgnoreCase(itemCode)){
    			bagProducts.remove(i);
    		}
    	}
    	
    	return bagProducts;
    }
    
    @Then("Verify previously added item is not shown in bag page")
    public void previously_added_items_not_shown() {
    	
    	@SuppressWarnings("unchecked")
    	List<Product> previousProducts = (List<Product>) shoppingBagPage.stateHolder.get("userBag");
    	Product previousProduct = previousProducts.get(0);
    	String previousItemCode = previousProduct.getProductCode();    	
    	List<Product> bagProducts = deleteProductFromStateHolder(previousItemCode);
    	   	
        assertTrue("Previously added items should not be shown", shoppingBagPage.matchList(bagProducts));
    }
}