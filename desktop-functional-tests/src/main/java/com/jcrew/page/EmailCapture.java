package com.jcrew.page;

import com.google.common.base.Function;
import com.jcrew.page.header.HeaderLogo;
import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/19/16.
 */
public class EmailCapture extends PageObject {

    private final WebDriverWait shortWait;

    public EmailCapture(WebDriver driver) {
        super(driver);
        this.shortWait = Util.createWebDriverWait(driver, 30);

        PageFactory.initElements(driver, this);
    }

    public void closeEmailCapture() {

        try {
            WebElement global__email_capture = shortWait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("global__email-capture")));

            List<WebElement> close_email_capture = global__email_capture.findElements(
                    By.xpath(".//span[@class='icon-close']/ancestor::div[contains(@class,'js-email-capture--close')]"));

            if (close_email_capture.size() > 0) {
                logger.debug("Email capture is visible, closing.");
                final WebElement close = close_email_capture.get(0);

                wait.until(new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        try {
                            close.click();
                        } catch (WebDriverException wde) {
                            logger.debug("Failed to close email capture");
                            return false;
                        }
                        return true;
                    }
                });
                shortWait.until(ExpectedConditions.stalenessOf(global__email_capture));
            } else {
                logger.debug("No email capture was found. Ignoring error.");
            }

        } catch (TimeoutException noEmailCapture) {
            logger.error("No email capture was found. Ignoring Error.");
        }

        //HeaderLogo logo = new HeaderLogo(driver);
        //logo.hoverLogo();
    }
}