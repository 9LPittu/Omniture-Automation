package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
        wait.until(ExpectedConditions.visibilityOf(pageContent));
        WebElement body = driver.findElement(By.tagName("body"));
        String bodyClass = body.getAttribute("class");

        return "jcrew home".equals(bodyClass);
    }

}
