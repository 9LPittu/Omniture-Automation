package com.jcrew.page.product;

import com.jcrew.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Created by ngarcia on 4/11/17.
 */
public class PersonalizationFactory {
    private static PropertyReader propertyReader = PropertyReader.getPropertyReader();

    public static IPersonalization getProductDetailsPersonalization(WebDriver driver) {
        String brand = propertyReader.getProperty("brand");

        switch (brand) {
            case "jcrew":
                return new com.jcrew.page.product.jcrew.ProductDetailsPersonalization(driver);
            case "factory":
                return new com.jcrew.page.product.factory.ProductDetailsPersonalization(driver);
            default:
                throw new WebDriverException("Unrecognized brand " + brand);
        }
    }

    public static IMonogram getProductMonogram(WebDriver driver) {
        String brand = propertyReader.getProperty("brand");

        switch (brand) {
            case "jcrew":
                return new com.jcrew.page.product.jcrew.ProductMonogram(driver);
            case "factory":
                return new com.jcrew.page.product.factory.ProductMonogram(driver);
            default:
                throw new WebDriverException("Unrecognized brand " + brand);
        }
    }
}
