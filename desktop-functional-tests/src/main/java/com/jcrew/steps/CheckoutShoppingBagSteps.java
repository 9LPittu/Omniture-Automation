package com.jcrew.steps;

import com.google.common.collect.Lists;
import com.jcrew.page.CheckoutShoppingBag;
import com.jcrew.page.Footer;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutShoppingBagSteps extends DriverFactory {
    private CheckoutShoppingBag bag = new CheckoutShoppingBag(getDriver());
    private StateHolder stateHolder = StateHolder.getInstance();

    @Then("Verify shopping bag is displayed")
    public void is_displayed() {
        assertTrue("Shopping bag is displayed", bag.isDisplayed());
        
        String subTotal = "0";
        try {
            subTotal = bag.getSubTotal();
        } catch (NoSuchElementException noSubtotal) {
            bag.logger.error("Bag has no subtotal");
        }

        stateHolder.put("ordersubtotal", subTotal);
    }

    @Then("Verify products added matches with products in bag")
    public void products_matches() {
        if(stateHolder.hasKey("toBag")) {
            List<Product> expectedList = stateHolder.getList("toBag");
            check_items(expectedList);
        }
    }

    private void check_items(List<Product> expectedList) {
        expectedList = Lists.reverse(expectedList);
        List<Product> actualList = bag.getProducts();

        assertEquals("Same numer of products expected", expectedList.size(), actualList.size());

        for (int i = 0; i < expectedList.size(); i++) {
            Product expected = expectedList.get(i);
            Product actual = expectedList.get(i);

            assertEquals("Same item number", expected.getItemNumber(), actual.getItemNumber());
            assertEquals("Same name for item number " + expected.getItemNumber(),
                    expected.getName().toLowerCase(), actual.getName().toLowerCase());
            assertEquals("Same color for item number " + expected.getItemNumber(),
                    expected.getColor().toLowerCase(), expected.getColor().toLowerCase());
            assertEquals("Same size for item number " + actual.getItemNumber(),
                    expected.getSize().toLowerCase(), expected.getSize().toLowerCase());
            assertEquals("Same quantity for item number " + expected.getItemNumber(),
                    expected.getQuantity(), actual.getQuantity());
        }
    }
    
    @Then("^Verify gift cards added matches with gift cards in bag$")
    public void gift_cards_matches() {
        assertTrue("Same gift cards are in bag", bag.giftCardsInBag());
    }

    @Then("Verify all products have edit and remove buttons")
    public void edit_and_remove_buttons() {
        assertTrue("All productus have edit and remove buttons", bag.itemsButtons());
    }

    @Then("Verify bag has a gift card section")
    public void gift_card_section() {
        assertTrue("Bag has a gift card section", bag.giftCard());
    }

    @Then("Verify bag has a order summary section")
    public void order_summary_section() {
        assertTrue("Bag has a order summary section", bag.summary());
    }

    @Then("Verify bag has a paypal button")
    public void paypal_button() {
        assertTrue("Bag has a paypal button", bag.payPalButton());
    }

    @Then("Verify bag has a help section with phone ([^\"]*) for questions")
    public void help_section(String phone) {
        assertTrue("Bag has a help section", bag.help());

        String phonePage = bag.getQuestionsPhone();
        assertEquals("Phone number for questions matches", phone, phonePage);
    }

    @When("User fills zip code field with ([^\"]*)")
    public void zipcode_field(String zipcode) {
        bag.estimateTax(zipcode);
    }

    @Then("Verify estimated tax is populated")
    public void estimated_tax() {
        String estimatedTax = bag.getEstimatedTax();
        assertNotEquals("Estimated tax is populated", "- - - -", estimatedTax);
    }

    @Then("Verify estimated total sum")
    public void estimated_total_sum() {
        String estimatedTax = bag.getEstimatedTax();
        String estimatedShipping = bag.getEstimatedShipping();
        String subTotal = bag.getSubTotal();
        String estimatedTotal = bag.getEstimatedTotal();

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

    @When("User clicks in CHECK OUT NOW button")
    public void check_out_now() {
    	
    	String subTotal = bag.getSubtotalValue().trim();
        subTotal=subTotal.replaceAll("[^0-9\\.]", "");
        stateHolder.put("subtotal",subTotal);
        
        stateHolder.put("total", bag.getTotalValue());
        
        bag.checkOutNow();
    }

    @Then("Verify that title is Shopping Bag")
    public void verify_title() {
        String title = bag.getTitle();

        assertTrue("Title is Shopping Bag", "Shopping Bag".equalsIgnoreCase(title));
    }

    @Then("Verify monogram products are unavailable in bag")
    public void monogram_unavailable() {
        List<Product> expected = stateHolder.getList("toBag");
        expected = Lists.reverse(expected);
        List<Product> actual = bag.getUnavailableItems();

        for (int i = 0; i < expected.size(); i++) {
            Product p = expected.get(i);
            if (p.getHasMonogram()) {
                assertEquals("Item is unavailable", p.getName().toLowerCase(), actual.get(i).getName().toLowerCase());
            }
        }
    }

    @Then("Verify items quantity and prices")
    public void items_times_quantity() {
		List<Product> products = stateHolder.getList("toBag");
        products = Lists.reverse(products);
        
        int expectedOrderSubTotal = 0;
        for(int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            String itemTotal = bag.getItemTotal(i);
            int total = Integer.parseInt(itemTotal);
            int qty = Integer.parseInt(p.getQuantity());
            String itemPrice = p.getPrice();
            itemPrice = itemPrice.replaceAll("[^0-9]", "");
            int price = Integer.parseInt(itemPrice);

            assertEquals("Item price of item " + i + " times quantity matches total", qty * price, total);
            expectedOrderSubTotal += qty * price;
        }
        
        stateHolder.put("expectedOrderSubTotal", expectedOrderSubTotal);
    }

    @When("User removes first item from bag")
    public void remove_first_item_from_bag() {
        bag.removeItem(0);
    }

    @When("User edits quantity of first item from bag")
    public void edit_quantity_first_item() {
        bag.editQuantity(0);
    }

    @When("User edits last item from bag")
    public void edit_last_item_from_bag() {
        bag.editItem(-1);
    }
    
    @Then("^Verify Order Subtotal is updated when item is removed$")
    public void verify_order_subtotal_when_item_removed(){
    	String orderSubTotalBeforeDeletion = stateHolder.get("ordersubtotal");
    	orderSubTotalBeforeDeletion = orderSubTotalBeforeDeletion.replaceAll("[^0-9]", "");
    	int subTotalBeforeDeletion = Integer.parseInt(orderSubTotalBeforeDeletion);
    	
    	String deletedItemPrice = stateHolder.get("deleteditemprice");
    	deletedItemPrice = deletedItemPrice.replaceAll("[^0-9]", "");
    	int itemPrice = Integer.parseInt(deletedItemPrice);
    	
    	String deletedItemQty = stateHolder.get("deleteditemqty");
    	int qty = Integer.parseInt(deletedItemQty);
    	
    	String orderSubTotalAfterDeletion = bag.getSubTotal();
    	orderSubTotalAfterDeletion = orderSubTotalAfterDeletion.replaceAll("[^0-9]", "");
    	int subTotalAfterDeletion = Integer.parseInt(orderSubTotalAfterDeletion);
    	
    	assertEquals("Order subtotal is updated correctly when item is removed", (subTotalBeforeDeletion - (itemPrice * qty)), subTotalAfterDeletion);
    }
    
    @Then("^Verify Order Subtotal is updated when item quantity is changed$")
    public void verify_order_subtotal_when_item_changed(){

    	int expectedOrderSubtotal = stateHolder.get("expectedOrderSubTotal");
    	bag.logger.debug("Expected Order subtotal: {}", expectedOrderSubtotal);
    	
    	String currentOrderSubTotal = bag.getSubTotal();
    	currentOrderSubTotal = currentOrderSubTotal.replaceAll("[^0-9]", "");
    	int intCurrentOrderSubTotal = Integer.parseInt(currentOrderSubTotal);
    	bag.logger.debug("Actual Order subtotal: {}", intCurrentOrderSubTotal);
    	
    	assertEquals("Order Subtotal is updated when item quantity is changed", expectedOrderSubtotal, intCurrentOrderSubTotal);
    }
    
    @Then("^Verify edited item is displayed first in shopping bag$")
    public void verify_edited_item_displayed_first_in_shopping_bag(){
    	assertTrue("Edited item should be displayed first in the shopping bag", bag.isEditedItemDisplayedFirst());
    }
    
    @Then("Verify previously added item is not shown in bag page")
    public void previously_added_items_not_shown() {
    	List<Product> previousProducts = stateHolder.get("userBag");
    	Product previousProduct = previousProducts.get(0);
    	String previousItemCode = previousProduct.getItemNumber();    	
    	List<Product> expected = deleteProductFromStateHolder(previousItemCode);
    	   	
        check_items(expected);
    }
    
    @When("^User edits first added item from bag$")
    public void user_edit_first_item_from_bag(){
    	
    	List<WebElement> editButtons = bag.getItemEditElements();
    	
    	WebElement lastEditButton = editButtons.get(editButtons.size()-1);    	
    	String editedItemCode = bag.getItemCode(lastEditButton);
    	
    	List<Product> bagProducts = deleteProductFromStateHolder(editedItemCode);    	
    	stateHolder.put("toBag",bagProducts);
    	
    	lastEditButton.click();
    	Util.waitLoadingBar(getDriver());
    }
    
    private List<Product> deleteProductFromStateHolder(String expectedItemCode){
    	List<Product> bagProducts = stateHolder.get("toBag");
    	for(int i=0;i<bagProducts.size();i++){
    		Product bagProduct = bagProducts.get(i);
    		
    		String itemCode = bagProduct.getItemNumber();
    		
    		if(expectedItemCode.equalsIgnoreCase(itemCode)){
    			bagProducts.remove(i);
    		}
    	}
    	
    	return bagProducts;
    }
    
    @Then("Verify user is in shopping bag page$")
    public void verify_user_is_in_bag_page() {
        assertTrue("User should be in shopping bag page", bag.isDisplayed());
    }

    @When("User clicks check out button")
    public void user_clicks_check_out_button() {
    	bag.checkOutNow();
    }

    @Then("Verify that shopping bag has expected context")
    public void verify_that_shopping_bag_has_expected_context() {
        Footer footer = new Footer(getDriver());
        String countryFooter = footer.getCountry();

        StateHolder stateHolder = StateHolder.getInstance();
        Country c = stateHolder.get("context");

        assertEquals("Shopping bag has the expected context",
                countryFooter.toLowerCase(), c.getName().toLowerCase());
    }
    
    @Then("^Verify gift card details in shopping bag page$")
    public void verify_gift_card_details(){
    	String expectedSenderName = stateHolder.get("giftCardSenderName");
    	String expectedRecipientName = stateHolder.get("giftCardRecipientName");
    	String expectedRecipientEmail = stateHolder.get("giftCardRecipientEmail");
    	   	
    	assertEquals("Same gift card sender name", expectedSenderName.toLowerCase(), bag.getEgiftCardSenderName().toLowerCase());
    	assertEquals("Same gift card recipient name", expectedRecipientName.toLowerCase(), bag.getEgiftCardRecipientName().toLowerCase());
    	assertEquals("Same gift card recipient email", expectedRecipientEmail.toLowerCase(), bag.getEgiftCardRecipientEmailAddress().toLowerCase());
    }
    

    @Then("^Verify the estimated shipping is ([^\"]*)$")
    public void verify_estimated_shipping_value(String expectedShippingVal){
    	String estimatedShipping = bag.getEstimatedShipping();       
        estimatedShipping = estimatedShipping.replaceAll("[^.0-9]", "");
        assertEquals("Estimated shipping cost is different", expectedShippingVal, estimatedShipping);
    }
    
   
}