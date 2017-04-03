package com.jcrew.page.product;

import com.jcrew.page.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 3/30/17.
 */
public class ProductImage extends PageObject {

    @FindBy(id = "c-product__photos")
    private WebElement product__photos;

    public ProductImage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(product__photos));
    }

    private WebElement selectedImage() {
        return product__photos.findElement(
                By.xpath(".//div[contains(@class,'js-product__image--zoom-fullsize') and contains(@class,'is-selected')]/img"));
    }

    public void clickSelectedImage() {
        WebElement selectedImage = selectedImage();
        wait.until(ExpectedConditions.elementToBeClickable(selectedImage));
        selectedImage.click();
    }

    public void hoverSelectedImage() {
        WebElement selectedImage = selectedImage();

        hoverAction.moveToElement(selectedImage);
        hoverAction.build().perform();
    }

    public boolean isSelectedImageZoomed() {
        WebElement selectedImage = selectedImage();
        String selectedImageClass = selectedImage.getAttribute("class");

        return selectedImageClass.contains("image-flyout-zoom--active");
    }

    public boolean isModalDisplayed() {
        WebElement modal = product__photos.findElement(By.className("js-product-modal-window"));
        return modal.isDisplayed();
    }
}
