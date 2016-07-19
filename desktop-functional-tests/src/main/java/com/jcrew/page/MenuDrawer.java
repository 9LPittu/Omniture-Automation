package com.jcrew.page;

import com.google.common.base.Predicate;
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

    public void openSaleLandingPage() {
        goBackToLevel1();
        WebElement level1Menus = drawer.findElement(By.className("js-menus--level1"));
        wait.until(ExpectedConditions.visibilityOf(level1Menus));

        WebElement saleLink = level1Menus.findElement(
                By.xpath(".//a[contains(" + Util.xpathGetTextLower + ",'sale')]"));

        wait.until(ExpectedConditions.elementToBeClickable(saleLink));
        saleLink.click();
    }

    public void selectCategory(List<String> categories) {
        int index = Util.randomIndex(categories.size());
        String randomCategory = categories.get(index);

        selectCategory(randomCategory);
    }

    public void selectCategory(String selectedCategory) {
        goBackToLevel1();
        WebElement level1Menus = drawer.findElement(By.className("js-menus--level1"));
        wait.until(ExpectedConditions.visibilityOf(level1Menus));

        WebElement categoryLink = level1Menus.findElement(
                By.xpath(".//a[" + Util.xpathGetTextLower + "='" + selectedCategory.toLowerCase() + "']"));

        logger.info("Selected category: {}", categoryLink.getText());
        wait.until(ExpectedConditions.elementToBeClickable(categoryLink));
        categoryLink.click();
    }

    public boolean isDrawerOpen() {
        String drawerStyle = drawer.getAttribute("style");
        logger.debug("global__nav style: {}", drawerStyle);
        return drawerStyle.contains("display: block;");
    }

    public String getDrawerTitle() {
        String title = "";
        WebElement navWrap = drawer.findElement(By.className("nav__wrap"));
        String navWrapClass = navWrap.getAttribute("class");

        if(navWrapClass.contains("is-offscreen-left-x1")) {
            WebElement menuLevel = navWrap.findElement(By.className("js-menus--level2"));
            WebElement displayedMenu = menuLevel.findElement(
                    By.xpath(".//div[@class='menu__item']/div[contains(@class,'menu__link--header')]"));

            title = displayedMenu.getText().toLowerCase();
        }

        return title;
    }

    public void selectSubCategory(String subCategory) {
        WebElement level2Menu = drawer.findElement(By.className("js-menus--level2"));
        wait.until(ExpectedConditions.visibilityOf(level2Menu));
        WebElement level2SubCategories = level2Menu.findElement(By.xpath(".//div[@class='menu__item']"));
        WebElement selectedSubCategory =
                level2SubCategories.findElement(By.xpath(".//a[contains(@name,'" + subCategory + "')]"));

        logger.info("Selected subcategory: {}", selectedSubCategory.getText());
        selectedSubCategory.click();

        Util.waitLoadingBar(driver);
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
        WebElement navWrap = drawer.findElement(By.className("nav__wrap"));
        String navWrapClass = navWrap.getAttribute("class");

        if (!"nav__wrap".equals(navWrapClass)) {
            HeaderWrap headerWrap = new HeaderWrap(driver);
            headerWrap.clickLogo();
            headerWrap.openMenu();
        }
    }

    public void goHome() {
        goBackToLevel1();
        WebElement homeLink = drawer.findElement(By.className("menu__btn--home__link"));

        wait.until(ExpectedConditions.visibilityOf(homeLink));
        homeLink.click();

        HeaderWrap header = new HeaderWrap(driver);
        header.reload();
    }
}
