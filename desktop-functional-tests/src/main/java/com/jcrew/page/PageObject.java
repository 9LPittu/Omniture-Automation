package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by nadiapaolagarcia on 7/19/16.
 */
public abstract class PageObject {

    protected final WebDriver driver;
    protected final Logger logger = LoggerFactory.getLogger(PageObject.class);
    protected final WebDriverWait wait;

    protected final StateHolder stateHolder = StateHolder.getInstance();
    public final Country country;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
        this.country = (Country) stateHolder.get("context");
    }

    public boolean verifyURL() {
        return Util.countryContextURLCompliance(driver, country);
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
}
