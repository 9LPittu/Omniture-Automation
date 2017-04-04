package com.jcrew.page.product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by ngarcia on 4/4/17.
 */
public class ProductDetailsPersonalization extends ProductDetails {

    @FindBy(id = "c-product__monogram")
    protected WebElement product__monogram;

    private final String ADD_MONOGRAM_CLASS = "p-monogram--add";
    private final String EDIT_MONOGRAM_CLASS = "p-monogram--edit__container";

    public ProductDetailsPersonalization(WebDriver driver) {
        super(driver);
    }

    public boolean isMonogrameable() {
        List<WebElement> monogram = product__monogram.findElements(By.className(ADD_MONOGRAM_CLASS));

        return product__monogram.isDisplayed() & monogram.size() > 0;
    }

    public void addMonogram() {
        WebElement monogram = product__monogram.findElement(By.className(ADD_MONOGRAM_CLASS));
        monogram.click();
    }

    public boolean hasMonogram() {
        List<WebElement> monogram = product__monogram.findElements(By.className(EDIT_MONOGRAM_CLASS));

        return monogram.size() > 0;
    }
}
