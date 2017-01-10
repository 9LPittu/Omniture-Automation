package com.jcrew.page;

import com.google.common.base.Predicate;
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
            List<WebElement> global__email_capture_list = shortWait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='global__email-capture']")));

            if (global__email_capture_list.size() > 0) {
                List<WebElement> close_email_capture =
                        global__email_capture_list.get(0).findElements(By.className("js-email-capture--close"));

                if (close_email_capture.size() > 0) {
                    logger.debug("Email capture is visible, closing.");
                    final WebElement close = close_email_capture.get(0);

                    wait.until(new Predicate<WebDriver>() {
                        @Override
                        public boolean apply(WebDriver driver) {
                            try {
                                logger.debug("Close: {}", close.getAttribute("class"));
                                Util.scrollAndClick(driver, close);
                                wait.until(ExpectedConditions.stalenessOf(close));
                            } catch (WebDriverException wde) {
                                return false;
                            }
                            return true;
                        }
                    });
                }
            }
        } catch (TimeoutException timeout) {
            //for some reason, in zanker, the email capture is not displayed with PhantomJS, but the overlay is.
            //catching this timeout and ignoring.
        	try{
        		WebElement overlay = driver.findElement(By.className("js-global__overlay--emailcapture"));
	            if(overlay.isDisplayed()) {
	                logger.error("No email capture, but we have the email capture overlay");
	            }
        	}
        	catch(Exception e){
        		logger.error("No email capture overlay!!");
        	}
        }
    }
}