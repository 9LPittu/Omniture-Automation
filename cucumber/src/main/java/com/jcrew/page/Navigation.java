package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Navigation {

    private final WebDriver driver;

    @FindBy(className = "header__promo__wrap--mobile")
    private WebElement headerPromoWrap;


    @FindBy(className = "header__department-nav")
    private WebElement headerDepartmentNavigationSection;


    public Navigation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isGlobalPromoDisplayed() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(headerPromoWrap));
        return headerPromoWrap.isDisplayed();
    }


    public boolean isDepartmentLinkPresent(String department) {
        return headerDepartmentNavigationSection.findElement(By.linkText(department)).isDisplayed();
    }

    public boolean isCurrentUrl(String page) {
        return driver.getCurrentUrl().startsWith(page);
    }
}
