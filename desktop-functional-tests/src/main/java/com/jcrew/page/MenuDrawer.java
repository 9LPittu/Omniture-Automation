package com.jcrew.page;

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
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class MenuDrawer {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(MenuDrawer.class);
    private final WebDriverWait wait;

    @FindBy(id = "global__nav")
    private WebElement drawer;

    public MenuDrawer(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);

    }

    public void selectCategoryFromList(List<String> categories) {
        WebElement level1Menus = drawer.findElement(By.className("js-menus--level1"));
        wait.until(ExpectedConditions.visibilityOf(level1Menus));

        int index = Util.randomIndex(categories.size());
        String selectedCategory = categories.get(index).toLowerCase();

        WebElement categoryLink = level1Menus.findElement(
                By.xpath(".//a[contains(" + Util.xpathGetTextLower + ",'" + selectedCategory + "')]"));

        logger.info("Selected category: {}", categoryLink.getText());
        wait.until(ExpectedConditions.elementToBeClickable(categoryLink));
        categoryLink.click();
    }

    public void selectSubCategory() {
        WebElement level2Menu = drawer.findElement(By.className("js-menus--level2"));
        wait.until(ExpectedConditions.visibilityOf(level2Menu));
        WebElement level2SubCategories = level2Menu.findElement(By.xpath(".//div[@class='menu__item']"));
        List<WebElement> level2SubCategoriesLinks = level2SubCategories.findElements(By.xpath(".//a[contains(@href,'/c/')]"));

        WebElement selectedSubCategory = Util.randomIndex(level2SubCategoriesLinks);

        logger.info("Selected subcategory: {}", selectedSubCategory.getText());
        selectedSubCategory.click();

        Util.waitLoadingBar(driver);
    }

    public void goBackToLevel1() {
        WebElement level2Menu = drawer.findElement(By.className("js-menus--level2"));
        wait.until(ExpectedConditions.visibilityOf(level2Menu));
        WebElement back = level2Menu.findElement(By.className("btn__label"));

        wait.until(ExpectedConditions.elementToBeClickable(back));
        back.click();
    }
}
