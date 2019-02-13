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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.Util;

public class TrainingOrder {
	private WebDriver driver;
	private String orderNum;
	private String newOrderNum = null;
	String newOrder = null;
	DriverFactory driverFactory = new DriverFactory();

	public String copyNewOrder(String finalSaleOrdNum) throws Exception {
		File f = new File(
				"C:\\Desktop\\sidecar-functional-tests\\desktop-functional-tests\\properties\\contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream(f);
		prop.load(inputFile);
		driver = driverFactory.getDriver();
		driver.get(prop.getProperty("ccUrl"));
		Util.waitForPageFullyLoaded(driver);
		WebElement userName = driver
				.findElement(By.xpath("//*[@id='idLoginCSR']/form/table/tbody/tr[1]/td[2]/input[3]"));
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
		// driver.switchTo().defaultContent();
		WebElement copyButton = driver.findElement(By.xpath("//button[@name='clone_order']/table/tbody/tr/td"));
		copyButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		WebElement submitOrder = driver
				.findElement(By.xpath("(//button[@name='submit_order']/table/tbody/tr/td[2])[1]"));
		submitOrder.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		WebElement getOrderNumber = driver.findElement(By.xpath("//span[@class='bmsSummaryHeader']"));
		orderNum = getOrderNumber.getText();
		newOrderNum = orderNum.replaceAll("[^0-9]", "");
		return newOrderNum;
	}

	public String copyPromoOrder(String finalSaleOrdNum, String promoCode) throws Exception {
		File f = new File(
				"C:\\Desktop\\sidecar-functional-tests\\desktop-functional-tests\\properties\\contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream(f);
		prop.load(inputFile);
		driver = driverFactory.getDriver();
		driver.get(prop.getProperty("ccUrl"));
		Util.waitForPageFullyLoaded(driver);
		WebElement userName = driver
				.findElement(By.xpath("//*[@id='idLoginCSR']/form/table/tbody/tr[1]/td[2]/input[3]"));
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
		// driver.switchTo().defaultContent();
		WebElement copyButton = driver.findElement(By.xpath("//button[@name='clone_order']/table/tbody/tr/td"));
		copyButton.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		WebElement promoCodeText = driver.findElement(By.xpath("//input[@id='promotionCode1']"));
		promoCodeText.sendKeys(promoCode);
		WebElement recalculateTotals = driver
				.findElement(By.xpath("(//button/table/tbody/tr/td[contains(text(),' Recalculate Totals')])[2]"));
		recalculateTotals.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		WebElement submitOrder = driver
				.findElement(By.xpath("(//button[@name='submit_order']/table/tbody/tr/td[2])[1]"));
		submitOrder.click();
		Util.waitForPageFullyLoaded(driver);
		Util.wait(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		WebElement getOrderNumber = driver.findElement(By.xpath("//span[@class='bmsSummaryHeader']"));
		orderNum = getOrderNumber.getText();
		newOrderNum = orderNum.replaceAll("[^0-9]", "");
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
		for (int j = 1; j <= i; j++) {
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
		for (int j = 1; j <= i; j++) {
			if (!sheet1.getRow(j).getCell(0).getStringCellValue().equalsIgnoreCase("null")) {
				finalOrder = sheet1.getRow(j).getCell(0).getStringCellValue();
				promoCode = sheet1.getRow(j).getCell(1).getStringCellValue();
			}
			newOrder = copyPromoOrder(finalOrder, promoCode);
			sheet1.getRow(j).getCell(2).setCellValue(newOrder);
			FileOutputStream fout = new FileOutputStream(src);
			wb.write(fout);
		}
		wb.close();
	}

	public void expressUser() {
		// driver.manage().timeouts().implicitlyWait(20000, null);
		
		int x;
		for (x = 119; x < 2220; x++) {
			String userName = "perftest" + x;
			String email = "perftest" + x + "@gmail.com";
			System.out.println("email is ============="+email);
			//driver.get("https://or.jcrew.com/r/login");
			driver.findElement(By.xpath("//*[text()='Sign In']")).click();
			//driver.navigate().to("https://or.jcrew.com/r/login");
			Util.wait(1000);
			driver.findElement(By.xpath("//*[@id='sidecarRegisterFirstName']")).sendKeys(userName);
			driver.findElement(By.xpath("//*[@id='sidecarRegisterLastName']")).sendKeys("tester");
			driver.findElement(By.xpath("//*[@id='sidecarRegisterEmail']")).sendKeys(email);
			driver.findElement(By.xpath("//*[@id='sidecarRegisterPassword']")).sendKeys("nft123");
			driver.findElement(By.xpath("//*[@id='page__signin']/article/section[2]/div/form/button")).click();
			System.out.println("User is able to create new account");
			System.out.println("User created >> " + email);
			Util.wait(5000);
			WebElement searchText = driver.findElement(By.xpath("//input[@class='nc-nav__search__input']"));
			searchText.sendKeys("pants");
			// await //driver.sleep(1000)
			// driver.actions().click(searchText).sendKeys(Key.ENTER).perform();
			searchText.sendKeys(Keys.ENTER);
			// WebElement productimage
			// =driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"));
			driver.findElement(By.xpath("(//div[@class='c-product__photos'])[3]")).click();
			Util.wait(2000);
			driver.findElement(By.xpath(
					"(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
					.click();
			Util.wait(2000);
			driver.findElement(By.id("btn__add-to-bag-wide")).click();
			Util.wait(2000);
			driver.findElement(By.xpath("//div[@class='nc-nav__bag-button__icon']")).click();
			Util.wait(2000);
			driver.findElement(By.xpath("//*[@id='button-checkout']")).click();
			Util.wait(2000);
			driver.findElement(By.xpath("//input[@id='firstNameSA']")).sendKeys(userName);
			driver.findElement(By.xpath("//input[@id='lastNameSA']")).sendKeys("tester");
			driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("123 street");
			driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys("94103");
			Util.wait(3000);
			driver.findElement(By.xpath("//input[@id='phoneNumSA']")).sendKeys("2345678912");

			driver.findElement(By.xpath("//*[@id='order-summary__button-continue']")).click();
			Util.wait(5000);
			try {
				WebElement popUp = driver.findElement(By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a"));
				popUp.click();
			} catch (Exception e) {
			}

			driver.findElement(By.xpath("//*[@id='main__button-continue']")).click();
			Util.wait(1000);
			driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys("5856371002514031");

			driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys("nft");

			driver.findElement(By.xpath("//*[@id='main__button-continue']")).click();

			//Actions a = new Actions(driver);
			Actions a = new Actions(driver);
			a.moveToElement(driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))).build().perform();
			Util.wait(2000);

			WebElement signOut = driver.findElement(By.xpath("//li[5]/a[text()='Sign Out']"));

			signOut.click();

		}
	}

	/*
	 * public static void main(String[] args) throws Exception { TrainingOrder t=
	 * new TrainingOrder(); t.excelUtility(); }
	 */
}
