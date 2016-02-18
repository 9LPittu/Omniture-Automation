package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderStatusPage {

    private final WebDriver driver;

    @FindBy(className = "orderstatus")
    private WebElement orderStatusForm;

    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isOrderStatusPage() {
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(orderStatusForm));
        return orderStatusForm.isDisplayed();
    }
}
