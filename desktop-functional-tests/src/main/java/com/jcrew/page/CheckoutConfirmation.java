package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
public class CheckoutConfirmation extends Checkout {

    @FindBy(id = "confirmation-number")
    private WebElement confirmation_number;
    @FindBy(id = "gifting-details")
    private WebElement giftDetails;

    public CheckoutConfirmation(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.visibilityOf(confirmation_number));
    }

    public boolean isDisplayed() {
        String bodyId = getBodyAttribute("id");
        logger.debug("class: {}", getBodyAttribute("class"));

        return bodyId.equals("confirmation");
    }

    public String getConfirmationCode() {
        WebElement numberElement = confirmation_number.findElement(By.className("order-number"));

        return numberElement.getText().trim();
    }

    public String getGitfMessage() {
        WebElement options = giftDetails.findElement(By.className("options-receiptmsg"));
        WebElement message = options.findElement(By.tagName("pre"));

        return message.getText();
    }
}
