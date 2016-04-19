package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nadiapaolagarcia on 4/18/16.
 */
public class WelcomeMat {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(WelcomeMat.class);
    private final WebDriverWait wait;
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final Country country;

    private WebElement welcomemat;
    private WebElement logo;
    private WebElement country_context;
    private WebElement header;
    private WebElement body;
    private WebElement footer;
    private WebElement terms;

    public WelcomeMat(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);
        this.country = (Country) stateHolder.get("context");

        PageFactory.initElements(driver, this);

        welcomemat = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.className("js-global-welcomemat-wrapper")));

        logo = welcomemat.findElement(By.className("c-header__welcomemat--logo"));
        country_context = welcomemat.findElement(By.className("c-header__welcomemat--country-context"));
        header = welcomemat.findElement(By.className("c-header__welcomemat--header"));
        footer = welcomemat.findElement(By.className("c-header__welcomemat--footer"));
        terms = welcomemat.findElement(By.className("c-header__welcomemat--terms"));

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

    public boolean verifyCountryContext() {
        boolean result;
        String countryName = country.getName().toLowerCase();
        String countryFlagName = countryName.replace(" ", "");

        String countryContextText = country_context.getText().toLowerCase();
        result = countryContextText.equals(countryName);

        WebElement flag = country_context.findElement(By.className("c-header__welcomemat--flag"));
        String flagClass = flag.getAttribute("class");

        result &= flagClass.contains(countryFlagName);

        return result;
    }

    public boolean verifyWelcomeMessage() {
        boolean result;
        WebElement headerH2 = header.findElement(By.tagName("h2"));
        String headerH2Text = headerH2.getText();

        if ("CA".equalsIgnoreCase(country.getCountry())) {
            result = headerH2Text.equalsIgnoreCase("Hello, Canada");
        } else {
            result = headerH2Text.equalsIgnoreCase("Around the World");
        }

        return result;
    }

    public void clickStartShopping() {
        WebElement startShopping = footer.findElement(By.className("js-start-shopping-button"));
        startShopping.click();
    }
}
