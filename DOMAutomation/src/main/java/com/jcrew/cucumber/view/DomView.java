package com.jcrew.cucumber.view;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcrew.cucumber.container.DomContainer;
import com.jcrew.cucumber.container.DomPOJO;
import com.jcrew.cucumber.util.E2EPropertyReader;
import com.jcrew.cucumber.util.e2e.E2ECommon;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.DatabaseReader;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

public class DomView extends E2ECommon {
	static String UName;
	static String pwd;
	static String orderStatus;
	private static DomContainer domContainer;
	static String devToolsPwd = "whap319";
	protected static E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
	static String domUrl = null;
	static String devToolsUrl = null;
	static String actualWeight = "1.05";
	static ArrayList<String> items = null;
	public static String orderNum = null;

	// static String
	public static void loginUser(String username, String password) throws InterruptedException {
		UName = username;
		pwd = password;
		Thread.sleep(5000);
		domContainer.username.clear();
		domContainer.username.sendKeys(username);
		BrowserDriver.waitForElement(domContainer.password, 20);
		domContainer.password.clear();
		domContainer.password.sendKeys(password);
		BrowserDriver.waitForElementToBeClickable(domContainer.login, 10);
		domContainer.login.click();
	}

	public static void getEnvUrl() throws Exception {
		if (e2ePropertyReader.getProperty("env").equalsIgnoreCase("Steel")) {
			domUrl = e2ePropertyReader.getProperty("q3Url");
			devToolsUrl = e2ePropertyReader.getProperty("q3DevToolsUrl");
		} else if (e2ePropertyReader.getProperty("env").equalsIgnoreCase("Platinum")) {
			domUrl = e2ePropertyReader.getProperty("q5Url");
			devToolsUrl = e2ePropertyReader.getProperty("q5DevToolsUrl");
		}
	}

	public static void selectMenu() {
		// BrowserDriver.waitForElementToBeClickable(domContainer.menu, 30);
		BrowserDriver.waitForSec(5);
		domContainer.menu.click();
		domContainer.orderLifeCycle.click();
		domContainer.salesOrders.click();
		domContainer.showFilterOptions.click();
		for (int i = 0; i <= 10; i++) {
			domContainer.selectFilterOptions.click();
			try {
				domContainer.selectFilterOptionsAll.click();
				domContainer.selectFilterButtonOk.click();
				break;
			} catch (ElementNotVisibleException e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.out.println("failed to select filter option in try " + i);
				if (i == 10) {
					e.printStackTrace();
					Assert.fail("Failed to select filter");
				}
			}
		}
	}

	public static void enterOrderNum(String orderNumb) {
		domContainer.orderNumber.clear();
		domContainer.orderNumber.sendKeys(orderNumb);
	}

	public static void clickOnApply() {
		domContainer.applyFilter.click();
	}

	public static void verifyOrderInSearchResults() {
		String orderTpe = domContainer.orderType.getText();
		DomPOJO.setOrderType(orderTpe);
		Assert.assertTrue(!domContainer.orderStatus.isEmpty());
	}

	public static void verifyOrderStatus(String status) {
		BrowserDriver.waitForPageToBestable();
		BrowserDriver.waitForSec(20);
		WebElement statusElement = domContainer.orderStatus.get(0);
		String statusTxt = statusElement.getText();
		Assert.assertTrue(status.equalsIgnoreCase(statusTxt));
	}

	public static void doubleClickOnOrder() {
		BrowserDriver.waitForPageToBestable();
		BrowserDriver.waitForSec(5);
		WebElement status = domContainer.orderStatus.get(0);
		String statusTxt = status.getText();
		if (statusTxt.equalsIgnoreCase("Allocated")) {
			Actions action = new Actions(BrowserDriver.getCurrentDriver());
			action.moveToElement(domContainer.orderStatus.get(0)).doubleClick().build().perform();
		} else {
			Actions action = new Actions(BrowserDriver.getCurrentDriver());
			action.moveToElement(domContainer.orderStatus.get(0)).doubleClick().build().perform();
		}
		/*
		 * String itemId = domContainer.itemId.getText(); DomPOJO.setItemId(itemId);
		 */
	}

	@SuppressWarnings("unused")
	public static void doubleClickOnDoOrder() {
		BrowserDriver.waitForPageToBestable();
		BrowserDriver.waitForSec(5);
		WebElement status = domContainer.doOderStatus;
		String statusTxt = status.getText();
		// if (statusTxt.equalsIgnoreCase("Allocated")) {
		Actions action = new Actions(BrowserDriver.getCurrentDriver());
		action.moveToElement(status).doubleClick().build().perform();
	}

	public static void selectOption(String optionValue) {
		BrowserDriver.waitForPageToBestable();
		BrowserDriver.waitForSec(10);
		BrowserDriver.waitForElementToBeClickable(domContainer.selectOptions, 30);
		Select select = new Select(domContainer.selectOptions);
		select.selectByValue(optionValue);
	}

	public static void clickOnLineAddDetails() {

		domContainer.lineAdditionalDetails.click();
	}

	public static void verifyOrderDetailsPageShouldDisplay() {
		Assert.assertTrue(domContainer.orderPageHeader.getText().equalsIgnoreCase("Order Details"));
	}

	public static void checkOnLines() {
		for (WebElement lineId : domContainer.lineIds) {
			BrowserDriver.waitForElementToBeClickable(lineId, 10);
			lineId.click();
		}
	}

	public static void clickOnCreateDo() {
		BrowserDriver.waitForElementToBeClickable(domContainer.createDo, 60);
		// domContainer.createDo.click();
		BrowserDriver.clickOnElementWithJS(domContainer.createDo);
		BrowserDriver.waitForSec(10);
		/*
		 * BrowserDriver.waitForTextPresent(BrowserDriver.getCurrentDriver(), By.xpath(
		 * "//table[@id='dataForm:coLineViewAdditionalListTable_body']//tr/td[5]/span"),
		 * "DO Created", 100);
		 */
		// BrowserDriver.waitForElementToDisappear(By.id("dataForm:coLViewAI_createDOButton"),
		// 60);
	}

	public static void verifyDoStatusFromAdditionDetailsPage(String doStatus) {
		BrowserDriver.waitForSec(1);
		BrowserDriver.getCurrentDriver().navigate().refresh();
		init();
		BrowserDriver.waitForPageToBestable();
		BrowserDriver.waitForElementToVisible(domContainer.doStatus, 60);
		BrowserDriver.waitForElement(domContainer.doStatusText(doStatus), 60);
		Assert.assertTrue(domContainer.doStatus.getText().equalsIgnoreCase(doStatus));
	}

	public static void verifyDoStatusFromDoPage(String doFilStatus) {
		BrowserDriver.waitForPageToBestable();
		BrowserDriver.waitForElementToVisible(domContainer.doFulfillStatus, 60);
		Assert.assertTrue(domContainer.doFulfillStatus.getText().equalsIgnoreCase(doFilStatus));
	}

	public static void copyDoNum() {
		// DomPOJO.getStoreDos().put("3070025200", "0306");
		// Set ldcSet= new HashSet();
		BrowserDriver.waitForSec(5);
		BrowserDriver.waitForPageToBestable();
		getItemsList();
		for (int i = 1; i <= domContainer.doDetails.size(); i++) {
			String fulfillmentFacility = BrowserDriver.getCurrentDriver().findElement(By.xpath(
					"//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row']["
							+ i + "]/td[7]/a"))
					.getText().trim();
			String destinationFacility = BrowserDriver.getCurrentDriver().findElement(By.xpath(
					"//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row']["
							+ i + "]/td[8]/a"))
					.getText().trim();
			String doId = BrowserDriver.getCurrentDriver().findElement(By.xpath(
					"//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row']["
							+ i + "]/td[1]/span[1]"))
					.getText().trim();
			String itemId = BrowserDriver.getCurrentDriver().findElement(By.xpath(
					"//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row']["
							+ i + "]/td[9]/a[1]"))
					.getText().trim();

			if (fulfillmentFacility.equalsIgnoreCase("LDC")) {
				DomPOJO.getLdcDos().put(doId, fulfillmentFacility);
			} else {
				DomPOJO.getStoreDos().put(doId, fulfillmentFacility);
				DomPOJO.getDestinationFacilities().put(doId, destinationFacility);
			}
			DomPOJO.getItemsInOrder().put(doId, itemId);
		}
		/*
		 * String doNum = domContainer.doNum.getText(); DomPOJO.setDoNum(doNum);
		 * System.out.println("Do: " + doNum);
		 */
	}

	public static void clickOnDisOrdersMenu() {
		domContainer.menu.click();
		domContainer.orderLifeCycle.click();
		BrowserDriver.waitForElementToBeClickable(domContainer.dcOrdersMenu, 30);
		domContainer.dcOrdersMenu.click();
	}

	public static void enterDcOrderId() {
		domContainer.dcOrderId.sendKeys(DomPOJO.getDoNum());
	}

	public static void clickOnApplyInDisOrders() {
		domContainer.dcOrderIdApply.click();
	}

	public static void clickOnReleaseBtn() {
		domContainer.doReleaseBtn.click();
	}

	public static String getOrderStatus() {
		BrowserDriver.waitForPageToBestable();
		BrowserDriver.waitForSec(5);
		WebElement statusElement = domContainer.orderStatus.get(0);
		String statusTxt = statusElement.getText();
		return statusTxt;
	}

	public static String releaseOrders(String orderNum) throws Exception {
		getEnvUrl();
		if (!DomPOJO.getLdcDos().isEmpty()) {
			for (String doId : DomPOJO.getLdcDos().keySet()) {
				clickOnDisOrdersMenu();
				domContainer.dcOrderId.sendKeys(doId);
				clickOnApplyInDisOrders();
				doubleClickOnDoOrder();
				BrowserDriver.waitForSec(5);
				clickOnReleaseBtn();
				selectMenu();
				enterOrderNum(orderNum);
				orderStatus = getOrderStatus();
			}
		}
		if (!DomPOJO.getStoreDos().isEmpty()) {
			for (String doID : DomPOJO.getStoreDos().keySet()) {
				String fFacility = DomPOJO.getStoreDos().get(doID);
				BrowserDriver.getCurrentDriver().get(domUrl);
				try {
					domContainer.logout.click();
					domContainer.logoutOk.click();
				} catch (Exception e) {
				}
				if (fFacility.startsWith("0")) {
					fFacility = fFacility.substring(1, fFacility.length());
				}
				loginToStore("jcrew" + fFacility, "store" + fFacility);
				domContainer.orderIdInStoreLogin.sendKeys(doID);
				domContainer.doPageApply.click();
				clickOnCheckboxForEachDo();
				BrowserDriver.waitForElementToEnabled(domContainer.acceptAndPickListBtn, 100);
				String parentWindow = BrowserDriver.getCurrentDriver().getWindowHandle();
				domContainer.acceptAndPickListBtn.click();
				BrowserDriver.waitForSec(5);
				Set<String> allWindows = BrowserDriver.getCurrentDriver().getWindowHandles();
				allWindows.remove(parentWindow);
				if (allWindows.size() > 0) {
					for (String curWindow : allWindows) {
						BrowserDriver.getCurrentDriver().switchTo().window(curWindow);
						BrowserDriver.getCurrentDriver().close();
						BrowserDriver.waitForSec(2);
					}
					BrowserDriver.getCurrentDriver().switchTo().window(parentWindow);
				}
				// waitAllDoToBecomeToStatus("Accepted");
				// BrowserDriver.waitForElementToEnabled(domContainer.packListBtn, 100);
				BrowserDriver.waitForSec(5);
				domContainer.packListBtn.click();
				String itemId = DomPOJO.getItemsInOrder().get(doID);
				System.out.println(itemId);
				if (items.size() > 1) {
					for (int i = 0; i < items.size(); i++) {
						domContainer.upc.sendKeys(items.get(i));
						BrowserDriver.waitForSec(2);
						domContainer.enterUpc.click();
						BrowserDriver.waitForSec(5);
						try {
							BrowserDriver.waitForElementToEnabled(domContainer.selectItemPopUp, 100);
							if (domContainer.selectItemPopUp.isDisplayed()) {
								domContainer.clickRadioButton.click();
								BrowserDriver.waitForSec(2);
								domContainer.selectButton.click();
								BrowserDriver.waitForSec(2);
							}
						} catch (Exception e) {
						}
					}
				} else {
					domContainer.upc.sendKeys(itemId);
					domContainer.enterUpc.click();
					BrowserDriver.waitForSec(2);
				}
				BrowserDriver.waitForElementToEnabled(domContainer.finishPacking, 100);
				domContainer.finishPacking.click();
				BrowserDriver.waitForSec(5);
				init();
				BrowserDriver.waitForElementToBeClickable(domContainer.shipItem, 100);
				String shipParentWindow = BrowserDriver.getCurrentDriver().getWindowHandle();
				domContainer.shipItem.click();
				Set<String> shipWindows = BrowserDriver.getCurrentDriver().getWindowHandles();
				shipWindows.remove(shipParentWindow);
				if (allWindows.size() > 0) {
					for (String curWindow : shipWindows) {
						BrowserDriver.getCurrentDriver().switchTo().window(curWindow);
						BrowserDriver.getCurrentDriver().close();
					}
					BrowserDriver.getCurrentDriver().switchTo().window(shipParentWindow);
				}
				// waitAllDoToBecomeToStatus("Manifested");
				BrowserDriver.getCurrentDriver().get(devToolsUrl);
				domContainer.devToolsPwd.sendKeys(devToolsPwd);
				domContainer.devToolsSubmit.click();
				// Thread.sleep(5000);
				try {
					if (domContainer.devToolsShipmentId.isDisplayed()) {
						domContainer.devToolsShipmentId.sendKeys(DatabaseReader.getShippingId(doID));
					} else {
						BrowserDriver.getCurrentDriver().get(devToolsUrl);
						init();
						domContainer.devToolsShipmentId.sendKeys(DatabaseReader.getShippingId(doID));
					}

				} catch (Exception e) {
					BrowserDriver.getCurrentDriver().get(devToolsUrl);
					init();
					domContainer.devToolsShipmentId.sendKeys(DatabaseReader.getShippingId(doID));
				}
				domContainer.devToolsUpdateShipmentId.click();
				BrowserDriver.waitForSec(2);
				Assert.assertTrue(domContainer.shipmentUpdated.isDisplayed());
				BrowserDriver.getCurrentDriver().get(domUrl);
				BrowserDriver.waitForSec(4);
				waitAllDoToBecomeToStatus("Shipped");
			}
		}
		return orderStatus;
	}

	public static void clickOnCheckboxForEachDo() {
		/*
		 * for (WebElement chk: domContainer.doStoreCheckbox()) { chk.click(); }
		 */
		domContainer.allCheckBox.click();
	}

	public static void waitAllDoToBecomeToStatus(String status) {
		BrowserDriver.waitForElement(domContainer.doStoreStatus().get(0), 100);
		for (WebElement orderStatus : domContainer.doStoreStatus()) {
			System.out.println("od status:: " + orderStatus.getText());
			WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(), 100);
			wait.until(ExpectedConditions.textToBePresentInElement(orderStatus, status));
		}
	}

	public static ArrayList<String> getItemsList() {
		items = new ArrayList<String>();
		String itemName = null;
		for (int i = 0; i < domContainer.itemsList.size(); i++) {
			itemName = domContainer.itemsList.get(i).getText();
			items.add(itemName);
		}
		return items;
	}

	public static void loginToStore(String username, String password) throws InterruptedException {
		loginUser(username, password);
	}

	public static void init() {
		domContainer = PageFactory.initElements(BrowserDriver.getCurrentDriver(), DomContainer.class);
	}

	public static void fullfilStsOrder_shipFromDifferentStore(String orderNumber) throws Exception {
		getEnvUrl();
		if (!DomPOJO.getLdcDos().isEmpty()) {
			for (String doId : DomPOJO.getLdcDos().keySet()) {
				clickOnDisOrdersMenu();
				domContainer.dcOrderId.sendKeys(doId);
				clickOnApplyInDisOrders();
				doubleClickOnDoOrder();
				clickOnReleaseBtn();
				selectMenu();
				enterOrderNum(orderNumber);
				orderStatus = getOrderStatus();
			}
		}
		if (!DomPOJO.getStoreDos().isEmpty()) {
			for (String doID : DomPOJO.getStoreDos().keySet()) {
				String fFacility = DomPOJO.getStoreDos().get(doID);
				BrowserDriver.getCurrentDriver().get(domUrl);
				try {
					domContainer.logout.click();
					domContainer.logoutOk.click();
				} catch (Exception e) {
				}
				if (fFacility.startsWith("0")) {
					fFacility = fFacility.substring(1, fFacility.length());
				}
				loginToStore("jcrew" + fFacility, "store" + fFacility);
				BrowserDriver.waitForElement(domContainer.orderIdInStoreLogin, 100);
				domContainer.orderIdInStoreLogin.sendKeys(doID);
				domContainer.doPageApply.click();
				clickOnCheckboxForEachDo();
				BrowserDriver.waitForElementToEnabled(domContainer.acceptAndPickListBtn, 100);
				String parentWindow = BrowserDriver.getCurrentDriver().getWindowHandle();
				domContainer.acceptAndPickListBtn.click();
				BrowserDriver.waitForSec(5);
				Set<String> allWindows = BrowserDriver.getCurrentDriver().getWindowHandles();
				allWindows.remove(parentWindow);
				if (allWindows.size() > 0) {
					for (String curWindow : allWindows) {
						BrowserDriver.getCurrentDriver().switchTo().window(curWindow);
						BrowserDriver.getCurrentDriver().close();
						BrowserDriver.waitForSec(2);
					}
					BrowserDriver.getCurrentDriver().switchTo().window(parentWindow);
				}
				// waitAllDoToBecomeToStatus("Accepted");
				// BrowserDriver.waitForElementToEnabled(domContainer.packListBtn, 100);
				BrowserDriver.waitForSec(5);
				domContainer.packListBtn.click();
				String itemId = DomPOJO.getItemsInOrder().get(doID);
				System.out.println(itemId);
				if (items.size() > 1) {
					for (int i = 0; i < items.size(); i++) {
						domContainer.upc.clear();
						domContainer.upc.sendKeys(items.get(i));
						BrowserDriver.waitForSec(2);
						domContainer.enterUpc.click();
						BrowserDriver.waitForSec(5);
						try {
							BrowserDriver.waitForElementToEnabled(domContainer.selectItemPopUp, 100);
							if (domContainer.selectItemPopUp.isDisplayed()) {
								domContainer.clickRadioButton.click();
								BrowserDriver.waitForSec(2);
								domContainer.selectButton.click();
								BrowserDriver.waitForSec(4);
							}
						} catch (Exception e) {
						}
					}
				} else {
					domContainer.upc.sendKeys(itemId);
					domContainer.enterUpc.click();
					BrowserDriver.waitForSec(2);
				}
				BrowserDriver.waitForElementToEnabled(domContainer.finishPacking, 100);
				domContainer.finishPacking.click();
				BrowserDriver.waitForSec(5);
				init();
				BrowserDriver.waitForElementToBeClickable(domContainer.shipItem, 100);
				String shipParentWindow = BrowserDriver.getCurrentDriver().getWindowHandle();
				domContainer.shipItem.click();
				Set<String> shipWindows = BrowserDriver.getCurrentDriver().getWindowHandles();
				shipWindows.remove(shipParentWindow);
				if (allWindows.size() > 0) {
					for (String curWindow : shipWindows) {
						BrowserDriver.getCurrentDriver().switchTo().window(curWindow);
						BrowserDriver.getCurrentDriver().close();
						BrowserDriver.waitForSec(2);
					}
					BrowserDriver.getCurrentDriver().switchTo().window(shipParentWindow);
				}
				// waitAllDoToBecomeToStatus("Manifested");
				BrowserDriver.getCurrentDriver().get(devToolsUrl);
				domContainer.devToolsPwd.sendKeys(devToolsPwd);
				domContainer.devToolsSubmit.click();
				// Thread.sleep(5000);
				try {
					if (domContainer.devToolsShipmentId.isDisplayed()) {
						domContainer.devToolsShipmentId.sendKeys(DatabaseReader.getShippingId(doID));
					} else {
						BrowserDriver.getCurrentDriver().get(devToolsUrl);
						init();
						domContainer.devToolsShipmentId.sendKeys(DatabaseReader.getShippingId(doID));
					}

				} catch (Exception e) {
					BrowserDriver.getCurrentDriver().get(devToolsUrl);
					init();
					domContainer.devToolsShipmentId.sendKeys(DatabaseReader.getShippingId(doID));
				}
				domContainer.devToolsUpdateShipmentId.click();
				BrowserDriver.waitForSec(4);
				Assert.assertTrue(domContainer.shipmentUpdated.isDisplayed());
				BrowserDriver.getCurrentDriver().get(domUrl);
				BrowserDriver.waitForSec(4);
				waitAllDoToBecomeToStatus("Shipped");
			}
			getNewDoNumber();
			configPutty_diffStore();
		}
	}

	public static void fullfilStsOrder_shipFromSameStore(String orderNumber) throws Exception {
		getEnvUrl();
		/*if (!DomPOJO.getLdcDos().isEmpty()) {
			for (String doId : DomPOJO.getLdcDos().keySet()) {
				clickOnDisOrdersMenu();
				domContainer.dcOrderId.sendKeys(doId);
				clickOnApplyInDisOrders();
				doubleClickOnDoOrder();
				clickOnReleaseBtn();
				selectMenu();
				enterOrderNum(orderNumber);
				orderStatus = getOrderStatus();
			}
		}
		if (!DomPOJO.getStoreDos().isEmpty()) {
			for (String doID : DomPOJO.getStoreDos().keySet()) {
				String fFacility = DomPOJO.getStoreDos().get(doID);
				BrowserDriver.getCurrentDriver().get(domUrl);
				try {
					domContainer.logout.click();
					domContainer.logoutOk.click();
				} catch (Exception e) {
				}
				if (fFacility.startsWith("0")) {
					fFacility = fFacility.substring(1, fFacility.length());
				}
				loginToStore("jcrew" + fFacility, "store" + fFacility);
				BrowserDriver.waitForElement(domContainer.orderIdInStoreLogin, 100);
				domContainer.orderIdInStoreLogin.sendKeys(doID);
				domContainer.doPageApply.click();
				clickOnCheckboxForEachDo();
				BrowserDriver.waitForElementToEnabled(domContainer.acceptAndPickListBtn, 100);
				String parentWindow = BrowserDriver.getCurrentDriver().getWindowHandle();
				domContainer.acceptAndPickListBtn.click();
				BrowserDriver.waitForSec(5);
				Set<String> allWindows = BrowserDriver.getCurrentDriver().getWindowHandles();
				allWindows.remove(parentWindow);
				if (allWindows.size() > 0) {
					for (String curWindow : allWindows) {
						BrowserDriver.getCurrentDriver().switchTo().window(curWindow);
						BrowserDriver.getCurrentDriver().close();
						BrowserDriver.waitForSec(2);
					}
					BrowserDriver.getCurrentDriver().switchTo().window(parentWindow);
				}
				// waitAllDoToBecomeToStatus("Accepted");
				// BrowserDriver.waitForElementToEnabled(domContainer.packListBtn, 100);
				BrowserDriver.waitForSec(5);
				domContainer.packListBtn.click();
				String itemId = DomPOJO.getItemsInOrder().get(doID);
				System.out.println(itemId);
				if (items.size() > 1) {
					for (int i = 0; i < items.size(); i++) {
						domContainer.upc.clear();
						domContainer.upc.sendKeys(items.get(i));
						BrowserDriver.waitForSec(2);
						domContainer.enterUpc.click();
						BrowserDriver.waitForSec(5);
						try {
							BrowserDriver.waitForElementToEnabled(domContainer.selectItemPopUp, 100);
							if (domContainer.selectItemPopUp.isDisplayed()) {
								domContainer.clickRadioButton.click();
								BrowserDriver.waitForSec(2);
								domContainer.selectButton.click();
								BrowserDriver.waitForSec(4);
							}
						} catch (Exception e) {
						}
					}
				} else {
					domContainer.upc.sendKeys(itemId);
					domContainer.enterUpc.click();
					BrowserDriver.waitForSec(2);
				}
				BrowserDriver.waitForElementToEnabled(domContainer.finishPacking, 100);
				domContainer.finishPacking.click();
				BrowserDriver.waitForSec(5);
				init();
				BrowserDriver.waitForElementToBeClickable(domContainer.shipItem, 100);
				String shipParentWindow = BrowserDriver.getCurrentDriver().getWindowHandle();
				domContainer.shipItem.click();
				Set<String> shipWindows = BrowserDriver.getCurrentDriver().getWindowHandles();
				shipWindows.remove(shipParentWindow);
				if (allWindows.size() > 0) {
					for (String curWindow : shipWindows) {
						BrowserDriver.getCurrentDriver().switchTo().window(curWindow);
						BrowserDriver.getCurrentDriver().close();
						BrowserDriver.waitForSec(2);
					}
					BrowserDriver.getCurrentDriver().switchTo().window(shipParentWindow);
				}
				//waitAllDoToBecomeToStatus("Pending pick up");
				DatabaseReader.loadToCustomPeople();
				String trackStatus = DatabaseReader.trackShipmentOrder(orderNum);
				Reporter.log("tracking shipment order: " + trackStatus);
			}*/
			configPutty_sameStore();
		//}
	}

	@SuppressWarnings("unused")
	public static void getNewDoNumber() throws Exception {
		try {
			domContainer.logout.click();
			domContainer.logoutOk.click();
		} catch (Exception e) {
		}
		loginUser("esdomqa1", "esdomqa1");
		selectMenu();
		String orderNum = "3050054879";
		enterOrderNum(orderNum);
		BrowserDriver.waitForSec(2);
		clickOnApply();
		doubleClickOnOrder();
		selectOption("distributionOrderDetails");
		for (int i = 1; i <= domContainer.doDetails.size(); i++) {
			String doId = BrowserDriver.getCurrentDriver().findElement(By.xpath(
					"//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row']["
							+ i + "]/td[1]/span[1]"))
					.getText().trim();
			String doStatus = BrowserDriver.getCurrentDriver().findElement(By.xpath(
					"//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row']["
							+ i + "]/td[4]/span"))
					.getText().trim();
			String destinationFacility = BrowserDriver.getCurrentDriver().findElement(By.xpath(
					"//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row']["
							+ i + "]/td[8]/a"))
					.getText().trim();
			if (doStatus.equalsIgnoreCase("Draft")) {
				DomPOJO.getNewDo().put(doStatus, doId);
				DomPOJO.getDestinationFacilities().put(doStatus, destinationFacility);
			}
		}
		for (String doStatus : DomPOJO.getDestinationFacilities().keySet()) {
			String dFacility = DomPOJO.getDestinationFacilities().get(doStatus);
			if (dFacility.startsWith("0")) {
				dFacility = dFacility.substring(1, dFacility.length());
			}
		}
		for (String doStatus : DomPOJO.getNewDo().keySet()) {
			String newDoNum = DomPOJO.getNewDo().get(doStatus);
		}
		// DatabaseReader.loadToCustomPeople();
		String trackStatus = DatabaseReader.trackShipmentOrder(orderNum);
		Reporter.log("tracking shipment order: " + trackStatus);
		// Assert.assertEquals("null", trackStatus);
	}

	static String getStatusAsBL = "/ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.sh /ES_DOM_SHARE/documents/scripts/JC_STS_OLS /ES_DOM_SHARE/documents/scripts/SetEnv_Q4.env >> /ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.log 2>/ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.err";
	static String manualReceving = "/ES_DOM_SHARE/documents/scripts/JC_STS_MANUAL_RECV/JC_STS_MANUAL_RECV.sh /ES_DOM_SHARE/documents/scripts/JC_STS_MANUAL_RECV /ES_DOM_SHARE/documents/scripts/SetEnv.env>> /ES_DOM_SHARE/documents/scripts/JC_STS_MANUAL_RECV/JC_STS_MANUAL_RECV.log 2>/ES_DOM_SHARE/documents/scripts/JC_STS_MANUAL_RECV/JC_STS_MANUAL_RECV.err";
	static String getStatusRP = "/ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.sh /ES_DOM_SHARE/documents/scripts/JC_STS_OLS /ES_DOM_SHARE/documents/scripts/SetEnv_Q4.env >> /ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.log 2>/ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.err";
	static String getConfirmPickUp = "/ES_DOM_SHARE/documents/scripts/JC_STS_PickupConfirm_OLS/JC_STS_PickupConfirm_OLS.sh /ES_DOM_SHARE/documents/scripts/JC_STS_PickupConfirm_OLS /ES_DOM_SHARE/documents/scripts/SetEnv_Q4.env >> /ES_DOM_SHARE/documents/scripts/JC_STS_PickupConfirm_OLS/JC_STS_PickupConfirm_OLS.log 2>/ES_DOM_SHARE/documents/scripts/JC_STS_PickupConfirm_OLS/JC_STS_PickupConfirm_OLS.err";

	@SuppressWarnings({ "unused", "static-access" })
	public static void configPutty_diffStore() throws Exception {

		Session session = new JSch().getSession("esadmq1", "bwi-esdom-q12.jcrew.com", 22);
		session.setPassword("changeme");
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(getStatusAsBL);
		String result = IOUtils.toString(channel.getInputStream());
		String trackStatus = DatabaseReader.trackShipmentOrder(orderNum);
		Reporter.log("tracking shipment order: " + trackStatus);
		Assert.assertTrue(trackStatus.equalsIgnoreCase("BL") || trackStatus.equalsIgnoreCase("BP")
				|| trackStatus.equalsIgnoreCase("BB"));

		BrowserDriver.getCurrentDriver().get("http://bwi-esmsf-q11/Manual_receiving/T_R_drop_down.php");
		BrowserDriver.waitForElementToVisible(DomContainer.manualTrack, 30);
		for (String doID : DomPOJO.getDestinationFacilities().keySet()) {
			String dFacility = DomPOJO.getDestinationFacilities().get(doID);
			BrowserDriver.waitForSec(2);
			DomContainer.enterStoreId.sendKeys(dFacility);
			BrowserDriver.waitForSec(2);
			DomContainer.enterUserId.sendKeys("123");
			BrowserDriver.waitForSec(2);
			DomContainer.enterOrderNum.sendKeys(orderNum);
			BrowserDriver.waitForSec(2);
			DomContainer.sendButton.click();
			BrowserDriver.waitForSec(2);
			Select trackList = new Select(DomContainer.trackNumList);
			trackList.selectByIndex(0);
			BrowserDriver.waitForSec(2);
			DomContainer.submitButton.click();
			BrowserDriver.waitForSec(4);
			Assert.assertTrue(DomContainer.submitionSuccess.isDisplayed());
			((ChannelExec) channel).setCommand(getStatusAsBL);
			String result_RP = IOUtils.toString(channel.getInputStream());
			Reporter.log("Ready for pickup status: " + result_RP);
			String trackStatus_RP = DatabaseReader.trackShipmentOrder(orderNum);
			Assert.assertTrue(trackStatus_RP.equalsIgnoreCase("RP"));
			BrowserDriver.getCurrentDriver().get("http://bwi-esmsf-q11/saving-signature-SPU_5/index_jcrew.php?");
			DomContainer.enterOrderNum_sign.sendKeys(orderNum);
			DomContainer.submit_sign.click();
			BrowserDriver driver = new BrowserDriver();
			Actions builder = new Actions(driver.getCurrentDriver());
			Action drawAction = builder.moveToElement(DomContainer.signature, 300, 100).clickAndHold()
					.moveByOffset(330, 130).moveByOffset(340, 140).release().build();
			drawAction.perform();
			DomContainer.doneSigning.click();
			BrowserDriver.waitForSec(4);
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			Assert.assertTrue(DomContainer.pickedUp.isDisplayed());
			((ChannelExec) channel).setCommand(getStatusAsBL);
			String result_CP = IOUtils.toString(channel.getInputStream());
			Reporter.log("Picked up status: " + result_CP);
			String trackStatus_CP = DatabaseReader.trackShipmentOrder(orderNum);
			Assert.assertTrue(trackStatus_CP.equalsIgnoreCase("CP"));
		}
		channel.disconnect();
		session.disconnect();

	}

	static String status_RP = "/ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.sh /ES_DOM_SHARE/documents/scripts/JC_STS_OLS /ES_DOM_SHARE/documents/scripts/SetEnv_Q4.env >> /ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.log 2>/ES_DOM_SHARE/documents/scripts/JC_STS_OLS/JC_STS_OLS.err";
	static String status_CP = "/ES_DOM_SHARE/documents/scripts/JC_STS_PickupConfirm_OLS/JC_STS_PickupConfirm_OLS.sh /ES_DOM_SHARE/documents/scripts/JC_STS_PickupConfirm_OLS /ES_DOM_SHARE/documents/scripts/SetEnv_Q4.env >> /ES_DOM_SHARE/documents/scripts/JC_STS_PickupConfirm_OLS/JC_STS_PickupConfirm_OLS.log 2>/ES_DOM_SHARE/documents/scripts/JC_STS_PickupConfirm_OLS/JC_STS_PickupConfirm_OLS.err";
	@SuppressWarnings({ "unused", "static-access" })
	public static void configPutty_sameStore() throws Exception {
		String orderNum = "3050054967";
		Session session = new JSch().getSession("esadmq1", "bwi-esdom-q12.jcrew.com", 22);
		session.setPassword("changeme");
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		//Channel channel = session.openChannel("shell");
		 ChannelExec channel = (ChannelExec) session.openChannel("exec");
		((ChannelExec) channel).setCommand(status_RP);
		((ChannelExec)channel).setErrStream(System.err);
		//String result = IOUtils.toString(channel.getInputStream());
		InputStream ins=channel.getInputStream();
		channel.connect();
		String trackStatus = DatabaseReader.trackShipmentOrder(orderNum);
		Reporter.log("tracking shipment order: " + trackStatus);
		Assert.assertTrue(trackStatus.equalsIgnoreCase("RP") || trackStatus.equalsIgnoreCase("BP"));
		BrowserDriver.getCurrentDriver().get("http://bwi-esmsf-q11/saving-signature-SPU_5/index_jcrew.php?");
		DomContainer.enterOrderNum_sign.sendKeys(orderNum);
		DomContainer.submit_sign.click();
		BrowserDriver driver = new BrowserDriver();
		Actions builder = new Actions(driver.getCurrentDriver());
		Action drawAction = builder.moveToElement(DomContainer.signature, 300, 100).clickAndHold()
				.moveByOffset(330, 130).moveByOffset(340, 140).release().build();
		drawAction.perform();
		DomContainer.doneSigning.click();
		BrowserDriver.waitForSec(4);
		Robot r = new Robot();

		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Assert.assertTrue(DomContainer.pickedUp.isDisplayed());
		((ChannelExec) channel).setCommand(status_CP);
		//String result_CP = IOUtils.toString(channel.getInputStream());
		ins=channel.getInputStream();
		//channel.connect();
		//Reporter.log("Picked up status : " + result_CP);
		String trackStatus_CP = DatabaseReader.trackShipmentOrder(orderNum);
		Assert.assertTrue(trackStatus_CP.equalsIgnoreCase("CP"));
		channel.disconnect();
		session.disconnect();
	}

	public static String getorderNumber(String orderNumber) {
		orderNum = orderNumber;
		return orderNum;
	}

}
