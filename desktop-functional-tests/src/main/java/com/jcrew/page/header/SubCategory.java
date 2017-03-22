package com.jcrew.page.header;

import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
        By.xpath(".//div[@class='nav-page__group' and not(@style='display: none;')]/." +
                "//a[contains(@class,'js-menu__link--has-href') and " + Util.xpathGetTextLower + "='" + subcategory +"']"));

        clickAndHideFlyout(flyoutLink);

    }

    public void selectSubCategory(String subCategory) {
        WebElement subCategoryElement;
        subcat_nav = wait.until(ExpectedConditions.visibilityOf(top_nav.findElement(
                By.xpath(".//div[contains(@class,'js-department-subcat-nav__wrap') and contains(@class,'is-visible')]"))));

        if (subCategory.equalsIgnoreCase("looks we love")) {

            subCategoryElement = subcat_nav.findElement(By.xpath(".//ul/li/a[contains(@name,'lookswelove')]"));

        } else {
            subCategoryElement = subcat_nav.findElement(
                    By.xpath(".//div[@class='nav-page__group' and not(@style='display: none;')]/" +
                            ".//a[contains(@class,'js-menu__link--has-href') and " +
                            Util.xpathGetTextLower + "='" + subCategory.toLowerCase() + "']"));
        }

        clickAndHideFlyout(subCategoryElement);
    }

    public void selectASaleSubCategory() {
        WebElement holder = wait.until(ExpectedConditions.visibilityOf(subcat_nav));
        List<WebElement> saleCategories = holder.findElements(By.xpath(".//ul/li/a[contains(@class,'nav-page__link')]"));

        clickAndHideFlyout(Util.randomIndex(saleCategories));

    }

    private void clickAndHideFlyout(WebElement link) {
        logger.info("Selected sale category: {}", link.getText());
        stateHolder.put("saleCategory", link.getText());
        link.click();

        Util.waitLoadingBar(driver);
        HeaderWrap header = new HeaderWrap(driver);
        header.hoverOverIcon("logo");
    }
}
