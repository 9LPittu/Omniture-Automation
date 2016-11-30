package com.jcrew.page;

import com.jcrew.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 9msyed on 11/22/2016.
 */
public class ExternalPage extends PageObject {
    private PropertyReader reader = PropertyReader.getPropertyReader();
    private Logger logger = LoggerFactory.getLogger(ExternalPage.class);

    public ExternalPage(WebDriver driver) {
        super(driver);
    }

    public String getURL() {
        String url = reader.getProperty("url");
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains(url)));
        return driver.getCurrentUrl();
    }
}
