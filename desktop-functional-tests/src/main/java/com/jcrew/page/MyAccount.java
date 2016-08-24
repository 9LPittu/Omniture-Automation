package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nadiapaolagarcia on 3/29/16.
 */
public class MyAccount {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(MyAccount.class);
    private final WebDriverWait wait;
    private final HeaderWrap header;
    private final StateHolder stateHolder = StateHolder.getInstance();

    public final String USER_DETAILS_FIRST_NAME = "firstName";
    public final String USER_DETAILS_LAST_NAME = "lastName";
    public final String USER_DETAILS_EMAIL = "emailAdd";
    public final String USER_DETAILS_COUNTRY = "country";

    @FindBy(id = "main_cont")
    private WebElement main_content;
    @FindBy(id = "main_inside")
    private WebElement main_inside;
    @FindBy(id = "containerBorderLeft")
    private WebElement leftContainer;
    @FindBy(id = "main_cont_hist")
    private WebElement main_content_history;

    public MyAccount(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(main_content));
        this.header = new HeaderWrap(driver);
    }

    public Map<String, String> getLoggedInUserDetails() {
        return  new AccountDetail(driver).getUserDetails();
    }

    public boolean isMyAccountMainPage() {
        WebElement myAccountBanner = main_content.findElement(By.tagName("h2"));
        String bannerText = myAccountBanner.getText();
        WebElement homecopy = main_inside.findElement(By.xpath(".//td[@class='homecopysm']"));

        boolean expectedContent = "MY ACCOUNT".equalsIgnoreCase(bannerText) && homecopy.isDisplayed();

        Country country = (Country) stateHolder.get("context");
        //for jsp pages, country context will not show in the url
        boolean expectedURL = Util.countryContextURLCompliance(driver, country, "/account/home.jsp");

        return expectedContent;

    }

    public void clickInMenuOption(String menuOption) {
        By optionLocator = By.xpath(".//a[@class='my_account_lefnav' and contains(text(),'" + menuOption + "')]");
        WebElement option = main_content.findElement(optionLocator);
        option.click();
    }

    public boolean isOrderHistoryPage() {
        wait.until(ExpectedConditions.visibilityOf(main_content_history));
        WebElement myAccountBanner = main_content_history.findElement(By.tagName("h2"));
        String bannerText = myAccountBanner.getText();
        wait.until(ExpectedConditions.visibilityOf(leftContainer));
        WebElement myOrdersTitle = leftContainer.findElement(By.className("myaccount_list"));

        boolean expectedContent = "MY ACCOUNT".equalsIgnoreCase(bannerText) &&
                "My Orders".equalsIgnoreCase(myOrdersTitle.getText());

        Country country = (Country) stateHolder.get("context");
        boolean expectedURL = Util.countryContextURLCompliance(driver, country, "account/reg_user_order_history.jsp?");

        return expectedContent & expectedURL;
    }

    public void click_menu_link(String link,String page) {
        WebElement menu = null;

        Country c = (Country) stateHolder.get("context");

        // to validate the my account page left nav links
        //US: Gift card balance, Catalog Preferences,My Details, Email Preferences, Payment Methods, Address Book, Order History, Wish list & Sign Out
        //CANADA: All the above except Gift card balance will be there
        //All the other countries: Gift card Balance and Catalog Preferences will not be present

        switch (c.getCountry()) {
            case "us":
                menu = getMenuLink(link,page);
                break;
            case "ca":
                if(!link.equalsIgnoreCase("GIFT CARD BALANCE")) {
                    menu = getMenuLink(link,page);
                }
                break;
            default:
                if(!link.equalsIgnoreCase("GIFT CARD BALANCE") && !link.equalsIgnoreCase("CATALOG PREFERENCES") ) {
                    menu = getMenuLink(link,page);
                }
                break;
        }

        if(menu != null) {
            wait.until(ExpectedConditions.elementToBeClickable(menu));
            Util.clickWithStaleRetry(menu);
        }
    }




    private WebElement getMenuLink(String link,String page) {
        Util.waitForPageFullyLoaded(driver);
        Country country = (Country) stateHolder.get("context");
        logger.debug(country.getCountry());
     if("My Account".equalsIgnoreCase(page)) {
         wait.until(ExpectedConditions.visibilityOf(main_inside));
         return main_inside.findElement(By.linkText(link));
     }else{
         return new AccountDetail(driver).getMenuLink(link);
     }
    }

    public boolean verifyRewardLink(String link, String userCategory) {
        boolean expected = false;
        Country c = (Country) stateHolder.get("context");

        if (userCategory.equalsIgnoreCase(User.CAT_LOYALTY) && ("us".equalsIgnoreCase(c.getCountry())))
            expected = true;

        return expected == isMenuLinkPresent(link);

    }

    public boolean isInMenuLinkPage(String page) {
        Country c = (Country) stateHolder.get("context");

        // to validate the my account page left nav links
        //US: Gift card balance, Catalog Preferences,My Details, Email Preferences, Payment Methods, Address Book, Order History, Wish list & Sign Out
        //CANADA: All the above except Gift card balance will be present
        //All the other countries: Gift card Balance and Catalog Preferences will not be present. everything else will be there


        boolean forOtherCountries = !(page.contains("giftcard") || page.contains("catalog_preferences"));

        if (("ca".equals(c.getCountry()) && !(page.contains("giftcard"))) || "us".equals(c.getCountry()) || forOtherCountries)
            return wait.until(ExpectedConditions.urlContains(page));
        else {
            logger.info("expected no " + page + " for " + c.getCountry());
            return true;
        }

    }

    public boolean isMenuLinkPresent(String link) {
        Util.waitForPageFullyLoaded(driver);
        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(main_inside));
        try {
            WebElement menuLink = driver.findElement(By.xpath("//a[@class='my_account_lefnav' and contains(" + Util.xpathGetTextLower + ",'" + link.toLowerCase() + "')]"));
            return (menuLink.isDisplayed());
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
