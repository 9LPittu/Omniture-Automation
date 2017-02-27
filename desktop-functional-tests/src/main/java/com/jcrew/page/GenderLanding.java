package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by 9msyed on 12/12/2016.
 */
public class GenderLanding extends PageObject {

    @FindBy(className = "jcSidecarLanding")
    private WebElement sidecarLanding;


 public GenderLanding(WebDriver driver){
     super(driver);
     PageFactory.initElements(driver,this);
     Util.waitLoadingBar(driver);
 }
 public boolean isLandingPageDisplayed(){
     wait.until(ExpectedConditions.visibilityOf(sidecarLanding));
     return sidecarLanding.isDisplayed();
 }

}
