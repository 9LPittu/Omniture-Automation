package com.jcrew.page;

import com.jcrew.util.PropertyReader;
import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Navigation {

    private final WebDriver driver;
    private final PropertyReader reader = PropertyReader.getPropertyReader();
    private final Logger logger = LoggerFactory.getLogger(Navigation.class);

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
        String browser = reader.getProperty("browser");
        String targetPage;

        if("iossafari".equals(browser) ||"androidchrome".equals(browser) || "phantomjs".equals(browser)){
            switch (page){
                case "facebook":
                    targetPage = "https://m.facebook.com/jcrew";
                    break;
                case "twitter":
                    targetPage = "https://mobile.twitter.com/jcrew";
                    break;
                case "youtube":
                    targetPage = "https://m.youtube.com/user/jcrewinsider";
                    break;
                default:
                    targetPage = page;
            }
        } else {
            switch (page){
                case "facebook":
                    targetPage = "https://www.facebook.com/jcrew";
                    break;
                case "twitter":
                    targetPage = "https://twitter.com/jcrew";
                    break;
                case "youtube":
                    targetPage = "https://www.youtube.com/user/jcrewinsider";
                    break;
                default:
                    targetPage = page;
            }
        }

        Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains(targetPage));
        String currentUrl = driver.getCurrentUrl();
        logger.debug("Current page: "+currentUrl);
        return currentUrl.contains(targetPage);
    }
}
