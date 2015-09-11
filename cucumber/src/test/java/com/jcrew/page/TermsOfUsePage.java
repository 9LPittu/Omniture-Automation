package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TermsOfUsePage {
    private final WebDriver driver;

    public TermsOfUsePage(WebDriver driver) {
        this.driver = driver;
    }


    public boolean isTermsOfUsePage() {
        return new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("termsofuse.jsp"));
    }
}
