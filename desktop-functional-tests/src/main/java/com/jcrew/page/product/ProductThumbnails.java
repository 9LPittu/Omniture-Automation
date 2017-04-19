package com.jcrew.page.product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ngarcia on 3/31/17.
 */
public class ProductThumbnails extends ProductImage {

    private WebElement thumnailsGroup;
    private List<WebElement> thumbnails;

    public ProductThumbnails(WebDriver driver) {
        super(driver);
        thumnailsGroup = product__photos.findElement(By.xpath(".//ul[contains(@class,'product__photos--thumbnails')]"));
        thumbnails = thumnailsGroup.findElements(By.tagName(".//li"));
    }

    public int getThumbnailsListSize() {
        return thumbnails.size();
    }

    public boolean hasSelectedThumbnail() {
        List<WebElement> selectedThumbnail = thumnailsGroup.findElements(By.xpath(".//li[contains(@class,'is-selected')]"));
        return selectedThumbnail.size() == 1;
    }

    public void selectThumbnail(int index) {
        thumbnails.get(index).click();
    }

    public String getShotType(int index) {
        return thumbnails.get(index).getAttribute("data-shottype");
    }

    public boolean isThumbnailDisplayed(int index) {
        return thumbnails.get(index).isDisplayed();
    }
}
