package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class MiniBag {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(MiniBag.class);
    private final WebDriverWait wait;

    @FindBy(id = "c-header__minibag")
    private WebElement minibag;

    public MiniBag(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
    }

    public int getItemsNumber(){
        List<WebElement> items = minibag.findElements(By.tagName("li"));

        return items.size();
    }

    public boolean showsMoreItems(){
        List<WebElement> see_more = minibag.findElements(By.xpath(".//div[@class='minibag__see-more']"));

        return see_more.size() == 1;
    }
}
