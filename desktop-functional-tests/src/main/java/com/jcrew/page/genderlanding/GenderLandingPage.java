package com.jcrew.page.genderlanding;
;
import com.jcrew.utils.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Created by ngarcia on 3/10/17.
 */
public class GenderLandingPage {
    private static PropertyReader propertyReader = PropertyReader.getPropertyReader();
    private static final String brand = propertyReader.getProperty("brand");

    public static IGenderLandingPage getGenderLandingPage(WebDriver driver) {

        switch (brand) {
            case "jcrew":
                return new JCrewGenderLanding(driver);
            case "factory":
                return new FactoryGenderLanding(driver);
            default:
                throw new WebDriverException("Unrecognized brand " + brand);
        }
    }
}
