package com.jcrew.page;

import com.jcrew.page.header.HeaderLogo;
import com.jcrew.pojo.Country;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
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
    private final StateHolder stateHolder = StateHolder.getInstance();

    @FindBy(id = "global__footer")
    private WebElement global__footer;

    @FindBy(className = "footer__country-context")
    private WebElement shipToSectionInFooter;

    @FindBy(xpath = ".//div[@class='footer__country-context']/descendant::span[@class='footer__country-context__country']")
    private WebElement countryNameInFooter;

    @FindBy(xpath = ".//div[@class='footer__country-context']/descendant::a[@class='footer__country-context__link']")
    private WebElement changeLinkInFooter;

    @FindBy(className = "footer__row--top")
    private WebElement footerRowTop;

    @FindBy(className = "js-footer__row__wrap--main")
    private WebElement footerWrapMain;

    @FindBy(xpath =".//div[contains(@class,'js-footer__row__wrap--bottom')]")
    private WebElement footerBottom;

    @FindBy(className = "c-footer__social")
    private WebElement footer_social;

    @FindBy(className = "footer__signup__form email__form")
    private WebElement signUp_from_footerSection;

    public Footer(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(global__footer));
        wait.until(ExpectedConditions.visibilityOf(footer_social));
        
        HeaderLogo logo = new HeaderLogo(driver);
        logo.hoverLogo();
    }

    private void waitForFooter() {
       try {
            new Footer(driver);
            wait.until(ExpectedConditions.visibilityOf(global__footer));
            wait.until(ExpectedConditions.visibilityOf(footer_social));
            wait.until(ExpectedConditions.visibilityOf(countryNameInFooter));
            wait.until(ExpectedConditions.elementToBeClickable(changeLinkInFooter));
        } catch (TimeoutException timeout) {
            logger.debug("Timed out while waiting for footer in page: {}", driver.getCurrentUrl());
            Logs errorLog = driver.manage().logs();
            LogEntries errors = errorLog.get(LogType.BROWSER);

            for (LogEntry error : errors) {
                logger.error("Broser logged: {}", error);
            }
        }
    }

    public String getCountry() {
        WebElement country = global__footer.findElement(By.className("footer__country-context__country"));
        return country.getText();
    }

    public boolean isShipToSectionDisplayed() {
        return shipToSectionInFooter.isDisplayed();
    }

    public boolean isCountryNameDisplayedInFooter() {
        return getCountryNameElementInFooter().isDisplayed();
    }

    public String getCountryNameInFooter() {
        return countryNameInFooter.getText().toLowerCase();
    }

    public WebElement getCountryNameElementInFooter() {
        return shipToSectionInFooter.findElement(By.className("footer__country-context__country"));

    }

    public boolean isChangeLinkDisplayedInFooter() {
        return changeLinkInFooter.isDisplayed();
    }

    public void clickChangeLinkInFooter() {
        WebElement changeLinkInFooter = shipToSectionInFooter.findElement(By.className("footer__country-context__link"));
        changeLinkInFooter.click();
        logger.info("clicked change link");
    }

    public boolean isCorrectCountryNameDisplayedInFooter() {
        Country c = (Country) stateHolder.get("context");
        String expectedCountryName = c.getName();

        waitForFooter();

        String actualCountryName = countryNameInFooter.getText();

        return actualCountryName.equalsIgnoreCase(expectedCountryName);
    }

    
    public boolean isLinkDisplayedInAccordion(String linkText, String accordionName){
    	WebElement footerLink = getAccordianLink(linkText, accordionName);
        return footerLink.isDisplayed();
    }


    private WebElement getAccordianLink(String link, String accordion) {
        WebElement drawerElement = getAccordionElement(accordion);
        String xpath;

        if (link.contains("'")) {
            xpath = ".//a[contains(@class,'footer__item__link') and translate(., 'ABCDEFGHJIKLMNOPQRSTUVWXYZ'," +
            "'abcdefghjiklmnopqrstuvwxyz')=\"" + link.toLowerCase() + "\"]";
        } else {
            xpath = ".//a[contains(@class,'footer__item__link') and translate(., 'ABCDEFGHJIKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghjiklmnopqrstuvwxyz')='" + link.toLowerCase() + "']";
        }

        return drawerElement.findElement(By.xpath(xpath));
    }

    public void clickFooterSocialLinks(String socialLink) {
        String xpath = ".//a[@class='footer__social__link' and contains(@href,'" + socialLink + "')]";
        WebElement sLink =  footer_social.findElement(By.xpath(xpath));
        sLink.click();
        Util.waitLoadingBar(driver);
    }

    public void clickFooterCopyRightLinks(String linkText){
        String xpath = ".//a[contains(@class,'footer__copyright__link') and text()='" + linkText + "']";
        logger.info(xpath);
        wait.until(ExpectedConditions.visibilityOf(footerBottom));
        WebElement link = footerBottom.findElement(By.xpath(xpath));
        link.click();
        Util.waitLoadingBar(driver);
    }
    private WebElement getAccordionElement(String name) {
    	waitForFooter();
        WebElement header = footerWrapMain.findElement(
                By.xpath(".//h6[contains(@class,'footer__header') and text()='" + name + "']"));
        WebElement accordion = header.findElement(By.xpath(".//parent::div[contains(@class,'accordian__wrap--footer')]"));
        return wait.until(ExpectedConditions.visibilityOf(accordion));
    }
    public void enterEmailInSignUp(String testEmailID){
        wait.until(ExpectedConditions.visibilityOf(signUp_from_footerSection));
        WebElement email_input = signUp_from_footerSection.findElement(By.name("subscribeEmail"));
        email_input.sendKeys(testEmailID);
    }
    public void clickSignUp(){
        wait.until(ExpectedConditions.visibilityOf(signUp_from_footerSection));
        WebElement email_signUp = signUp_from_footerSection.findElement(By.xpath(".//button[contains(@class,'js-footer__submit-button')]"));
        email_signUp.click();

    }

    public void clickFooterLinkFromDrawer(String linkText, String drawer) {
        WebElement footerLink = getAccordianLink(linkText, drawer);
        wait.until(ExpectedConditions.elementToBeClickable(footerLink));

        footerLink.click();
        Util.waitLoadingBar(driver);
    }

}
