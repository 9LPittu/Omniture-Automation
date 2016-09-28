package com.jcrew.page;

import java.util.List;

import com.jcrew.pojo.Country;
import com.jcrew.util.PropertyReader;
import com.jcrew.util.StateHolder;
import com.jcrew.util.UsersHub;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

    @FindBy(id = "containerBorderLeft")
    private WebElement myAccountRightContent;

    @FindBy(className = "c-account__left__nav")
    private WebElement myAccountLeftNav;


    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isInAccountPage() {
        try {
            Util.waitForPageFullyLoaded(driver);
            Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myAccountContainer));
            return myAccountContainer.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getMyAccountHeader() {
        return myAccountContent.findElement(By.tagName("h2")).getText();
    }


    public boolean isMenuLinkPresent(String link) {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myAccountContainer));
        try {
            WebElement menuLink = driver.findElement(By.xpath("//a[@class='my_account_lefnav' and contains(" + Util.xpathGetTextLower + ",'" + link.toLowerCase() + "')]"));
            return (menuLink.isDisplayed());
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement getMenuLink(String link) {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(myAccountContainer)));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(myAccountContainer));
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@class='my_account_lefnav']")));
        WebElement menuLink = Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@class='my_account_lefnav' and contains(" + Util.xpathGetTextLower + ",'" + link.toLowerCase() + "')]")));
        return menuLink;
    }

    public boolean verifyRewardLink(String link, String userCategory) {
        boolean expected = false;
        Country c = (Country) stateHolder.get("context");

        if (userCategory.equalsIgnoreCase(UsersHub.CAT_LOYALTY) && ("us".equalsIgnoreCase(c.getCountry())))
            expected = true;

        return expected == isMenuLinkPresent(link);

    }

    public void click_menu_link(String link) {
        WebElement menu;

        Country c = (Country) stateHolder.get("context");
        String userCategory = (String) stateHolder.get("sidecaruserCategory");

        boolean ifReward = link.equalsIgnoreCase("J.Crew Card Rewards Status");
        boolean testRewardVisible = true;
        if (ifReward) {
            testRewardVisible = ((userCategory.equalsIgnoreCase(UsersHub.CAT_LOYALTY)) && "us".equalsIgnoreCase(c.getCountry()) && ifReward);
        }


        boolean forOtherCountries = !(link.equals("GIFT CARD BALANCE") || link.equals("CATALOG PREFERENCES"));
        if ((("ca".equals(c.getCountry()) && !(link.equals("GIFT CARD BALANCE"))) || "us".equals(c.getCountry()) || forOtherCountries) && testRewardVisible) {
            menu = getMenuLink(link);
            Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(menu));
            Util.clickWithStaleRetry(menu);
            Util.waitLoadingBar(driver);
        }
    }


    public boolean isInMenuLinkPage(String page) {
        Country c = (Country) stateHolder.get("context");
        String userCategory = (String) stateHolder.get("sidecaruserCategory");

        boolean ifReward = page.contains("rewards");
        boolean testRewardVisible = true;
        if (ifReward) {
            testRewardVisible = ((userCategory.equalsIgnoreCase(UsersHub.CAT_LOYALTY)) && "us".equalsIgnoreCase(c.getCountry()) && ifReward);
        }

        boolean forOtherCountries = !(page.contains("giftcard") || page.contains("catalog_preferences"));

        if ((("ca".equals(c.getCountry()) && !(page.contains("giftcard"))) || "us".equals(c.getCountry()) || forOtherCountries) && testRewardVisible) {
            return Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains(page));
        } else {
            logger.info("expected no " + page + " for " + c.getCountry());
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

    public void deleteNonDefaultAddresses() {

        PropertyReader propertyReader = PropertyReader.getPropertyReader();

        if (!propertyReader.getProperty("browser").equalsIgnoreCase("phantomjs")) {
            List<WebElement> tables = driver.findElements(By.xpath("//td[@id='containerBorderLeft']/form/table/tbody/tr/td/table"));

            while (tables.size() > 2) {
                WebElement deleteButton = tables.get(1).findElement(By.linkText("DELETE"));
                //going directly to the url to avoid having a confirmation pop-up that cannot be handled in iphone
                String url = deleteButton.getAttribute("href");
                driver.get(url);

                tables = driver.findElements(By.xpath("//td[@id='containerBorderLeft']/form/table/tbody/tr/td/table"));
            }
        }
    }

    public void deleteNonDefaultCreditCards() {

        PropertyReader propertyReader = PropertyReader.getPropertyReader();

        if (!propertyReader.getProperty("browser").equalsIgnoreCase("phantomjs")) {
            List<WebElement> tables = driver.findElements(By.xpath("//div[@id='creditCardList']/table"));

            while (tables.size() > 2) {
                WebElement deleteButton = tables.get(1).findElement(By.linkText("DELETE"));
                //going directly to the url to avoid having a confirmation pop-up that cannot be handled in iphone
                String url = deleteButton.getAttribute("href");
                driver.get(url);

                tables = driver.findElements(By.xpath("//div[@id='creditCardList']/table"));
            }
        }
    }
}
