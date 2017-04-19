package com.jcrew.page.product.factory;

import com.jcrew.page.product.IPersonalization;
import com.jcrew.page.product.ProductDetails;
import org.openqa.selenium.WebDriver;

/**
 * Created by ngarcia on 4/4/17.
 */
public class ProductDetailsPersonalization extends ProductDetails implements IPersonalization {
    public ProductDetailsPersonalization(WebDriver driver) {
        super(driver);
    }

    public boolean isMonogrameable() {
        return false;
    }

    public void addMonogram() {

    }

    public boolean hasMonogram() {
        return false;
    }
}
