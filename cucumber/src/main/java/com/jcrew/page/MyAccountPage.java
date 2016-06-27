package com.jcrew.page;

import java.util.List;

import com.jcrew.pojo.Country;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAccountPage {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(MyAccountPage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();

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
        Country country = (Country) stateHolder.get("context");
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
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@class='my_account_lefnav']")));
        return myAccountContainer.findElement(By.linkText(link));

    }

    public void click_menu_link(String link) {
        WebElement menu;

        Country c = (Country)stateHolder.get("context");

        boolean ifOtherCountries = !(link.equals("GIFT CARD BALANCE")|| link.equals("CATALOG PREFERENCES"));
        if(("ca".equals(c.getCountry()) && !(link.equals("GIFT CARD BALANCE"))) || "us".equals(c.getCountry())|| ifOtherCountries) {
            menu = getMenuLink(link);
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(menu));
            Util.clickWithStaleRetry(menu);
            Util.waitLoadingBar(driver);
        }
    }

    public boolean isInMenuLinkPage(String page) {
        Country c = (Country)stateHolder.get("context");
        boolean forOtherCountries = !( page.contains("giftcard")|| page.contains("catalog_preferences"));

        if (("ca".equals(c.getCountry()) && !(page.contains("giftcard"))) || "us".equals(c.getCountry()) || forOtherCountries)
           return Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains(page));
        else {
            logger.info("expected no "+page+" for "+c.getCountry());
            return true;
        }

    }

    public void click_order_for_review() {
        Util.createWebDriverWait(driver).until(
                ExpectedConditions.visibilityOf(myAccountContainer.findElement(By.className("orderTableData"))));
        WebElement orderTableData = myAccountContainer.findElement(By.className("orderTableData"));
        WebElement orderReviewLink = orderTableData.findElement(By.tagName("a"));
        orderReviewLink.click();
    }
    
    public void deleteNonDefaultAddresses(){
    	
    	PropertyReader propertyReader = PropertyReader.getPropertyReader();

    	if(!propertyReader.getProperty("browser").equalsIgnoreCase("phantomjs")){
	        List<WebElement> tables = driver.findElements(By.xpath("//td[@id='containerBorderLeft']/form/table/tbody/tr/td/table"));
	
	        while(tables.size() > 2){
	            WebElement deleteButton = tables.get(1).findElement(By.linkText("DELETE"));
                //going directly to the url to avoid having a confirmation pop-up that cannot be handled in iphone
                String url = deleteButton.getAttribute("href");
                driver.get(url);
	
	            tables = driver.findElements(By.xpath("//td[@id='containerBorderLeft']/form/table/tbody/tr/td/table"));
	        }
    	}
    }
    
    public void deleteNonDefaultCreditCards(){

    	PropertyReader propertyReader = PropertyReader.getPropertyReader();

    	if(!propertyReader.getProperty("browser").equalsIgnoreCase("phantomjs")){
	    	List<WebElement> tables = driver.findElements(By.xpath("//div[@id='creditCardList']/table"));
	
	        while(tables.size() > 2){
	            WebElement deleteButton = tables.get(1).findElement(By.linkText("DELETE"));
                //going directly to the url to avoid having a confirmation pop-up that cannot be handled in iphone
                String url = deleteButton.getAttribute("href");
                driver.get(url);

	            tables = driver.findElements(By.xpath("//div[@id='creditCardList']/table"));
	        }
    	}
    }
}
