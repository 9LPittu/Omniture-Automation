package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class ArraySearch extends Array{


    @FindBy(id = "page__search")
    private WebElement pageSearch;
    @FindBy(id = "c-search__results")
    private WebElement searchResults;

    @FindBy(className = "header__search")
    private WebElement headerSearch;

    @FindBy(xpath = "//div[@class='product__grid']")
    private WebElement productGrid;


    public ArraySearch(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(pageSearch));

    }
    public void selectRandomProduct()
    {
        selectRandomProduct(searchResults);
    }

    public boolean isSearchPage() {
        Util.waitWithStaleRetry(driver, headerSearch);
        wait.until(ExpectedConditions.visibilityOf(searchResults));

        return headerSearch.isDisplayed() && searchResults.isDisplayed() && Util.countryContextURLCompliance(driver, country);
    }


    public void click_first_product_in_grid() {
        WebElement random_product_tile = getFirstProduct();
        wait.until(ExpectedConditions.visibilityOf(random_product_tile));
        WebElement random_product_name = random_product_tile.findElement(By.className(NAME_CLASS));

        logger.info("Selected product: {}", random_product_name.getText());

        WebElement random_product_image = random_product_tile.findElement(By.tagName("img"));
        wait.until(ExpectedConditions.visibilityOf(random_product_image));
        random_product_image.click();

        Util.waitLoadingBar(driver);
    }

    private WebElement getFirstProduct() {
        return getProductTiles(searchResults).get(0);
    }

    public List<String> getPrices() {

        return getProductPrices(searchResults,PRICE_LIST_CLASS);
    }

    public List<String> getWasPrices() {

        return getProductPrices(searchResults,PRICE_WAS_CLASS);
    }

    public List<String> getSalePrices() {

        return getProductPrices(searchResults,PRICE_SALE_CLASS);
    }
}
