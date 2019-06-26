package com.jcrew.page;

import com.jcrew.utils.Util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailCapture extends PageObject {

	@SuppressWarnings("unused")
	private final WebDriverWait shortWait;

	@FindBy(xpath = "//div[contains(@class,'bx-creative bx-creative')]")
	private WebElement global__email_capture;

	@FindBy(xpath = "(//*[@class='bx-close-xstroke bx-close-x-adaptive'])[1]")
	private WebElement closeIcon_jcrew;
	@FindBy(xpath = "//div[@name='close button']//*[@class='icon-close']")
	private WebElement closeIcon_factory;
	@FindBy(xpath = "//*[text()='Continue to site ']")
	private WebElement continueToSite;
	String currentUrl = driver.getCurrentUrl();

	public EmailCapture(WebDriver driver) {
		super(driver);
		this.shortWait = Util.createWebDriverWait(driver, 30);
		PageFactory.initElements(driver, this);
	}

	public void closeEmailCapture() {
		try {
			if (currentUrl.contains("factory")) {
				wait.until(ExpectedConditions.elementToBeClickable(continueToSite));
				continueToSite.click();
				wait.until(ExpectedConditions.elementToBeClickable(closeIcon_factory));
				logger.debug("Email capture is visible, closing.");
				closeIcon_factory.click();

			} else {
				closeIcon_jcrew.isDisplayed();
				logger.debug("Email capture is visible, closing.");
				closeIcon_jcrew.click();
			}
		} catch (Exception e) {
			logger.error("No email capture was found. Ignoring Error.");
		}

	}
}