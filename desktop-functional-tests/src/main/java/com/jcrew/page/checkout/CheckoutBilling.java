package com.jcrew.page.checkout;

import com.jcrew.page.PaypalLogin;
import com.jcrew.page.PaypalReview;
import com.jcrew.pojo.Address;
import com.jcrew.pojo.Country;
import com.jcrew.pojo.User;
import com.jcrew.steps.E2ECommon;
import com.jcrew.utils.E2EPropertyReader;
import com.jcrew.utils.TestDataReader;
import com.jcrew.utils.Util;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by nadiapaolagarcia on 5/9/16.
 */
@SuppressWarnings({ "deprecation"})
public class CheckoutBilling extends Checkout {

	@FindBy(id = "method-container")
	private WebElement method_container;
	@FindBy(id = "credit-card-billing")
	private WebElement card_container;
	@FindBy(id = "wallet")
	private WebElement wallet_container;
	@FindBy(id = "payment_page")
	private WebElement payment_page;
	@FindBy(id = "creditCardNumber")
	private WebElement creditCardNumber;
	@FindBy(id = "securityCode")
	private WebElement securityCode;
	@FindBy(id = "nameOnCard")
	private WebElement nameOnCard;
	@FindBy(id = "emailReceipt")
	private WebElement emailReceipt;
	@FindBy(id = "expirationMonth")
	private WebElement expirationMonth;
	@FindBy(id = "expirationYear")
	private WebElement expirationYear;
	@FindBy(id = "address-entry-new")
	private WebElement newAddressEntry;
	@FindBy(id = "paypalPayment")
	private WebElement paypalRadioButton;
	@FindBy(xpath = "(//iframe[@class='xcomponent-component-frame xcomponent-visible'])[1]")
	private WebElement frame;
	@FindBy(xpath = "//div[@id='order-summary']/div[2]/div/div")
	private WebElement paypalButton;
	@FindBy(xpath = "//div[@id='summary-gift-card-header']/h3[contains(text(),'Gift or Credit Card Reward?')]")
	private WebElement giftCardButton;
	@FindBy(xpath = "//input[@id='giftCard']")
	private WebElement cardNumberField;
	@FindBy(xpath = "//input[@id='giftCardPin']")
	private WebElement cardPinField;
	@FindBy(xpath = "//a[@id='giftApply']")
	private WebElement applyButton;
	@FindBy(xpath = "//div[@id='summary-gift-card-form']/a[@class='item-remove']")
	private WebElement removeButton;
	@FindBy(xpath = "//a[@data-bma='continue_selected_address']")
	private WebElement selectedAddress;
	@FindBy(xpath = "//div[@class='address-suggested']/div/ul/li/label/input[2]")
	private WebElement addressRadioButton;
	@FindBy(xpath = "//div[@id='billing-details']/h2/a")
	private WebElement changeButton;
	

	protected TestDataReader testdataReader = TestDataReader.getTestDataReader();

	public CheckoutBilling(WebDriver driver) {
		super(driver);
		wait.until(ExpectedConditions.visibilityOf(payment_page));
	}

	@Override
	public boolean isDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(payment_page));
		String bodyId = getBodyAttribute("id");

		return bodyId.equals("billing");
	}

	public void switchToPayPalWindow() {
		Util.waitForPageFullyLoaded(driver);
		String parentWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		allWindows.remove(parentWindow);
		if (allWindows.size() > 0) {
			for (String curWindow : allWindows) {
				driver.switchTo().window(curWindow);
				Util.waitForPageFullyLoaded(driver);
				PaypalLogin paypalLogin = new PaypalLogin(driver);
				String paypalEmail = testdataReader.getData("paypal.email");
				String paypalPassword = testdataReader.getData("paypal.password");
				paypalLogin.submitPaypalCredentials(paypalEmail, paypalPassword);
				PaypalReview paypalReview = new PaypalReview(driver);
				paypalReview.clickContinue();

				stateHolder.put("isBillingContinueClicked", true);
			}
			driver.switchTo().window(parentWindow);
		}
	}

	private void fillCardFields(String key, boolean isGuest) {
		TestDataReader testData = TestDataReader.getTestDataReader();
		User checkoutUSer;

		creditCardNumber.sendKeys(testData.getData(key + ".number"));
		securityCode.sendKeys(testData.getData(key + ".cvv"));

		Select month = new Select(expirationMonth);
		month.selectByVisibleText(testData.getData(key + ".month"));

		Select year = new Select(expirationYear);
		year.selectByVisibleText(testData.getData(key + ".year"));

		if (isGuest) {
			checkoutUSer = User.getFakeUser();
		} else {
			checkoutUSer = User.getUser(User.NO_DEFAULT);
		}

		nameOnCard.sendKeys(checkoutUSer.getFirstName() + " " + checkoutUSer.getLastName());

		if (isGuest)
			emailReceipt.sendKeys(checkoutUSer.getEmail());
	}

	public void fillPaymentMethod(boolean isGuest) {
		fillCardFields("card", isGuest);
	}

	public void fillPaymentMethod(String card) {
		fillCardFields(card + ".card", true);
	}

	public void continueCheckout() {
		if (stateHolder.hasKey("isBillingContinueClicked"))
			return;

		nextStep(payment_page);
	}

	public List<String> getPaymentMethods() {
		List<WebElement> methods = method_container.findElements(By.className("form-radio-set"));
		List<String> methodsString = new ArrayList<>(methods.size());

		for (WebElement method : methods) {
			String methodString = method.getText().trim();
			methodsString.add(methodString);
		}

		return methodsString;
	}

	public List<String> getAcceptedCards() {
		WebElement cardsContainer = card_container.findElement(By.className("credit-card-icons-id"));
		List<WebElement> methods = cardsContainer.findElements(By.className("credit-card-icon"));
		List<String> methodsString = new ArrayList<>(methods.size());

		for (WebElement method : methods) {
			String methodString = method.getAttribute("class");
			methodString = methodString.replace("credit-card-icon cc-", "");
			methodString = methodString.replace("-id", "");

			methodsString.add(methodString);
		}

		return methodsString;
	}

	public void addNewBillingAddress() {
		WebElement label = newAddressEntry.findElement(By.tagName("label"));
		Util.scrollToElement(driver, label);
		label.click();
	}

	public void addNewCard() {
		WebElement addNewCardElement = payment_page.findElement(By.id("address-new"));
		addNewCardElement.click();
	}

	public void removeCard(String type) {
		WebElement cardElement = wait.until(ExpectedConditions.elementToBeClickable(payment_page
				.findElement(By.xpath(".//span[contains(@class,'wallet-brand') and text()='" + type + "']"))));
		cardElement.click();
		Util.waitLoadingBar(driver);

		WebElement cardInfoElement = cardElement.findElement(By.xpath(".//ancestor::label"));
		String cardInfo = cardInfoElement.getText();
		stateHolder.put("removedCard", cardInfo);
		logger.debug("Removed card:\n{}", cardInfo);

		WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(cardElement
				.findElement(By.xpath(".//ancestor::label/following-sibling::span/a[@class='item-remove']"))));

		removeButton.click();
		Util.waitLoadingBar(driver);

		// checking one more time if the payment method is removed
		// In phantomjs, card is not removing. So, using alternate way to delete the
		// payment method
		try {
			cardElement = wait.until(ExpectedConditions.elementToBeClickable(payment_page
					.findElement(By.xpath(".//span[contains(@class,'wallet-brand') and text()='" + type + "']"))));

			if (cardElement.isDisplayed()) {
				removeButton = wait.until(ExpectedConditions.elementToBeClickable(cardElement
						.findElement(By.xpath(".//ancestor::label/following-sibling::span/a[@class='item-remove']"))));

				driver.get(removeButton.getAttribute("href"));
				Util.waitLoadingBar(driver);
			}
		} catch (Exception e) {
			logger.debug("Remove button is no longer displayed for '{}' card", type);
		}
	}

	public void editCard() {
		List<WebElement> cards = payment_page
				.findElements(By.xpath(".//span[contains(@class,'wallet-brand')]/ancestor::label"));

		WebElement cardToBeEdited = cards.get(cards.size() - 1);
		WebElement editButton = cardToBeEdited
				.findElement(By.xpath(".//following-sibling::span/a[@class='item-edit']"));

		editButton.click();
	}

	public List<String> getCards() {
		wait.until(ExpectedConditions.visibilityOf(payment_page));
		List<WebElement> cards = payment_page
				.findElements(By.xpath(".//span[contains(@class,'wallet-brand')]/ancestor::label"));
		List<String> cardsInfo = new ArrayList<>(cards.size());

		for (WebElement card : cards) {
			logger.debug("I have a card with this info:\n{}", card.getText());
			cardsInfo.add(card.getText());
		}

		return cardsInfo;
	}

	public void SelectPaymentMethodNoDefault() {
		List<WebElement> paymentMethodRadioButtons = wallet_container
				.findElements(By.xpath(".//input[@class='address-radio' and not(@checked='')]"));
		int randomIndex = Util.randomIndex(paymentMethodRadioButtons.size());

		paymentMethodRadioButtons.get(randomIndex).click();
		Util.waitLoadingBar(driver);

		WebElement selectedPaymentMethodLabel = paymentMethodRadioButtons.get(randomIndex)
				.findElement(By.xpath(".//parent::label"));
		String paymentMethod = selectedPaymentMethodLabel.getText();
		logger.debug("Selected payment method: {}", paymentMethod);

		stateHolder.put("selectedPaymentMethod", paymentMethod);
	}

	
	public void selectSpecificPaymentMethod(String paymentMethodName) {
		if (paymentMethodName.equalsIgnoreCase("Gift Card")) {
			E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
			Util.wait(3000);
			//Util.scrollAndClick(driver, giftCardButton);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", giftCardButton);
			Util.wait(1000);
			giftCardButton.click();
			Util.wait(1000);
			cardNumberField.sendKeys(e2ePropertyReader.getProperty("giftcard.card.number"));
			Util.wait(1000);
			cardPinField.sendKeys(e2ePropertyReader.getProperty("giftcard.card.pin"));
			Util.wait(1000);
			applyButton.click();
			Util.waitForPageFullyLoaded(driver);
			Assert.assertTrue(removeButton.isDisplayed());
		} else {
			E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
			String cardNumber = e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".card.number");
			String lastFourDigitsOfCardNum = cardNumber.substring(cardNumber.length() - 4);

			String cardDisplayName = e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".display.name");

			List<WebElement> paymentMethodElements = wallet_container
					.findElements(By.xpath(".//span[contains(@class,'wallet-brand') and " + Util.xpathGetTextLower
							+ "='" + cardDisplayName.toLowerCase()
							+ "']/following-sibling::span[contains(@class,'wallet-line')"
							+ " and contains(normalize-space(.),'" + lastFourDigitsOfCardNum + "')]"));

			WebElement paymentRadioButton = paymentMethodElements.get(0)
					.findElement(By.xpath("preceding-sibling::input[@class='address-radio']"));
			paymentRadioButton.click();
		}
	}

	public void fillPaymentCardDetails(String paymentMethodName) {
		E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();

		creditCardNumber.sendKeys(e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".card.number"));

		if (!paymentMethodName.equalsIgnoreCase("JCC")) {
			securityCode.sendKeys(e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".security.code"));

			Select month = new Select(expirationMonth);
			month.selectByVisibleText(
					e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".expiration.month"));

			Select year = new Select(expirationYear);
			year.selectByVisibleText(
					e2ePropertyReader.getProperty(paymentMethodName.toLowerCase() + ".expiration.year"));
		}
		User user = User.getNewFakeUser();
		nameOnCard.sendKeys(user.getFirstName() + " " + user.getLastName());
		emailReceipt.sendKeys(user.getEmail());
		E2ECommon e2e = new E2ECommon();
		Country countryPojo = stateHolder.get("context");
		Address address = new Address(countryPojo.getCountry());
		if (e2e.getDataFromTestDataRowMap("OrderType").equalsIgnoreCase("STS")) {
			WebElement firstName = driver.findElement(By.name("ADDRESS<>firstName"));
			firstName.sendKeys(user.getFirstName());

			WebElement lastName = driver.findElement(By.name("ADDRESS<>lastName"));
			lastName.sendKeys(user.getFirstName());

			WebElement address1 = driver.findElement(By.name("ADDRESS<>address1"));
			address1.sendKeys(address.getLine1());

			WebElement address2 = driver.findElement(By.name("ADDRESS<>address2"));
			address2.sendKeys(address.getLine2());

			WebElement zipcode = driver.findElement(By.name("ADDRESS<>postal"));
			zipcode.sendKeys(address.getZipcode());
			Util.wait(2000);
			WebElement phone = driver.findElement(By.name("ADDRESS<>phone"));
			phone.sendKeys(address.getPhone());
		}
	}

	public void clickTwoCardsPayment() {
		WebElement payWithTwoCardsElement = payment_page.findElement(
				By.xpath(".//a[contains(@class,'item-link-submit') and contains(text(),'pay with two cards')]"));
		payWithTwoCardsElement.click();
		wait.until(ExpectedConditions.visibilityOf(payment_page.findElement(
				By.xpath(".//a[contains(@class,'item-link-submit') and contains(text(),'pay with one card')]"))));

		List<WebElement> numberofCardsAvailable = payment_page.findElements(By.xpath(".//li[contains(@id, 'cardId')]"));
		stateHolder.put("numberofCardsAvailable", numberofCardsAvailable.size());
	}

	public void splitPayment(String paymentMethod1, String paymentMethod2) {
		E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
		String cardShortName1 = e2ePropertyReader.getProperty(paymentMethod1.toLowerCase() + ".short.name");
		String cardShortName2 = e2ePropertyReader.getProperty(paymentMethod2.toLowerCase() + ".short.name");

		String cardNumber1 = e2ePropertyReader.getProperty(paymentMethod1.toLowerCase() + ".card.number");
		String cardNumber2 = e2ePropertyReader.getProperty(paymentMethod2.toLowerCase() + ".card.number");

		String lastFourDigitsOfCardNum1 = cardNumber1.substring(cardNumber1.length() - 4);
		String lastFourDigitsOfCardNum2 = cardNumber2.substring(cardNumber2.length() - 4);

		WebElement splitPaymentForm = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-multitender")));

		int numberofCardsAvailable = stateHolder.get("numberofCardsAvailable");

		String valueToBeSelected;
		Select select;
		List<WebElement> paymentDropdowns = splitPaymentForm
				.findElements(By.xpath(".//select[contains(@id, 'distributionCard')]"));

		if (numberofCardsAvailable > 2) {
			valueToBeSelected = cardShortName1.toUpperCase() + " ending in " + lastFourDigitsOfCardNum1;
			select = new Select(paymentDropdowns.get(0));
			select.selectByVisibleText(valueToBeSelected);
		}

		valueToBeSelected = cardShortName2.toUpperCase() + " ending in " + lastFourDigitsOfCardNum2;
		select = new Select(paymentDropdowns.get(1));
		select.selectByVisibleText(valueToBeSelected);

		// String orderTotal = /*stateHolder.get("total")*/ "237.29";
		String orderTotal = splitPaymentForm.findElement(By.xpath("//input[@name='distributionTotal']"))
				.getAttribute("value");
		Double dblOrderTotal = Double.parseDouble(orderTotal.replaceAll("[^\\d.]*", ""));
		dblOrderTotal = dblOrderTotal / 2;

		WebElement secondAmountElement = splitPaymentForm.findElement(By.id("secondAmount"));
		secondAmountElement.clear();
		secondAmountElement.sendKeys(dblOrderTotal.toString());
		secondAmountElement.sendKeys(Keys.TAB);

		splitPaymentForm.findElement(By.id("multiTenderDistributionSubmit")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("multiTenderDistributionSubmit")));
	}

	public void selectPaypalRadioButton() {
		paypalRadioButton.click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//p[@class='page-msg']")));
	}

	public void enterBillingAddress(Address address) {
		User user = User.getNewFakeUser();

		WebElement country = payment_page.findElement(By.name("ADDRESS<>country_cd"));
		Select countrySelect = new Select(country);
		countrySelect.selectByValue(address.getCountry());

		WebElement firstName = payment_page.findElement(By.name("ADDRESS<>firstName"));
		if (address.getFirstName().isEmpty()) {
			firstName.sendKeys(user.getFirstName());
		} else {
			firstName.sendKeys(address.getFirstName());
		}

		WebElement lastName = payment_page.findElement(By.name("ADDRESS<>lastName"));
		if (address.getLastName().isEmpty()) {
			lastName.sendKeys(user.getLastName());
		} else {
			lastName.sendKeys(address.getLastName());
		}

		WebElement address1 = payment_page.findElement(By.name("ADDRESS<>address1"));
		address1.sendKeys(address.getLine1());

		WebElement address2 = payment_page.findElement(By.name("ADDRESS<>address2"));
		address2.sendKeys(address.getLine2());

		WebElement zipcode = payment_page.findElement(By.name("ADDRESS<>postal"));
		zipcode.sendKeys(address.getZipcode());

		WebElement phone = payment_page.findElement(By.name("ADDRESS<>phone"));
		phone.sendKeys(address.getPhone());
	}

	public void enterEmailAddress() {
		User user = User.getNewFakeUser();
		emailReceipt.sendKeys(user.getEmail());
	}

	public void clickOnPayPalButton() {
		paypalButton.click();
		Util.waitForPageFullyLoaded(driver);
	}

	public void clickOnSelectedAddress() {
		try {
			if(addressRadioButton.isDisplayed()) {
				addressRadioButton.click();
				Util.wait(2000);
			}
		}catch (Exception e) {

		}
		selectedAddress.click();
	}
	
	public void clickOnChangeButton() {
		changeButton.click();
		Util.waitForPageFullyLoaded(driver);
	}
}
