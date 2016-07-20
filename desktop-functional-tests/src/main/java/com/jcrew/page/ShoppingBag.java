package com.jcrew.page;

import com.jcrew.pojo.Country;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class ShoppingBag extends Checkout {
    private final Footer footer;

    @FindBy(id = "button-checkout")
    private WebElement checkoutButton;
    @FindBy(id = "order-summary")
    private WebElement orderSummary;
    @FindBy(id = "checkout")
    private WebElement articleCheckout;

    public ShoppingBag(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(orderListing));
        this.footer = new Footer(driver);
    }

    public boolean isShoppingBagPage() {
        logger.info("country context is  : {}", country.getName());
        Util.waitForPageFullyLoaded(driver);
        wait.until(ExpectedConditions.visibilityOf(articleCheckout));

        return articleCheckout.isDisplayed();
    }

    public void clickCheckoutButton() {
        String url = driver.getCurrentUrl();
        Util.waitForPageFullyLoaded(driver);
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));

        checkoutButton.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        Util.waitForPageFullyLoaded(driver);
    }

    public boolean verifyContext() {
        String countryFooter = footer.getCountry();

        boolean result = countryFooter.equalsIgnoreCase(country.getName());
        return result;
    }

}

