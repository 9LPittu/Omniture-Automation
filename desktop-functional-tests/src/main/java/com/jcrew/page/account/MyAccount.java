package com.jcrew.page.account;

import com.jcrew.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Created by ngarcia on 3/2/17.
 */
public class MyAccount {

    private static PropertyReader propertyReader = PropertyReader.getPropertyReader();
    private static final String brand = propertyReader.getProperty("brand");

    public static IMyAccount getAccountMain(WebDriver driver) {

        switch (brand) {
            case "jcrew":
                return new com.jcrew.page.account.JcrewMyAccount(driver);
            case "factory":
                return new com.jcrew.page.account.FactoryMyAccount(driver);
            default:
                throw new WebDriverException("Unrecognized brand " + brand);
        }
    }
}
