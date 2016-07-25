package com.jcrew.page;

import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/15/16.
 */
public class Checkout extends PageObject{

    @FindBy(xpath = "//section[@class='checkout-container']")
    private  WebElement checkoutContainer;
    @FindBy(id = "order-summary")
    private WebElement order_summary;
    @FindBy(id = "order-listing")
    protected WebElement orderListing;

    public Checkout(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
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

    public boolean hasErrors() {
        List<WebElement> errors = driver.findElements(By.id("errors"));

        return errors.size() > 0;
    }

    public boolean orderNumberIsVisible() {
        boolean result;
        WebElement confirmation = driver.findElement(By.id("confirmation-number"));

        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String env = propertyReader.getProperty("environment");

        if (!"production".equals(env)) {
            result = confirmation.isDisplayed();
        } else {
            logger.info("Trying to place an order in production, ignoring");
            result = true;
        }

        return result;
    }

    public boolean isOrderConfirmationPage() {
        try {
            WebElement confirmation = driver.findElement(By.id("confirmation-number"));
            return confirmation.isDisplayed();
        } catch (NoSuchElementException noConfirmationNumber) {
            return false;
        }
    }

    public List<String> getItemsPrice() {
        wait.until(ExpectedConditions.visibilityOf(orderListing));
        List<WebElement> productpricess = orderListing.findElements(By.xpath("//div[contains(@class,'item-price') " +
                "or contains(@class,'item-total')]"));
        List<String> prices = new ArrayList<>(productpricess.size());

        for(WebElement priceElement : productpricess) {
            prices.add(priceElement.getText());
        }

        return prices;

    }
}
