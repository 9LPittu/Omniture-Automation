package com.jcrew.page.genderlanding;

import com.jcrew.page.PageObject;
import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by 9msyed on 12/12/2016.
 */
public class JCrewGenderLanding extends PageObject implements IGenderLandingPage {

    @FindBy(className = "jcSidecarLanding")
    private WebElement sidecarLanding;


    public JCrewGenderLanding(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        Util.waitLoadingBar(driver);
    }

    public boolean isLandingPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(sidecarLanding));
        return sidecarLanding.isDisplayed();
    }

}
