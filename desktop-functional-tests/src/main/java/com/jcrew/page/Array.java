package com.jcrew.page;

import com.jcrew.pojo.Product;
import com.jcrew.utils.Util;
import org.apache.commons.exec.util.StringUtils;
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

    protected final String VARIATION_CLASS = "tile__detail--alsoin";
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

    protected List<WebElement> getVariationProductsList(WebElement productList){
        return productList.findElements(By.xpath(".//div[@class='"+PRODUCT_TITLE_CLASS+"' and contains(.,'also in')]"));

    }

    protected List<String> getProductPrices(WebElement productList,String priceType) {
        List<WebElement> priceList = productList.findElements(By.className(priceType));
        List<String> productPrices = new ArrayList<>(priceList.size());

        for (WebElement price:priceList ) {
            productPrices.add(price.getText());
        }

        return productPrices;
    }

    protected void selectRandomVariationProduct(WebElement productList){
        List<WebElement> productTiles = getVariationProductsList(productList);

        logger.info("This array page has {} products", productTiles.size());
        WebElement random_product_tile = Util.randomIndex(productTiles);

        clickProduct(random_product_tile);
        new ProductDetails(driver);
    }
    protected void selectRandomProduct(WebElement productList) {
        List<WebElement> productTiles = getProductTiles(productList);
        logger.info("This array page has {} products", productTiles.size());
        // WebElement random_product_tile = productTiles.get(5);
        WebElement random_product_tile = Util.randomIndex(productTiles);
        clickProduct(random_product_tile);
        new ProductDetails(driver);
    }

    private void clickProduct(WebElement product) {

        wait.until(ExpectedConditions.visibilityOf(product));

        WebElement product_name = product.findElement(By.className(NAME_CLASS));
        logger.info("Click on product: {}", product_name.getText());

        WebElement image = product.findElement(By.tagName("img"));
        WebElement link = product.findElement(By.className("product-tile__link"));

        stateHolder.put("fromArray", getProduct(product));
        stateHolder.put("urlFromArray", link.getAttribute("href"));

        wait.until(ExpectedConditions.visibilityOf(image));
        wait.until(ExpectedConditions.elementToBeClickable(image));
        String url = driver.getCurrentUrl();
        image.click();
        Util.waitLoadingBar(driver);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
    }

    public Product getProduct(WebElement tile) {

        Product product = new Product();
        product.setName(getProductName(tile));
        product.setPrice(getProductPrice(tile));
        logger.info("Picked product: {}", product.getName());
        logger.info("Price: {}", product.getPrice());


        return product;
    }

    private String getProductName(WebElement selectedItem) {
        WebElement name = selectedItem.findElement(By.className("tile__detail--name"));
        return name.getText();
    }

    public int getProductAvailableColors(WebElement selectedItem) {
        int colors = 1;

        List<WebElement> availableColorsTag = selectedItem.findElements(By.className("js-colors__wrap"));

        if(availableColorsTag.size() > 0) {
            WebElement colors__wrap = availableColorsTag.get(0);
            String data_colors = colors__wrap.getAttribute("data-colors-count");
            colors = Integer.parseInt(data_colors);
        }

        return colors;
    }

    public String getProductPrice(WebElement selectedItem) {
        String price;

        List<WebElement> salePrice = selectedItem.findElements(By.className("tile__detail--price--sale"));
        if (salePrice.size() == 1) {
            price = salePrice.get(0).getText();
            price = price.replace("select colors", "").replace("now", "");
        } else {

            WebElement listPrice = selectedItem.findElement(By.className("tile__detail--price--list"));
            price = listPrice.getText();
        }
        return price;
    }

}
