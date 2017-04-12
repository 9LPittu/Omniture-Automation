package com.jcrew.page.product.factory;

import com.jcrew.page.product.IMonogram;
import org.openqa.selenium.WebDriver;

/**
 * Created by ngarcia on 4/4/17.
 */
public class ProductMonogram extends ProductDetailsPersonalization implements IMonogram {

    public ProductMonogram(WebDriver driver) {
        super(driver);
    }

    public void selectOptions() {

    }
}
