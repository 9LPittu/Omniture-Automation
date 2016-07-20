package com.jcrew.page;

import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import com.thoughtworks.selenium.webdriven.commands.Check;
import org.openqa.selenium.By;
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
public class CheckoutReview extends Checkout{
    @FindBy(id = "slidertrack")
    private WebElement slidertrack;
    @FindBy(id = "billing-details")
    private WebElement billing_details;
    @FindBy(id = "confirmOrder")
    private WebElement pageMessage;

    @FindBy(xpath = ".//*[@id='orderSummaryContainer']/div/a")
    private WebElement placeYourOrder;

    public CheckoutReview(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(slidertrack));
    }

    public boolean isReviewPage() {
        return pageMessage.isDisplayed();
    }

    public void placeOrder() {
        Util.waitForPageFullyLoaded(driver);

        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String env = propertyReader.getProperty("environment");

        if (!"production".equals(env)) {
            String currentUrl = driver.getCurrentUrl();
            WebElement place_my_order = slidertrack.findElement(By.className("button-submit-bg"));

            place_my_order.click();
            wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
        } else {
            logger.info("Trying to place an order in production, ignoring");
        }
    }

    public void fillSecurityCode() {
        TestDataReader testDataReader = TestDataReader.getTestDataReader();
        WebElement securityCode = billing_details.findElement(By.id("securityCode"));
        securityCode.sendKeys(testDataReader.getData("card.cvv"));
    }

}
