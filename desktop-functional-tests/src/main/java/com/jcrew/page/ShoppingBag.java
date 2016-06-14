package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.CurrencyChecker;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    private HeaderWrap header;

    public ShoppingBag(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
        this.footer = new Footer(driver);
        this.header = new HeaderWrap(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(checkoutButton));
    }

    public boolean isShoppingBagPage() {
        Country country = (Country) stateHolder.get("context");
        logger.info("country context is  : {}",country.getName());
        Util.waitForPageFullyLoaded(driver);
        wait.until(ExpectedConditions.visibilityOf(articleCheckout));
        return  articleCheckout.isDisplayed();
    }

    public void clickCheckoutButton() {
        String urlBeforeClickingCheckoutButton = driver.getCurrentUrl();

        PropertyReader reader = PropertyReader.getPropertyReader();
        String browser = reader.getProperty("browser");

        if("desktop".equals(browser)) {
            String href = checkoutButton.getAttribute("href");
            try {
                href = URLDecoder.decode(href, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("not able to decode!", e);
            }
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript(href);
        } else {
            checkoutButton.click();
        }

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(urlBeforeClickingCheckoutButton)));
        Util.waitForPageFullyLoaded(driver);
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
        logger.debug("did currency match?", result);
        return result;
    }

    public boolean verifyContext() {
        String countryFooter = footer.getCountry();

        boolean result = countryFooter.equalsIgnoreCase(country.getName());
        return result;
    }

    public boolean isCorrectCurrencySymbol(String type) {
        Country c = (Country) stateHolder.get("context");

        String xpath = "";

        switch (type) {
            case "item":
                xpath = "//div[contains(@class,'item-price') or contains(@class,'item-total')]";
                break;
            case "summary":
                xpath = "//span[contains(@class,'summary-value')]";
                break;
            case "shipping method":
                xpath = "//span[contains(@class,'method-price')]";
                break;
            case "shipping":
                xpath = "//span[contains(@class,'shipping-price')]";
                break;
        }

        List<WebElement> productpricess = driver.findElements(By.xpath(xpath));

        return CurrencyChecker.validatePrices(productpricess, c);

    }

}

