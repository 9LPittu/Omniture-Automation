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
public class ProductsArray {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(ProductsArray.class);
    private final WebDriverWait wait;

    private final String PRICE_LIST_CLASS = "tile__detail--price--list";
    private final String NAME_CLASS = "tile__detail--name";
    private final String SHIPPING_CLASS = "tile__detail--shipping";
    private final String PRICE_SALE_CLASS = "tile__detail--price--sale";
    private final String COLOR_COUNTS_CLASS = "js-tile__detail--colors-count";
    private final String CUSTOM_MESSAGE = "monetate_custom_index_text";

    @FindBy(id = "c-product__list")
    private WebElement productList;

    public ProductsArray(WebDriver driver) {
        this.driver = driver;
        wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(productList));
    }

    public void selectRandomProduct(){
        List<WebElement> productTiles = productList.findElements(By.className("c-product-tile"));
        logger.info("This subcategory has {} products", productTiles.size());

        WebElement random_product_tile = Util.randomIndex(productTiles);
        wait.until(ExpectedConditions.visibilityOf(random_product_tile));
        WebElement random_product_name = random_product_tile.findElement(By.className(NAME_CLASS));

        logger.info("Selected product: {}", random_product_name.getText());

        WebElement random_product_image = random_product_tile.findElement(By.tagName("img"));
        wait.until(ExpectedConditions.visibilityOf(random_product_image));
        random_product_image.click();

        Util.waitLoadingBar(driver);
    }

}
