package com.jcrew.page;

import com.jcrew.utils.PropertyReader;
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
public class OrderConfirmation {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(OrderConfirmation.class);
    private final WebDriverWait wait;

    @FindBy(id = "confirmation-number")
    private WebElement confirmation;

    public OrderConfirmation(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(confirmation));
    }

    public boolean orderNumberIsVisible() {
        boolean result;
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
        return confirmation.isDisplayed();
    }
}
