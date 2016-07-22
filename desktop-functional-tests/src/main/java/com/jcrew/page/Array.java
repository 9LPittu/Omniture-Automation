package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 7/20/16.
 */
public abstract class Array extends PageObject{

    protected final String PRODUCT_TITLE_CLASS = "c-product-tile";
    protected final String PRICE_LIST_CLASS = "tile__detail--price--list";
    protected final String PRICE_WAS_CLASS = "tile__detail--price--was";
    protected final String NAME_CLASS = "tile__detail--name";
    protected final String SHIPPING_CLASS = "tile__detail--shipping";
    protected final String PRICE_SALE_CLASS = "tile__detail--price--sale";
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

    protected List<String> getListPrices(WebElement productList,String priceType) {
        List<WebElement> listPricesElements = productList.findElements(By.className(priceType));
        List<String> listPrices = new ArrayList<>(listPricesElements.size());

        for (WebElement price:listPricesElements ) {
            listPrices.add(price.getText());
        }

        return listPrices;
    }

    /*
    protected List<String> getListPrices(WebElement productList) {
        List<WebElement> listPricesElements = productList.findElements(By.className(PRICE_LIST_CLASS));
        List<String> listPrices = new ArrayList<>(listPricesElements.size());

        for (WebElement price:listPricesElements ) {
            listPrices.add(price.getText());
        }

        return listPrices;
    }

    protected List<String> getWasPrices(WebElement productList) {
        List<WebElement> listPricesElements = productList.findElements(By.className(PRICE_WAS_CLASS));
        List<String> listPrices = new ArrayList<>(listPricesElements.size());

        for (WebElement price:listPricesElements ) {
            listPrices.add(price.getText());
        }

        return listPrices;
    }

    protected List<String> getSalePrices(WebElement productList) {
        List<WebElement> listPricesElements = productList.findElements(By.className(PRICE_SALE_CLASS));
        List<String> listPrices = new ArrayList<>(listPricesElements.size());

        for (WebElement price:listPricesElements ) {
            listPrices.add(price.getText());
        }

        return listPrices;
    }
*/
}
