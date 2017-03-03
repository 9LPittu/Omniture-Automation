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
        TestDataReader testdataReader = TestDataReader.getTestDataReader();
        String clothClassName = testdataReader.getData("clothing.className");
        String shoesClassName = testdataReader.getData("shoes.className");

        int counter = 0;
        boolean retry = true;

        String selectedSubCategoryText = "";

        do {
            try {
                String url = driver.getCurrentUrl();
                WebElement holder = wait.until(ExpectedConditions.visibilityOf(subcat_nav));

                List<WebElement> subCategories = holder.findElements(
                        By.xpath(".//div[contains(@class,'" + clothClassName + "') or " +
                                "contains(@class,'" + shoesClassName + "')]/div/ul/li/a"));

                WebElement selectedSubCategory = Util.randomIndex(subCategories);
                selectedSubCategoryText = selectedSubCategory.getText();

                logger.info("Selected subcategory: {}", selectedSubCategoryText);
                stateHolder.put("subcategory", selectedSubCategoryText);
                selectedSubCategory.click();

                wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
                Util.waitLoadingBar(driver);
                HeaderWrap header = new HeaderWrap(driver);
                header.hoverOverIcon("logo");

                //verify if if the category array page is non-akamai
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("c-product__list")));

                retry = false;

            } catch (Exception e) {
                logger.info("Selected subcategory: {} is an akamai page. So, trying with a different sub category",
                        selectedSubCategoryText);
                hoverCategory();
                counter ++;
            }

        } while (retry && (counter < 3));

        if(!retry) {
            throw new WebDriverException("Script not able to find a category that is not akamai page");
        }
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
