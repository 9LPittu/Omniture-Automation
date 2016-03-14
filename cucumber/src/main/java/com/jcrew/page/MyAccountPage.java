package com.jcrew.page;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jcrew.util.Util;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MyAccountPage {

    private final WebDriver driver;

    @FindBy(id = "main_inside")
    private WebElement myAccountContainer;

    @FindBy(id = "main_cont")
    private WebElement myAccountContent;

    @FindBy (id = "containerBorderLeft")
    private WebElement myAccountRightContent;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isInAccountPage() {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myAccountContainer));
        return myAccountContainer.isDisplayed();
    }

    public String getMyAccountHeader() {
        return myAccountContent.findElement(By.tagName("h2")).getText();
    }

    public boolean isMenuLinkPresent(String link) {
        return Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(getMenuLink(link))).isDisplayed();
    }

    private WebElement getMenuLink(String link) {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myAccountContainer));
        return myAccountContainer.findElement(By.linkText(link));
    }

    public void click_menu_link(String link) {
        WebElement menu = getMenuLink(link);
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(menu));
        menu.click();
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(By.className("header__promo__wrap")));
    }

    public boolean isInMenuLinkPage(String page) {
        return Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains(page));
    }

    public void click_order_for_review() {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(myAccountContainer.findElement(By.className("orderTableData"))));
        WebElement orderTableData = myAccountContainer.findElement(By.className("orderTableData"));
        WebElement orderReviewLink = orderTableData.findElement(By.tagName("a"));
        orderReviewLink.click();
    }
    
    public void deleteNonDefaultAddresses(){

        //td[@id='containerBorderLeft']/form/table/tbody/tr/td/table

        List<WebElement> tables = driver.findElements(By.xpath("//td[@id='containerBorderLeft']/form/table/tbody/tr/td/table"));

        while(tables.size() > 2){
            WebElement deleteButton = tables.get(1).findElement(By.linkText("DELETE"));
            deleteButton.click();

            //Util.createWebDriverWait(driver).until(ExpectedConditions.alertIsPresent());
            Alert removeAddress = driver.switchTo().alert();
            removeAddress.accept();

            tables = driver.findElements(By.xpath("//td[@id='containerBorderLeft']/form/table/tbody/tr/td/table"));
        }
    }
    
    public void deleteNonDefaultCreditCards(){
        List<WebElement> tables = driver.findElements(By.xpath("//div[@id='creditCardList']/table"));

        while(tables.size() > 2){
            WebElement deleteButton = tables.get(1).findElement(By.linkText("DELETE"));
            deleteButton.click();

            tables = driver.findElements(By.xpath("//div[@id='creditCardList']/table"));
        }
    }
}
