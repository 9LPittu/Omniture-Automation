package com.jcrew.page;

import com.google.common.base.Function;
import com.jcrew.utils.Util;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/19/16.
 */
public class EmailCapture extends PageObject {

    private final WebDriverWait shortWait;

    @FindBy(xpath = "//div[contains(@class,'bx-creative bx-creative')]")
    private WebElement global__email_capture;
    
    @FindBy(xpath = "(//*[@class='bx-close-xstroke bx-close-x-adaptive'])[1]")
    private WebElement closeIcon;
    
    public EmailCapture(WebDriver driver) {
        super(driver);
        this.shortWait = Util.createWebDriverWait(driver, 30);

        PageFactory.initElements(driver, this);
    }
    
    public void closeEmailCapture() {
    	Util.wait(20000);
    	try {
    		closeIcon.isDisplayed();
    		logger.debug("Email capture is visible, closing.");
    		closeIcon.click();
    	}catch (Exception e) {
            logger.error("No email capture was found. Ignoring Error.");
        }
    	
    		/*   try {
            /*WebElement global__email_capture = shortWait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'bx-creative bx-creative')]")));//global__email-capture
            Assert.assertTrue(global__email_capture.isDisplayed());
            List<WebElement> close_email_capture = global__email_capture.findElements(
                    By.xpath("//div[text()='close dialog']"));//.//span[@class='icon-close']/ancestor::div[contains(@class,'js-email-capture--close')]

        	//shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'bx-creative bx-creative')]")));
        	
        	//List<WebElement> close_email_capture = closeIcon;
            
        			
        		
        		
        		
        		/*    logger.debug("Email capture is visible, closing.");
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
            *//*} else {
                logger.debug("No email capture was found. Ignoring error.");
            }

        } */

        //HeaderLogo logo = new HeaderLogo(driver);
        //logo.hoverLogo();
    }
}