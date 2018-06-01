package com.jcrew.page;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.Util;

public class TrainingOrder {
	private WebDriver driver;
	private String orderNum;
	private String newOrderNum=null;
	String newOrder = null;
	DriverFactory driverFactory = new DriverFactory();
	public String copyNewOrder(String finalSaleOrdNum) throws Exception {
		File f = new File("C:\\Desktop\\sidecar-functional-tests\\desktop-functional-tests\\properties\\contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream(f);
		prop.load(inputFile);
		driver = driverFactory.getDriver();
		driver.get(prop.getProperty("ccUrl"));
		Util.waitForPageFullyLoaded(driver);
		WebElement userName = driver.findElement(By.xpath("//*[@id='idLoginCSR']/form/table/tbody/tr[1]/td[2]/input[3]"));
		userName.sendKeys(prop.getProperty("userName"));
		WebElement password = driver.findElement(By.xpath("(//input[@name='LOGIN<>password'])[1]"));
		password.sendKeys(prop.getProperty("password"));
		WebElement loginButton = driver.findElement(By.xpath("(//input[@class='bmsFormButton'])[1]"));
		loginButton.click();
		Util.waitForPageFullyLoaded(driver);
		WebElement frameTabs = driver.findElement(By.xpath("//iframe[@id='cc_frame_tabs']"));
		driver.switchTo().frame(frameTabs);
		Util.wait(3000);
		WebElement ordersButton = driver.findElement(By.xpath("//*[@id='idorders']/div/a"));
		ordersButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(3000);
		driver.switchTo().defaultContent();
		WebElement ccFrameContent = driver.findElement(By.xpath("//iframe[@id='cc_frame_content']"));
		driver.switchTo().frame(ccFrameContent);
		WebElement orderNumber = driver.findElement(By.xpath("//input[@name='ORDER_SEARCH<>orderNum']"));
		orderNumber.sendKeys(finalSaleOrdNum);
		Util.wait(3000);
		WebElement searchButton = driver.findElement(By.xpath("//input[@name='CC_search_ok']"));
		searchButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(3000);
		//driver.switchTo().defaultContent();
		WebElement copyButton = driver.findElement(By.xpath("//button[@name='clone_order']/table/tbody/tr/td"));
		copyButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		WebElement submitOrder = driver.findElement(By.xpath("(//button[@name='submit_order']/table/tbody/tr/td[2])[1]"));
		submitOrder.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		WebElement getOrderNumber = driver.findElement(By.xpath("//span[@class='bmsSummaryHeader']"));
		orderNum = getOrderNumber.getText();
		newOrderNum= orderNum.replaceAll("[^0-9]", "");
		return newOrderNum;
	}
	public String copyPromoOrder(String finalSaleOrdNum,String promoCode) throws Exception {
		File f = new File("C:\\Desktop\\sidecar-functional-tests\\desktop-functional-tests\\properties\\contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream(f);
		prop.load(inputFile);
		driver = driverFactory.getDriver();
		driver.get(prop.getProperty("ccUrl"));
		Util.waitForPageFullyLoaded(driver);
		WebElement userName = driver.findElement(By.xpath("//*[@id='idLoginCSR']/form/table/tbody/tr[1]/td[2]/input[3]"));
		userName.sendKeys(prop.getProperty("userName"));
		WebElement password = driver.findElement(By.xpath("(//input[@name='LOGIN<>password'])[1]"));
		password.sendKeys(prop.getProperty("password"));
		WebElement loginButton = driver.findElement(By.xpath("(//input[@class='bmsFormButton'])[1]"));
		loginButton.click();
		Util.waitForPageFullyLoaded(driver);
		WebElement frameTabs = driver.findElement(By.xpath("//iframe[@id='cc_frame_tabs']"));
		driver.switchTo().frame(frameTabs);
		Util.wait(3000);
		WebElement ordersButton = driver.findElement(By.xpath("//*[@id='idorders']/div/a"));
		ordersButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(3000);
		driver.switchTo().defaultContent();
		WebElement ccFrameContent = driver.findElement(By.xpath("//iframe[@id='cc_frame_content']"));
		driver.switchTo().frame(ccFrameContent);
		WebElement orderNumber = driver.findElement(By.xpath("//input[@name='ORDER_SEARCH<>orderNum']"));
		orderNumber.sendKeys(finalSaleOrdNum);
		Util.wait(3000);
		WebElement searchButton = driver.findElement(By.xpath("//input[@name='CC_search_ok']"));
		searchButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(3000);
		//driver.switchTo().defaultContent();
		WebElement copyButton = driver.findElement(By.xpath("//button[@name='clone_order']/table/tbody/tr/td"));
		copyButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		WebElement promoCodeText = driver.findElement(By.xpath("//input[@id='promotionCode1']"));
		promoCodeText.sendKeys(promoCode);
		WebElement recalculateTotals = driver.findElement(By.xpath("(//button/table/tbody/tr/td[contains(text(),' Recalculate Totals')])[2]"));
		recalculateTotals.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		WebElement submitOrder = driver.findElement(By.xpath("(//button[@name='submit_order']/table/tbody/tr/td[2])[1]"));
		submitOrder.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		WebElement getOrderNumber = driver.findElement(By.xpath("//span[@class='bmsSummaryHeader']"));
		orderNum = getOrderNumber.getText();
		newOrderNum= orderNum.replaceAll("[^0-9]", "");
		Util.wait(3000);
		WebElement logOut = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		logOut.click();
		Util.wait(3000);
		driver.manage().deleteAllCookies();
		Util.wait(1000);
		return newOrderNum;
	}
	public void getNewOrder() throws Exception {
		File src = new File("properties\\Training.xlsx");
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		int i = sheet1.getLastRowNum();
		String finalOrder = null;
		for (int j = 1; j <=i; j++) {
			if (!sheet1.getRow(j).getCell(0).getStringCellValue().equalsIgnoreCase("null")) {
				finalOrder = sheet1.getRow(j).getCell(0).getStringCellValue();
			}
			newOrder = copyNewOrder(finalOrder);
			sheet1.getRow(j).getCell(1).setCellValue(newOrder);
			FileOutputStream fout = new FileOutputStream(src);
			wb.write(fout);
		}
		wb.close();
	}
	
	public void getPromoOrder() throws Exception {
		File src = new File("properties\\Training.xlsx");
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(1);
		int i = sheet1.getLastRowNum();
		String finalOrder = null;
		String promoCode = null;
		for (int j = 1; j <=i; j++) {
			if (!sheet1.getRow(j).getCell(0).getStringCellValue().equalsIgnoreCase("null")) {
				finalOrder = sheet1.getRow(j).getCell(0).getStringCellValue();
				promoCode = sheet1.getRow(j).getCell(1).getStringCellValue();
			}
			newOrder = copyPromoOrder(finalOrder,promoCode);
			sheet1.getRow(j).getCell(2).setCellValue(newOrder);
			FileOutputStream fout = new FileOutputStream(src);
			wb.write(fout);
		}
		wb.close();
	}
	
	/*public static void main(String[] args) throws Exception {
		TrainingOrder t= new TrainingOrder();
		t.excelUtility();
	}*/
}
