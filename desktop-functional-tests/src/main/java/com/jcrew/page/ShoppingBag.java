package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.Product;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class ShoppingBag {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(ShoppingBag.class);
    private final WebDriverWait wait;
    private final Footer footer;
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final Country country = (Country) stateHolder.get("context");

    @FindBy(id = "button-checkout")
    private WebElement checkoutButton;
    
    @FindBy(id = "order-listing")
    private WebElement orderListing;
    
    @FindBy(id = "order-summary")
    private WebElement orderSummary; 
    
    @FindBy(id = "checkout")
    private WebElement articleCheckout;

    public ShoppingBag(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
        this.footer = new Footer(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(checkoutButton));
    }

    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    private boolean verifyCurrency() {
        String currency = country.getCurrency();

        boolean result = true;
        List<WebElement> items = orderListing.findElements(By.className("item-row"));
        Iterator<WebElement> itemsIterator = items.iterator();

        while (result & itemsIterator.hasNext()) {
            WebElement item = itemsIterator.next();
            WebElement pricePerItem = item.findElement(By.className("item-price"));
            WebElement totalPerItem = item.findElement(By.className("item-total"));

            String pricePerItemText = pricePerItem.getText().trim();
            String totalPerItemText = totalPerItem.getText().trim();

            result = CurrencyChecker.listPrice(currency, pricePerItemText)
                    & CurrencyChecker.listPrice(currency, totalPerItemText);


        }

        List<WebElement> summaryPrices = orderSummary.findElements(By.className("summary-value"));
        Iterator<WebElement> subtotalIterator = summaryPrices.iterator();

        while (result & subtotalIterator.hasNext()) {
            WebElement summaryPrice = subtotalIterator.next();
            String subtotalText = summaryPrice.getText().trim();

            result &= CurrencyChecker.listPrice(currency, subtotalText);
        }

        return result;
    }

    public boolean verifyContext() {
        String countryFooter = footer.getCountry();

        boolean result = countryFooter.equalsIgnoreCase(country.getName());
        result &= verifyCurrency();

        return result;
    }
    
    public boolean isArticleCheckoutPresent() {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(articleCheckout));
        return true;
    }
    
    public void click_edit_button() {
        Util.waitForPageFullyLoaded(driver);

        Product product = (Product) stateHolder.get("recentlyAdded");

        String xpath;

        if (product.getProductName().contains("'")) {
            xpath = ".//a[" + Util.xpathGetTextLower + " = \"" + product.getProductName().toLowerCase() + "\"]" +
                    "/ancestor::div[@class='item-product']";
        } else {
            xpath = ".//a[" + Util.xpathGetTextLower + " = '" + product.getProductName().toLowerCase() + "']" +
                    "/ancestor::div[@class='item-product']";
        }

        WebElement order_listing = driver.findElement(By.id("order-listing"));
        WebElement item_product = order_listing.findElement(
                By.xpath(xpath));
        WebElement item_product_edit = item_product.findElement(By.className("item-edit"));

        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(item_product_edit));
        item_product_edit.click();
    }
    
    public void click_checkout_button() {
        String url = driver.getCurrentUrl();
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(checkoutButton));

        checkoutButton.click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        Util.waitForPageFullyLoaded(driver);
    }
}
