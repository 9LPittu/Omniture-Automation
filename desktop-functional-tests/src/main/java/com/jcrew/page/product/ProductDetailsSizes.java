package com.jcrew.page.product;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 3/22/17.
 */
public class ProductDetailsSizes extends ProductDetails {

    @FindBy(id = "c-product__sizes")
    private WebElement sizes;

    public ProductDetailsSizes(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(sizes));
    }

    public void selectRandomSize() {
        wait.until(ExpectedConditions.visibilityOf(sizes));
        String availableSizesSelector = ".//li[contains(@class,'js-product__size sizes-list__item') " +
                "and not(contains(@class,'is-unavailable')) " +
                "and not(contains(@class,'is-selected'))]";

        List<WebElement> availableSizes = sizes.findElements(By.xpath(availableSizesSelector));

        if (availableSizes.size() > 0) {
            final WebElement selectedSize = Util.randomIndex(availableSizes);
            Util.scrollAndClick(driver, selectedSize);
        }
    }

    public void selectSpecifiedSize(String size){
        wait.until(ExpectedConditions.visibilityOf(sizes));
        String sizeSelector = ".//li[contains(@class,'js-product__size sizes-list__item')"
                + " and @data-name='" + size.toUpperCase() + "']";

        WebElement sizeElement = sizes.findElement(By.xpath(sizeSelector));
        Util.scrollToElement(driver, sizeElement);
        sizeElement.click();
        logger.debug("Selected size: {}", size);
    }

    public String getSelectedSize() {
        WebElement selectedSize = sizes.findElement(
                By.xpath(".//li[contains(@class,'js-product__size') and contains(@class,'is-selected')]"));
        return selectedSize.getAttribute("data-name");
    }

    public void selectSize(String size) {
        List<WebElement> productSizes = sizes.findElements(
                By.xpath(".//li[contains(@class,'js-product__size') and @data-name='" + size.toUpperCase() + "']"));

        if (productSizes.size() > 0) {
            WebElement selectedSize = productSizes.get(0);
            WebElement selectedSizeLabel = selectedSize.findElement(By.className("btn__label"));

            Util.scrollPage(driver, Util.DOWN, 100);
            selectedSizeLabel.click();

            logger.info("Selecting specified size {}", size);
        } else {
            logger.info("Size " + size + " not found");
        }
    }

    public boolean isDisplayed() {
        return sizes.isDisplayed();
    }

    public boolean sizeChartIsDisplayed() {
        WebElement sizeChartLink = sizes.findElement(By.xpath(".//a[contains(@class,'js-link__size-chart')]"));
        return sizeChartLink.isDisplayed();
    }

    public String getMessage() {
        String message = "";
        List<WebElement> messageList = sizes.findElements(By.className("product__us-sizes"));

        if(messageList.size() > 0) {
            message = messageList.get(0).getText().trim();
        }

        return message;
    }
}
