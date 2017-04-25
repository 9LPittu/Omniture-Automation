package com.jcrew.page.checkout;

import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 4/19/17.
 */
public class CheckoutSummary extends Checkout {

    @FindBy(id = "order-summary")
    private WebElement order_summary;
    @FindBy(id = "orderSummaryContainer")
    protected WebElement orderSummary;
    @FindBy(id="zipcode")
    private WebElement zipCode;

    public CheckoutSummary(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(order_summary));
    }

    @Override
    public boolean isDisplayed() {
        return order_summary.isDisplayed();
    }

    public float getOrderTotal() {
        String total = getEstimatedTotal();

        float fTotal = CurrencyChecker.getPrice(total, country);

        logger.info("Order total is {}", fTotal);
        return fTotal;

    }

    public String getEstimatedTotal() {
        wait.until(ExpectedConditions.visibilityOf(order_summary));
        WebElement estimatedTotal = order_summary.findElement(By.className("summary-total"));
        WebElement estimatedTotalvalue = estimatedTotal.findElement(By.className("summary-value"));

        return estimatedTotalvalue.getText();
    }

    public String getSubtotal() {
        wait.until(ExpectedConditions.visibilityOf(order_summary));
        WebElement subTotal = order_summary.findElement(By.className("summary-subtotal"));
        WebElement subTotalvalue = subTotal.findElement(By.className("summary-value"));

        return subTotalvalue.getText();
    }

    public String getEstimatedShipping() {
        wait.until(ExpectedConditions.visibilityOf(order_summary));
        WebElement estimatedShipping = order_summary.findElement(By.className("summary-shipping"));
        WebElement estimatedShippingvalue = estimatedShipping.findElement(By.className("summary-value"));

        return estimatedShippingvalue.getText();
    }

    protected String getSummaryText(String field) {
        By xpath;
        switch (field) {
            case "subtotal":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'SUBTOTAL')]/following-sibling::*");
                break;
            case "estimated total":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Estimated Total')]/following-sibling::*");
                break;
            case "estimated tax":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Estimated Tax')]/following-sibling::*");
                break;
            case "estimated shipping":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Estimated Shipping')]/following-sibling::*");
                break;
            case "shipping":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Shipping')]/following-sibling::*");
                break;
            case "total":
                xpath = By.xpath(".//span[@class='summary-label' and contains(text(), 'Total')]/following-sibling::*");
                break;
            default:
                throw new WebDriverException(field + " field on the checkout page is not recognized!!!");
        }

        WebElement span = orderSummary.findElement(xpath);
        String text = span.getText();
        logger.debug("Returning summary text {}", text);

        return text;
    }


    public String getSubTotal() {
        return getSummaryText("subtotal");
    }

    public String getPromoDiscount() {
        String promo = "0";
        List<WebElement> promoElement = orderSummary.findElements(
                By.xpath(".//li[contains(@class,'summary-promo')]/span[contains(@class,'summary-value')]"));

        if (promoElement.size() > 0) {
            promo = promoElement.get(0).getText();
        }

        return promo;
    }

    public void estimateTax(String zipcode) {
        WebElement zipcodeField = orderSummary.findElement(By.id("zipcode"));
        zipcodeField.sendKeys(zipcode);

        WebElement zipcodeIndicator = wait.until(ExpectedConditions.visibilityOf(
                orderSummary.findElement(By.id("zipcode-transition-indicator"))));;
        wait.until(ExpectedConditions.stalenessOf(zipcodeIndicator));
        wait.until(ExpectedConditions.visibilityOf(checkoutNow));
    }

    public WebElement getPromoMessageElementFromOrderSummary(){
        WebElement promoMessageElement = orderSummary.findElement(By.xpath(".//span[@class='summary-label' and text()='"
                + stateHolder.get("promoMessage") + "']"));
        return promoMessageElement;
    }
    public void addZipCode(String code) {
        wait.until(ExpectedConditions.visibilityOf(zipCode));
        zipCode.sendKeys(code);
    }

    public String getZipCodeMessage() {
        wait.until(ExpectedConditions.visibilityOf(orderSummary));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("summary-zipcode-message")));
        return message.getText();
    }

    private WebElement getPaypalElement(){
        return orderSummary.findElement(By.className("button-paypal"));
    }

    public boolean payPalButton() {
        WebElement payPal = getPaypalElement();

        return payPal.isDisplayed();
    }

    public void clickPaypalElement(){
        WebElement paypalElement = getPaypalElement();
        paypalElement.click();

        Util.waitLoadingBar(driver);
    }

    public String getEstimatedTax() {
        return getSummaryText("estimated tax");
    }

    public String getTotalValue(){
        return getSummaryText("total");
    }
}
