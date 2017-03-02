package com.jcrew.page.header;

import com.jcrew.page.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by ngarcia on 3/2/17.
 */
public class GlobalPromo extends PageObject {

    @FindBy(id = "global__promo")
    private WebElement global_promo;

    public GlobalPromo(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(global_promo));
    }


}
