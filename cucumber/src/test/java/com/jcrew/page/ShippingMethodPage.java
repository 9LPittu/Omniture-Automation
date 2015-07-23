package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShippingMethodPage {

    private final WebDriver driver;

    private final Logger logger = LoggerFactory.getLogger(ShippingMethodPage.class);

    @FindBy(id = "method0")
    private WebElement economyUps;

    @FindBy(id = "noGifts")
    private WebElement noGifts;

    @FindBy(xpath = ".//*[@id='frmSelectShippingMethod']/section/div[2]/a")
    private WebElement continueButton;

    public ShippingMethodPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void continue_to_billing() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.info("an exception happened while waiting for page load.");
        }

        continueButton.click();

    }

    public boolean isEconomyUps() {
        return economyUps.isSelected();
    }

    public boolean isNoGifts() {
        return noGifts.isSelected();
    }
}
