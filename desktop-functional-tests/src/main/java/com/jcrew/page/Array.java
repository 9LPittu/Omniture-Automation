package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/20/16.
 */
public abstract class Array extends PageObject{

    protected final String PRODUCT_TITLE_CLASS = "c-product-tile";
    protected final String PRICE_LIST_CLASS = "tile__detail--price--list";
    protected final String PRICE_SALE_CLASS = "tile__detail--price--sale";
    protected final String PRICE_WAS_CLASS = "tile__detail--price--was";
    protected final String NAME_CLASS = "tile__detail--name";
    protected final String SHIPPING_CLASS = "tile__detail--shipping";
    protected final String COLOR_COUNTS_CLASS = "js-tile__detail--colors-count";
    protected final String CUSTOM_MESSAGE = "monetate_custom_index_text";

    protected Footer footer;
    protected HeaderWrap header;

    public Array(WebDriver driver) {
        super(driver);
    }

    protected List<WebElement> getProductTiles(WebElement productList) {
        return productList.findElements(By.className(PRODUCT_TITLE_CLASS));
    }


    protected List<String> getProductPrices(WebElement productList,String priceType) {
        List<WebElement> priceList = productList.findElements(By.className(priceType));
        List<String> productPrices = new ArrayList<>(priceList.size());

        for (WebElement price:priceList ) {
            productPrices.add(price.getText());
        }

        return productPrices;
    }
    public void selectRandomProduct(WebElement productList) {
        List<WebElement> productTiles = getProductTiles(productList);
        logger.info("This array page has {} products", productTiles.size());

        WebElement random_product_tile = Util.randomIndex(productTiles);
        wait.until(ExpectedConditions.visibilityOf(random_product_tile));
        WebElement random_product_name = random_product_tile.findElement(By.className(NAME_CLASS));

        logger.info("Selected product: {}", random_product_name.getText());

        WebElement random_product_image = random_product_tile.findElement(By.tagName("img"));
        wait.until(ExpectedConditions.visibilityOf(random_product_image));
        random_product_image.click();

        Util.waitLoadingBar(driver);
        new ProductDetails(driver);
    }
}
