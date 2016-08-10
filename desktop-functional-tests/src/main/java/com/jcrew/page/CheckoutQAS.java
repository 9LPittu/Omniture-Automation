package com.madewell.page;

import com.madewell.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 6/2/16.
 */
public class CheckoutQAS extends Checkout {
    @FindBy(id = "modalContainer")
    private WebElement modal;
    @FindBy(id = "qas-interaction")
    private WebElement interaction;

    public CheckoutQAS(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(modal));
    }

    @Override
    public boolean isDisplayed() {
        return modal.isDisplayed();
    }

    public void selectSuggested() {
        WebElement suggested = interaction.findElement(By.className("address-suggested"));
        List<WebElement> suggestedAddresses = suggested.findElements(By.className("address-entry"));

        int random = Util.randomIndex(suggestedAddresses.size());

        WebElement selectedAddress = suggestedAddresses.get(random);
        WebElement radioButton = selectedAddress.findElement(By.className("address-radio"));
        radioButton.click();
    }

    public void clickContinue() {
        WebElement continueDiv = interaction.findElement(By.className("form-button"));
        WebElement continueButton = continueDiv.findElement(By.tagName("a"));

        continueButton.click();
    }
}
