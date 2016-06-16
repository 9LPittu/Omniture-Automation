package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.PropertyReader;
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

import java.util.List;

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
            wait.until(ExpectedConditions.visibilityOf(global__footer));
            wait.until(ExpectedConditions.visibilityOf(footer_social));
            wait.until(ExpectedConditions.visibilityOf(countryNameInFooter));
            wait.until(ExpectedConditions.elementToBeClickable(changeLinkInFooter));
        } catch (TimeoutException timeout) {
            logger.debug("Timed out while waiting for header in page: {}", driver.getCurrentUrl());
            Logs errorLog = driver.manage().logs();
            LogEntries errors = errorLog.get(LogType.BROWSER);

            for (LogEntry error : errors) {
                logger.error("Broser logged: {}", error.getMessage());
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

    public void closeEmailCapture() {
        PropertyReader propertyReader = PropertyReader.getPropertyReader();
        String browser = propertyReader.getProperty("browser");

        if ("chrome".equals(browser) || "firefox".equals(browser)) {
            List<WebElement> email_capture_list = driver.findElements(By.id("global__email-capture"));
            if(email_capture_list.size() > 0) {
                WebElement email_capture = email_capture_list.get(0);
                WebElement closeButton = email_capture.findElement(By.className("js-email-capture--close"));
                WebElement closeIcon = closeButton.findElement(By.tagName("span"));

                wait.until(ExpectedConditions.elementToBeClickable(closeIcon));
                closeIcon.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("js-email-capture--close")));
            }
        }
    }
}
