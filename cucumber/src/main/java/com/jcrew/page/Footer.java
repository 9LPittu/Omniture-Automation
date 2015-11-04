package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Footer {

    private final WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(Footer.class);
    @FindBy(className = "js-footer__row__wrap--main")
    private WebElement footerWrapMain;
    @FindBy(className = "footer__row--bottom")
    private  WebElement footerRowBottom;

    public Footer(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isFooterLinkPresent(String footerLink) {
        return getFooterLinkElement(footerLink).isDisplayed();

    }

    private WebElement getFooterLinkElement(String footerLink) {
        try {
            return footerWrapMain.findElement(By.xpath("//h6[text()='" + footerLink + "']"));
        } catch (StaleElementReferenceException e) {
            logger.debug("Stale Element Exception was thrown, retry to click on footer element {}", footerLink);
            return getFooterLinkElement(footerLink);
        }
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
        WebElement footerSubTextElement = Util.createWebDriverWait(driver).until(
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
        Util.createWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(footerSublink));
        footerSublink.click();
    }


    public String getFooterHeaderLegend() {
        return footerWrapMain.findElement(By.tagName("legend")).getText();
    }

    public void click_bottom_link(String bottomLink) {
        footerRowBottom.findElement(By.linkText(bottomLink)).click();
    }
}
