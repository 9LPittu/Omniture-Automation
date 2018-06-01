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

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by nadiapaolagarcia on 5/3/16.
 */
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
	
	
	public String orderNumber = null;
	protected E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
	 private final StateHolder stateHolder = StateHolder.getInstance();
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
		Util.wait(2000);
	}

	public void selectCustomer() {
		driver.switchTo().frame(iframe);
		emailSearch.sendKeys(getDataFromTestDataRowMap("Customer"));
		searchButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(2000);
		customer.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(2000);
	}

	public void selectBrand() {
		createrOrder.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		Select selectBrand = new Select(brandType);
		selectBrand.selectByVisibleText(e2ePropertyReader.getProperty("cc.brandType"));
		Util.wait(5000);
	}

	public void addMultiLineItems() {
		addVariant.sendKeys(getDataFromTestDataRowMap("Item Identifier1"));
		addButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(8000);
		qty.get(0).clear();
		Util.wait(2000);
		qty.get(0).sendKeys(getDataFromTestDataRowMap("Quantity1"));
		Util.wait(2000);
		selectShippingMethod();
		addVariant.sendKeys(getDataFromTestDataRowMap("Item Identifier2"));
		Util.wait(2000);
		addButton.click();
		Util.wait(8000);
		qty.get(1).clear();
		Util.wait(2000);
		qty.get(1).sendKeys(getDataFromTestDataRowMap("Quantity2"));
		Util.wait(2000);
		reCalTotals.click();
		Util.wait(8000);
	}

	public void singleLineItem() {
		addVariant.sendKeys(getDataFromTestDataRowMap("Item Identifier1"));
		addButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(8000);
		qty.get(0).clear();
		Util.wait(2000);
		qty.get(0).sendKeys(getDataFromTestDataRowMap("Quantity1"));
		Util.wait(2000);
		selectShippingMethod();
		/*if (!getDataFromTestDataRowMap("Ship To Country").equalsIgnoreCase("US")) {
			Select shippingMethod = new Select(shippingMethodList);
			shippingMethod.selectByIndex(1);
		}*/
		Util.wait(2000);
		reCalTotals.click();
		Util.wait(8000);
	}

	public void applyDiscount() {
		if (!getDataFromTestDataRowMap("Discount%").equalsIgnoreCase("0")) {
			discountPercentage.sendKeys(getDataFromTestDataRowMap("Discount%"));
			Util.wait(3000);
			applyDiscountButton.click();
			Util.wait(5000);
			reCalTotals.click();
			Util.wait(8000);
		}
	}

	public void applyPromo() {
		if (!getDataFromTestDataRowMap("PromoCode").isEmpty()) {
			promoCode.sendKeys(getDataFromTestDataRowMap("PromoCode"));
			Util.wait(1000);
			reCalTotals.click();
			Util.wait(5000);
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
		} else if (paymentMethodName.equalsIgnoreCase("GiftCard")) {
			paymentMethod = e2ePropertyReader.getProperty("GiftCard");
		} else if (paymentMethodName.equalsIgnoreCase("JCC")) {
			paymentMethod = e2ePropertyReader.getProperty("JCC");
		}
		return paymentMethod;
	}

	public void selectPaymentMethods() {
		if (getDataFromTestDataRowMap("Split Payments Required?").equalsIgnoreCase("Yes")) {
			if (removeCheckBox.isDisplayed()) {
				removeCheckBox.click();
				Util.wait(3000);
				updatePayment.click();
				Util.wait(5000);
			} else {
				updatePayment.click();
				Util.wait(3000);
			}
			String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
			String paymentMethod2 = getDataFromTestDataRowMap("Payment Method 2");
			Select paymentMethods = new Select(selectPaymentMethod);
			paymentMethods.selectByVisibleText(getPaymentMethod(paymentMethod1));
			Util.wait(2000);
			addPayment.click();
			Util.wait(6000);
			updatePayment.click();
			Util.wait(6000);
			paymentMethods.selectByVisibleText(getPaymentMethod(paymentMethod2));
			Util.wait(2000);
			addPayment.click();
			Util.wait(6000);
			if(paymentMethod1.equalsIgnoreCase("GiftCard")||paymentMethod2.equalsIgnoreCase("GiftCard")) {
				giftCard.sendKeys(e2ePropertyReader.getProperty("giftcard.card.number"));
				Util.wait(2000);
				giftCardPin.sendKeys(e2ePropertyReader.getProperty("giftcard.card.pin"));
				Util.wait(2000);
			}
			String total = orderTotal.getText();
			Double dblOrderTotal = Double.parseDouble(total.replaceAll("[^\\d.]*", ""));
			dblOrderTotal = dblOrderTotal / 2;
			amount1.clear();
			Util.wait(2000);
			amount1.sendKeys(dblOrderTotal.toString());
			Util.wait(2000);
			amount2.clear();
			Util.wait(2000);
			amount2.sendKeys(dblOrderTotal.toString());
			Util.wait(2000);
			reCalTotals.click();
		} else {
			if (removeCheckBox.isDisplayed()) {
				removeCheckBox.click();
				Util.wait(2000);
				updatePayment.click();
				Util.wait(5000);
			} else {
				updatePayment.click();
				Util.wait(3000);
			}
			String paymentMethod1 = getDataFromTestDataRowMap("Payment Method 1");
			Select paymentMethods = new Select(selectPaymentMethod);
			paymentMethods.selectByVisibleText(getPaymentMethod(paymentMethod1));
			Util.wait(2000);
			addPayment.click();
			Util.wait(6000);
			reCalTotals.click();
		}

	}

	public void selectShippingMethod() {
		try {
			WebElement selectShipping = driver.findElement(By.xpath("(//select/option[contains(text(),'Select a shipping method')])[1]"));
		if (!getDataFromTestDataRowMap("Ship To Country").equalsIgnoreCase("US")||selectShipping.isDisplayed()) {
			Select shippingMethod = new Select(shippingMethodList);
			shippingMethod.selectByIndex(1);
			Util.wait(5000);
		}else {
			if (!getDataFromTestDataRowMap("Shipping Methods").isEmpty()) {
				Select selectShippingMethod = new Select(shippingMethod);
				for (int i = 0; i < shippingMethods.size(); i++) {
					String getShippingMethod = getDataFromTestDataRowMap("Shipping Methods");
					if (shippingMethods.get(i).getText().contains(getShippingMethod)) {
						selectShippingMethod.selectByVisibleText(shippingMethods.get(i).getText());
						Util.wait(5000);
						return;
					}
				}
			}
		}
		}
		catch (Exception e) {
		} 
		
	}

	public void selectShippingInfo() {
		Select shippingInfo = new Select(shippingAddress);
		shippingInfo.selectByVisibleText(getDataFromTestDataRowMap("Shipping Addresses1"));
		Util.wait(5000);
		Select selectBillToInfo = new Select(billToInfo);
		selectBillToInfo.selectByVisibleText(getDataFromTestDataRowMap("Shipping Addresses1"));
		Util.wait(5000);
	}

	public String submitOrder() throws AWTException {
		submitOrder.click();
		Util.wait(15000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Util.wait(3000);
		Assert.assertTrue(orderNum.isDisplayed());
		orderNumber = orderNum.getText().replaceAll("[^0-9]", "");
		stateHolder.put("orderNumber", orderNumber);
		return orderNumber;
	}

	public void createNewOrder() throws Exception {
		if (getDataFromTestDataRowMap("OrderType").equalsIgnoreCase("Regular")) {
			Select selectOrderType = new Select(orderType);
			selectOrderType.selectByVisibleText(getDataFromTestDataRowMap("OrderType"));
			Util.wait(2000);
			selectShippingInfo();
			if (getDataFromTestDataRowMap("MultiLine").equalsIgnoreCase("Yes")) {
				addMultiLineItems();
			} else {
				singleLineItem();
			}
			applyPromo();
			selectPaymentMethods();
			applyDiscount();
			applyPromo();
		} else if (getDataFromTestDataRowMap("OrderType").equalsIgnoreCase("STS")) {
			shipToStore.click();
			Util.wait(5000);
			Select selectBillToInfo = new Select(billToInfo);
			selectBillToInfo.selectByVisibleText(getDataFromTestDataRowMap("Shipping Addresses1"));
			Util.wait(5000);
			Select stores = new Select(selectStore);
			stores.selectByVisibleText(getDataFromTestDataRowMap("Store Zip Code"));
			Util.wait(2000);
			saveStoreAddress.click();
			Util.wait(6000);
			if (getDataFromTestDataRowMap("MultiLine").equalsIgnoreCase("Yes")) {
				addMultiLineItems();
			} else {
				singleLineItem();
			}
			applyPromo();
			selectPaymentMethods();
			applyDiscount();

		}
	}

}
