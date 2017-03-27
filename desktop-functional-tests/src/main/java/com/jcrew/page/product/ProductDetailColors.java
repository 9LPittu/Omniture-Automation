package com.jcrew.page.product;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 3/22/17.
 */
public class ProductDetailColors extends ProductDetails {

    @FindBy(id = "c-product__price-colors")
    private WebElement price_colors;

    public ProductDetailColors(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(price_colors));
    }

    public void selectRandomColor() {
        wait.until(ExpectedConditions.visibilityOf(price_colors));
        List<WebElement> availableColors =
                price_colors.findElements(By.xpath(".//li[@class='js-product__color colors-list__item'"
                        + " and not(contains(@class,'is-selected'))]"));

        if (availableColors.size() > 0) {
            WebElement selectedColor = Util.randomIndex(availableColors);

            Util.scrollAndClick(driver, selectedColor);
        }
    }

    public void selectSpecifiedColor(String colorName){
        wait.until(ExpectedConditions.visibilityOf(price_colors));
        WebElement colorElement = price_colors.findElement(By.xpath(".//li[contains(@class, 'js-product__color colors-list__item')"
                + " and @data-name='" + colorName.toUpperCase() + "']"));
        colorElement.click();
        logger.debug("Selected color: {}", colorName);
    }

    public String getSelectedColor() {
        wait.until(ExpectedConditions.visibilityOf(price_colors));
        WebElement selectedColor = price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));

        return selectedColor.getAttribute("data-name");
    }

    public boolean hasGroups() {
        List<WebElement> priceGroups = price_colors.findElements(By.xpath(".//div[@class='product__group']"));

        return priceGroups.size() > 1;
    }

    public String getGroupPrice() {
        WebElement selectedColor = price_colors.findElement(By.xpath(".//li[contains(@class,'is-selected')]"));
        WebElement productPrice = selectedColor.findElement(By.xpath(".//ancestor::div[@class='product__group']/span"));

        return productPrice.getText();
    }

    public void selectColor(String colorName) {
        wait.until(ExpectedConditions.visibilityOf(price_colors));

        List<WebElement> colors = price_colors.findElements(
                By.xpath(".//li[contains(@class, 'js-product__color') and @data-name='" + colorName.toUpperCase() + "']"));

        if (colors.size() > 0) {
            WebElement selectedColor = colors.get(0);
            WebElement selectedColorImage = selectedColor.findElement(By.tagName("img"));

            logger.info("Selecting specified color {}", colorName);

            wait.until(ExpectedConditions.visibilityOf(selectedColorImage));
            selectedColorImage.click();
        } else {
            logger.error("Color " + colorName + " not found");
            throw new WebDriverException("Color " + colorName + " not found");
        }
    }
}
