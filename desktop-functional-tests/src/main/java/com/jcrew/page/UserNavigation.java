package com.jcrew.page;

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
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final Logger logger = LoggerFactory.getLogger(UserNavigation.class);

    public UserNavigation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isCurrentUrl(String page) {

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
}
