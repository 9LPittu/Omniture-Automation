package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage {

    public static final String ACCOUNT_PAGE_PREFIX = "account/";
    private final WebDriver driver;

    @FindBy(id = "main_wrapper")
    private WebElement myAccountContainer;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isInAccountPage() {
        new WebDriverWait(driver, 50).until(ExpectedConditions.visibilityOf(myAccountContainer));

        return myAccountContainer.isDisplayed();
    }

    public String getMyAccountHeader() {
        return myAccountContainer.findElement(By.tagName("h2")).getText();
    }

    public boolean isMenuLinkPresent(String link) {
        return getMenuLink(link).isDisplayed();
    }

    private WebElement getMenuLink(String link) {
        return myAccountContainer.findElement(By.linkText(link));
    }

    public void click_menu_link(String link) {
        getMenuLink(link).click();
    }

    public boolean isInMenuLinkPage(String page) {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains(ACCOUNT_PAGE_PREFIX + page));
    }

    public void click_order_for_review() {
        WebElement orderTableData = myAccountContainer.findElement(By.className("orderTableData"));
        WebElement orderReviewLink = orderTableData.findElement(By.tagName("a"));
        orderReviewLink.click();
    }
}
