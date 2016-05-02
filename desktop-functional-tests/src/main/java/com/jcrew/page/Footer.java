package com.jcrew.page;

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

/**
 * Created by nadiapaolagarcia on 4/19/16.
 */
public class Footer {
    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Footer.class);
    private final WebDriverWait wait;

    @FindBy(id = "global__footer")
    private WebElement global__footer;

    @FindBy(className="footer__country-context")
    private WebElement shipToSectionInFooter;

    @FindBy(xpath=".//div[@class='footer__country-context']/descendant::span[@class='footer__country-context__country']")
    private WebElement countryNameInFooter;

    @FindBy(xpath=".//div[@class='footer__country-context']/descendant::a[@class='footer__country-context__link']")
    private WebElement changeLinkInFooter;

    public Footer(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(global__footer));
    }

    public String getCountry() {
        WebElement country = global__footer.findElement(By.className("footer__country-context__country"));
        String countryName = country.getText();

        return  countryName;
    }

    public boolean isShipToSectionDisplayed(){
        return shipToSectionInFooter.isDisplayed();
    }

    public boolean isCountryNameDisplayedInFooter(){
        return countryNameInFooter.isDisplayed();
    }

    public boolean isChangeLinkDisplayedInFooter(){
        return changeLinkInFooter.isDisplayed();
    }

    public void clickChangeLinkInFooter(){
        changeLinkInFooter.click();
    }



}
