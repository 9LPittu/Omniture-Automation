package com.jcrew.page;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
public class WelcomeMat extends PageObject{

    @FindBy(className = "js-global-welcomemat-wrapper")
    private WebElement welcomemat;
    @FindBy(className = "c-header__welcomemat--logo")
    private WebElement logo;
    @FindBy(className = "c-header__welcomemat--country-context")
    private WebElement country_context;
    @FindBy(className = "c-header__welcomemat--header")
    private WebElement header;
    private WebElement body;
    @FindBy(className = "c-header__welcomemat--footer")
    private WebElement footer;
    @FindBy(className = "c-header__welcomemat--terms")
    private WebElement terms;

    public WelcomeMat(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        wait.until(ExpectedConditions.visibilityOf(welcomemat));

        if ("CA".equalsIgnoreCase(country.getCountry())) {
            body = welcomemat.findElement(By.className("c-header__welcomematCanada--byline"));
        } else {
            body = welcomemat.findElement(By.className("c-header__welcomemat--body"));
        }
    }

    public boolean verifyMatElements() {
        WebElement mat = welcomemat.findElement(By.className("c-header__welcomemat"));

        return mat.isDisplayed()
                && !logo.isDisplayed()
                && country_context.isDisplayed()
                && header.isDisplayed()
                && body.isDisplayed()
                && footer.isDisplayed()
                && terms.isDisplayed();
    }

    public String getCountry() {
        String countryContextText = country_context.getText().toLowerCase();
        logger.debug("Country name in welcome mat: {}", countryContextText);

        return countryContextText;
    }

    public String getCountryFlagClass() {
        WebElement flag = country_context.findElement(By.className("c-header__welcomemat--flag"));
        String flagClass = flag.getAttribute("class");
        logger.debug("Country flag class in welcome mat: {}", flagClass);

        return flagClass;
    }

    public String getWelcomeMessage() {
        WebElement headerH2 = header.findElement(By.tagName("h2"));

        return headerH2.getText();
    }

    public void clickStartShopping() {
        WebElement startShopping = footer.findElement(By.className("js-start-shopping-button"));
        startShopping.click();
    }

    public void goToUsSite(){
        String url = driver.getCurrentUrl();
        WebElement usSite = footer.findElement(By.className("js-to-us-site"));
        usSite.click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(url)));
        Util.waitForPageFullyLoaded(driver);
    }
}
