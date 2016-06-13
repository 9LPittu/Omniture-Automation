package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/30/16.
 */
public class HomePage {
    private final WebDriver driver;
    private HeaderWrap headerWrap;
    private Footer footer;
    private final Logger logger = LoggerFactory.getLogger(HomePage.class);
    private final StateHolder stateHolder = StateHolder.getInstance();
    private WebDriverWait wait;

    @FindBy(id = "page__home")
    private WebElement pageContent;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        headerWrap = new HeaderWrap(driver);
        footer = new Footer(driver);
        wait = Util.createWebDriverWait(driver);
        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(pageContent));
    }

    public boolean isHomePage() {
        headerWrap.reload();
        WebElement body = driver.findElement(By.tagName("body"));
        String bodyClass = body.getAttribute("class");

        return "jcrew home".equals(bodyClass);
    }

    public boolean verifyInternational() {
        Country country = (Country) stateHolder.get("context");
        String countryName = country.getName();
        String countryFooter = footer.getCountry();

        boolean urlCompliance = Util.countryContextURLCompliance(driver, country);
        boolean footerCompliance = countryName.equalsIgnoreCase(countryFooter);

        return urlCompliance & footerCompliance;
    }


}
