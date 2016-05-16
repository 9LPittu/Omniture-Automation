package com.jcrew.page;

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
 * Created by nadiapaolagarcia on 4/7/16.
 */
public class SaleLanding {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(SaleLanding.class);
    private final WebDriverWait wait;
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy(id = "page__sale")
    private WebElement page__sale;

    @FindBy(className = "c-sale__c-category-list")
    private WebElement saleCategoryList;

    public SaleLanding(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(page__sale));
    }

    public void selectRandomSaleCategory() {
        List<WebElement> categoryListItem = page__sale.findElements(By.className("c-category__list-item"));
        WebElement randomItem = Util.randomIndex(categoryListItem);
        WebElement randomItemLink = randomItem.findElement(By.xpath(".//parent::a"));
        String data_name = randomItemLink.getAttribute("data-label");

        logger.info("Selected {} for sale category", data_name);

        randomItem.click();
        Util.waitLoadingBar(driver);
    }

    public void click_on_sale_subcategory(String subcategory) {
        Util.waitLoadingBar(driver);
        getSubcategoryFromSale(subcategory).click();
        stateHolder.put("sale category", subcategory);
        wait.until(ExpectedConditions.urlContains("search"));
        Util.waitLoadingBar(driver);
    }

    private WebElement getSubcategoryFromSale(String subcategory) {
        wait.until(ExpectedConditions.visibilityOf(saleCategoryList));
        return saleCategoryList.findElement(By.xpath(".//div[@class='c-category__header accordian__header' and " +
                Util.xpathGetTextLower + " = " +
                "translate('" + subcategory + "', 'ABCDEFGHJIKLMNOPQRSTUVWXYZ','abcdefghjiklmnopqrstuvwxyz')]/.."));
    }

}
