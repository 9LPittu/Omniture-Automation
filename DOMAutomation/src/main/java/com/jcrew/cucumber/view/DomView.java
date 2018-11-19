package com.jcrew.cucumber.view;

import com.jcrew.cucumber.container.DomContainer;
import com.jcrew.cucumber.container.DomPOJO;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.DatabaseReader;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Set;

public class DomView {
    static String UName;
    static String pwd;
    private static DomContainer domContainer;
    public static int orderNum;
    public static String orderNumber;
    public static void loginUser(String username, String password) throws InterruptedException {
        UName = username;
        pwd = password;
       // domContainer.username.clear();
      domContainer.username.sendKeys(username);
        BrowserDriver.waitForElement(domContainer.password, 20);
       // domContainer.password.clear();
        domContainer.password.sendKeys(pwd);
        BrowserDriver.waitForElementToBeClickable(domContainer.login, 10);
        domContainer.login.click();
        
    }

    public static void selectMenu() {
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
    
    public enum Category {
		HomePage("HomePage"), Women("Women"), Men("Men"), Girls("Girls"), Boys(
				"Boys");

		private final String name;

		public String getName() {
			return name;
		}

		Category(String s) {
			name = s;
		}

	}


    public static void enterOrderNum(/*String orderNumb*/) throws Exception {
    	/*File fs = new File("E:\\Users\\laxman_p\\Desktop\\E2E_JCrew_Regression_TestCases.xlsx");
        FileInputStream fis = new FileInputStream(fs);
    	HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();
        for(int i=0;i<rows;i++){
        	String orderNumber = sheet.getRow(i).getCell(6).getStringCellValue();
        	if(!(orderNumber==null)){*/
    		//ArrayList<String> list = new ArrayList<String>();
			ArrayList<String> excelReading = new ArrayList<String>();
			String path = System.getProperty("user.dir") + File.separator +"src" + File.separator + "test" + File.separator+ "resources"+ File.separator+"E2E_JCrew_Regression_Test_Data.xlsx";
            File src = new File(path);
			//File src = new File("E:\\Users\\laxman_p\\Desktop\\E2E_JCrew_Regression_TestCases.xlsx");
			FileInputStream fis = new FileInputStream(src);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = wb.getSheetAt(0);
			int i = sheet1.getLastRowNum();
			System.out.println("===================="+sheet1.getRow(2).getCell(6).getStringCellValue());
			for (int j = 2; j <= i; j++) {
				//if (Integer.parseInt(sheet1.getRow(j).getCell(6).getStringCellValue())!=0)
				orderNumber = sheet1.getRow(j).getCell(6).getStringCellValue();
				//System.out.println("====================ord"+orderNum);
				
					//orderNumber = Integer.toString(orderNum);
					excelReading.add(orderNumber);
				//System.out.println("===================="+orderNumber);
				break;
			}
        		domContainer.orderNumber.clear();
                domContainer.orderNumber.sendKeys(excelReading.get(0));
        	/*}
        }*/
    }

    public static void clickOnApply() {
        domContainer.applyFilter.click();
    }

    public static void verifyOrderInSearchResults() {
        String orderTpe= domContainer.orderType.getText();
        DomPOJO.setOrderType(orderTpe);
        Assert.assertTrue(!domContainer.orderStatus.isEmpty());
    }

    public static void verifyOrderStatus(String status) {
        BrowserDriver.waitForPageToBestable();
        BrowserDriver.waitForSec(30);
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
        /*String itemId = domContainer.itemId.getText();
        DomPOJO.setItemId(itemId);*/
    }

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
        //domContainer.createDo.click();
        BrowserDriver.clickOnElementWithJS(domContainer.createDo);
        BrowserDriver.waitForSec(10);
        BrowserDriver.waitForTextPresent(BrowserDriver.getCurrentDriver(), By.xpath("//table[@id='dataForm:coLineViewAdditionalListTable_body']//tr/td[5]/span"), "DO Created", 100);
        //       BrowserDriver.waitForElementToDisappear(By.id("dataForm:coLViewAI_createDOButton"), 60);

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
        //DomPOJO.getStoreDos().put("3070025200", "0306");
        //Set ldcSet= new HashSet();
        BrowserDriver.waitForSec(5);
        BrowserDriver.waitForPageToBestable();
        for (int i = 1; i <= domContainer.doDetails.size(); i++) {
            String fulfillmentFacility = BrowserDriver.getCurrentDriver().findElement(By.xpath("//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row'][" + i + "]/td[7]/a")).getText().trim();
            String doId = BrowserDriver.getCurrentDriver().findElement(By.xpath("//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row'][" + i + "]/td[1]/span[1]")).getText().trim();
            String itemId = BrowserDriver.getCurrentDriver().findElement(By.xpath("//table[@id='dataForm:dolinelistview_id:DOLineList_MainListTable_body']/tbody/tr[@class='advtbl_row'][" + i + "]/td[9]/a[1]")).getText().trim();
            if (fulfillmentFacility.equalsIgnoreCase("LDC")) {
                DomPOJO.getLdcDos().put(doId, fulfillmentFacility);
            } else {
                DomPOJO.getStoreDos().put(doId, fulfillmentFacility);
            }
            DomPOJO.getItemsInOrder().put(doId, itemId);
        }
        /*
        String doNum = domContainer.doNum.getText();
        DomPOJO.setDoNum(doNum);
        System.out.println("Do: " + doNum);*/
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

    public static void releaseOrders() throws InterruptedException {
        if (!DomPOJO.getLdcDos().isEmpty()) {
            for (String doId : DomPOJO.getLdcDos().keySet()) {
                clickOnDisOrdersMenu();
                domContainer.dcOrderId.sendKeys(doId);
                clickOnApplyInDisOrders();
                doubleClickOnDoOrder();
                clickOnReleaseBtn();
            }
        }
        if (!DomPOJO.getStoreDos().isEmpty()) {
            for (String doID : DomPOJO.getStoreDos().keySet()) {
                String fFacility = DomPOJO.getStoreDos().get(doID);
                BrowserDriver.getCurrentDriver().get("http://bwi-esdom-q11.jcrew.com:30000/ucl/auth/jsp/home.jsp");
                try {
                    domContainer.logout.click();
                    domContainer.logoutOk.click();
                }catch (Exception e){
                }
                if (fFacility.startsWith("0")) {
                    fFacility = fFacility.substring(1, fFacility.length());
                }
                loginToStore("jcrew" + fFacility, "store" + fFacility);
                //domContainer.blueMartiniDoId.sendKeys(doID);
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
                    }
                    BrowserDriver.getCurrentDriver().switchTo().window(parentWindow);
                }
                waitAllDoToBecomeToStatus("Accepted");
                BrowserDriver.waitForElementToEnabled(domContainer.packListBtn, 100);
                domContainer.packListBtn.click();
                String itemId = DomPOJO.getItemsInOrder().get(doID);
                System.out.println(itemId);
                domContainer.upc.sendKeys(itemId);
                domContainer.enterUpc.click();
                BrowserDriver.waitForSec(5);
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

                BrowserDriver.getCurrentDriver().get("http://bwi-esdom-q12.jcrew.com:33000/eem/parcel/jsp/MarkShipmentAsInTransit.jsp");

                try {
                    BrowserDriver.waitForElement(domContainer.username, 30);
                    loginUser(UName, pwd);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                domContainer.devToolsPwd.sendKeys("whap319");
                domContainer.devToolsSubmit.click();
                try {
                    domContainer.devToolsShipmentId.sendKeys(DatabaseReader.getShippingId(doID));
                } catch (Exception e) {
                    BrowserDriver.getCurrentDriver().get("http://bwi-esdom-q12.jcrew.com:33000/eem/parcel/jsp/MarkShipmentAsInTransit.jsp");
                    init();
                    domContainer.devToolsShipmentId.sendKeys(DatabaseReader.getShippingId(doID));
                }
                domContainer.devToolsUpdateShipmentId.click();
                BrowserDriver.waitForSec(2);
            }
        }
    }

    public static void clickOnCheckboxForEachDo() {
        /*for (WebElement chk: domContainer.doStoreCheckbox()) {
            chk.click();
        }*/
        domContainer.allCheckBox.click();
    }

    public static void waitAllDoToBecomeToStatus(String status) {
        BrowserDriver.waitForSec(5);
        BrowserDriver.waitForElement(domContainer.doStoreStatus().get(0), 100);
        for (WebElement orderStatus : domContainer.doStoreStatus()) {
            System.out.println("od status:: " + orderStatus.getText());
            WebDriverWait wait = new WebDriverWait(BrowserDriver.getCurrentDriver(), 100);
            wait.until(ExpectedConditions.textToBePresentInElement(orderStatus, status));
        }
    }

    public static void loginToStore(String username, String password) throws InterruptedException {
        loginUser(username, password);
    }
    public static void init() {
        domContainer = PageFactory.initElements(BrowserDriver.getCurrentDriver(), DomContainer.class);
    }
    public static void excel() throws Exception{
    	//File fs = new File("E:\\Users\\laxman_p\\Desktop\\E2E_JCrew_Regression_TestCases.xlsx");
    	String path = System.getProperty("user.dir") + File.separator +"src" + File.separator + "test" + File.separator+ "resources"+ File.separator+"E2E_JCrew_Regression_Test_Data.xlsx";
        File fs = new File(path);

        FileInputStream fis = new FileInputStream(fs);
    	HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();
        for(int i=0;i<rows;i++){
        	String orderNumber = sheet.getRow(i).getCell(6).getStringCellValue();
        	if(!(orderNumber==null)){
        		
        	}
        	
        }
    }
}
