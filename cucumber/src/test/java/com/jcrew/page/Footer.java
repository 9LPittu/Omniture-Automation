package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Footer {

    private final WebDriver driver;

    @FindBy(className = "js-footer__row__wrap--main")
    private WebElement footerWrapMain;
    private Logger logger = LoggerFactory.getLogger(Footer.class);

    public Footer(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isFooterLinkPresent(String footerLink) {
        return getFooterLinkElement(footerLink).isDisplayed();

    }

    private WebElement getFooterLinkElement(String footerLink) {
        WebElement footerLinkElement = null;
        int attempts = 0;
        while (attempts < 2) {
            try {
                footerLinkElement = footerWrapMain.findElement(By.xpath("//h6[text()='" + footerLink + "']"));
                break;
            } catch (StaleElementReferenceException e) {
                logger.debug("Stale Element Exception was thrown, retry {} to click on footer element {}",
                        attempts + 1, footerLink);
            }
            attempts++;
        }

        return footerLinkElement;
    }

    public List<String> getFooterHeaderNames() {
        List<WebElement> footerHeaderNamesElements = footerWrapMain.findElements(By.tagName("h6"));
        List<String> footerHeaderNamesText = new ArrayList<>();

        for (WebElement footerHeaderElement : footerHeaderNamesElements) {
            footerHeaderNamesText.add(footerHeaderElement.getText());
        }
        return footerHeaderNamesText;
    }

    public void click_on(String footerLink) {
        getFooterLinkElement(footerLink).click();
    }


    public String getFooterSubText(String footerLink) {
        WebElement listOfSubElements = getListOfSubElementsForFooterLink(footerLink);
        WebElement footerSubTextElement = new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOf(listOfSubElements.findElement(By.className("footer__item__text"))));
        return footerSubTextElement.getText();
    }

    private WebElement getListOfSubElementsForFooterLink(String footerLink) {
        WebElement footerLinkElement = getFooterLinkElement(footerLink);
        return footerLinkElement.findElement(By.xpath("following-sibling::ul"));
    }

    public void click_sublink_from(String footerSubLink, String footerLink) {
        WebElement listOfSubElements = getListOfSubElementsForFooterLink(footerLink);
        WebElement footerSublink = listOfSubElements.findElement(By.linkText(footerSubLink));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(footerSublink));
        footerSublink.click();
    }


    public String getFooterHeaderLegend() {
        return footerWrapMain.findElement(By.tagName("legend")).getText();
    }
}
