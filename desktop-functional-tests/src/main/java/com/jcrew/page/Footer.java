package com.jcrew.page;

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

import static org.junit.Assert.assertEquals;

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

    @FindBy(className = "c-footer__social")
    private WebElement footer_social;

    public Footer(WebDriver driver) {
        this.driver = driver;
        this.wait = Util.createWebDriverWait(driver);

        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(global__footer));
        wait.until(ExpectedConditions.visibilityOf(footer_social));
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
            xpath = ".//a[contains(@class,'accordian__menu__link ') and text()=\"" + link + "\"]";
        } else {
            xpath = ".//a[contains(@class,'accordian__menu__link ') and text()='" + link + "']";
        }

        return drawerElement.findElement(By.xpath(xpath));
    }
    
    private WebElement getAccordionElement(String name) {
    	waitForFooter();
        WebElement header = footerWrapMain.findElement(
                By.xpath(".//h6[contains(@class,'footer__header') and text()='" + name + "']"));
        WebElement accordion = header.findElement(By.xpath(".//parent::div[contains(@class,'accordian__wrap--footer')]"));

        return wait.until(ExpectedConditions.visibilityOf(accordion));
    }

    public void clickFooterLinkFromDrawer(String linkText, String drawer) {
        WebElement footerLink = getAccordianLink(linkText, drawer);
        wait.until(ExpectedConditions.elementToBeClickable(footerLink));

        footerLink.click();
        Util.waitLoadingBar(driver);
    }
    public String getPageHeader(String page){
        String header2;
        switch (page.toLowerCase()){
            case "order status":
                header2 = "u.s. order tracking";
                break;
            case "shipping":
                header2 = "SHIPPING & HANDLING (THE FINE PRINT)";
                break;
            case "size charts":
                header2 = "SIZE CHARTS";
                break;
            case "returns & exchanges":
                header2 = "RETURNS & EXCHANGES";
                break;
            case "international orders":
                header2 = "INTERNATIONAL ORDERS";
                break;
            case "our catalog":
                header2 = "WANT TO GET OUR CATALOG?";
                break;
            case "careers":
                header2 = "SEARCH OUR JOBS";
                break;
            case "gift cards":
                header2 ="Go ahead-make someone’s day, in one of two ways.";
                break;
            case "mail preferences":
                header2 ="WANT FUN STUFF IN THE MAIL?";
                break;
            default:
                header2 = page;
                break;
        }
        return getHeader2Text(header2);
    }


    public String getHeader2Text(String headerText) {
        WebElement headerElement;
        String text;
        if (headerText.equalsIgnoreCase("size charts")) {
            headerElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h3"))));
            text = headerElement.getText().trim();
        } else {
            headerElement = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h2"))));
            text = headerElement.getText().trim();
        }
        return text;
    }
}
