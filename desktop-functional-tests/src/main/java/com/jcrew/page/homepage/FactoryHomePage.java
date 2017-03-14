package com.jcrew.page.homepage;

import com.jcrew.page.Footer;
import com.jcrew.page.HeaderWrap;
import com.jcrew.page.PageObject;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 3/10/17.
 */
public class FactoryHomePage extends PageObject implements IHomePage {
    @FindBy(id = "page__home")
    private WebElement pageContent;

    public FactoryHomePage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(pageContent));

        HeaderWrap headerWrap = new HeaderWrap(driver);
        Footer footer = new Footer(driver);
    }

    public boolean isHomePage() {
        wait.until(ExpectedConditions.visibilityOf(pageContent));
        WebElement body = driver.findElement(By.tagName("body"));
        String bodyClass = body.getAttribute("class");
        Util.waitForPageFullyLoaded(driver);
        logger.info("home page body class is " + bodyClass);
        return "factory home size-desktop".equals(bodyClass);
    }
}
