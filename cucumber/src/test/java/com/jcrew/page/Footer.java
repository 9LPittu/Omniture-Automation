package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Footer {

    @FindBy(className = "footer__row__wrap")
    private WebElement footerWrap;

    public Footer(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isFooterLinkPresent(String footerLink) {
        return footerWrap.findElement(By.xpath("//h6[text()='" + footerLink + "']")).isDisplayed();

    }
}
