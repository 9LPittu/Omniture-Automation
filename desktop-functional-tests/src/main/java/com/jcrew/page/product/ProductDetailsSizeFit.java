package com.jcrew.page.product;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by ngarcia on 4/10/17.
 */
public class ProductDetailsSizeFit extends ProductDetails {

    @FindBy(id = "c-product__information")
    private WebElement information;

    private final String SIZE_FIT_ID = "c-product__size-fit";

    public ProductDetailsSizeFit(WebDriver driver) {
        super(driver);
    }

    public boolean hasProductDetails() {
        List<WebElement> size_fit = information.findElements(By.id(SIZE_FIT_ID));

        return size_fit.size() > 0;
    }

    public int getYCoordinate() {
        List<WebElement> size_fit = information.findElements(By.id(SIZE_FIT_ID));
        int yCoordinate = 0;

        if (size_fit.size() > 0) {
            Point p = size_fit.get(0).getLocation();
            yCoordinate = p.getY();
        }

        return yCoordinate;
    }

    public boolean hasSlider() {
        List<WebElement> slider = information.findElements(By.id("c-product__reviews--fit"));

        return slider.size() > 0;
    }
}
