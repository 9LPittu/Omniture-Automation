package com.jcrew.page;

import com.google.common.base.Function;
import com.jcrew.pojo.Country;
import com.jcrew.util.StateHolder;
import com.jcrew.util.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public abstract class PageObject {

    protected final WebDriver driver;
    public final Logger logger = LoggerFactory.getLogger(PageObject.class);
    protected final WebDriverWait wait;

    public final StateHolder stateHolder = StateHolder.getInstance();
    public final Country country;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
        this.country = (Country) stateHolder.get("context");
    }

    public boolean verifyURL() {
        return Util.countryContextURLCompliance(driver, country);
    }
    
    public boolean urlContains(String url) {
        String driverCurrentUrl = driver.getCurrentUrl();
        logger.debug("current url: {}, expecting to contain: {}", driverCurrentUrl, url);
        return driverCurrentUrl.contains(url);
    }

    public String getCookiePath(String cookieName) {
        String cookiePath;
        Cookie cookie = driver.manage().getCookieNamed(cookieName);
        if (cookie == null) {
            cookiePath = "";
            Set<Cookie> cookieSet = driver.manage().getCookies();
            for (Cookie c : cookieSet) {
                logger.debug("domain : {} cookie : {}", c.getDomain(), c.getName());
            }
        } else {
            cookiePath = cookie.getPath();
        }
        return cookiePath;
    }
    
    public String getBodyAttribute(final String attribute) {
        return wait.until(new Function<WebDriver, String>() {
            @Override
            public String apply(WebDriver driver) {
                List<WebElement> body = null;
                try {
                    body = driver.findElements(By.tagName("body"));
                    return body.get(0).getAttribute(attribute);
                } catch (StaleElementReferenceException stale) {
                    logger.error("Exception when trying to get body element.");
                }
                return null;
            }
        });
    }
}
