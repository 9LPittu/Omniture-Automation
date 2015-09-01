package com.jcrew.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SizeChartsPage {

    @FindBy(id="us-sizes")
    private WebElement sizesElement;

    public SizeChartsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isSizeChartPage() {
        return sizesElement.isDisplayed();
    }
}
