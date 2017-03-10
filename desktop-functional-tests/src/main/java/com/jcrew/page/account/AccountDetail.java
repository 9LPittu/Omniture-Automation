package com.jcrew.page.account;

import com.jcrew.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Created by ngarcia on 3/2/17.
 */
public class AccountDetail {

    private static PropertyReader propertyReader = PropertyReader.getPropertyReader();
    private static final String brand = propertyReader.getProperty("brand");

    public static IAccountDetail getAccountDetail(WebDriver driver) {

        switch (brand) {
            case "jcrew":
                return new JcrewAccountDetail(driver);
            case "factory":
                return new FactoryAccountDetail(driver);
            default:
                throw new WebDriverException("Unrecognized brand " + brand);
        }
    }
}
