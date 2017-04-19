package com.jcrew.steps.checkout;

import com.jcrew.page.checkout.CheckoutSummary;
import com.jcrew.pojo.Country;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.TestDataReader;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriverException;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ngarcia on 4/19/17.
 */
public class CheckoutSummarySteps extends DriverFactory {
    private CheckoutSummary summary = new CheckoutSummary(getDriver());
    private StateHolder stateHolder = StateHolder.getInstance();

    @Then("^Verify proper currency symbol for subtotal is displayed on bag page$")
    public void verify_subtotal_currency_sign_matches_context_on_bag_page() {
        Country country = stateHolder.get("context");
        String countryName = country.getName();

        String subtotal = summary.getSubtotal();
        assertTrue("Subtotal " + subtotal + " matches country context " + countryName,
                CurrencyChecker.isValid(subtotal));
    }

    @When("User adds a zip code ([^\"]*)")
    public void add_zip_code(String code) {
        summary.addZipCode(code);
    }

    @Then("Verify zipcode message says: ([^\"]*)")
    public void zip_message(String message) {
        String actual = summary.getZipCodeMessage();

        assertEquals("Expected zip code message", message, actual);
    }

    @Then("Verify bag has a order summary section")
    public void order_summary_section() {
        assertTrue("Bag has a order summary section", summary.isDisplayed());
    }

    @Then("Verify bag has a paypal button")
    public void paypal_button() {
        assertTrue("Bag has a paypal button", summary.payPalButton());
    }

    @Then("^Verify proper currency symbol for shipping is displayed on bag page$")
    public void verify_shipping_currency_sign_matches_context() {
        String shipping = summary.getEstimatedShipping();
        Country country = stateHolder.get("context");
        String countryName = country.getName();
        assertTrue("Shipping " + shipping + " matches country context " + countryName,
                CurrencyChecker.isValid(shipping));

    }

    @Then("^Verify proper currency symbol for total is displayed on bag page$")
    public void verify_total_currency_sign_matches_context() {
        String total = summary.getEstimatedTotal();
        Country country = stateHolder.get("context");
        String countryName = country.getName();
        assertTrue("Subtotal " + total + " matches country context " + countryName,
                CurrencyChecker.isValid(total));
    }

    @When("User fills zip code field with ([^\"]*)")
    public void zipcode_field(String zipcode) {
        summary.estimateTax(zipcode);
    }


    @Then("Verify estimated tax is populated")
    public void estimated_tax() {
        String estimatedTax = summary.getEstimatedTax();
        assertNotEquals("Estimated tax is populated", "- - - -", estimatedTax);
    }

    @Then("Verify estimated total sum")
    public void estimated_total_sum() {
        String estimatedTax = summary.getEstimatedTax();
        String estimatedShipping = summary.getEstimatedShipping();
        String subTotal = summary.getSubTotal();
        String estimatedTotal = summary.getEstimatedTotal();

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

    @Then("^Verify Order Subtotal is updated when item is removed$")
    public void verify_order_subtotal_when_item_removed(){
        String orderSubTotalBeforeDeletion = stateHolder.get("subtotal");

        orderSubTotalBeforeDeletion = orderSubTotalBeforeDeletion.replaceAll("[^0-9]", "");
        int subTotalBeforeDeletion = Integer.parseInt(orderSubTotalBeforeDeletion);

        String deletedItemPrice = stateHolder.get("deleteditemprice");
        deletedItemPrice = deletedItemPrice.replaceAll("[^0-9]", "");
        int itemPrice = Integer.parseInt(deletedItemPrice);

        String deletedItemQty = stateHolder.get("deleteditemqty");

        int qty = Integer.parseInt(deletedItemQty);

        String orderSubTotalAfterDeletion = summary.getSubTotal();
        orderSubTotalAfterDeletion = orderSubTotalAfterDeletion.replaceAll("[^0-9]", "");
        int subTotalAfterDeletion = Integer.parseInt(orderSubTotalAfterDeletion);

        assertEquals("Order subtotal is updated correctly when item is removed", (subTotalBeforeDeletion - (itemPrice * qty)), subTotalAfterDeletion);
    }

    @Then("^Verify Order Subtotal is updated when item quantity is changed$")
    public void verify_order_subtotal_when_item_changed(){

        int expectedOrderSubtotal = stateHolder.get("expectedOrderSubTotal");
        summary.logger.debug("Expected Order subtotal: {}", expectedOrderSubtotal);

        String currentOrderSubTotal = summary.getSubTotal();
        currentOrderSubTotal = currentOrderSubTotal.replaceAll("[^0-9]", "");
        int intCurrentOrderSubTotal = Integer.parseInt(currentOrderSubTotal);
        summary.logger.debug("Actual Order subtotal: {}", intCurrentOrderSubTotal);

        assertEquals("Order Subtotal is updated when item quantity is changed", expectedOrderSubtotal, intCurrentOrderSubTotal);
    }

    @Then("^Verify the estimated shipping is ([^\"]*)$")
    public void verify_estimated_shipping_value(String expectedShippingVal){
        String estimatedShipping = summary.getEstimatedShipping();
        estimatedShipping = estimatedShipping.replaceAll("[^.0-9]", "");
        assertEquals("Estimated shipping cost is different", expectedShippingVal, estimatedShipping);
    }

    @Then("^Verify no additional charges are applied for gift receipt$")
    public void verify_no_additional_charges_applied_for_gift_receipt(){
        String orderSubtotalBeforeGiftReceipt = stateHolder.get("subtotal");

        String orderSubtotalOnBilling = summary.getSubTotal().replaceAll("[^0-9\\.]", "");

        assertEquals("No additional charges should be applied for gift receipt on billing page", orderSubtotalBeforeGiftReceipt, orderSubtotalOnBilling);
    }

    @Then("Verify promo code applied (\\d+) percent from subtotal")
    public void applied_promo(int discount) {
        String subtotal = summary.getSubTotal();
        subtotal = subtotal.replaceAll("[^0-9]", "");

        String promo = summary.getPromoDiscount();
        promo = promo.replaceAll("[^0-9]", "");
        int promoInt = Integer.parseInt(promo) * 10;

        double subtotalFloat = Integer.parseInt(subtotal) * (discount / 100.00);
        subtotalFloat = round(subtotalFloat);
        double promoFloat = promoInt / 10.00;

        assertEquals("Promo was applied correctly", (int) subtotalFloat, (int) promoFloat);
    }

    private double round(double doubleNumber) {
        double decimals = doubleNumber - (int) doubleNumber;
        double integer = doubleNumber - decimals;

        return decimals >= .60? integer + 1 : integer;
    }

    @And("^Verify promo message is updated in the summary section$")
    public void promo_message_updated_in_summary_section(){
        assertTrue("Promo message is updated in the order summary section after promo code is applied",
                summary.getPromoMessageElementFromOrderSummary().isDisplayed());
    }

    @And("^Verify order total is calculated correctly after promo is applied$")
    public void verify_order_total_calculated_correctly_after_promo_applied(){
        String promoCode = stateHolder.get("promocode");
        promoCode = promoCode.toLowerCase();

        String orderSubTotal = stateHolder.get("subtotal");
        Double orderSubTotalDblVal = Double.valueOf(orderSubTotal);

        Double promoDiscountedAmount = 0.0;
        if(stateHolder.hasKey("promoDiscountedAmount")){
            promoDiscountedAmount = stateHolder.get("promoDiscountedAmount");
        }

        promoDiscountedAmount += getPromoDiscountedAmount(orderSubTotalDblVal, promoCode);
        stateHolder.put("promoDiscountedAmount", promoDiscountedAmount);

        String price = stateHolder.get("shippingCost");
        price = price.replaceAll("[^0-9.]", "");
        Double shippingMethodPrice = Double.valueOf(price);

        Double expectedOrderTotal = orderSubTotalDblVal - promoDiscountedAmount + shippingMethodPrice;

        DecimalFormat df = new DecimalFormat(".##");
        df.setRoundingMode(RoundingMode.FLOOR);
        expectedOrderTotal = Double.valueOf(df.format(expectedOrderTotal));

        Double actualOrderTotal = Double.valueOf(summary.getEstimatedTotal().replaceAll("[^0-9.]", ""));

        assertEquals("Order total is not calculated correctly", expectedOrderTotal, actualOrderTotal);
    }

    private Double getPromoDiscountedAmount(Double orderSubtotal, String promoCode){

        Double promoDiscountedAmount = 0.0;
        Double percentage;
        Double freeShippingThresholdAmt;

        DecimalFormat df = new DecimalFormat(".###");
        df.setRoundingMode(RoundingMode.HALF_DOWN);

        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        switch(promoCode){
            case "stack10p":
            case "test-10p":
                percentage = Double.valueOf(testDataReader.getData(promoCode + ".percentage"));
                promoDiscountedAmount = Double.valueOf(df.format(orderSubtotal * (percentage/100)));
                break;
            case "stack-fs-50":
                freeShippingThresholdAmt = Double.valueOf(testDataReader.getData(promoCode + ".percentage"));
                if(orderSubtotal > freeShippingThresholdAmt){
                    stateHolder.put("shippingCost", "0");
                }
                break;
            case "test-15pf-fs":
                percentage = Double.valueOf(testDataReader.getData(promoCode + ".percentage"));
                promoDiscountedAmount = Double.valueOf(df.format(orderSubtotal * (percentage/100)));
                stateHolder.put("shippingCost", "0");
                break;
            default:
                throw new WebDriverException(promoCode + " is not recognized!");
        }

        return promoDiscountedAmount;
    }
}
