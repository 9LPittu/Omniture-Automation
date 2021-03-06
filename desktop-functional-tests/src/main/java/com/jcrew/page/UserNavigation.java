package com.jcrew.page;

import com.google.common.base.Predicate;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by 9hvenaga on 5/2/2016.
 */
public class UserNavigation {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(UserNavigation.class);

    public UserNavigation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isCurrentUrl(String page) {
    	
    	Util.createWebDriverWait(driver).until(new Predicate<WebDriver>(){
			@Override
			public boolean apply(WebDriver driver) {
				ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
				if(tabs.size()>=2){
					return true;
				}
				else{
					return false;
				}
			}    		
    	});
    	
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        logger.info("no.of windowhandles; {}", tabs.size());

        // get the current window handle
        for (String tab : tabs) {
            logger.info("parent window handle {}", tab);
            driver.switchTo().window(tab);
            logger.debug("Title in handle: {}", driver.getTitle());
        }
        Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains(page));
        String currentUrl = driver.getCurrentUrl();

        driver.close();                                 // close newly opened window when done with it
        driver.switchTo().window(tabs.get(0));
        return currentUrl.contains(page);
    }
    
    public void clearBag() {
        PropertyReader reader = PropertyReader.getPropertyReader();
        String cleanBagURL = (reader.getProperty("url")) + "/CleanPersistentCart.jsp";
        logger.info("Clearing bag with url: {}", cleanBagURL);
        driver.navigate().to(cleanBagURL);

        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.urlToBe(cleanBagURL)));
        logger.debug("After clearing bag, user is being redirected to: {}", driver.getCurrentUrl());
    }
}
