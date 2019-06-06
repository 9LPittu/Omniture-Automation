package com.jcrew.page;

import com.jcrew.page.checkout.Checkout;
import com.jcrew.utils.E2EPropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ccPage extends Checkout {

	@FindBy(xpath = "//*[@id='idLoginCSR']/form/table/tbody/tr[1]/td[2]/input[3]")
	private WebElement userName;
	@FindBy(xpath = "(//input[@name='LOGIN<>password'])[1]")
	private WebElement password;
	@FindBy(xpath = "(//input[@class='bmsFormButton'])[1]")
	private WebElement loginButton;
	@FindBy(xpath = "//iframe[@id='cc_frame_content']")
	private WebElement iframe;
	@FindBy(xpath = "(//input[@name='USER_ACCOUNT_SEARCH<>email_search'])[1]")
	private WebElement emailSearch;
	@FindBy(xpath = "//input[@name='cc_home_contacts_search']")
	private WebElement searchButton;
	@FindBy(xpath = "//*[@id='idContentContainer']/table[3]/tbody/tr[2]/td[2]/a[2]")
	private WebElement customer;
	@FindBy(xpath = "//a[contains(text(),'Create an order')]")
	private WebElement createrOrder;
	@FindBy(xpath = "//select[@name='ORDER<>ATR_special_handle']")
	private WebElement orderType;
	@FindBy(xpath = "//input[@name='shipToStore']")
	private WebElement sts;
	@FindBy(xpath = "//select[@name='ORDER<>ATR_ord_brand']")
	private WebElement brandType;
	@FindBy(xpath = "//input[@name='GENERIC<>addVariants']")
	private WebElement addVariant;
	@FindBy(xpath = "(//input[@name='add_variants'])[1]")
	private WebElement addButton;
	@FindBy(xpath = "//input[@name='discount_percent']")
	private WebElement discountPercentage;
	@FindBy(xpath = "//button[@name='apply_discount']")
	private WebElement applyDiscountButton;
	@FindBy(xpath = "//select[@name='e4xShippingMethodList']")
	private WebElement shippingMethodList;
	@FindBy(xpath = "//select[@name='shippingMethodList']")
	private WebElement shippingMethod;
	@FindBy(xpath = "//select[@name='shippingMethodList']/option")
	private List<WebElement> shippingMethods;
	@FindBy(xpath = "(//button[@name='submit_order'])[2]")
	private WebElement submitOrder;
	@FindBy(xpath = "//input[@name='CART_ITEM_ARRAY<>quantity']")
	private List<WebElement> qty;
	@FindBy(xpath = "//span[@class='bmsSummaryHeader']")
	private WebElement orderNum;
	@FindBy(xpath = "(//button[@name='recalc_button'])[1]")
	private WebElement reCalTotals;
	@FindBy(xpath = "//input[@name='shipToStore']")
	private WebElement shipToStore;
	@FindBy(xpath = "//select[@name='selectedStore']")
	private WebElement selectStore;
	@FindBy(xpath = "//input[@value='Save']")
	private WebElement saveStoreAddress;
	@FindBy(xpath = "//select[@name='paymentOptionList']")
	private WebElement selectPaymentMethod;
	@FindBy(xpath = "//input[@id='updatePaymentID']")
	private WebElement updatePayment;
	@FindBy(xpath = "//input[@name='CREDIT_CARD_ARRAY<>remove']")
	private WebElement removeCheckBox;
	@FindBy(xpath = "//*[@id='idContentContainer']/table/tbody/tr[5]/td/table[3]/tbody/tr/td/table/tbody/tr[2]/td[2]")
	private WebElement orderTotal;
	@FindBy(xpath = "//table[@class='bmsFormTable']//tbody/tr[2]/td[6]/input[2]")
	private WebElement amount1;
	@FindBy(xpath = "//table[@class='bmsFormTable']//tbody/tr[3]/td[6]/input[2]")
	private WebElement amount2;
	@FindBy(xpath = "//input[@id='addPaymentID']")
	private WebElement addPayment;
	@FindBy(xpath = "//select[@name='shipToList']")
	private WebElement shippingAddress;
	@FindBy(xpath = "//select[@name='billToList']")
	private WebElement billToInfo;
	@FindBy(xpath = "//input[@name='promotionCode1']")
	private WebElement promoCode;
	@FindBy(xpath = "//img[@title='Ship-to New Address']")
	private List<WebElement> newShippingAddress;
	@FindBy(xpath = "//select[@name='CART_ITEM_ARRAY<>shipToID']")
	private List<WebElement> shipTo;
	@FindBy(xpath = "//select[@name='CART_ITEM_ARRAY<>e4xShippingMethodList']")
	private List<WebElement> shipMethods;
	@FindBy(xpath = "//input[@name='ACCOUNT_ARRAY<>accountNumber']")
	private WebElement giftCard;
	@FindBy(xpath = "//input[@name='ACCOUNT_ARRAY<>pinNumber']")
	private WebElement giftCardPin;
	@FindBy(xpath = "(//*[@name='redeem_points'])[2]")
	private WebElement redeemButton;
	@FindBy(xpath = "//input[@value='REDEEMED']")
	private WebElement redeemedButton;
	@FindBy(xpath = "//td[text()='Rewards Redeemed']")
	private WebElement redeemedAmt;
	@FindBy(xpath = "(//*[@name='override_shipping'])[1]")
	private WebElement shipToNewAdd1;
	@FindBy(xpath = "(//*[@name='override_shipping'])[2]")
	private WebElement shipToNewAdd2;
	@FindBy(xpath = "(//select[@name='CART_ITEM_ARRAY<>shipToID'])[1]")
	private WebElement overRideShippingAdd1;
	@FindBy(xpath = "(//select[@name='CART_ITEM_ARRAY<>shipToID'])[2]")
	private WebElement overRideShippingAdd2;
	@FindBy(xpath = "(//select[@name='CART_ITEM_ARRAY<>shippingMethod'])[1]")
	private WebElement overRideShippingMethod1;
	@FindBy(xpath = "(//select[@name='CART_ITEM_ARRAY<>shippingMethod'])[2]")
	private WebElement overRideShippingMethod2;
	@FindBy(xpath = "(//select[@name='CART_ITEM_ARRAY<>shippingMethod'])[1]/option")
	private List<WebElement> overRideShippingMethods1;
	@FindBy(xpath = "(//select[@name='CART_ITEM_ARRAY<>shippingMethod'])[2]/option")
	private List<WebElement> overRideShippingMethods2;
	@FindBy(xpath = "//button[@name='remove_discount']")
	private WebElement removeDiscount;
	@FindBy(xpath = "//button[@name='remove_promo_recalc']")
	private WebElement promoRemoveButton;
	@FindBy(xpath = "//button[@name='addGiftOptions']//td[text()='Gift Options']")
	private WebElement giftOptions;
	@FindBy(xpath = "//select[contains(@name,'select')]")
	private List<WebElement> selectBox;
	@FindBy(xpath = "(//img[contains(@src,'finishedwithgiftoptions.gif')])[1]")
	private WebElement finishedWithGiftOptions;

	public String orderNumber = null;
	protected E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
	private final StateHolder stateHolder = StateHolder.getInstance();
	JavascriptExecutor executor = (JavascriptExecutor) driver;

	public ccPage(WebDriver driver) {
		super(driver);
		isDisplayed();
	}

	@Override
	public boolean isDisplayed() {
		return false;
	}

	public String getDataFromTestDataRowMap(String columnName) {
		Map<String, Object> testdataMap = stateHolder.get("testdataRowMap");
		String columnValue = null;
		if (testdataMap.containsKey(columnName)) {
			columnValue = ((String) testdataMap.get(columnName)).trim();
		}
		logger.debug("Testdata for '{}' = {}", columnName, columnValue);
		return columnValue;
	}

	public void goToHomePage() {
		driver.get(e2ePropertyReader.getProperty("cc.steel.jcrew"));
		Util.waitForPageFullyLoaded(driver);
	}

	public void loginToCC() {
		userName.sendKeys(e2ePropertyReader.getProperty("cc.steel.jcrew.userName"));
		password.sendKeys(e2ePropertyReader.getProperty("cc.steel.jcrew.password"));
		loginButton.click();
		Util.waitForPageFullyLoaded(driver);
	}

	public void selectCustomer() {
		driver.switchTo().frame(iframe);
		emailSearch.sendKeys(getDataFromTestDataRowMap("Customer"));
		searchButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(2000);
		try {
			if (customer.isDisplayed()) {
				customer.click();
			}
		} catch (Exception e) {

		}

		Util.waitForPageFullyLoaded(driver);
	}

	public void selectBrand() {
		createrOrder.click();
		Util.waitForPageFullyLoaded(driver);
		Select selectBrand = new Select(brandType);
		selectBrand.selectByVisibleText(e2ePropertyReader.getProperty("cc.brandType"));
	}

	public void addMultiLineItems() {
		addVariant.sendKeys(getDataFromTestDataRowMap("Item Identifier1"));
		executor.executeScript("arguments[0].click();", addButton);
		Util.waitForPageFullyLoaded(driver);
		qty.get(0).clear();
		qty.get(0).sendKeys(getDataFromTestDataRowMap("Quantity1"));
		addVariant.sendKeys(getDataFromTestDataRowMap("Item Identifier2"));
		executor.executeScript("arguments[0].click();", addButton);
		qty.get(1).clear();
		qty.get(1).sendKeys(getDataFromTestDataRowMap("Quantity2"));
		reCalTotals.click();
	}

	public void singleLineItem() {
		addVariant.sendKeys(getDataFromTestDataRowMap("Item Identifier1"));
		executor.executeScript("arguments[0].click();", addButton);
		Util.waitForPageFullyLoaded(driver);
		qty.get(0).clear();
		qty.get(0).sendKeys(getDataFromTestDataRowMap("Quantity1"));
		executor.executeScript("arguments[0].click();", reCalTotals);
	}

	public void applyDiscount() {
		if (!getDataFromTestDataRowMap("Discount%").equalsIgnoreCase("0")) {
			discountPercentage.sendKeys(getDataFromTestDataRowMap("Discount%"));
			applyDiscountButton.click();
			executor.executeScript("arguments[0].click();", reCalTotals);
			Assert.assertTrue("Discount applied successfully", removeDiscount.isDisplayed());
		}
	}

	public void applyPromo() {
		if (!getDataFromTestDataRowMap("PromoCode").isEmpty()
				&& getDataFromTestDataRowMap("Discount%").equalsIgnoreCase("0")) {
			promoCode.sendKeys(getDataFromTestDataRowMap("PromoCode"));
			Util.wait(1000);
			reCalTotals.click();
			Assert.assertTrue("Promo applied successfully", promoRemoveButton.isDisplayed());
		}
	}

	public String getPaymentMethod(String paymentMethodName) {
		String paymentMethod = null;
		if (paymentMethodName.equalsIgnoreCase("Master")) {
			paymentMethod = e2ePropertyReader.getProperty("Master");
		} else if (paymentMethodName.equalsIgnoreCase("Visa")) {
			paymentMethod = e2ePropertyReader.getProperty("Visa");
		} else if (paymentMethodName.equalsIgnoreCase("Discover")) {
			paymentMethod = e2ePropertyReader.getProperty("Discover");
		} else if (paymentMethodName.equalsIgnoreCase("AMEX")) {
			paymentMethod = e2ePropertyReader.getProperty("AMEX");
		} else if (paymentMethodName.equalsIgnoreCase("Gift Card")) {
			paymentMethod = e2ePropertyReader.getProperty("GiftCard");
		} else if (paymentMethodName.equalsIgnoreCase("JCC")) {
			paymentMethod = e2ePropertyReader.getProperty("JCC");
		} else if (paymentMethodName.equalsIgnoreCase("Loyalty.Visa")) {
			paymentMethod = e2ePropertyReader.getProperty("Loyalty.Visa");
		} else if (paymentMethodName.equalsIgnoreCase("JCB")) {
			paymentMethod = e2ePropertyReader.getProperty("JCB");
		}
		return paymentMethod;
	}

	public void selectPaymentMethods() {
		if (getDataFromTestDataRowMap("Split Payments Required?").equalsIgnoreCase("Yes")) {
			if (removeCheckBox.isDisplayed()) {
				removeCheckBox.click();
				Util.wait(3000);
				updatePayment.click();
			} else {
				updatePayment.click();
			}
			String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
			String paymentMethod2 = getDataFromTestDataRowMap("Payment Method 2");
			Select paymentMethods = new Select(selectPaymentMethod);
			paymentMethods.selectByVisibleText(getPaymentMethod(paymentMethod1));
			addPayment.click();
			updatePayment.click();
			paymentMethods.selectByVisibleText(getPaymentMethod(paymentMethod2));
			addPayment.click();
			if (paymentMethod1.equalsIgnoreCase("GiftCard") || paymentMethod2.equalsIgnoreCase("GiftCard")) {
				giftCard.sendKeys(e2ePropertyReader.getProperty("giftcard.card.number"));
				giftCardPin.sendKeys(e2ePropertyReader.getProperty("giftcard.card.pin"));
			}
			String total = orderTotal.getText();
			Double dblOrderTotal = Double.parseDouble(total.replaceAll("[^\\d.]*", ""));
			dblOrderTotal = dblOrderTotal / 2;
			amount1.clear();
			amount1.sendKeys(dblOrderTotal.toString());
			amount2.clear();
			amount2.sendKeys(dblOrderTotal.toString());
			reCalTotals.click();
		} else {
			if (removeCheckBox.isDisplayed()) {
				removeCheckBox.click();
				updatePayment.click();
			} else {
				updatePayment.click();
			}
			String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
			Select paymentMethods = new Select(selectPaymentMethod);
			paymentMethods.selectByVisibleText(getPaymentMethod(paymentMethod1));
			addPayment.click();
			if (paymentMethod1.equalsIgnoreCase("Gift Card")) {
				giftCard.sendKeys(e2ePropertyReader.getProperty("giftcard.card.number"));
				giftCardPin.sendKeys(e2ePropertyReader.getProperty("giftcard.card.pin"));
			}
			reCalTotals.click();
		}

	}

	public void overRideShippingMethod(String getShippingMethod, List<WebElement> shippingMethods,
			Select selectShippingMethod) {
		for (int i = 0; i < shippingMethods.size(); i++) {
			if (shippingMethods.get(i).getText().contains(getShippingMethod)) {
				selectShippingMethod.selectByVisibleText(shippingMethods.get(i).getText());
				return;
			}
		}
	}

	public void selectShippingMethod() {
		if (!getDataFromTestDataRowMap("Shipping Method1").isEmpty()) {
			Select selectShippingMethod = new Select(shippingMethod);
			for (int i = 0; i < shippingMethods.size(); i++) {
				String getShippingMethod = getDataFromTestDataRowMap("Shipping Method1");
				if (shippingMethods.get(i).getText().contains(getShippingMethod)) {
					selectShippingMethod.selectByVisibleText(shippingMethods.get(i).getText());
					return;
				}
			}
		}

	}

	public void selectShippingInfo() {
		Select shippingInfo = new Select(shippingAddress);
		Select selectShippingAdd1 = new Select(overRideShippingAdd1);
		Select selectShippingAdd2 = new Select(overRideShippingAdd2);
		Select selectShippingMethod1 = new Select(overRideShippingMethod1);
		Select selectShippingMethod2 = new Select(overRideShippingMethod2);
		String shippingAdd1 = getDataFromTestDataRowMap("Shipping Addresses1");
		String shippingAdd2 = getDataFromTestDataRowMap("Shipping Addresses2");
		String shippingMethod1 = getDataFromTestDataRowMap("Shipping Method1");
		String shippingMethod2 = getDataFromTestDataRowMap("Shipping Method2");
		String isMultipleShiipingAddress = getDataFromTestDataRowMap("Multiple Shipping Address Required?");
		if (isMultipleShiipingAddress.equalsIgnoreCase("Yes")) {
			shipToNewAdd1.click();
			selectShippingAdd1.selectByVisibleText(shippingAdd1);
			overRideShippingMethod(shippingMethod1, overRideShippingMethods1, selectShippingMethod1);
			shipToNewAdd2.click();
			selectShippingAdd2.selectByVisibleText(shippingAdd2);
			overRideShippingMethod(shippingMethod2, overRideShippingMethods2, selectShippingMethod2);
		} else {
			shippingInfo.selectByVisibleText(getDataFromTestDataRowMap("Shipping Addresses1"));
			Select selectBillToInfo = new Select(billToInfo);
			selectBillToInfo.selectByVisibleText(getDataFromTestDataRowMap("Shipping Addresses1"));
			selectShippingMethod();
		}
	}

	public String submitOrder() throws AWTException {
		submitOrder.click();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Util.wait(3000);
		Assert.assertTrue(orderNum.isDisplayed());
		orderNumber = orderNum.getText().replaceAll("[^0-9]", "");
		stateHolder.put("orderNumber", orderNumber);
		return orderNumber;
	}

	public void redeemRewardPoints() {
		if (getDataFromTestDataRowMap("E2E Scenario Description").contains("redeem reward points")) {
			redeemButton.isDisplayed();
			redeemButton.click();
			redeemedButton.isDisplayed();
			redeemedAmt.isDisplayed();
		}
	}

	public void createNewOrder() throws Exception {
		if (getDataFromTestDataRowMap("OrderType").equalsIgnoreCase("Regular")) {
			Select selectOrderType = new Select(orderType);
			selectOrderType.selectByVisibleText(getDataFromTestDataRowMap("OrderType"));
			if (getDataFromTestDataRowMap("MultiLine").equalsIgnoreCase("Yes")) {
				addMultiLineItems();
			} else {
				singleLineItem();
			}
			selectGiftOptions();
			selectShippingInfo();
			applyDiscount();
			applyPromo();
			redeemRewardPoints();
			selectPaymentMethods();
		} else if (getDataFromTestDataRowMap("OrderType").equalsIgnoreCase("STS")) {
			shipToStore.click();
			Select stores = new Select(selectStore);
			stores.selectByVisibleText(getDataFromTestDataRowMap("Store Zip Code"));
			saveStoreAddress.click();
			if (getDataFromTestDataRowMap("MultiLine").equalsIgnoreCase("Yes")) {
				addMultiLineItems();
			} else {
				singleLineItem();
			}
			selectGiftOptions();
			selectShippingMethod();
			applyDiscount();
			applyPromo();
			redeemRewardPoints();
			selectPaymentMethods();
		}
	}

	public void selectGiftOptions() {
		String giftOption = getDataFromTestDataRowMap("Gift Option Selection");
		String giftService = getDataFromTestDataRowMap("Gift Wrapping Service");
		if (!giftOption.equalsIgnoreCase("None") || !giftService.equalsIgnoreCase("None")) {
			giftOptions.click();
			String parentWindow = driver.getWindowHandle();
			Set<String> allWindows = driver.getWindowHandles();
			allWindows.remove(parentWindow);
			if (allWindows.size() > 0) {
				for (String curWindow : allWindows) {
					driver.switchTo().window(curWindow);
					Util.waitForPageFullyLoaded(driver);
					for (int i = 0; i <= selectBox.size(); i++) {
						if (giftService.equalsIgnoreCase("All Items In One Box")) {
							Select giftBoxList = new Select(selectBox.get(0));
							giftBoxList.selectByIndex(i + 1);
						} else if (giftService.equalsIgnoreCase("Each Item In New Box")) {
							selectBox.get(0).sendKeys(giftOption);
						}
					}
					finishedWithGiftOptions.click();
					Util.wait(2000);
				}
				System.out.println(driver.getTitle());
				driver.switchTo().window(parentWindow);
			}
		}
	}

}
