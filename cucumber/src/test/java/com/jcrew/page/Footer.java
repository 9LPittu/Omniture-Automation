package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Footer {

    @FindBy(className = "js-footer__row__wrap--main")
    private WebElement footerWrapMain;

    public Footer(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isFooterLinkPresent(String footerLink) {
        return footerWrapMain.findElement(By.xpath("//h6[text()='" + footerLink + "']")).isDisplayed();

    }

    public List<String> getFooterHeaderNames() {
        List<WebElement> footerHeaderNamesElements = footerWrapMain.findElements(By.tagName("h6"));
        List<String> footerHeaderNamesText = new ArrayList<>();

        for (WebElement footerHeaderElement: footerHeaderNamesElements) {
            footerHeaderNamesText.add(footerHeaderElement.getText());
        }
        return footerHeaderNamesText;
    }
}
