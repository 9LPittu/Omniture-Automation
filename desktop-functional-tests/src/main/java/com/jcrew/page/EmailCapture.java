package com.jcrew.page;

import com.google.common.base.Predicate;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/19/16.
 */
public class EmailCapture extends PageObject {

    @FindBy(id = "global__email-capture")
    private WebElement global__email_capture;

    private final WebDriverWait shortWait;

    public EmailCapture(WebDriver driver) {
        super(driver);
        this.shortWait = Util.createWebDriverWait(driver, 5);

        PageFactory.initElements(driver, this);
    }

    public void closeEmailCapture() {

        if (global__email_capture.isDisplayed()) {
            List<WebElement> close_email_capture = global__email_capture.findElements(By.className("js-email-capture--close"));

            if (close_email_capture.size() > 0) {
                logger.debug("Email capture is visible, closing.");
                final WebElement close = close_email_capture.get(0);

                wait.until(new Predicate<WebDriver>() {
                    @Override
                    public boolean apply(WebDriver driver) {
                        try {
                            close.click();
                        } catch (WebDriverException wde) {
                            return false;
                        }
                        return true;
                    }
                });
            }
        }
    }
}
