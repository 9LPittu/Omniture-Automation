package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nadiapaolagarcia on 4/8/16.
 */
public class ShoppingBag {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(ShoppingBag.class);
    private final WebDriverWait wait;

    @FindBy(id = "button-checkout")
    private WebElement checkoutButton;

    public ShoppingBag(WebDriver driver){
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(checkoutButton));
    }

    public void clickCheckoutButton() {
        checkoutButton.click();
    }
}
