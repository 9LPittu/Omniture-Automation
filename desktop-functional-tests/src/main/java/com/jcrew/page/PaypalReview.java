package com.jcrew.page;

import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaypalReview extends PageObject {

	@FindBy(xpath = "//input[@id='confirmButtonTop']")
	private WebElement continueButton;
	
	@FindBy(xpath = "//*[@class='btn full confirmButton continueButton']")
	private WebElement confirmButton;
	
	public PaypalReview(WebDriver driver) {
		super(driver);
		Util.waitForPageFullyLoaded(driver);
		//PageFactory.initElements(driver, this);
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
	}

	public void clickContinue() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
			wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
			Util.wait(1000);
			confirmButton.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("spinner")));
			wait.until(ExpectedConditions.elementToBeClickable(continueButton));
			Util.wait(1000);
			continueButton.click();
		} catch (Exception e) {
			wait.until(ExpectedConditions.elementToBeClickable(continueButton));
			Util.wait(1000);
			continueButton.click();
		}
	}
}
