package com.jcrew.page;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.listners.Reporter;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.Util;

@SuppressWarnings("unused")
public class ContentRegression {
	private WebDriver driver;
	private String dataValue;
	private long totalTime;
	private int urlStatus;
	private int imageURLStatus;
	ArrayList<String> list;
	public int code;
	String filePath;
	String colUrl = "URL";
	String colPageLoadTime = "Page Load Time";
	String colUrlStatus = "Url Status";
	String colImageStatus = "Images Status";
	String colFailedUrl = "Failed Url";
	private final PropertyReader reader = PropertyReader.getPropertyReader();
	DriverFactory driverFactory = new DriverFactory();
	public final Logger logger = LoggerFactory.getLogger(PageObject.class);
	private final StateHolder stateHolder = StateHolder.getInstance();
	Util util = new Util();
	String failedUrl = null;
	XSSFSheet sheet1;
	HashSet<String> linkUrl;
	@FindBy(xpath = "//div/a[contains(text(),'START SHOPPING')]")
	private WebElement startShopping;
	@FindBy(xpath = "//div[@class='email-capture--close modal-capture--close js-email-capture--close']/span[@class='icon-close']")
	private WebElement emailCapture;

	public void goesToHomePage() {;
		driver = driverFactory.getDriver();
		String country = reader.getProperty("country");
		String envUrl = reader.getProperty("url");
		Reporter.addStepLog("Country is: " + country);
		Reporter.addStepLog("Environment url is: " + envUrl);
		driver.get(envUrl);
		try {
			emailCapture.click();
		} catch (Exception e) {
		}
		try {
			startShopping.click();
		} catch (Exception e) {
		}

	}

	public long pageLoadTime(String url) {
		long start = System.currentTimeMillis() / 1000;
		driver.navigate().to(url);
		long finish = System.currentTimeMillis() / 1000;
		totalTime = finish - start;
		return totalTime;
	}

	public int responseCode(String responseURL) {
		try {
			URL url;
			url = new URL(responseURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			code = connection.getResponseCode();
		} catch (Exception e) {
		}
		return code;
	}

	public int imageResponse() throws Exception {
		int code = 0;
		try {
			WebElement view = driver.findElement(By.xpath("(//div/ul/li[4]/span/a[contains(text(),'View')])[1]"));
			if (view.isDisplayed())
				view.click();
		} catch (Exception e) {
		}
		List<WebElement> imgs = driver.findElements(By.xpath("//img[contains(@src,'https')]"));
		list = new ArrayList<String>();
		for (WebElement img : imgs) {
			list.add(img.getAttribute("src"));
		}
		System.out.println("Total no.of images:  " + list.size());
		for (int j = 0; j < list.size(); j++) {
			URL url;
			try {
				url = new URL(list.get(j));
				driver.navigate().to(url);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				code = connection.getResponseCode();
				// Reporter.addStepLog("Image URL: " + list.get(j) + "," + "Response code for
				// the Image URL: " + code);
				boolean brokenImge = testImageComparison(list.get(j));
				if (brokenImge) {
					failedUrl = list.get(j);
					Reporter.addStepLog("Broken image Url::::: " + failedUrl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return code;
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

	public Row getRowFromExcel(int rowNumber) {
		Row row = sheet1.getRow(rowNumber);
		return row;
	}

	public int getColumnNumberFromExcel(int rowNumber, String searchText) throws Exception {
		int columnNumber = -1;
		Row row = getRowFromExcel(rowNumber);
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String cellValue = (String) getCellValueFromExcel(cell);
			if (cellValue.equalsIgnoreCase(searchText)) {
				columnNumber = cell.getColumnIndex();
				break;
			}
		}
		return columnNumber;
	}

	@SuppressWarnings("deprecation")
	public Object getCellValueFromExcel(Cell cell) {

		Object cellValue = "";

		switch (cell.getCellType()) {
		case 0:
			cellValue = cell.getNumericCellValue();
			break;
		case 1:
			cellValue = cell.getStringCellValue();
			break;
		case 4:
			cellValue = cell.getBooleanCellValue();
			break;
		}

		return cellValue;
	}

	public void readAndWriteResultsIntoExcel(String sheetName) throws Exception {
		String jcrew_filePath = "ContentTestingSheet/Content_testing_template_Jcrew.xlsx";
		String factory_filePath = "ContentTestingSheet/Content_testing_template_Factory.xlsx";
		String madewell_filePath = "ContentTestingSheet/Content_testing_template_Madewell.xlsx";
		File f = new File(System.getProperty("user.dir") + "\\properties\\contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream("properties/contextchooser.properties");
		prop.load(inputFile);
		if (prop.getProperty("brand").equalsIgnoreCase("jcrew")) {
			filePath = jcrew_filePath;
		}
		if (prop.getProperty("brand").equalsIgnoreCase("factory")) {
			filePath = factory_filePath;
		}
		if (prop.getProperty("brand").equalsIgnoreCase("madewell")) {
			filePath = madewell_filePath;
		}
		File src = new File(filePath);
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		sheet1 = wb.getSheet(sheetName);
		int i = 0;
		String getUrls = prop.getProperty("websiteCrolling");
		String homePageUrl = prop.getProperty("homePageUrl");
		String excelReading = prop.getProperty("excelReading");
		if (excelReading.equalsIgnoreCase("true")) {
			int rowCount = sheet1.getLastRowNum();
			System.out.println("::::::::::::::::::"+rowCount);
			for (i = 0; i <= rowCount; i++) {
				int columnNumURL = getColumnNumberFromExcel(0, colUrl);
				int columnNumPageLoadTime = getColumnNumberFromExcel(0, colPageLoadTime);
				int columnNumUrlStatus = getColumnNumberFromExcel(0, colUrlStatus);
				int columnNumImageStatus = getColumnNumberFromExcel(0, colImageStatus);
				int columnNumFailedUrl = getColumnNumberFromExcel(0, colFailedUrl);
				if (sheet1.getRow(i).getCell(columnNumURL).getStringCellValue().contains("https:")) {
					dataValue = sheet1.getRow(i).getCell(columnNumURL).getStringCellValue();
					System.out.println("===================" + dataValue);
					urlStatus = responseCode(dataValue);
					totalTime = pageLoadTime(dataValue);
					sheet1.getRow(i).getCell(columnNumPageLoadTime).setCellValue(totalTime);
					sheet1.getRow(i).getCell(columnNumUrlStatus).setCellValue(urlStatus);
					Reporter.addStepLog("URL is: " + dataValue + "," + "Response code is: " + urlStatus + ","
							+ "Total time for page load: " + totalTime);
					String imageReading = prop.getProperty("imageReading");
					if (imageReading.equals("true")) {
						imageURLStatus = imageResponse();
						sheet1.getRow(i).getCell(columnNumImageStatus).setCellValue(imageURLStatus);
					}
				}
				if (failedUrl != null) {
					String failedImageUrl = sheet1.getRow(i).getCell(columnNumFailedUrl).getStringCellValue();
					sheet1.getRow(i).getCell(columnNumFailedUrl).setCellValue(failedImageUrl + "\n" + failedUrl);
					Reporter.addStepLog("Broken image URL is: " + failedImageUrl);
				}
				FileOutputStream fout = new FileOutputStream(src);
				wb.write(fout);
			}
		}
		wb.close();
	}

	public boolean testImageComparison(String webUrl) throws Exception {

		File fileInput = new File("ImageReading/Image.png");
		URL url = new URL(webUrl);
		BufferedImage bufileInput = ImageIO.read(fileInput);
		DataBuffer dafileInput = bufileInput.getData().getDataBuffer();
		int sizefileInput = dafileInput.getSize();
		BufferedImage bufileOutPut = ImageIO.read(url);
		int sizefileOutPut=0;
		DataBuffer dafileOutPut=null;
		try {
		dafileOutPut = bufileOutPut.getData().getDataBuffer();
		sizefileOutPut = dafileOutPut.getSize();
		}catch (Exception e) {
			
		}
		Boolean matchFlag = true;
		if (sizefileInput == sizefileOutPut) {
			for (int j = 0; j < sizefileInput; j++) {
				if (dafileInput.getElem(j) != dafileOutPut.getElem(j)) {
					matchFlag = false;
					break;
				}
			}
		} else
			matchFlag = false;
		return matchFlag;
	}

}
