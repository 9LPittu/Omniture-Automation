package com.jcrew.page.header;

import com.jcrew.page.HeaderWrap;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 3/2/17.
 */
public class SubCategory extends TopNav {

    private WebElement subcat_nav;

    public SubCategory(WebDriver driver) {
        super(driver);

        subcat_nav = wait.until(ExpectedConditions.visibilityOf(top_nav.findElement(
                By.xpath(".//div[contains(@class,'js-department-subcat-nav__wrap') and contains(@class,'is-visible')]"))));
    }

    public void selectSubCategory() {
        subcat_nav = wait.until(ExpectedConditions.visibilityOf(top_nav.findElement(
            By.xpath(".//div[contains(@class,'js-department-subcat-nav__wrap') and contains(@class,'is-visible')]"))));

        String category = stateHolder.get("category");

        TestDataReader testdataReader = TestDataReader.getTestDataReader();
        String[] subCategories = testdataReader.getData(category.toLowerCase()).split(";");
        String subcategory = subCategories[Util.randomIndex(subCategories.length)];

        WebElement flyoutLink = subcat_nav.findElement(
        By.xpath(".//a[contains(@class,'js-menu__link--has-href') and " + Util.xpathGetTextLower + "='" + subcategory +"']"));

        flyoutLink.click();
        Util.waitLoadingBar(driver);
        HeaderWrap header = new HeaderWrap(driver);
        header.hoverOverIcon("logo");

    }

    public void selectSubCategory(String subCategory) {
        WebElement subCategoryElement;
        WebElement holder = wait.until(ExpectedConditions.visibilityOf(subcat_nav));

        if (subCategory.equalsIgnoreCase("looks we love")) {
            subCategoryElement = holder.findElement(By.xpath(".//ul/li/a[contains(@name,'lookswelove')]"));
        } else {
            subCategoryElement = holder.findElement(
                    By.xpath(".//ul/li/a[" + Util.xpathGetTextLower + "='" + subCategory.toLowerCase() + "']"));
        }

        logger.info("Selected subcategory: {}", subCategoryElement.getText());
        stateHolder.put("subcategory", subCategoryElement.getText());
        subCategoryElement.click();

        Util.waitLoadingBar(driver);
        HeaderWrap header = new HeaderWrap(driver);
        header.hoverOverIcon("logo");
    }

    public void selectASaleSubCategory() {
        WebElement holder = wait.until(ExpectedConditions.visibilityOf(subcat_nav));
        List<WebElement> saleCategories = holder.findElements(By.xpath(".//ul/li/a[contains(@class,'nav-page__link')]"));

        WebElement selectedSaleCategory = Util.randomIndex(saleCategories);

        logger.info("Selected sale category: {}", selectedSaleCategory.getText());
        stateHolder.put("saleCategory", selectedSaleCategory.getText());
        selectedSaleCategory.click();

        Util.waitLoadingBar(driver);
        HeaderWrap header = new HeaderWrap(driver);
        header.hoverOverIcon("logo");
    }
}
