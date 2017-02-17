package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 3/30/16.
 */
public class HomePage extends PageObject{
    private HeaderWrap headerWrap;
    private Footer footer;

    @FindBy(id = "page__home")
    private WebElement pageContent;

    public HomePage(WebDriver driver) {

        super(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(pageContent));

        headerWrap = new HeaderWrap(driver);
        footer = new Footer(driver);
    }

    public boolean isHomePage() {
    	logger.error("a");
        wait.until(ExpectedConditions.visibilityOf(pageContent));
        logger.error("b");
        WebElement body = driver.findElement(By.tagName("body"));
        logger.error("c");
        String bodyClass = body.getAttribute("class");
        logger.error("d");
        Util.waitForPageFullyLoaded(driver);
        logger.error("e");
        return "jcrew home".equals(bodyClass);
    }

}
