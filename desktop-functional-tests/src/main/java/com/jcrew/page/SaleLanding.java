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

    @FindBy(id = "c-promo-categories")
    private WebElement saleCategory;

    public SaleLanding(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(page__sale));
    }

    public void selectRandomSaleCategory() {
        List<WebElement> categoryListItem = page__sale.findElements(By.className("c-category__list-item"));
        WebElement randomItem = Util.randomIndex(categoryListItem);
        WebElement randomItemLink = randomItem.findElement(By.tagName("a"));

        logger.info("Selected {} for sale category with link {}",
                randomItemLink.getAttribute("data-label"),
                randomItemLink.getAttribute("href"));

        randomItemLink.click();
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
        wait.until(ExpectedConditions.visibilityOf(saleCategory));

        return saleCategory.findElement(
                By.xpath(".//a[@class='js-sale__link' and @data-label='" + subcategory.toLowerCase() + "']"));
    }

}
