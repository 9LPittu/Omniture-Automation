package com.jcrew.page.homepage;

import com.jcrew.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Created by ngarcia on 3/10/17.
 */
public class HomePage {
    private static PropertyReader propertyReader = PropertyReader.getPropertyReader();
    private static final String brand = propertyReader.getProperty("brand");

    public static IHomePage getHomePage(WebDriver driver) {

        switch (brand) {
            case "jcrew":
                return new JCrewHomePage(driver);
            case "factory":
                return new FactoryHomePage(driver);
            default:
                throw new WebDriverException("Unrecognized brand " + brand);
        }
    }
}
