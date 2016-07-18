package com.jcrew.page;

import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/15/16.
 */
public class Checkout {
    protected final WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(Checkout.class);
    protected final WebDriverWait wait;

    @FindBy(xpath = "//section[@class='checkout-container']")
    private  WebElement checkoutContainer;

    public Checkout(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
    }

    public float getOrderTotal() {
        WebElement order_summary = wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath(".//*[@id='order-summary']"))));
        WebElement totalLine = order_summary.findElement(By.className("summary-total"));
        WebElement totalNumber = totalLine.findElement(By.className("summary-value"));

        String sTotal = totalNumber.getText();
        sTotal = sTotal.replaceAll("[^0-9]", "");

        int iTotal = Integer.parseInt(sTotal);
        float fTotal = (float) iTotal /100;

        logger.info("Order total is {}", fTotal);
        return fTotal;

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
        WebElement confirmation = driver.findElement(By.id("confirmation-number"));

        return confirmation.isDisplayed();
    }
}
