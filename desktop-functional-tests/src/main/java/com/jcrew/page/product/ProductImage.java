package com.jcrew.page.product;

import com.jcrew.page.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by ngarcia on 3/30/17.
 */
public class ProductImage extends PageObject {

    @FindBy(id = "c-product__photos")
    private WebElement product__photos;

    public ProductImage(WebDriver driver) {
        super(driver);
    }


}
