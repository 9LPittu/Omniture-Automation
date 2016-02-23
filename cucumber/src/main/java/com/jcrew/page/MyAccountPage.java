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
    	try{
    		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    		List<WebElement> deleteButtons = driver.findElements(By.xpath("//a[text()='DELETE']"));
        	while(deleteButtons.size()>=2){    		
        		deleteButtons.get(1).click();
        		
        		Util.createWebDriverWait(driver).until(ExpectedConditions.alertIsPresent());
        		Alert alert = driver.switchTo().alert();        		
        		alert.accept();
        		
        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myAccountContainer));
        		deleteButtons = driver.findElements(By.xpath("//a[text()='DELETE']"));
        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElements(deleteButtons));
        	}
    	}
    	catch(Exception e){
    		driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    	}
    }
    
    public void deleteNonDefaultCreditCards(){
    	try{
    		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    		List<WebElement> deleteButtons = driver.findElements(By.xpath("//a[text()='DELETE']"));
        	while(deleteButtons.size()>=2){    		
        		deleteButtons.get(1).click();        		
        		deleteButtons = driver.findElements(By.xpath("//a[text()='DELETE']"));
        		Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElements(deleteButtons));
        	}
    	}
    	catch(Exception e){
    		driver.manage().timeouts().implicitlyWait(Util.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    	}
    }
}
