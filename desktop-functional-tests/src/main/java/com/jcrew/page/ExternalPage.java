package com.jcrew.page;

import com.jcrew.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by 9msyed on 11/22/2016.
 */
public class ExternalPage extends PageObject {
    private PropertyReader reader = PropertyReader.getPropertyReader();

    public ExternalPage(WebDriver driver) {
        super(driver);
    }

    public String getURL() {
        String url = reader.getProperty("url");
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains(url)));
        return driver.getCurrentUrl();
    }
    
    public boolean isVariablePresentInSourceCode(String var) {
        return driver.getPageSource().contains(var);
    }

}
