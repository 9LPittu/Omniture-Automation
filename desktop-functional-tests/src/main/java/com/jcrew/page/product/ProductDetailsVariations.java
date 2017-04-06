package com.jcrew.page.product;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 4/3/17.
 */
public class ProductDetailsVariations extends ProductDetails {

    @FindBy(id = "c-product__price")
    private WebElement price;
    @FindBy(id = "c-product__variations")
    private WebElement variations;

    private final String PRICE_SALE_CLASS = "product__price--sale";
    private final String PRICE_LIST_CLASS = "product__price--list";

    public ProductDetailsVariations(WebDriver driver) {
        super(driver);
    }

    public boolean isPriceDisplayed() {
        return price.isDisplayed();
    }

    public boolean areVariationsDisplayed() {
        return variations.isDisplayed();
    }

    public  String getProductPrice(){
        WebElement productPrice;
        List<WebElement> variationsPrice = variations.findElements(By.tagName("li"));
        if (variationsPrice.size() > 0) {
            WebElement selectedVariation = variations.findElement(By.className("is-selected"));

            //check if variation has sale price
            productPrice = selectedVariation.findElement(
                    By.xpath(".//span[contains(@class,'" + PRICE_SALE_CLASS + "')]"));
            if (!productPrice.isDisplayed()) {
                //if no sale price get regular price from varations
                productPrice = selectedVariation.findElement(
                        By.xpath(".//span[contains(@class,'" + PRICE_LIST_CLASS + "')]"));
            }

        } else { //if no variations, get sale price
            wait.until(ExpectedConditions.visibilityOf(price));
            productPrice = price.findElement(By.className(PRICE_SALE_CLASS));
            if (!productPrice.isDisplayed()) {
                //if no sale price get regular price
                productPrice = price.findElement(By.className(PRICE_LIST_CLASS));
            }
        }
        String price = productPrice.getText();
        price = price.trim().toLowerCase();
        price = price.replace("select colors", "").replace("now", "");
        price = price.replace("was", "");
        price = price.replace(" ", "");
        return price;
    }

    public String getPrice() {
        ProductDetailColors colors = new ProductDetailColors(driver);

        if (colors.hasGroups()) {
            return colors.getGroupPrice();

        } else {
            //if has variations, get price from variations
            List<WebElement> variationsPrice = variations.findElements(By.tagName("li"));
            WebElement productPrice;

            if (variationsPrice.size() > 0) {
                WebElement selectedVariation = variations.findElement(By.className("is-selected"));

                //check if variation has sale price
                productPrice = selectedVariation.findElement(
                        By.xpath(".//span[contains(@class,'" + PRICE_SALE_CLASS + "')]"));
                if (!productPrice.isDisplayed()) {
                    //if no sale price get regular price
                    productPrice = selectedVariation.findElement(
                            By.xpath(".//span[contains(@class,'" + PRICE_LIST_CLASS + "')]"));
                }

            } else { //if no variations, get sale price
                wait.until(ExpectedConditions.visibilityOf(price));
                productPrice = price.findElement(By.className(PRICE_SALE_CLASS));
                if (!productPrice.isDisplayed()) {
                    //if no sale price get regular price
                    productPrice = price.findElement(By.className(PRICE_LIST_CLASS));
                }
            }

            return productPrice.getText();
        }

    }

    public void selectRandomVariantOnPDP() {
        List<WebElement> productVariations = variations.findElements(By.className("radio__label"));

        int randomIndex = Util.randomIndex(productVariations.size());
        WebElement selectedVariation = productVariations.get(randomIndex);
        WebElement selectedVariationName = selectedVariation.findElement(By.className("product__variation--name"));
        logger.debug("Selected variation {}", selectedVariationName.getText());
        selectedVariation.click();

    }
}
