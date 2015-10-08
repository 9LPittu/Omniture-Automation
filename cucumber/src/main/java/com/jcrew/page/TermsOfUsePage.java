package com.jcrew.page;

import com.jcrew.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TermsOfUsePage {
    private final WebDriver driver;

    public TermsOfUsePage(WebDriver driver) {
        this.driver = driver;
    }


    public boolean isTermsOfUsePage() {
        return Util.createWebDriverWait(driver).until(ExpectedConditions.urlContains("termsofuse.jsp"));
    }
}
