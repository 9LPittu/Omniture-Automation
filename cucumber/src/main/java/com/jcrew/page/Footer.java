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
    @FindBy(className = "footer__row--top")
    private  WebElement footerRowTop;
    @FindBy(className = "js-footer__menu")
    private List<WebElement> subLinks;

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

    public boolean isSubLinkDisplayed(String sublink) {
        boolean subLinkDisplayed = false;
        for(WebElement subLink: subLinks) {
            subLinkDisplayed = subLink.findElement(By.xpath("//a[text()='" + sublink + "']")).isDisplayed();
        }
            return subLinkDisplayed;
    }


    public String getFooterHeaderLegend() {
        return footerWrapMain.findElement(By.tagName("legend")).getText();
    }

    public boolean isTopHeaderVisible(String text) {
        return footerRowTop.findElement(By.xpath("//h6[text()='" + text + "']")).isDisplayed();
    }

    public boolean isIconAndTextDisplayed(String icon) {
        List<WebElement> contactUsIconsList = footerRowTop.findElements(By.className("footer__help__menu"));
        boolean iconDisplayed = false;
        for(WebElement contactUsIcon: contactUsIconsList) {
            //Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(contactUsIcon.findElement(By.cssSelector("a[href*='"+icon+"']"))));
            System.out.println(contactUsIcon.getText());
             iconDisplayed =contactUsIcon.findElement(By.className("footer__help__item--"+icon)).isDisplayed();
        }
        return iconDisplayed;
    }

    public boolean isSocialIconDisplayed(String socialIcon) {
        List<WebElement> socialNetworkIconsList = footerWrapMain.findElements(By.className("footer__social__menu"));
        boolean iconDisplayed = false;
        for(WebElement socialNetworkIcon: socialNetworkIconsList) {
            //Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(contactUsIcon.findElement(By.cssSelector("a[href*='"+icon+"']"))));
            iconDisplayed =socialNetworkIcon.findElement(By.className("footer-"+socialIcon)).isDisplayed();
        }
        return iconDisplayed;
    }

    public boolean isEmailFieldDisplayed()  {
        return footerWrapMain.findElement(By.tagName("input")).isDisplayed();
    }


    public void click_bottom_link(String bottomLink) {
        footerRowBottom.findElement(By.linkText(bottomLink)).click();
    }
}
